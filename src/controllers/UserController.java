package controllers;

import dao.UserDAO;
import models.UserModel;
import util.LRUCache;
import java.util.List;

public class UserController {
    private UserDAO userDAO = new UserDAO();
    private UserModel loggedInUser = null; // Tracks the currently logged-in user
    
    // Cache for user lists (admin operations)
    private final LRUCache<Integer, List<UserModel>> userListCache = new LRUCache<>(10);
    // Cache for user role changes
    private final LRUCache<Integer, String> userRoleCache = new LRUCache<>(100);

    // Method to register a new user with timing
    public void registerUser(String username, String password, String role) {
        long startTime = System.nanoTime();
        
        if (role == null || role.isEmpty()) {
            role = "student"; // Default role
        }
        UserModel user = new UserModel(0, username, password, role);
        if (userDAO.registerUser(user)) {
            // Invalidate user list cache
            userListCache.remove(0);
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Failed to register user.");
        }
        
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;
        System.out.printf("Operation completed in %.3f ms%n", duration);
    }

    // Method to get a user by ID with caching and timing
    public void getUserById(int id) {
        long startTime = System.nanoTime();
        
        // Try to get from role cache first
        String role = userRoleCache.get(id);
        UserModel user;
        
        if (role != null) {
            System.out.println("Role found in cache...");
            user = userDAO.getUserById(id);
        } else {
            System.out.println("Role not in cache - fetching from database...");
            user = userDAO.getUserById(id);
            if (user != null) {
                userRoleCache.put(id, user.getRole());
            }
        }
        
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;
        
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("User not found!");
        }
        System.out.printf("Operation completed in %.3f ms%n", duration);
    }

    // Method to list all users with caching and timing
    public void listAllUsers() {
        long startTime = System.nanoTime();
        
        // Try to get from cache first
        List<UserModel> users = userListCache.get(0); // Using 0 as key for all users
        if (users == null) {
            System.out.println("Cache miss - fetching from database...");
            users = userDAO.getAllUsers();
            if (!users.isEmpty()) {
                userListCache.put(0, users);
            }
        } else {
            System.out.println("Cache hit - using cached data...");
        }
        
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0; // Convert to milliseconds
        
        if (users.isEmpty()) {
            System.out.println("No users found!");
        } else {
            users.forEach(System.out::println);
        }
        System.out.printf("Operation completed in %.3f ms%n", duration);
    }
    public List<UserModel> listAllUsers(int x) {
        return userDAO.getAllUsers(); // Return the list of users from the DAO
    }

    // Method to update a user's role with caching
    public void updateUserRole(int id, String newRole) {
        userDAO.updateUserRole(id, newRole);
        // Update role cache
        userRoleCache.put(id, newRole);
        // Invalidate user list cache
        userListCache.remove(0);
        System.out.println("User role updated successfully!");
    }

    // Method to delete a user with timing
    public void deleteUser(int id) {
        long startTime = System.nanoTime();
        
        userDAO.deleteUser(id);
        // Invalidate caches
        userListCache.remove(0);
        userRoleCache.remove(id);
        System.out.println("User deleted successfully!");
        
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;
        System.out.printf("Operation completed in %.3f ms%n", duration);
    }

    // Method to log in a user with timing
    public boolean login(String username, String password) {
        long startTime = System.nanoTime();
        
        UserModel user = userDAO.login(username, password);
        if (user != null) {
            loggedInUser = user;
            // Cache the user's role
            userRoleCache.put(user.getId(), user.getRole());
            System.out.println("Login successful! Welcome, " + user.getUsername());
        } else {
            System.out.println("Invalid username or password.");
        }
        
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;
        System.out.printf("Operation completed in %.3f ms%n", duration);
        return user != null;
    }

    // Method to log out the current user
    public void logout() {
        if (loggedInUser != null) {
            System.out.println("Goodbye, " + loggedInUser.getUsername() + "!");
            loggedInUser = null; // Clear the logged-in user
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    // Method to check if a user is logged in
    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    // Method to get the currently logged-in user
    public UserModel getLoggedInUser() {
        return loggedInUser;
    }

    // Method to change a user's role with caching and timing
    public void changeUserRole(int userId, String newRole) {
        long startTime = System.nanoTime();
        
        if (loggedInUser != null && loggedInUser.getRole().equals("admin")) {
            userDAO.updateUserRole(userId, newRole);
            // Update role cache
            userRoleCache.put(userId, newRole);
            // Invalidate user list cache
            userListCache.remove(0);
            System.out.println("User role updated successfully!");
        } else {
            System.out.println("Access denied. Only admins can change user roles.");
        }
        
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;
        System.out.printf("Operation completed in %.3f ms%n", duration);
    }
}
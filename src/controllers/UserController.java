package controllers;

import dao.UserDAO;
import models.UserModel;

import java.util.List;

public class UserController {
    private UserDAO userDAO = new UserDAO();
    private UserModel loggedInUser = null; // Tracks the currently logged-in user

    // Method to register a new user
    public void registerUser(String username, String password, String role) {
        if (role == null || role.isEmpty()) {
            role = "student"; // Default role
        }
        UserModel user = new UserModel(0, username, password, role);
        if (userDAO.registerUser(user)) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Failed to register user.");
        }
    }

    // Method to get a user by ID
    public void getUserById(int id) {
        UserModel user = userDAO.getUserById(id);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("User not found!");
        }
    }

    // Method to list all users
    public void listAllUsers() {
        List<UserModel> users = userDAO.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found!");
        } else {
            users.forEach(System.out::println);
        }
    }
    public List<UserModel> listAllUsers(int x) {
        return userDAO.getAllUsers(); // Return the list of users from the DAO
    }

    // Method to update a user's role
    public void updateUserRole(int id, String newRole) {
        userDAO.updateUserRole(id, newRole);
        System.out.println("User role updated successfully!");
    }

    // Method to delete a user
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
        System.out.println("User deleted successfully!");
    }

    // Method to log in a user
    public boolean login(String username, String password) {
        UserModel user = userDAO.login(username, password);
        if (user != null) {
            loggedInUser = user;
            System.out.println("Login successful! Welcome, " + user.getUsername());
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
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

    // Method to change a user's role (admin only)
    public void changeUserRole(int userId, String newRole) {
        if (loggedInUser != null && loggedInUser.getRole().equals("admin")) {
            userDAO.updateUserRole(userId, newRole);
            System.out.println("User role updated successfully!");
        } else {
            System.out.println("Access denied. Only admins can change user roles.");
        }
    }
}
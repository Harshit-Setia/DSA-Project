import controllers.QuizController;
import controllers.QuestionController;
import controllers.UserController;

import java.util.Scanner;

public class CliApp {
    public static void main(String[] args) {
        UserController userController = new UserController();
        QuizController quizController = new QuizController();
        QuestionController questionController = new QuestionController();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Quiz App ===");
            System.out.println("1. Register");

            // Dynamically display Login or Logout based on login status
            if (userController.isLoggedIn()) {
                System.out.println("2. Logout");
            } else {
                System.out.println("2. Login");
            }

            // Show teacher-specific options
            if (userController.isLoggedIn() && userController.getLoggedInUser().getRole().equals("teacher")) {
                System.out.println("3. Create Quiz");
                System.out.println("4. List All Quizzes");
                System.out.println("5. Add Question to Quiz");
                System.out.println("6. List Questions for a Quiz");
                System.out.println("7. Fetch Quiz Results");
            }

            // Show student-specific options
            if (userController.isLoggedIn() && userController.getLoggedInUser().getRole().equals("student")) {
                System.out.println("3. Attempt Quiz");
            }

            // Show admin-specific options only if the logged-in user is an admin
            if (userController.isLoggedIn() && userController.getLoggedInUser().getRole().equals("admin")) {
                System.out.println("8. List All Users (Admin Only)");
                System.out.println("9. Change User Role (Admin Only)");
            }

            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Register
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter role (leave blank for default 'student'): ");
                    String role = scanner.nextLine();
                    userController.registerUser(username, password, role);
                    break;

                case 2: // Login or Logout
                    if (userController.isLoggedIn()) {
                        userController.logout();
                    } else {
                        System.out.print("Enter username: ");
                        username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        password = scanner.nextLine();
                        userController.login(username, password);
                    }
                    break;

                case 3: // Create Quiz or Attempt Quiz
                    if (userController.isLoggedIn() && userController.getLoggedInUser().getRole().equals("teacher")) {
                        System.out.print("Enter quiz name: ");
                        String quizName = scanner.nextLine();
                        System.out.print("Enter quiz description: ");
                        String quizDescription = scanner.nextLine();
                        quizController.createQuiz(userController.getLoggedInUser().getId(), quizName, quizDescription);
                    } else if (userController.isLoggedIn() && userController.getLoggedInUser().getRole().equals("student")) {
                        System.out.print("Enter quiz ID: ");
                        int quizId = scanner.nextInt();
                        scanner.nextLine(); // Consume the leftover newline character
                        questionController.attemptQuiz(userController.getLoggedInUser().getId(), quizId);
                    } else {
                        System.out.println("Access denied. Only teachers or students can perform this action.");
                    }
                    break;

                case 4: // List All Quizzes
                    quizController.listAllQuizzes();
                    break;

                case 5: // Add Question to Quiz
                    System.out.print("Enter quiz ID: ");
                    int quizId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter question text: ");
                    String questionText = scanner.nextLine();
                    System.out.print("Enter option A: ");
                    String optionA = scanner.nextLine();
                    System.out.print("Enter option B: ");
                    String optionB = scanner.nextLine();
                    System.out.print("Enter option C: ");
                    String optionC = scanner.nextLine();
                    System.out.print("Enter option D: ");
                    String optionD = scanner.nextLine();
                    System.out.print("Enter correct answer (A/B/C/D): ");
                    String correctAnswer = scanner.nextLine();
                    System.out.print("Enter points for the question: ");
                    int points = scanner.nextInt();
                    questionController.addQuestion(quizId, questionText, optionA, optionB, optionC, optionD, correctAnswer, points);
                    break;

                case 6: // List Questions for a Quiz
                    System.out.print("Enter quiz ID: ");
                    quizId = scanner.nextInt();
                    questionController.listQuestionsByQuizId(quizId);
                    break;

                case 7: // Fetch Quiz Results
                    System.out.print("Enter quiz ID: ");
                    quizId = scanner.nextInt();
                    questionController.fetchQuizResults(quizId);
                    break;

                case 8: // List All Users (Admin Only)
                    if (userController.isLoggedIn() && userController.getLoggedInUser().getRole().equals("admin")) {
                        userController.listAllUsers();
                    } else {
                        System.out.println("Access denied. Only admins can perform this action.");
                    }
                    break;

                case 9: // Change User Role (Admin Only)
                    if (userController.isLoggedIn() && userController.getLoggedInUser().getRole().equals("admin")) {
                        System.out.print("Enter user ID to change role: ");
                        int userId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter new role (teacher/student): ");
                        String newRole = scanner.nextLine();
                        userController.changeUserRole(userId, newRole);
                    } else {
                        System.out.println("Access denied. Only admins can perform this action.");
                    }
                    break;

                case 10: // Exit
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

import controllers.UserController;
import controllers.QuizController;
import controllers.QuestionController;
import models.QuestionModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GuiApp {
    private JFrame frame;
    private JPanel mainPanel;
    private UserController userController;
    private QuizController quizController;
    private QuestionController questionController;

    public GuiApp() {
        userController = new UserController();
        quizController = new QuizController();
        questionController = new QuestionController();

        // Initialize the main frame
        frame = new JFrame("Quiz App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Initialize the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.add(mainPanel);

        // Show the main menu
        showMainMenu();

        frame.setVisible(true);
    }

    private void showMainMenu() {
        mainPanel.removeAll();

        JLabel welcomeLabel = new JLabel("Welcome to Quiz App");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(welcomeLabel);

        JButton registerButton = new JButton("Register");
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.addActionListener(e -> showRegisterScreen());
        mainPanel.add(registerButton);

        JButton loginLogoutButton = new JButton(userController.isLoggedIn() ? "Logout" : "Login");
        loginLogoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLogoutButton.addActionListener(e -> {
            if (userController.isLoggedIn()) {
                userController.logout();
                JOptionPane.showMessageDialog(frame, "Logged out successfully!");
                showMainMenu();
            } else {
                showLoginScreen();
            }
        });
        mainPanel.add(loginLogoutButton);

        if (userController.isLoggedIn()) {
            String role = userController.getLoggedInUser().getRole();
            if (role.equals("teacher")) {
                JButton createQuizButton = new JButton("Create Quiz");
                createQuizButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                createQuizButton.addActionListener(e -> showCreateQuizScreen());
                mainPanel.add(createQuizButton);

                JButton listQuizzesButton = new JButton("List Quizzes");
                listQuizzesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                listQuizzesButton.addActionListener(e -> listQuizzes());
                mainPanel.add(listQuizzesButton);

                JButton addQuestionButton = new JButton("Add Question to Quiz");
                addQuestionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                addQuestionButton.addActionListener(e -> showAddQuestionScreen());
                mainPanel.add(addQuestionButton);
            } else if (role.equals("student")) {
                JButton attemptQuizButton = new JButton("Attempt Quiz");
                attemptQuizButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                attemptQuizButton.addActionListener(e -> showAttemptQuizScreen());
                mainPanel.add(attemptQuizButton);
            } else if (role.equals("admin")) {
                JButton listUsersButton = new JButton("List All Users");
                listUsersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                listUsersButton.addActionListener(e -> listAllUsers());
                mainPanel.add(listUsersButton);

                JButton changeRoleButton = new JButton("Change User Role");
                changeRoleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                changeRoleButton.addActionListener(e -> showChangeRoleScreen());
                mainPanel.add(changeRoleButton);
            }
        }

        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));
        mainPanel.add(exitButton);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showRegisterScreen() {
        mainPanel.removeAll();

        JLabel registerLabel = new JLabel("Register");
        registerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(registerLabel);

        JTextField usernameField = new JTextField(20);
        usernameField.setMaximumSize(usernameField.getPreferredSize());
        mainPanel.add(new JLabel("Username:"));
        mainPanel.add(usernameField);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(passwordField.getPreferredSize());
        mainPanel.add(new JLabel("Password:"));
        mainPanel.add(passwordField);

        JTextField roleField = new JTextField(20);
        roleField.setMaximumSize(roleField.getPreferredSize());
        mainPanel.add(new JLabel("Role (student/teacher/admin):"));
        mainPanel.add(roleField);

        JButton registerButton = new JButton("Register");
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = roleField.getText();
            userController.registerUser(username, password, role);
            JOptionPane.showMessageDialog(frame, "Registration successful!");
            showMainMenu();
        });
        mainPanel.add(registerButton);

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showLoginScreen() {
        mainPanel.removeAll();

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(loginLabel);

        JTextField usernameField = new JTextField(20);
        usernameField.setMaximumSize(usernameField.getPreferredSize());
        mainPanel.add(new JLabel("Username:"));
        mainPanel.add(usernameField);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(passwordField.getPreferredSize());
        mainPanel.add(new JLabel("Password:"));
        mainPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (userController.login(username, password)) {
                JOptionPane.showMessageDialog(frame, "Login successful!");
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.");
            }
        });
        mainPanel.add(loginButton);

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showCreateQuizScreen() {
        mainPanel.removeAll();

        JLabel createQuizLabel = new JLabel("Create Quiz");
        createQuizLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(createQuizLabel);

        JTextField quizNameField = new JTextField(20);
        quizNameField.setMaximumSize(quizNameField.getPreferredSize());
        mainPanel.add(new JLabel("Quiz Name:"));
        mainPanel.add(quizNameField);

        JTextField quizDescriptionField = new JTextField(20);
        quizDescriptionField.setMaximumSize(quizDescriptionField.getPreferredSize());
        mainPanel.add(new JLabel("Quiz Description:"));
        mainPanel.add(quizDescriptionField);

        JButton createButton = new JButton("Create");
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.addActionListener(e -> {
            String quizName = quizNameField.getText();
            String quizDescription = quizDescriptionField.getText();
            quizController.createQuiz(userController.getLoggedInUser().getId(), quizName, quizDescription);
            JOptionPane.showMessageDialog(frame, "Quiz created successfully!");
            showMainMenu();
        });
        mainPanel.add(createButton);

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void listQuizzes() {
        mainPanel.removeAll();

        JLabel listQuizzesLabel = new JLabel("List of Quizzes");
        listQuizzesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(listQuizzesLabel);

        JTextArea quizzesArea = new JTextArea(10, 40);
        quizzesArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(quizzesArea);
        mainPanel.add(scrollPane);

        quizController.listAllQuizzes(1).forEach(quiz -> quizzesArea.append(quiz.toString() + "\n"));

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showAttemptQuizScreen() {
        mainPanel.removeAll();

        JLabel attemptQuizLabel = new JLabel("Attempt Quiz");
        attemptQuizLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(attemptQuizLabel);

        JTextField quizIdField = new JTextField(20);
        quizIdField.setMaximumSize(quizIdField.getPreferredSize());
        mainPanel.add(new JLabel("Enter Quiz ID:"));
        mainPanel.add(quizIdField);

        JButton attemptButton = new JButton("Attempt");
        attemptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        attemptButton.addActionListener(e -> {
            int quizId = Integer.parseInt(quizIdField.getText());
            List<QuestionModel> questions = questionController.getQuestionsByQuizId(quizId);

            if (questions.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No questions found for this quiz!");
                showMainMenu();
            } else {
                startQuiz(quizId, questions);
            }
        });
        mainPanel.add(attemptButton);

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void startQuiz(int quizId, List<QuestionModel> questions) {
        mainPanel.removeAll();

        JLabel questionLabel = new JLabel();
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(questionLabel);

        ButtonGroup optionsGroup = new ButtonGroup();
        JRadioButton optionA = new JRadioButton();
        JRadioButton optionB = new JRadioButton();
        JRadioButton optionC = new JRadioButton();
        JRadioButton optionD = new JRadioButton();
        optionsGroup.add(optionA);
        optionsGroup.add(optionB);
        optionsGroup.add(optionC);
        optionsGroup.add(optionD);
        mainPanel.add(optionA);
        mainPanel.add(optionB);
        mainPanel.add(optionC);
        mainPanel.add(optionD);

        JButton nextButton = new JButton("Next");
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(nextButton);

        JButton finishButton = new JButton("Finish");
        finishButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        finishButton.setVisible(false);
        mainPanel.add(finishButton);

        final int[] currentQuestionIndex = {0};
        final int[] totalScore = {0};

        displayQuestion(questions, currentQuestionIndex[0], questionLabel, optionA, optionB, optionC, optionD);

        nextButton.addActionListener(e -> {
            String selectedAnswer = null;
            if (optionA.isSelected()) selectedAnswer = "A";
            if (optionB.isSelected()) selectedAnswer = "B";
            if (optionC.isSelected()) selectedAnswer = "C";
            if (optionD.isSelected()) selectedAnswer = "D";

            if (selectedAnswer != null && selectedAnswer.equals(questions.get(currentQuestionIndex[0]).getAnswer())) {
                totalScore[0] += questions.get(currentQuestionIndex[0]).getPts();
            }

            currentQuestionIndex[0]++;
            if (currentQuestionIndex[0] < questions.size()) {
                displayQuestion(questions, currentQuestionIndex[0], questionLabel, optionA, optionB, optionC, optionD);
            } else {
                nextButton.setVisible(false);
                finishButton.setVisible(true);
            }
        });

        finishButton.addActionListener(e -> {
            questionController.recordQuizAttempt(userController.getLoggedInUser().getId(), quizId, totalScore[0]);
            JOptionPane.showMessageDialog(frame, "Quiz completed! Your score: " + totalScore[0]);
            showMainMenu();
        });

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void displayQuestion(List<QuestionModel> questions, int index, JLabel questionLabel,
                                 JRadioButton optionA, JRadioButton optionB, JRadioButton optionC, JRadioButton optionD) {
        QuestionModel question = questions.get(index);
        questionLabel.setText("Question " + (index + 1) + ": " + question.getQuestion());
        optionA.setText("A: " + question.getOptionA());
        optionB.setText("B: " + question.getOptionB());
        optionC.setText("C: " + question.getOptionC());
        optionD.setText("D: " + question.getOptionD());
    }

    private void listAllUsers() {
        mainPanel.removeAll();

        JLabel listUsersLabel = new JLabel("List of All Users");
        listUsersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(listUsersLabel);

        JTextArea usersArea = new JTextArea(10, 40);
        usersArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(usersArea);
        mainPanel.add(scrollPane);

        userController.listAllUsers(1).forEach(user -> usersArea.append(user.toString() + "\n"));

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showChangeRoleScreen() {
        mainPanel.removeAll();

        JLabel changeRoleLabel = new JLabel("Change User Role");
        changeRoleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(changeRoleLabel);

        JTextField userIdField = new JTextField(20);
        userIdField.setMaximumSize(userIdField.getPreferredSize());
        mainPanel.add(new JLabel("Enter User ID:"));
        mainPanel.add(userIdField);

        JTextField newRoleField = new JTextField(20);
        newRoleField.setMaximumSize(newRoleField.getPreferredSize());
        mainPanel.add(new JLabel("Enter New Role (teacher/student):"));
        mainPanel.add(newRoleField);

        JButton changeRoleButton = new JButton("Change Role");
        changeRoleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        changeRoleButton.addActionListener(e -> {
            int userId = Integer.parseInt(userIdField.getText());
            String newRole = newRoleField.getText();
            userController.changeUserRole(userId, newRole);
            JOptionPane.showMessageDialog(frame, "User role updated successfully!");
            showMainMenu();
        });
        mainPanel.add(changeRoleButton);

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showAddQuestionScreen() {
        mainPanel.removeAll();

        JLabel addQuestionLabel = new JLabel("Add Question to Quiz");
        addQuestionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(addQuestionLabel);

        JTextField quizIdField = new JTextField(20);
        quizIdField.setMaximumSize(quizIdField.getPreferredSize());
        mainPanel.add(new JLabel("Enter Quiz ID:"));
        mainPanel.add(quizIdField);

        JTextField questionTextField = new JTextField(20);
        questionTextField.setMaximumSize(questionTextField.getPreferredSize());
        mainPanel.add(new JLabel("Enter Question Text:"));
        mainPanel.add(questionTextField);

        JTextField optionAField = new JTextField(20);
        optionAField.setMaximumSize(optionAField.getPreferredSize());
        mainPanel.add(new JLabel("Option A:"));
        mainPanel.add(optionAField);

        JTextField optionBField = new JTextField(20);
        optionBField.setMaximumSize(optionBField.getPreferredSize());
        mainPanel.add(new JLabel("Option B:"));
        mainPanel.add(optionBField);

        JTextField optionCField = new JTextField(20);
        optionCField.setMaximumSize(optionCField.getPreferredSize());
        mainPanel.add(new JLabel("Option C:"));
        mainPanel.add(optionCField);

        JTextField optionDField = new JTextField(20);
        optionDField.setMaximumSize(optionDField.getPreferredSize());
        mainPanel.add(new JLabel("Option D:"));
        mainPanel.add(optionDField);

        JTextField correctAnswerField = new JTextField(20);
        correctAnswerField.setMaximumSize(correctAnswerField.getPreferredSize());
        mainPanel.add(new JLabel("Correct Answer (A/B/C/D):"));
        mainPanel.add(correctAnswerField);

        JTextField pointsField = new JTextField(20);
        pointsField.setMaximumSize(pointsField.getPreferredSize());
        mainPanel.add(new JLabel("Points:"));
        mainPanel.add(pointsField);

        JButton addButton = new JButton("Add Question");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(e -> {
            int quizId = Integer.parseInt(quizIdField.getText());
            String questionText = questionTextField.getText();
            String optionA = optionAField.getText();
            String optionB = optionBField.getText();
            String optionC = optionCField.getText();
            String optionD = optionDField.getText();
            String correctAnswer = correctAnswerField.getText();
            int points = Integer.parseInt(pointsField.getText());

            questionController.addQuestion(quizId, questionText, optionA, optionB, optionC, optionD, correctAnswer, points);
            JOptionPane.showMessageDialog(frame, "Question added successfully!");
            showMainMenu();
        });
        mainPanel.add(addButton);

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        new GuiApp();
    }
}

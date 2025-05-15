import controllers.UserController;
import controllers.QuizController;
import controllers.QuestionController;
import models.QuestionModel;
import com.formdev.flatlaf.FlatLightLaf;
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
        // Set FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        userController = new UserController();
        quizController = new QuizController();
        questionController = new QuestionController();

        // Initialize the main frame
        frame = new JFrame("Quiz App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center the frame

        // Initialize the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        frame.add(mainPanel);

        // Show the main menu
        showMainMenu();

        frame.setVisible(true);
    }

    private void showMainMenu() {
        mainPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        JLabel welcomeLabel = new JLabel("Welcome to Quiz App");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(welcomeLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 1;
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> showRegisterScreen());
        mainPanel.add(registerButton, gbc);

        gbc.gridy = 2;
        JButton loginLogoutButton = new JButton(userController.isLoggedIn() ? "Logout" : "Login");
        loginLogoutButton.addActionListener(e -> {
            if (userController.isLoggedIn()) {
                userController.logout();
                JOptionPane.showMessageDialog(frame, "Logged out successfully!");
                showMainMenu();
            } else {
                showLoginScreen();
            }
        });
        mainPanel.add(loginLogoutButton, gbc);

        if (userController.isLoggedIn()) {
            String role = userController.getLoggedInUser().getRole();
            if (role.equals("teacher")) {
                gbc.gridy = 3;
                JButton createQuizButton = new JButton("Create Quiz");
                createQuizButton.addActionListener(e -> showCreateQuizScreen());
                mainPanel.add(createQuizButton, gbc);

                gbc.gridy = 4;
                JButton listQuizzesButton = new JButton("List Quizzes");
                listQuizzesButton.addActionListener(e -> listQuizzes());
                mainPanel.add(listQuizzesButton, gbc);

                gbc.gridy = 5;
                JButton addQuestionButton = new JButton("Add Question to Quiz");
                addQuestionButton.addActionListener(e -> showAddQuestionScreen());
                mainPanel.add(addQuestionButton, gbc);

            } else if (role.equals("student")) {
                gbc.gridy = 3;
                JButton attemptQuizButton = new JButton("Attempt Quiz");
                attemptQuizButton.addActionListener(e -> showAttemptQuizScreen());
                mainPanel.add(attemptQuizButton, gbc);

            } else if (role.equals("admin")) {
                gbc.gridy = 3;
                JButton listUsersButton = new JButton("List All Users");
                listUsersButton.addActionListener(e -> listAllUsers());
                mainPanel.add(listUsersButton, gbc);

                gbc.gridy = 4;
                JButton changeRoleButton = new JButton("Change User Role");
                changeRoleButton.addActionListener(e -> showChangeRoleScreen());
                mainPanel.add(changeRoleButton, gbc);
            }
        }

        gbc.gridy++;
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        mainPanel.add(exitButton, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showRegisterScreen() {
        mainPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel registerLabel = new JLabel("Register");
        registerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(registerLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy++;
        mainPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(20);
        mainPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Role (student/teacher/admin):"), gbc);
        gbc.gridx = 1;
        JTextField roleField = new JTextField(20);
        mainPanel.add(roleField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = roleField.getText();
            userController.registerUser(username, password, role);
            JOptionPane.showMessageDialog(frame, "Registration successful!");
            showMainMenu();
        });
        mainPanel.add(registerButton, gbc);

        gbc.gridy++;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showLoginScreen() {
        mainPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy++;
        mainPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(20);
        mainPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
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
        mainPanel.add(loginButton, gbc);

        gbc.gridy++;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showCreateQuizScreen() {
        mainPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel createQuizLabel = new JLabel("Create Quiz");
        createQuizLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(createQuizLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy++;
        mainPanel.add(new JLabel("Quiz Name:"), gbc);
        gbc.gridx = 1;
        JTextField quizNameField = new JTextField(20);
        mainPanel.add(quizNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Quiz Description:"), gbc);
        gbc.gridx = 1;
        JTextField quizDescriptionField = new JTextField(20);
        mainPanel.add(quizDescriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> {
            String quizName = quizNameField.getText();
            String quizDescription = quizDescriptionField.getText();
            quizController.createQuiz(userController.getLoggedInUser().getId(), quizName, quizDescription);
            JOptionPane.showMessageDialog(frame, "Quiz created successfully!");
            showMainMenu();
        });
        mainPanel.add(createButton, gbc);

        gbc.gridy++;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void listQuizzes() {
        mainPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;

        JLabel listQuizzesLabel = new JLabel("List of Quizzes");
        listQuizzesLabel.setFont(new Font("Arial", Font.BOLD, 20));
        listQuizzesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        mainPanel.add(listQuizzesLabel, gbc);

        JTextArea quizzesArea = new JTextArea();
        quizzesArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(quizzesArea);
        gbc.gridy = 1;
        gbc.weighty = 0.9;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane, gbc);

        quizController.listAllQuizzes(1).forEach(quiz -> quizzesArea.append(quiz.toString() + "\n"));

        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showAttemptQuizScreen() {
        mainPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel attemptQuizLabel = new JLabel("Attempt Quiz");
        attemptQuizLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(attemptQuizLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy++;
        mainPanel.add(new JLabel("Enter Quiz ID:"), gbc);
        gbc.gridx = 1;
        JTextField quizIdField = new JTextField(20);
        mainPanel.add(quizIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton attemptButton = new JButton("Attempt");
        attemptButton.addActionListener(e -> {
            try {
                int quizId = Integer.parseInt(quizIdField.getText());
                List<QuestionModel> questions = questionController.getQuestionsByQuizId(quizId);

                if (questions.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No questions found for this quiz!");
                    showMainMenu();
                } else {
                    startQuiz(quizId, questions);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid quiz ID.");
            }
        });
        mainPanel.add(attemptButton, gbc);

        gbc.gridy++;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void startQuiz(int quizId, List<QuestionModel> questions) {
        mainPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(questionLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy++;
        JRadioButton optionA = new JRadioButton();
        mainPanel.add(optionA, gbc);

        gbc.gridy++;
        JRadioButton optionB = new JRadioButton();
        mainPanel.add(optionB, gbc);

        gbc.gridy++;
        JRadioButton optionC = new JRadioButton();
        mainPanel.add(optionC, gbc);

        gbc.gridy++;
        JRadioButton optionD = new JRadioButton();
        mainPanel.add(optionD, gbc);

        ButtonGroup optionsGroup = new ButtonGroup();
        optionsGroup.add(optionA);
        optionsGroup.add(optionB);
        optionsGroup.add(optionC);
        optionsGroup.add(optionD);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton nextButton = new JButton("Next");
        mainPanel.add(nextButton, gbc);

        gbc.gridx = 1;
        JButton finishButton = new JButton("Finish");
        finishButton.setVisible(false);
        mainPanel.add(finishButton, gbc);

        final int[] currentQuestionIndex = {0};
        final int[] totalScore = {0};

        displayQuestion(questions, currentQuestionIndex[0], questionLabel, optionA, optionB, optionC, optionD);

        nextButton.addActionListener(e -> {
            String selectedAnswer = null;
            if (optionA.isSelected()) selectedAnswer = "A";
            if (optionB.isSelected()) selectedAnswer = "B";
            if (optionC.isSelected()) selectedAnswer = "C";
            if (optionD.isSelected()) selectedAnswer = "D";

            if (selectedAnswer != null && selectedAnswer.equalsIgnoreCase(questions.get(currentQuestionIndex[0]).getAnswer())) {
                totalScore[0] += questions.get(currentQuestionIndex[0]).getPts();
            }

            currentQuestionIndex[0]++;
            if (currentQuestionIndex[0] < questions.size()) {
                displayQuestion(questions, currentQuestionIndex[0], questionLabel, optionA, optionB, optionC, optionD);
            } else {
                nextButton.setVisible(false);
                finishButton.setVisible(true);
            }
            optionsGroup.clearSelection();
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;

        JLabel listUsersLabel = new JLabel("List of All Users");
        listUsersLabel.setFont(new Font("Arial", Font.BOLD, 20));
        listUsersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        mainPanel.add(listUsersLabel, gbc);

        JTextArea usersArea = new JTextArea();
        usersArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(usersArea);
        gbc.gridy = 1;
        gbc.weighty = 0.9;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane, gbc);

        userController.listAllUsers(1).forEach(user -> usersArea.append(user.toString() + "\n"));

        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showChangeRoleScreen() {
        mainPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel changeRoleLabel = new JLabel("Change User Role");
        changeRoleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(changeRoleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy++;
        mainPanel.add(new JLabel("Enter User ID:"), gbc);
        gbc.gridx = 1;
        JTextField userIdField = new JTextField(20);
        mainPanel.add(userIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Enter New Role (teacher/student):"), gbc);
        gbc.gridx = 1;
        JTextField newRoleField = new JTextField(20);
        mainPanel.add(newRoleField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton changeRoleButton = new JButton("Change Role");
        changeRoleButton.addActionListener(e -> {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                String newRole = newRoleField.getText();
                userController.changeUserRole(userId, newRole);
                JOptionPane.showMessageDialog(frame, "User role updated successfully!");
                showMainMenu();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid User ID.");
            }
        });
        mainPanel.add(changeRoleButton, gbc);

        gbc.gridy++;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showAddQuestionScreen() {
        mainPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel addQuestionLabel = new JLabel("Add Question to Quiz");
        addQuestionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(addQuestionLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy++;
        mainPanel.add(new JLabel("Enter Quiz ID:"), gbc);
        gbc.gridx = 1;
        JTextField quizIdField = new JTextField(20);
        mainPanel.add(quizIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Enter Question Text:"), gbc);
        gbc.gridx = 1;
        JTextField questionTextField = new JTextField(20);
        mainPanel.add(questionTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Option A:"), gbc);
        gbc.gridx = 1;
        JTextField optionAField = new JTextField(20);
        mainPanel.add(optionAField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Option B:"), gbc);
        gbc.gridx = 1;
        JTextField optionBField = new JTextField(20);
        mainPanel.add(optionBField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Option C:"), gbc);
        gbc.gridx = 1;
        JTextField optionCField = new JTextField(20);
        mainPanel.add(optionCField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Option D:"), gbc);
        gbc.gridx = 1;
        JTextField optionDField = new JTextField(20);
        mainPanel.add(optionDField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Correct Answer (A/B/C/D):"), gbc);
        gbc.gridx = 1;
        JTextField correctAnswerField = new JTextField(20);
        mainPanel.add(correctAnswerField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Points:"), gbc);
        gbc.gridx = 1;
        JTextField pointsField = new JTextField(20);
        mainPanel.add(pointsField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton addButton = new JButton("Add Question");
        addButton.addActionListener(e -> {
            try {
                int quizId = Integer.parseInt(quizIdField.getText());
                String questionText = questionTextField.getText();
                String optionA = optionAField.getText();
                String optionB = optionBField.getText();
                String optionC = optionCField.getText();
                String optionD = optionDField.getText();
                String correctAnswer = correctAnswerField.getText().toUpperCase();
                int points = Integer.parseInt(pointsField.getText());

                questionController.addQuestion(quizId, questionText, optionA, optionB, optionC, optionD, correctAnswer, points);
                JOptionPane.showMessageDialog(frame, "Question added successfully!");
                showMainMenu();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numeric values for Quiz ID and Points.");
            }
        });
        mainPanel.add(addButton, gbc);

        gbc.gridy++;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());
        mainPanel.add(backButton, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuiApp::new);
    }
}


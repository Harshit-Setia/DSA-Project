package dao;

import util.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizAttemptDAO {
    private db database = new db();

    // Method to check if a student has already attempted a quiz
    public boolean hasAttemptedQuiz(int studentId, int quizId) {
        String query = "SELECT COUNT(*) FROM quiz_attempts WHERE student_id = ? AND quiz_id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, studentId);
            statement.setInt(2, quizId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to record a quiz attempt
    public void recordQuizAttempt(int studentId, int quizId, int marksObtained) {
        String query = "INSERT INTO quiz_attempts (student_id, quiz_id, marks_obtained) VALUES (?, ?, ?)";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, studentId);
            statement.setInt(2, quizId);
            statement.setInt(3, marksObtained);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch all quiz attempts for a specific quiz
    public List<String> getQuizAttemptsByQuizId(int quizId) {
        // Inner class to hold attempt data
        class Attempt {
            String username;
            int marks;
            Attempt(String username, int marks) {
                this.username = username;
                this.marks = marks;
            }
        }

        List<Attempt> attemptList = new ArrayList<>();
        List<String> attempts = new ArrayList<>();
        String query = "SELECT u.username, qa.marks_obtained FROM quiz_attempts qa " +
                       "JOIN users u ON qa.student_id = u.id WHERE qa.quiz_id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quizId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int marks = resultSet.getInt("marks_obtained");
                attemptList.add(new Attempt(username, marks));
            }
            // Sort by marks descending
            attemptList.sort((a, b) -> Integer.compare(b.marks, a.marks));
            // Format output
            for (Attempt att : attemptList) {
                attempts.add("Student: " + att.username + ", Marks: " + att.marks);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attempts;
    }
}
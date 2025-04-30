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
        List<String> attempts = new ArrayList<>();
        String query = "SELECT u.username, qa.marks_obtained FROM quiz_attempts qa " +
                       "JOIN users u ON qa.student_id = u.id WHERE qa.quiz_id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quizId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String result = "Student: " + resultSet.getString("username") +
                                ", Marks: " + resultSet.getInt("marks_obtained");
                attempts.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attempts;
    }
}
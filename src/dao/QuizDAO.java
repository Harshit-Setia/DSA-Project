package dao;

import models.QuizModel;
import util.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    private db database = new db(); // Database utility class

    // Method to insert a new quiz
    public void insertQuiz(QuizModel quiz) {
        // Added total_questions with default value 0 to avoid SQL error
        String query = "INSERT INTO quizzes (teacher_id, title, description, total_questions,total_points) VALUES (?, ?, ?, ?,?)";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quiz.getTeacherId());
            statement.setString(2, quiz.getName());
            statement.setString(3, quiz.getDescription());
            statement.setInt(4, 0); // Set total_questions to 0 initially
            statement.setInt(5, 0); // Set total_questions to 0 initially
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all quizzes
    public List<QuizModel> getAllQuizzes() {
        List<QuizModel> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quizzes";
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int quizId = resultSet.getInt("id");
                int totalQuestions = getQuestionCountByQuizId(quizId);
                int totalPoints = getTotalPointsByQuizId(quizId);

                quizzes.add(new QuizModel(
                        quizId,
                        resultSet.getInt("teacher_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        totalQuestions,
                        totalPoints
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    // Method to retrieve a quiz by ID
    public QuizModel getQuizById(int quizId) {
        QuizModel quiz = null;
        String query = "SELECT * FROM quizzes WHERE id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quizId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int totalQuestions = getQuestionCountByQuizId(quizId);
                int totalPoints = getTotalPointsByQuizId(quizId);

                quiz = new QuizModel(
                        quizId,
                        resultSet.getInt("teacher_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        totalQuestions,
                        totalPoints
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quiz;
    }

    // Helper method to get the total number of questions for a quiz
    private int getQuestionCountByQuizId(int quizId) {
        String query = "SELECT COUNT(*) AS question_count FROM questions WHERE quiz_id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quizId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("question_count");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching question count for quiz ID " + quizId);
            e.printStackTrace();
        }
        return 0;
    }

    // Helper method to get the total points for a quiz
    private int getTotalPointsByQuizId(int quizId) {
        String query = "SELECT SUM(points) AS total_points FROM questions WHERE quiz_id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quizId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total_points");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total points for quiz ID " + quizId);
            e.printStackTrace();
        }
        return 0;
    }
}
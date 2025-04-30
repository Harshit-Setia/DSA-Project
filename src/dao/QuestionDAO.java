package dao;

import models.QuestionModel;
import util.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private db database = new db(); // Database utility class

    // Method to insert a new question
    public void insertQuestion(QuestionModel question) {
        String query = "INSERT INTO questions (quiz_id, question_text, option_a, option_b, option_c, option_d, correct_answer, points) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, question.getQuizId());
            statement.setString(2, question.getQuestion());
            statement.setString(3, question.getOptionA());
            statement.setString(4, question.getOptionB());
            statement.setString(5, question.getOptionC());
            statement.setString(6, question.getOptionD());
            statement.setString(7, question.getAnswer());
            statement.setInt(8, question.getPts());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all questions for a specific quiz
    public List<QuestionModel> getQuestionsByQuizId(int quizId) {
        List<QuestionModel> questions = new ArrayList<>();
        String query = "SELECT * FROM questions WHERE quiz_id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quizId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                questions.add(new QuestionModel(
                        resultSet.getInt("id"),
                        resultSet.getInt("quiz_id"),
                        resultSet.getString("question_text"),
                        resultSet.getString("option_a"),
                        resultSet.getString("option_b"),
                        resultSet.getString("option_c"),
                        resultSet.getString("option_d"),
                        resultSet.getString("correct_answer"),
                        resultSet.getInt("points")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    // Method to update a question
    public void updateQuestion(QuestionModel question) {
        String query = "UPDATE questions SET question_text = ?, option_a = ?, option_b = ?, option_c = ?, option_d = ?, correct_answer = ?, points = ? WHERE id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, question.getQuestion());
            statement.setString(2, question.getOptionA());
            statement.setString(3, question.getOptionB());
            statement.setString(4, question.getOptionC());
            statement.setString(5, question.getOptionD());
            statement.setString(6, question.getAnswer());
            statement.setInt(7, question.getPts());
            statement.setInt(8, question.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a question by ID
    public void deleteQuestion(int id) {
        String query = "DELETE FROM questions WHERE id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
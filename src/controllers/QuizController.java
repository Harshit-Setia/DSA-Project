package controllers;

import dao.QuizDAO;
import models.QuizModel;

import java.util.List;

public class QuizController {
    private QuizDAO quizDAO = new QuizDAO();


    // Method to create a new quiz
    public void createQuiz(int teacherId, String name, String description) {
        QuizModel quiz = new QuizModel(0, teacherId, name, description);
        quizDAO.insertQuiz(quiz);
        System.out.println("Quiz created successfully!");
    }

    // Method to list all quizzes
    public void listAllQuizzes() {
        List<QuizModel> quizzes = quizDAO.getAllQuizzes();
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes found!");
        } else {
            for (QuizModel quiz : quizzes) {
                System.out.println(quiz); // `toString` already includes totalQuestions and totalPoints
            }
        }
    }
    public List<QuizModel> listAllQuizzes(int x) {
        return quizDAO.getAllQuizzes(); // Return the list of quizzes from the DAO
    }

    // Method to get a quiz by ID
    public void getQuizById(int quizId) {
        QuizModel quiz = quizDAO.getQuizById(quizId);
        if (quiz != null) {
            System.out.println(quiz); // `toString` already includes totalQuestions and totalPoints
        } else {
            System.out.println("Quiz not found!");
        }
    }
}
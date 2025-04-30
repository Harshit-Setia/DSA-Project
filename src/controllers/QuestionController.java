package controllers;

import dao.QuestionDAO;
import dao.QuizAttemptDAO;
import models.QuestionModel;

import java.util.List;

public class QuestionController {
    private QuestionDAO questionDAO = new QuestionDAO();
    private QuizAttemptDAO quizAttemptDAO = new QuizAttemptDAO();

    // Method to add a question to a quiz
    public void addQuestion(int quizId, String questionText, String optionA, String optionB, String optionC, String optionD, String correctAnswer, int points) {
        QuestionModel question = new QuestionModel(0, quizId, questionText, optionA, optionB, optionC, optionD, correctAnswer, points);
        questionDAO.insertQuestion(question);
        System.out.println("Question added successfully!");
    }

    // Method to list all questions for a quiz
    public void listQuestionsByQuizId(int quizId) {
        List<QuestionModel> questions = questionDAO.getQuestionsByQuizId(quizId);
        if (questions.isEmpty()) {
            System.out.println("No questions found for this quiz!");
        } else {
            questions.forEach(System.out::println);
        }
    }
    public List<QuestionModel> getQuestionsByQuizId(int quizId) {
        return questionDAO.getQuestionsByQuizId(quizId); // Return the list of questions from the DAO
    }

    // Method to delete a question
    public void deleteQuestion(int questionId) {
        questionDAO.deleteQuestion(questionId);
        System.out.println("Question deleted successfully!");
    }

    // Method to allow a student to attempt a quiz
    public void attemptQuiz(int studentId, int quizId) {
        if (quizAttemptDAO.hasAttemptedQuiz(studentId, quizId)) {
            System.out.println("You have already attempted this quiz.");
            return;
        }

        List<QuestionModel> questions = questionDAO.getQuestionsByQuizId(quizId);
        if (questions.isEmpty()) {
            System.out.println("No questions found for this quiz!");
            return;
        }

        int totalMarks = 0;
        for (QuestionModel question : questions) {
            System.out.println("Question: " + question.getQuestion());
            System.out.println("A: " + question.getOptionA());
            System.out.println("B: " + question.getOptionB());
            System.out.println("C: " + question.getOptionC());
            System.out.println("D: " + question.getOptionD());
            System.out.print("Enter your answer (A/B/C/D): ");
            String answer = new java.util.Scanner(System.in).nextLine().toUpperCase();

            if (answer.equals(question.getAnswer())) {
                totalMarks += question.getPts();
            }

        }

        quizAttemptDAO.recordQuizAttempt(studentId, quizId, totalMarks);
        System.out.println("Quiz completed! You scored: " + totalMarks + " marks.");
    }
    public void recordQuizAttempt(int studentId, int quizId, int marksObtained) {
        quizAttemptDAO.recordQuizAttempt(studentId, quizId, marksObtained);
        System.out.println("Quiz attempt recorded successfully!");
    }

    // Method for teachers to fetch quiz results
    public void fetchQuizResults(int quizId) {
        List<String> attempts = quizAttemptDAO.getQuizAttemptsByQuizId(quizId);
        if (attempts.isEmpty()) {
            System.out.println("No students have attempted this quiz.");
        } else {
            System.out.println("Quiz Results:");
            attempts.forEach(System.out::println);
        }
    }
}
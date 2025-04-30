package models;

public class QuizModel {
    private int id; // Unique identifier for the quiz
    private int teacherId; // ID of the teacher who created the quiz
    private String name; // Name of the quiz
    private String description; // Description of the quiz
    private int totalQuestions; // Dynamically calculated
    private int totalPoints; // Dynamically calculated

    // Constructor
    public QuizModel(int id, int teacherId, String name, String description, int totalQuestions, int totalPoints) {
        this.id = id;
        this.teacherId = teacherId;
        this.name = name;
        this.description = description;
        this.totalQuestions = totalQuestions;
        this.totalPoints = totalPoints;
    }
    public QuizModel(int id, int teacherId, String name, String description) {
        this.id = id;
        this.teacherId = teacherId;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    @Override
    public String toString() {
        return "QuizModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", totalQuestions=" + totalQuestions +
                ", totalPoints=" + totalPoints +
                '}';
    }
}

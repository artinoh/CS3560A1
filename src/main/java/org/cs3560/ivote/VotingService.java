package org.cs3560.ivote;
import java.util.ArrayList;
import java.util.HashMap;


/*
VotingService class that represents a voting service that registers students, sets questions, and collects responses
Acts as a mediator between students and questions
 */
public class VotingService {
    private final HashMap<String, Student> studentMap;
    private final HashMap<String, ArrayList<Integer>> studentResponses;
    private Question currentQuestion;

    public VotingService() {
        studentMap = new HashMap<>();
        studentResponses = new HashMap<>();
        currentQuestion = null;
    }

    public void registerStudent(Student student) {
        if (studentMap.containsKey(student.getId())) {
            throw new IllegalArgumentException("Student is already registered");
        }

        studentMap.put(student.getId(), student);
    }

    public void setCurrentQuestion(Question question) {
        currentQuestion = question;
        studentResponses.clear();

        System.out.println(question.toString());
    }

    public void submitResponse(Student student, ArrayList<Integer> choices) {
        if (currentQuestion == null) {
            throw new IllegalStateException("No question has been set");
        }

        if (!studentMap.containsKey(student.getId())) {
            throw new IllegalArgumentException("Student is not registered");
        }

        if (choices.isEmpty() ||
            choices.size() > currentQuestion.choices().size() ||
            (currentQuestion.type() == QuestionType.SingleChoice && choices.size() != 1)) {

            throw new IllegalArgumentException("Invalid number of choices");
        }

        if (studentResponses.containsKey(student.getId())) {
            studentResponses.get(student.getId()).clear();
        }

        studentResponses.put(student.getId(), choices);
    }

    public void displayResults() {
        System.out.println("**********************************");
        if (currentQuestion == null) {
            throw new IllegalStateException("No question has been set");
        }

        if (studentResponses.isEmpty()) {
            throw new IllegalStateException("No responses have been submitted");
        }

        System.out.println("Question: " + currentQuestion.question());
        currentQuestion.displayAnswer();

        HashMap<Integer, Integer> choiceCounts = new HashMap<>();
        ArrayList<Student> correctStudents = new ArrayList<>();
        for (String studentId : studentResponses.keySet()) {
            for (int choice : studentResponses.get(studentId)) {
                choiceCounts.put(choice, choiceCounts.getOrDefault(choice, 0) + 1);
            }

            if (currentQuestion.isCorrect(studentResponses.get(studentId))) {
                correctStudents.add(studentMap.get(studentId));
            }
        }

        for (int i = 0; i < currentQuestion.choices().size(); i++) {
            System.out.println(i + ". " + currentQuestion.choices().get(i) + ": " + choiceCounts.getOrDefault(i, 0));
        }

        System.out.println("Correct students:");
        for (Student student : correctStudents) {
            System.out.println(student.toString());
        }
        System.out.println("**********************************");
    }



}

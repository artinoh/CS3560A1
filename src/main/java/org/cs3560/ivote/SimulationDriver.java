package org.cs3560.ivote;

import java.util.ArrayList;
import java.util.List;

public class SimulationDriver {

    public static ArrayList<Student> createStudents(int numStudents, VotingService votingService) {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < numStudents; i++) {
            Student student = new Student(Integer.toString(i), votingService);
            students.add(student);
        }
        return students;
    }

    public static void randomlyAnswerSingleChoice(Student student, Question question) {
        int response = (int) (Math.random() * question.choices().size());
        student.submitResponse(new ArrayList<>(List.of(response)));
    }

    public static void randomlyAnswerMultipleChoice(Student student, Question question) {
        ArrayList<Integer> response = new ArrayList<>();
        for (int i = 0; i < question.choices().size(); i++) {
            if (Math.random() < 0.5) {
                response.add(i);
            }
        }
        if (response.isEmpty()) {
            response.add((int) (Math.random() * question.choices().size()));
        }
        student.submitResponse(response);
    }

    public static void randomlyAnswer(Student student, Question question) {
        QuestionType type = question.type();
        if (type == QuestionType.SingleChoice) {
            randomlyAnswerSingleChoice(student, question);
        } else if (type == QuestionType.MultipleChoice) {
            randomlyAnswerMultipleChoice(student, question);
        }
    }

    public static void main(String[] args) {
        VotingService votingService = new VotingService();
        ArrayList<Student> students = createStudents(20, votingService);

        Question q1 = new Question.QuestionBuilder()
                .setQuestion("What is the capital of France?")
                .addCorrectChoice("Paris")
                .addIncorrectChoice("London")
                .addIncorrectChoice("Berlin")
                .addIncorrectChoice("Madrid")
                .setType(QuestionType.SingleChoice)
                .build();

        votingService.setCurrentQuestion(q1);


        for (Student student : students) {
            randomlyAnswer(student, q1);
        }
        votingService.displayResults();

        Question q2 = new Question.QuestionBuilder()
                .setQuestion("Which superheros are in the Avengers?")
                .addCorrectChoice("Iron Man")
                .addCorrectChoice("Captain America")
                .addIncorrectChoice("Batman")
                .addCorrectChoice("Thor")
                .addIncorrectChoice("Superman")
                .setType(QuestionType.MultipleChoice)
                .build();

        votingService.setCurrentQuestion(q2);

        for (Student student : students) {
            randomlyAnswer(student, q2);
        }



        votingService.displayResults();
    }
}
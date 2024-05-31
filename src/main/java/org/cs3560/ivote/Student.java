package org.cs3560.ivote;

import java.util.ArrayList;

public class Student {
    private final String id;
    VotingService votingService;

    public Student(String id, VotingService votingService) {
        this.id = id;
        this.votingService = votingService;
        votingService.registerStudent(this);
    }

    public String getId() {
        return id;
    }

    public void submitResponse(ArrayList<Integer> choices) {
        votingService.submitResponse(this, choices);
    }

    @Override
    public String toString() {
        return "Student{id=" + id + "}";
    }
}

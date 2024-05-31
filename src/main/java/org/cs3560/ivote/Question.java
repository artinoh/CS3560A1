package org.cs3560.ivote;

import java.util.ArrayList;

enum QuestionType {
    None, SingleChoice, MultipleChoice
}

/*
Question class that represents a question with multiple choices and correct choices
Builder pattern is used to create Question objects correctly
 */
public record Question(String question, ArrayList<String> choices, ArrayList<Integer> correctChoices, QuestionType type) {
    static class QuestionBuilder {
        private String question;
        private ArrayList<String> choices = new ArrayList<>();
        private ArrayList<Integer> correctChoices = new ArrayList<>();
        private QuestionType type = QuestionType.None;

        public QuestionBuilder setQuestion(String question) {
            this.question = question;
            return this;
        }

        public QuestionBuilder setType(QuestionType type) {
            this.type = type;
            return this;
        }

        public QuestionBuilder addCorrectChoice(String choice) {
            choices.add(choice);
            correctChoices.add(choices.size() - 1);
            return this;
        }

        public QuestionBuilder addIncorrectChoice(String choice) {
            choices.add(choice);
            return this;
        }

        public Question build() {
            if (question == null || choices.isEmpty() || correctChoices.isEmpty() || type == QuestionType.None) {
                throw new IllegalStateException("QuestionBuilder not fully initialized");
            }
            Question q = new Question(question, new ArrayList<>(choices), new ArrayList<>(correctChoices), type);
            resetBuilder();
            return q;
        }

        private void resetBuilder() {
            question = null;
            choices = new ArrayList<>();
            correctChoices = new ArrayList<>();
        }
    }

    public boolean isCorrect(ArrayList<Integer> choices) {
        if (choices.size() != correctChoices.size()) {
            return false;
        }
        for (Integer choice : choices) {
            if (!correctChoices.contains(choice)) {
                return false;
            }
        }
        return true;
    }

    public void displayAnswer() {
        StringBuilder sb = new StringBuilder();
        sb.append("Correct Answer: ");
        for (Integer correctChoice : correctChoices) {
            sb.append(choices.get(correctChoice)).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        System.out.println(sb.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(question).append("\n");
        for (int i = 0; i < choices.size(); i++) {
            sb.append(i).append(". ").append(choices.get(i)).append("\n");
        }
        return sb.toString();
    }

}

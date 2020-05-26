package com.jovialsa.myquiz2;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class QuizQuestion {

    private String question;
    private LinkedHashMap<String, Boolean> answers;

    public QuizQuestion() {
        question = null;
        answers = new LinkedHashMap<String, Boolean>();
    }

    void setQuestion(String question) {

        this.question = question;
    }

    public String getQuestion() {

        return question;
    }

    void addAnswer(String answer, boolean isCorrect) {

        answers.put(answer, isCorrect);
    }


    public ArrayList<String> getAnswers() {
        return new ArrayList<String>(answers.keySet());
    }

    public LinkedHashMap<String, Boolean> getAnswersWithSolutions(){
        return this.answers;
    }

    public String getAnswer(int index) {

        ArrayList<String> answers = new ArrayList<>(this.answers.keySet());

        // check index to avoid out of bounds exception
        if (index < answers.size()) {
            return answers.get(index);
        } else {
            return null;
        }
    }

    public boolean isCorrect(String answer) {
        if (answers.containsKey(answer))
            return answers.get(answer);
        else
            return false;
    }

    public int size() {
        return answers.keySet().size();
    }



}

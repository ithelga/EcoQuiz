package com.ecoquiz;

import java.util.Random;

/**
 * Created by Helga on
 */
public class Question {

    private String question;
    private String[] answers;
    private int[] corrects;

    public Question(String question, String[] answers, int[] corrects) {
        this.question = question;
        this.answers = answers;
        this.corrects = corrects;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int[] getCorrects() {
        return corrects;
    }

    public void shuffleAnswers() {
        int index, temp;
        String sTemp;
        Random random = new Random();
        for (int i = answers.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = corrects[index];
            corrects[index] = corrects[i];
            corrects[i] = temp;

            sTemp = answers[index];
            answers[index] = answers[i];
            answers[i] = sTemp;
        }
    }


}

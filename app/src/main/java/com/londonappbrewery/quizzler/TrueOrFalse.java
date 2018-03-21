package com.londonappbrewery.quizzler;

/**
 * Created by tharu on 21-03-2018.
 */

public class TrueOrFalse {
    private int mQuestionId;
    private boolean mAnswer;

    public TrueOrFalse(int QuestionResourceId, boolean answer){
        mQuestionId = QuestionResourceId;
        mAnswer = answer;
    }

    // Generated using right click, generate
    public int getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(int questionId) {
        mQuestionId = questionId;
    }

    public boolean isAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }
}

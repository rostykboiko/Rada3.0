package com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB;

public class Option {
    private String optionKey;
    private String optionTitle;
    private long answerCounter;

    public String getOptionTitle() {
        return optionTitle;
    }

    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }

    public long getAnswerCounter() {
        return answerCounter;
    }

    public void setAnswerCounter(long answerCounter) {
        this.answerCounter = answerCounter;
    }

    public String getOptionKey() {
        return optionKey;
    }

    public void setOptionKey(String optionKey) {
        this.optionKey = optionKey;
    }
}

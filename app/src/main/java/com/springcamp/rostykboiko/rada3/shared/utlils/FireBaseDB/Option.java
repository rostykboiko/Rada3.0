package com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB;

import java.util.ArrayList;
import java.util.List;

public class Option {
    private boolean checked;
    private String optionKey;
    private String optionTitle;
    private long answerCounter;
    private List<String> participantsList = new ArrayList<>();

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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<String> getParticipantsList() {
        return participantsList;
    }

    public void setParticipantsList(List participantsList) {
        this.participantsList = participantsList;
    }
}

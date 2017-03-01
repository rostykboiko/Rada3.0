package com.springcamp.rostykboiko.rada3.data;

public class EditorUseCase {

    interface EditorCallback{
        void editedSurvey();

        void newSurvey();

        void error();
    }
}

package com.example.user.tp2quizz;

import android.widget.TextView;

public class QuizzApp {
    TextView cw;

    public void setVue(TextView cw) {
        this.cw = cw;
    }

    public void newQuestion(String question){
        Update(question);
    }

    private void Update(String textToShow) {
        cw.setText(textToShow);
    }

    public QuizzApp(TextView cw) {
        String textToShow = "";

        setVue(cw) ;
        Update(textToShow);
    }
}



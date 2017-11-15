package com.example.user.tp2quizz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_score);
        Bundle b = getIntent().getExtras();
        int score = (b.getInt("score"));
        int maxScore = (b.getInt("maxScore"));
        TextView TView=(TextView)findViewById(R.id.textView);
        TView.setText("Final Score is : " + score + "/"+ maxScore);
    }
}

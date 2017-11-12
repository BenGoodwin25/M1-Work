package com.example.user.tp2quizz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Question extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHelper DBhelper;

    Cursor quiz;
    Cursor nbProposition;

    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_items);
        setContentView(R.layout.activity_question);

        DBhelper = new DatabaseHelper(this);
        db = DBhelper.getWritableDatabase();

        Bundle b = getIntent().getExtras();
        int quizzId = (b.getInt("id") + 1);
        System.out.println(quizzId);
        quiz = db.rawQuery("SELECT * FROM Question WHERE quizz = " + quizzId, null,
                null);

        if(!quiz.moveToFirst()){
            Log.i("DATABASE","Init BDD");
        } else {
            score=0;
            question();
        }
    }

    public void question(){
        if(quiz.isAfterLast()){
            Intent i = new Intent(Question.this, ScoreActivity.class);
            i.putExtra("score", score);
            startActivity(i);
        }
        else {

            String[] tmpArray = new String[10];//MAX Propositions possible
            int[] idArray = new int[10];
            int index = 0;
            TextView TView=(TextView)findViewById(R.id.textView2);
            TView.setText("" + quiz.getString(1));

            nbProposition = db.rawQuery("SELECT * FROM Proposition WHERE question =" +
                    quiz.getShort(0), null, null);
            nbProposition.moveToFirst();
            final int answer=quiz.getInt(2)-nbProposition.getInt(0);
            System.out.println("answer : " + answer);
            while (!nbProposition.isAfterLast()) {
                idArray[index] = nbProposition.getInt(0);
                tmpArray[index] = nbProposition.getString(1);
                System.out.println(nbProposition.getString(1));
                index++;
                nbProposition.moveToNext();
            }
            final String[] mobileArray = new String[index];
            final ListView listView = (ListView) findViewById(R.id.question_list);
            for (int i = 0; i < index; i++) {
                mobileArray[i] = tmpArray[i];
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.questions_items,
                    mobileArray);

            listView.setAdapter(adapter);
            listView.setClickable(true);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // When clicked, show a toast with the TextView text or do whatever you need.
                    if(position==answer){
                        score++;
                        quiz.moveToNext();
                        question();
                    } else {
                        quiz.moveToNext();
                        question();
                    }

                }
            });

        }
    }

}

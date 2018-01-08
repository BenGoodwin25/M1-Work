package com.example.user.tp2quizz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHelper DBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_items);
        setContentView(R.layout.activity_main);

        DBhelper = new DatabaseHelper(this);
        db = DBhelper.getWritableDatabase();

        Cursor quiz = db.rawQuery("SELECT * FROM Quizz", null, null);

        if(!quiz.moveToFirst()){
            Log.i("DATABASE","Init BDD");
        } else {
            int nbQuizzs = quiz.getCount();
            final String[] mobileArray = new String[nbQuizzs];
            for (int i = 0; i < nbQuizzs; i++) {
                mobileArray[i] = quiz.getString(1);
                System.out.println(quiz.getString(1));
                quiz.moveToNext();
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.questions_items, mobileArray);

            final ListView listView = (ListView) findViewById(R.id.quiz_list);
            listView.setAdapter(adapter);

            listView.setClickable(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(MainActivity.this, Question.class);
                    i.putExtra("id",position);
                    startActivity(i);
                }
            });
        }
    }
}



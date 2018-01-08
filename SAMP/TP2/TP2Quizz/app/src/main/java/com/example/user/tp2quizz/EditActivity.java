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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHelper DBhelper;


    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_items);
        setContentView(R.layout.activity_edit);
        index = 1;

        DBhelper = new DatabaseHelper(this);
        db = DBhelper.getWritableDatabase();

        Button b0=(Button)findViewById(R.id.DownloadQuiz);
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadTask task = new DownloadTask();
                task.execute(getApplicationContext());
            }
        });

        Button b1=(Button)findViewById(R.id.refresh);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });


        Cursor quiz = db.rawQuery("SELECT * FROM Quizz", null, null);

        if(!quiz.moveToFirst()){
            Log.i("DATABASE","Init BDD");
        } else {
            int nbQuizzs = quiz.getCount();
            String[] mobileArray = new String[nbQuizzs];
            for (int i = 0; i < nbQuizzs; i++) {
                mobileArray[i] = quiz.getString(1);
                System.out.println(quiz.getString(1));
                quiz.moveToNext();
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.questions_items, mobileArray);

            final ListView listView = (ListView) findViewById(R.id.mobile_list);
            listView.setAdapter(adapter);

            listView.setClickable(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(EditActivity.this, EditQuestionActivity.class);
                    i.putExtra("id",position);
                    i.putExtra("quizzName", ((TextView) view).getText());
                    startActivity(i);
                }
            });
        }
    }
}
package com.example.user.tp2quizz;

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
        final Cursor quiz = db.rawQuery("SELECT * FROM Question WHERE quizz = " + quizzId, null, null);

        if(!quiz.moveToFirst()){
            Log.i("DATABASE","Init BDD");
        } else {
            String[] tmpArray= new String[10];
            int index = 0;
            int answer = quiz.getInt(2);
            tmpArray[index] = quiz.getString(1);
            System.out.println(quiz.getString(2));
            index++;
            final Cursor nbProposition = db.rawQuery("SELECT * FROM Proposition WHERE question =" + quiz.getShort(0), null, null);
            quiz.moveToNext();
            nbProposition.moveToFirst();
            while (!nbProposition.isAfterLast()) {
                tmpArray[index] = nbProposition.getString(1);
                System.out.println(nbProposition.getString(1));
                index++;
                nbProposition.moveToNext();
            }
            final String[] mobileArray = new String[index];
            for(int i=0; i<index;i++){
                mobileArray[i]=tmpArray[i];
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.questions_items, mobileArray);


            final ListView listView = (ListView) findViewById(R.id.question_list);

            listView.setAdapter(adapter);

            listView.setClickable(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // When clicked, show a toast with the TextView text or do whatever you need.
                    if(position+1==answer){
                        Toast.makeText(getApplicationContext(), "True", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "False", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

}

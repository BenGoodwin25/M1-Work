package com.example.user.tp2quizz;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.tp2quizz.R.id.spinner;

public class EditSingleQuestionActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHelper DBhelper;

    Cursor quiz;
    Cursor nbProposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_edit);
        setContentView(R.layout.activity_edit_single_question);

        DBhelper = new DatabaseHelper(this);
        db = DBhelper.getWritableDatabase();

        Bundle b = getIntent().getExtras();
        int questionId = (b.getInt("id"));
        System.out.println(questionId);
        quiz = db.rawQuery("SELECT * FROM Question WHERE id = " + questionId, null, null);

        if(!quiz.moveToFirst()){
            Log.i("DATABASE","Init BDD");
        } else {
            String[] tmpArray = new String[10];//MAX Propositions possible
            int[] idArray = new int[10];

            TextView TView=(TextView)findViewById(R.id.editText);
            TView.setText("" + quiz.getString(1));

            nbProposition = db.rawQuery("SELECT * FROM Proposition WHERE Question =" +
                    questionId, null, null);
            nbProposition.moveToFirst();
            final int answer=quiz.getInt(2)-nbProposition.getInt(0);
            System.out.println("answer : " + answer);
            int index = 0;

            while (!nbProposition.isAfterLast()) {
                idArray[index] = nbProposition.getInt(0);
                tmpArray[index] = nbProposition.getString(1);
                index++;
                nbProposition.moveToNext();
            }

            final String[] mobileArray = new String[index];
            final ListView listView = (ListView) findViewById(R.id.mobile_list);

            //get the spinner from the xml.
            Spinner dropdown = (Spinner)findViewById(spinner);

            //create a list of items for the spinner.
            String[] items = new String[index];
            //create an adapter to describe how the items are displayed, adapters are used in several places in android.
            //There are multiple variations of this, but this is the basic variant.
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
            //set the spinners adapter to the previously created one.
            dropdown.setAdapter(adapter2);
            dropdown.setSelection(answer);

            for (int i = 0; i < index; i++) {
                mobileArray[i] = tmpArray[i];
                items[i]=mobileArray[i];
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.questions_edit,
                    mobileArray);

            listView.setAdapter(adapter);
            listView.setClickable(true);

            dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    System.out.println(pos);
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

}

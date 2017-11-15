package com.example.user.tp2quizz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
        final int questionId = (b.getInt("id"));
        System.out.println(questionId);
        quiz = db.rawQuery("SELECT * FROM Question WHERE id = " + questionId, null, null);

        if(!quiz.moveToFirst()){
            Log.i("DATABASE","Init BDD");
        } else {
            String[] tmpArray = new String[10];//MAX Propositions possible
            int[] idArray = new int[10];

            final TextView TView=(TextView)findViewById(R.id.editText);
            TView.setText("" + quiz.getString(1));

            nbProposition = db.rawQuery("SELECT * FROM Proposition WHERE Question =" +
                    questionId, null, null);
            nbProposition.moveToFirst();
            final int minAnswer=nbProposition.getInt(0);
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
            final Spinner dropdown = (Spinner)findViewById(spinner);

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

            final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.questions_items,
                    mobileArray);

            listView.setAdapter(adapter);
            listView.setClickable(true);

            Button b1=(Button)findViewById(R.id.updateQuestion);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ContentValues questionsChanges = new ContentValues();
                    questionsChanges.put(DatabaseContract.TableQuestion.COLUMN_NAME_TEXT,TView.getText().toString());
                    questionsChanges.put(DatabaseContract.TableQuestion.COLUMN_NAME_REPONSE,minAnswer+dropdown.getSelectedItemPosition());
                    db.update(DatabaseContract.TableQuestion.TABLE_NAME, questionsChanges, DatabaseContract.TableQuestion.COLUMN_NAME_ID + "=" + questionId, null);

                    /*      Doesn't work ...
                    for(int i=0;i < adapter.getCount();i++){
                        ContentValues propositionsChanges = new ContentValues();
                        propositionsChanges.put(DatabaseContract.TableProposition.COLUMN_NAME_TEXT,adapter.getItem(i).toString());
                        String id = ""+(i+minAnswer);
                        db.update(DatabaseContract.TableProposition.TABLE_NAME,propositionsChanges,DatabaseContract.TableProposition.COLUMN_NAME_ID + "=" + id, null);
                    }
                    */
                }
            });
        }
    }
}

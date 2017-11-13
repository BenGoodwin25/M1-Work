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

public class EditQuestionActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHelper DBhelper;

    Cursor quiz;
    Cursor nbProposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_items);
        setContentView(R.layout.activity_edit_question);

        DBhelper = new DatabaseHelper(this);
        db = DBhelper.getWritableDatabase();

        Bundle b = getIntent().getExtras();
        int quizzId = (b.getInt("id") + 1);
        String cat = (b.getString("quizzName"));
        System.out.println(quizzId);
        quiz = db.rawQuery("SELECT * FROM Question WHERE quizz = " + quizzId, null, null);

        TextView TView=(TextView)findViewById(R.id.editText);
        TView.setText("" + cat);

        if(!quiz.moveToFirst()){
            Log.i("DATABASE","Init BDD");
        } else {
            int nbQuizzs = quiz.getCount();
            final String[] mobileArray = new String[nbQuizzs];
            final int[] idArray = new int[nbQuizzs];
            for (int i = 0; i < nbQuizzs; i++) {
                mobileArray[i] = quiz.getString(1);
                idArray[i] = quiz.getInt(0);
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
                    Intent i = new Intent(EditQuestionActivity.this, EditSingleQuestionActivity.class);
                    i.putExtra("id",idArray[position]);
                    startActivity(i);
                }
            });
        }
    }

}

package com.example.user.tp2quizz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    QuizzApp qApp;
    TextView textV;

    SQLiteDatabase db;
    QuizzDatabase DB;
    DatabaseHelper DBhelper;


    int index;
    int indexMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB = new QuizzDatabase(this);

        textV=(TextView)findViewById(R.id.textView1);
        qApp=new QuizzApp(textV);
        index=1;
        indexMax=DB.getIndexMax();
        qApp.newQuestion(DB.getQuestion(index));
        index+=1;

        DBhelper = new DatabaseHelper(this);
        db = DBhelper.getWritableDatabase();

        //TODO ADD BUTTON show answer
        Cursor quiz = db.rawQuery("SELECT * FROM Quizz",null, null);

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
                    // When clicked, show a toast with the TextView text or do whatever you need.
                    Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        //TODO Change all the answer button with a listView with action click
        /*Button b0=(Button)findViewById(R.id.button0);
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index>indexMax-1){
                    Toast.makeText(getBaseContext(),"No more questions ...",Toast.LENGTH_SHORT).show();

                } else {
                    qApp.newQuestion(DB.getQuestion(index));
                    index += 1;
                }
            }
        });

        Button b1=(Button)findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index>indexMax-1){
                    Toast.makeText(getBaseContext(),"No more questions ...",Toast.LENGTH_SHORT).show();
                } else {
                    if(DB.getAnswer(index)==1){
                        Toast.makeText(getBaseContext(),"Yeah, you're right",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(),"So bad ...",Toast.LENGTH_SHORT).show();
                    }
                    qApp.newQuestion(DB.getQuestion(index));
                    index += 1;
                }

            }
        });

        Button b2=(Button)findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index>indexMax-1){
                    Toast.makeText(getBaseContext(),"No more questions ...",Toast.LENGTH_SHORT).show();
                } else {
                    if(DB.getAnswer(index)==0){
                        Toast.makeText(getBaseContext(),"Yeah, you're right",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(),"So bad ...",Toast.LENGTH_SHORT).show();
                    }
                    qApp.newQuestion(DB.getQuestion(index));
                    index += 1;
                }
            }
        });
        */
    }
}



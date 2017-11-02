package com.example.user.tp2quizz;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    // Array of strings...
    QuizzDatabase DB;

    int index;
    int indexMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_items);
        setContentView(R.layout.activity_edit);

        DB = new QuizzDatabase(this);
        indexMax=DB.getIndexMax();
        String[] mobileArray = new String[indexMax];

        for(index=1;index<=indexMax;index++){
            System.out.println(DB.getQuestion(index));
            mobileArray[index-1] = DB.getQuestion(index);
        }

        Button b0=(Button)findViewById(R.id.DownloadQuiz);
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadTask().execute();
            }
        });
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
}
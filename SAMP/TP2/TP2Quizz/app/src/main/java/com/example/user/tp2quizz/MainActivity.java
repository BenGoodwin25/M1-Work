package com.example.user.tp2quizz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    QuizzApp qApp;
    TextView textV;
    QuizzDatabase DB;


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

        //TODO ADD BUTTON show answer

        Button b0=(Button)findViewById(R.id.button0);
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
    }
}



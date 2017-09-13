package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity {

    CalcApp cApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textV=(TextView)findViewById(R.id.textView);
        cApp=new CalcApp(textV);

        Button b0=(Button)findViewById(R.id.button_0);
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.chiffre("0");
            }
        });
        Button b1=(Button)findViewById(R.id.button_1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.chiffre("1");
            }
        });
        Button b2=(Button)findViewById(R.id.button_2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.chiffre("2");
            }
        });
        Button b3=(Button)findViewById(R.id.button_3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.chiffre("3");
            }
        });
        Button b4=(Button)findViewById(R.id.button_4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.chiffre("4");
            }
        });
        Button b5=(Button)findViewById(R.id.button_5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.chiffre("5");
            }
        });
        Button b6=(Button)findViewById(R.id.button_6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.chiffre("6");
            }
        });
        Button b7=(Button)findViewById(R.id.button_7);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.chiffre("7");
            }
        });
        Button b8=(Button)findViewById(R.id.button_8);
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.chiffre("8");
            }
        });
        Button b9=(Button)findViewById(R.id.button_9);
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.chiffre("9");
            }
        });
        Button b_div=(Button)findViewById(R.id.button_div);
        b_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.div();
            }
        });
        Button b_x=(Button)findViewById(R.id.button_x);
        b_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.mult();
            }
        });
        Button b_malus=(Button)findViewById(R.id.button_malus);
        b_malus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.moins();
            }
        });
        Button b_plus=(Button)findViewById(R.id.button_plus);
        b_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.plus();
            }
        });
        Button b_equal=(Button)findViewById(R.id.button_equal);
        b_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.egal();
            }
        });
        Button b_dot=(Button)findViewById(R.id.button_dot);
        b_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cApp.point();
            }
        });
    }


}

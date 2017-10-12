package com.example.user.tp2quizz;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);

        List<Tweet> tweets = genererTweets();

        TweetAdapter adapter = new TweetAdapter(EditActivity.this, tweets);
        mListView.setAdapter(adapter);
    }

    private List<Tweet> genererTweets(){
        List<Tweet> tweets = new ArrayList<Tweet>();
        tweets.add(new Tweet("Florent", "Mon premier tweet !"));
        tweets.add(new Tweet("Kevin", "C'est ici que ça se passe !"));
        tweets.add(new Tweet("Logan", "Que c'est beau..."));
        tweets.add(new Tweet("Mathieu", "Il est quelle heure ??"));
        tweets.add(new Tweet("Willy", "On y est presque"));
        return tweets;
    }

    private void afficherListeTweets(){
        List<Tweet> tweets = genererTweets();

        TweetAdapter adapter = new TweetAdapter(EditActivity.this, tweets);
        mListView.setAdapter(adapter);
    }
}
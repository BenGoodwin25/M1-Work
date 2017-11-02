package com.example.user.tp2quizz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class DownloadTask extends AsyncTask<Context, Void, Void>{

    @Override
    protected Void doInBackground(Context... c) {

        /*int io = c.length;
        Log.i("Length","" + io);
        Context context = c[io-1];*/
        String url = "https://dept-info.univ-fcomte.fr/joomla/images/CR0700/Quizzs.xml";

        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            Log.i("TEST","Connection well executed");
        }
        catch (IOException ex) {
            Log.w("DownloadTask", "Exception download " + url + ": " + ex.getMessage());
        }
        /*catch (SAXException | ParserConfigurationException ex) {
            Log.w("DownloadTask", "Exception parsing " + url + ": " + ex.getMessage());
        }*/
        return null;
    }
}

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

        int io = c.length;
        Log.i("Length","" + io);
        Context context = c[io-1];
        String url = "https://dept-info.univ-fcomte.fr/joomla/images/CR0700/Quizzs.xml";

        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            Log.i("TEST","Connection well executed");
            InputStream stream = connection.getInputStream();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringElementContentWhitespace(true);
            Document document = dbf.newDocumentBuilder().parse(stream);

            NodeList quizzs = document.getElementsByTagName("Quizz");

            DatabaseHelper mDbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            long idProposition = 0;
            for (int i = 0; i < quizzs.getLength(); ++i) {
                Element quizz = (Element) quizzs.item(i);
                String type = quizz.getAttribute("type");
                type = type.replaceAll("\\A\\s+","");
                type = type.replaceAll("\\s+\\Z","");
                Cursor testQuizz = db.rawQuery("SELECT * FROM Quizz WHERE type = \"" + type + "\"",null,null);
                if(testQuizz.getCount() == 0) {
                    Log.i("TEST STRING", "'" + type + "'");
                    ContentValues quizzType = new ContentValues();
                    quizzType.put(DatabaseContract.TableQuizz.COLUMN_NAME_TYPE, type);
                    long idQuizz = db.insert(DatabaseContract.TableQuizz.TABLE_NAME, null, quizzType);
                    NodeList questions = quizz.getElementsByTagName("Question");
                    for (int j = 0; j < questions.getLength(); ++j) {
                        Element question = (Element) questions.item(j);
                        String text = question.getFirstChild().getTextContent();
                        text = text.replaceAll("\\A\\s+", "");
                        text = text.replaceAll("\\s+\\Z", "");
                        ContentValues questionText = new ContentValues();
                        questionText.put(DatabaseContract.TableQuestion.COLUMN_NAME_TEXT, text);
                        questionText.put(DatabaseContract.TableQuestion.COLUMN_NAME_QUIZZ, idQuizz);
                        NodeList propositionss = question.getElementsByTagName("Propositions");
                        Element propositions_elmt = (Element) propositionss.item(0);
                        Element nombre = (Element) propositions_elmt.getElementsByTagName("Nombre").item(0);
                        Element reponse = (Element) question.getElementsByTagName("Reponse").item(0);
                        int reponseTrue = Integer.parseInt(reponse.getAttribute("valeur"));
                        NodeList propositions = propositions_elmt.getElementsByTagName("Proposition");
                        for (int l = 0; l < propositions.getLength(); ++l) {
                            Element proposition = (Element) propositions.item(l);
                            String propText = proposition.getTextContent();
                            propText = propText.replaceAll("\\A\\s+", "");
                            propText = propText.replaceAll("\\s+\\Z", "");
                            ContentValues propositionText = new ContentValues();
                            propositionText.put(DatabaseContract.TableProposition.COLUMN_NAME_TEXT, propText);
                            propositionText.put(DatabaseContract.TableProposition.COLUMN_NAME_QUESTION, 0);
                            idProposition = db.insert(DatabaseContract.TableProposition.TABLE_NAME, null, propositionText);
                            if (l == reponseTrue - 1) {
                                questionText.put(DatabaseContract.TableQuestion.COLUMN_NAME_REPONSE, idProposition);
                            }
                        }
                        long idQuestion = db.insert(DatabaseContract.TableQuestion.TABLE_NAME, null, questionText);
                        ContentValues updateProposition = new ContentValues();
                        updateProposition.put(DatabaseContract.TableProposition.COLUMN_NAME_QUESTION, idQuestion);
                        db.update(DatabaseContract.TableProposition.TABLE_NAME, updateProposition, DatabaseContract.TableProposition.COLUMN_NAME_QUESTION + "=" + 0, null);
                    }
                }
            }
        }
        catch (IOException ex) {
            Log.w("DownloadTask", "Exception download " + url + ": " + ex.getMessage());
        }
        catch (SAXException | ParserConfigurationException ex) {
            Log.w("DownloadTask", "Exception parsing " + url + ": " + ex.getMessage());
        }
        return null;
    }

}

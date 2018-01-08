package com.example.user.tp2quizz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";

    private static final String SQL_CREATE_QUIZZ =
            "CREATE TABLE " + DatabaseContract.TableQuizz.TABLE_NAME + " (" +
                    DatabaseContract.TableQuizz.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    DatabaseContract.TableQuizz.COLUMN_NAME_TYPE + TEXT_TYPE + " )";

    private static final String SQL_CREATE_QUESTION =
            "CREATE TABLE " + DatabaseContract.TableQuestion.TABLE_NAME + " (" +
                    DatabaseContract.TableQuestion.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    DatabaseContract.TableQuestion.COLUMN_NAME_TEXT + TEXT_TYPE + COMMA_SEP +
                    DatabaseContract.TableQuestion.COLUMN_NAME_REPONSE + " INTEGER REFERENCES " + DatabaseContract.TableProposition.TABLE_NAME + " (" + DatabaseContract.TableProposition.COLUMN_NAME_ID + ") " + COMMA_SEP +
                    DatabaseContract.TableQuestion.COLUMN_NAME_QUIZZ + " INTEGER REFERENCES " + DatabaseContract.TableQuizz.TABLE_NAME + " (" + DatabaseContract.TableQuizz.COLUMN_NAME_ID + ") " + " )";

    private static final String SQL_CREATE_PROPOSITION =
            "CREATE TABLE " + DatabaseContract.TableProposition.TABLE_NAME + " (" +
                    DatabaseContract.TableProposition.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    DatabaseContract.TableProposition.COLUMN_NAME_TEXT + TEXT_TYPE + COMMA_SEP +
                    DatabaseContract.TableProposition.COLUMN_NAME_QUESTION + " INTEGER REFERENCES " + DatabaseContract.TableQuestion.TABLE_NAME + " (" + DatabaseContract.TableQuestion.COLUMN_NAME_ID + ") " + " )";

    private static final String SQL_DELETE_QUIZZ =
            "DROP TABLE IF EXISTS " + DatabaseContract.TableQuizz.TABLE_NAME;

    private static final String SQL_DELETE_QUESTION =
            "DROP TABLE IF EXISTS " + DatabaseContract.TableQuestion.TABLE_NAME;

    private static final String SQL_DELETE_PROPOSITION =
            "DROP TABLE IF EXISTS " + DatabaseContract.TableProposition.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Quizz.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_QUIZZ);
        db.execSQL(SQL_CREATE_QUESTION);
        db.execSQL(SQL_CREATE_PROPOSITION);
        Log.i("DB", "creation");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_QUIZZ);
        db.execSQL(SQL_DELETE_QUESTION);
        db.execSQL(SQL_DELETE_PROPOSITION);
        Log.i("DB", "deletion");
        onCreate(db);
    }
}


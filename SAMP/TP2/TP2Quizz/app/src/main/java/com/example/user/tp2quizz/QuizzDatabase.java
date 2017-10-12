package com.example.user.tp2quizz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuizzDatabase extends SQLiteOpenHelper {
    SQLiteDatabase db ;
    private static final String DATABASE_NAME = "dbs.db" ;
    private static final int DATABASE_VERSION = 1 ;
    private static final String DATABASE_CREATE = "create table dbs "
            + "(_id integer primary key autoincrement, " + "name text not null"+"answer int not null) ;";
    public QuizzDatabase(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION) ;
    }
    public void onCreate(SQLiteDatabase database) {
        this.db = database;
        database.execSQL(DATABASE_CREATE) ;
        db.execSQL("INSERT INTO dbs (name,answer) VALUES ('Le diable de Tansmanie vit dans la jungle du Brésil',0)");
        db.execSQL("INSERT INTO dbs (name,answer) VALUES ('La sauterelle saute l''équivalent de 200 fois sa taille',1)");
        db.execSQL("INSERT INTO dbs (name,answer) VALUES ('Les pandas hibernent',0)");
        db.execSQL("INSERT INTO dbs (name,answer) VALUES ('On trouve des dromadaires en liberté en Australie',1)");
        db.execSQL("INSERT INTO dbs (name,answer) VALUES ('Le papillon monarque vole plus de 4000km',1)");
        db.execSQL("INSERT INTO dbs (name,answer) VALUES ('Les gorilles mâles dorment dans les arbres',0)");
    }

    public Cursor getCursor(){
        db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM dbs", null);
    }

    public Cursor getCursor(int id){
        db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM dbs WHERE _id="+id, null);
    }

    public String getQuestion(int id){
        Cursor cursor = getCursor(id);
        cursor.moveToFirst();
        return cursor.getString(1);
    }

    public int getAnswer(int id){
        Cursor cursor = getCursor(id);
        cursor.moveToFirst();
        return cursor.getInt(2);
    }

    public int getIndexMax(){
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(_id) FROM dbs", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
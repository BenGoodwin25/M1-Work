package com.example.user.tp2quizz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class DB {
	public static SQLiteDatabase db;
	public static MaBase maBase;
	
	public static class MaBase extends SQLiteOpenHelper {
		
		public static final String CREATE_CHANNEL =
				"CREATE TABLE channel (" +
				"id VARCHAR(256) PRIMARY KEY, " +
				"title VARCHAR(128), " +
				"description TEXT, " +
				"lastBuildDate TIMESTAMP, " +
				"link VARCHAR(256)," +
				"image BLOB)";
		
		public static final String CREATE_ITEM =
				"CREATE TABLE item (" +
				"id INTEGER PRIMARY KEY, " +
				"channelId VARCHAR(256), " +
				"title VARCHAR(128), " +
				"description TEXT, " +
				"pubDate TIMESTAMP, " +
				"link VARCHAR(256), " +
				"FOREIGN KEY(channelId) REFERENCES channel(id) ON DELETE CASCADE)";
		
		public MaBase(Context context, String name, CursorFactory factory,int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_CHANNEL);
			db.execSQL(CREATE_ITEM);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE channel");
			db.execSQL("DROP TABLE item");
			
			onCreate(db);
		}
	}

	
}

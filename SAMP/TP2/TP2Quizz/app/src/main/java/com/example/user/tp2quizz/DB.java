package com.example.user.tp2quizz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DB {
	public static SQLiteDatabase db;
	public static MaBase maBase;
	public static final int version = 2;
	/*
	public static void open(Context context){
		Log.e("DB", "open");
		
		maBase = new MaBase(context, "Test", null, version);
		db = maBase.getWritableDatabase();
		db.execSQL("PRAGMA foreign_keys=ON;");	
	}

	public static boolean isOpen(){
		if(maBase != null && db.isOpen()){
			return true;
		}
		else{
			return false;
		}
	}

	public static void close(){
		Log.e("DB", "close");
		if(db != null){
			db.close();
			maBase.close();
		}
	}

	public static long insertChannel(String url, String title, String description, long lastBuildDate, String link, byte[] image){
		Log.d("insertChannel", "url = "+url+", title = "+title+", lastBuildDate = "+lastBuildDate+", link = "+link);
		
		ContentValues values = new ContentValues();
		
		values.put("title", title);
		values.put("description", description);
		values.put("lastBuildDate", String.valueOf(lastBuildDate));
		values.put("link", link);
		if(image != null){
			values.put("image", image);
		}
		else{
			values.putNull("image");
		}
		
		Cursor mCurs = db.rawQuery("SELECT * FROM channel WHERE id = ?", new String[]{url});
		
		if(mCurs.moveToFirst()){
			mCurs.close();
			return db.update("channel", values, "id = ?", new String[]{url});
		}
		else{
			mCurs.close();
			values.put("id", url);
			return db.insert("channel", null, values);
		}

	}

	public static long insertItem(String channelId, String title, String description, long pubDate, String link){
		Log.d("insertItem", "title = "+title+", pubDate : "+pubDate+", link : "+link);
		ContentValues values = new ContentValues();
		values.put("channelId", channelId);
		values.put("title", title);
		values.put("description", description);
		values.put("pubDate", String.valueOf(pubDate));
		values.put("link", link);
		
		return db.insert("item", null, values);
	}

	public static long getMaxDate(String channelId){
		Cursor mCurs = db.rawQuery("SELECT MAX(pubDate) FROM item WHERE channelId = ? ", new String[]{channelId});
		               
		long retour = 0;
		if(mCurs.moveToFirst()){
			retour = mCurs.getLong(0);
		}
		mCurs.close();
		
		return retour;
	}

	public static Cursor getChannels(){
		return db.rawQuery(
				"SELECT id AS id, title AS titre FROM channel", null);
	}
	
	public static String[] getChannelsURL(){
		Cursor mCurs = db.rawQuery("SELECT id FROM channel", null);
		
		String[] retour = null;
		if(mCurs.moveToFirst()){
			int length = mCurs.getCount();
			retour = new String[length];
			for(int i=0; i<length; i++){
				retour[i] = mCurs.getString(0);
				mCurs.moveToNext();
			}			
		}
		mCurs.close();
		
		return retour;		
	}

	public static int getItemsNumber(String channelId){
		Cursor mCurs = db.rawQuery("SELECT COUNT(*) FROM item WHERE channelId = ?", new String[]{channelId});
		
		int retour = 0;
		if(mCurs.moveToFirst()){
			retour = mCurs.getInt(0);
			mCurs.close();
		}
		
		return retour;
	}

	public static Cursor getItems(String channelId){
		return db.rawQuery("SELECT * FROM item WHERE channelId = ?", new String[]{channelId});
	}

	public static Cursor getChannel(String channelId){
		return db.rawQuery("SELECT * FROM channel WHERE id = ?", new String[]{channelId});
	}

	public static int deleteChannel(String channelId){
		int retour =  db.delete("channel", "id = ?", new String[]{channelId});
		return retour;
	}*/
	
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

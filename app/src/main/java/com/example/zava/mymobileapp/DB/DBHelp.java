package com.example.zava.mymobileapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zava on 17.10.2018.
 */

public class DBHelp extends SQLiteOpenHelper {

  public  DBHelp(Context c){
    super(c, "Favourite", null, 1);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table album( id INTEGER PRIMARY KEY AUTOINCREMENT, albumName text, " +
        "artistName text, albumImageURL text, albumReleaseDate text)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//    db.execSQL("drop table if exists album");
  }

  public boolean insert(String albumName, String artistName, String
      albumImageURL,  String albumReleaseDate) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("albumName", albumName);
    contentValues.put("artistName", artistName);
    contentValues.put("albumImageURL", albumImageURL);
    contentValues.put("albumReleaseDate", albumReleaseDate);
    long res = db.insert("album", null, contentValues);
    System.out.print(res);
    return true;
  }

  public boolean delete(String albumImageURL) {
    SQLiteDatabase db = this.getWritableDatabase();
    String sql = String.format("delete from album where albumImageURL = '%s'", albumImageURL);
    db.execSQL(sql);
    return true;
  }

  public Cursor queueAll(){
    SQLiteDatabase db = this.getWritableDatabase();
      return db.rawQuery("select  albumName,  artistName,albumImageURL ,\n" +
        "albumReleaseDate from album", null);
  }
}
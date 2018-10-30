package com.example.zava.mymobileapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelp extends SQLiteOpenHelper {

  private static String sDB_NAME = "Favourite";
  private static String ALBUM_NAME = "albumName";
  private static String ARTIST_NAME = "artistName";
  private static String ALBUM_IMAGE_URL = "albumImageURL";
  private static String ALBUM_RELEASE_DATE = "albumReleaseDate";
  private static String TABLE_NAME = "album";


  public DBHelp(Context c) {
    super(c, sDB_NAME, null, 1);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table album( id INTEGER PRIMARY KEY AUTOINCREMENT," +
        " albumName text, " +
        "artistName text, " +
        "albumImageURL text," +
        " albumReleaseDate text)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//    db.execSQL("drop table if exists album");
  }

  public boolean insert(String albumName, String artistName, String
      albumImageURL, String albumReleaseDate) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(ALBUM_NAME, albumName);
    contentValues.put(ARTIST_NAME, artistName);
    contentValues.put(ALBUM_IMAGE_URL, albumImageURL);
    contentValues.put(ALBUM_RELEASE_DATE, albumReleaseDate);
    long res = db.insert(TABLE_NAME, null, contentValues);
    System.out.print(res);
    return true;
  }

  public boolean delete(String albumImageURL) {
    SQLiteDatabase db = this.getWritableDatabase();
    String sql = String.format("delete from album where albumImageURL = '%s'", albumImageURL);
    db.execSQL(sql);
    return true;
  }

  public Cursor queueAll() {
    SQLiteDatabase db = this.getWritableDatabase();
    return db.rawQuery("select  albumName,  artistName,albumImageURL ,\n" +
        "albumReleaseDate from album", null);
  }
}
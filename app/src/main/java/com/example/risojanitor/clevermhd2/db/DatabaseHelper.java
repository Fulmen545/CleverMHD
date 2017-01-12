package com.example.risojanitor.clevermhd2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static java.lang.String.format;

public class DatabaseHelper extends SQLiteOpenHelper {

  private static final String TAG = DatabaseHelper.class.getSimpleName();
  private static final String KE_DB_NAME = "ke.db";
  private static final String DEFAULT_KE_DB_PATH = "assets/databases/" + KE_DB_NAME;

  public DatabaseHelper(Context context) throws IOException {
    super(context, KE_DB_NAME, null, 1);
    copyDatabaseIfNecessary(context);
  }

  private void copyDatabaseIfNecessary(Context context) throws IOException {
    File databasePath = context.getDatabasePath(KE_DB_NAME);
    if (!databasePath.exists()) {
      Log.d(TAG, format("Copying default database from %s to destination %s",
        DEFAULT_KE_DB_PATH, databasePath.getPath()));
      copyDefaultDatabaseToPhoneFileDir(context);
    }
  }

  private void copyDefaultDatabaseToPhoneFileDir(Context context) throws IOException {
    OutputStream onPhoneDb = new FileOutputStream(context.getDatabasePath(KE_DB_NAME));
    InputStream defaultDb = getClass().getClassLoader().getResourceAsStream(DEFAULT_KE_DB_PATH);

    byte[] buffer = new byte[1024];
    int length;
    while ((length = defaultDb.read(buffer)) > 0) {
      onPhoneDb.write(buffer, 0, length);
    }

    //Close the streams
    onPhoneDb.flush();
    onPhoneDb.close();
    defaultDb.close();
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    Log.d(TAG, "On create...");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.d(TAG, "On upgrade...");
  }
}

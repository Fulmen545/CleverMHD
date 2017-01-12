package com.example.risojanitor.clevermhd2.db;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSelects {

  private static final String TAG = DatabaseSelects.class.getSimpleName();
  private final SQLiteDatabase database;

  public DatabaseSelects(DatabaseHelper helper) {
    database = helper.getReadableDatabase();
  }

  public List<String> findAllBusStops() {
    Cursor c = getZastavkySql();
    List<String> zastavky = new ArrayList<>();
    if (c != null) {
      if (c.moveToFirst()) {
        do {
          String dir = c.getString(c.getColumnIndex("zastavky"));
          zastavky.add(dir);
        } while (c.moveToNext());
      }
    } else {
      zastavky.add("Hello");
    }
    return zastavky;
  }

  private Cursor getZastavkySql() {
    try {
      String sql = "SELECT zastavky FROM zastavky order by zastavky";

      Cursor mCur = database.rawQuery(sql, null);
      if (mCur != null) {
        mCur.moveToNext();
      }
      return mCur;
    } catch (SQLException mSQLException) {
      Log.e(TAG, "getZastavkySql >>" + mSQLException.toString());
      throw mSQLException;
    }
  }
}

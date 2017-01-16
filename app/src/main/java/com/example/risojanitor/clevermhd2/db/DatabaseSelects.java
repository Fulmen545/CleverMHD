package com.example.risojanitor.clevermhd2.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseSelects {
  private static DatabaseSelects instance;

  private static final String TAG = DatabaseSelects.class.getSimpleName();
  private final SQLiteDatabase database;

  public DatabaseSelects(DatabaseHelper helper) {
    database = helper.getReadableDatabase();
  }

  public static DatabaseSelects getInstance() {
    if (instance == null) {
      throw new RuntimeException("You need to initialize DatabaseSelects first!");
    }
    return instance;
  }

  public static void init(Context context) {
    // Here should go all initializations
    DatabaseHelper databaseHelper = null;
    try {
      databaseHelper = new DatabaseHelper(context);
    } catch (IOException e) {
      throw new RuntimeException("Unable to initialize database helper!", e);
    }
    instance = new DatabaseSelects(databaseHelper);
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

  public Cursor getLinkySql() {
    try {
      String sql = "SELECT distinct linka, smer FROM trvanie order by id, linka";

      Cursor mCur = database.rawQuery(sql, null);
      if (mCur != null) {
        mCur.moveToNext();
      }
      return mCur;
    } catch (SQLException mSQLException) {
      Log.e(TAG, "getLinkySql >>" + mSQLException.toString());
      throw mSQLException;
    }
  }

  public List<String> findAllLinky() {
    Cursor c = getLinkySql();
    List<String> linky = new ArrayList<>();
    if (c != null) {
      if (c.moveToFirst()) {
        do {
          String dir = c.getString(c.getColumnIndex("linka"));
          linky.add(dir);
        } while (c.moveToNext());
      }
    } else {
      linky.add("Hello");
    }
    return linky;
  }

  public Cursor getSpecificLinka(String linka) {
    try {
      String sql = "SELECT distinct linka, smer FROM trvanie where odkial='" + linka + "'";

      Cursor mCur = database.rawQuery(sql, null);
      if (mCur != null) {
        mCur.moveToNext();
      }
      return mCur;
    } catch (SQLException mSQLException) {
      Log.e(TAG, "getSpecificLinka >>" + mSQLException.toString());
      throw mSQLException;
    }
  }

  /**
   * Metóda na získanie zastávky pre konkrétnu linku a smer z databázy
   *
   * @param linka
   * @param smer
   * @return Dáta z DB
   */
  public Cursor getZoznZast(String linka, String smer) {
    try {
      String sql = "select distinct kam from trvanie where linka ='" + linka + "' and smer ='" + smer + "' order by id";

      Cursor mCur = database.rawQuery(sql, null);
      if (mCur != null) {
        mCur.moveToNext();
      }
      return mCur;
    } catch (SQLException mSQLException) {
      Log.e(TAG, "getZoznZast >>" + mSQLException.toString());
      throw mSQLException;
    }
  }

  /**
   * Metóda na získanie typu dna pre konkrétnu linku z databázy
   *
   * @param linka
   * @return Dáta z DB
   */
  public Cursor getCountDen(String linka) {
    try {
      String sql = "select distinct typ_dna from spojenia where linka ='" + linka + "'";

      Cursor mCur = database.rawQuery(sql, null);
      if (mCur != null) {
        mCur.moveToNext();
      }
      return mCur;
    } catch (SQLException mSQLException) {
      Log.e(TAG, "getZoznZast >>" + mSQLException.toString());
      throw mSQLException;
    }
  }

  /**
   * Metóda na získanie zastávok a trvanie medzi nimi z databázy
   *
   * @param linka
   * @param smer
   * @param odkial
   * @return Dáta z DB
   */
  public Cursor getTrvanie(String linka, String smer, String odkial) {
    try {
      String sql = "select trvanie, kam from trvanie where linka ='" + linka + "' and smer ='" + smer + "' and odkial='" + odkial + "' order by id";

      Cursor mCur = database.rawQuery(sql, null);
      if (mCur != null) {
        mCur.moveToNext();
      }
      return mCur;
    } catch (SQLException mSQLException) {
      Log.e(TAG, "getTrvanie >>" + mSQLException.toString());
      throw mSQLException;
    }
  }

  /**
   * Metóda na získanie časov z databázy
   * @param linka
   * @param smer
   * @param zastavka
   * @param den
   * @return Dáta z DB
   */
  public Cursor getCasyPreZast(String linka, String smer, String zastavka, String den)
  {
    try
    {
      String sql ="select cas from spojenia where linka='"+linka+"' and smer='"+smer+"' and zastavka='"+zastavka+"' and typ_dna like '%"+den+"%'";

      Cursor mCur = database.rawQuery(sql, null);
      if (mCur!=null)
      {
        mCur.moveToNext();
      }
      return mCur;
    }
    catch (SQLException mSQLException)
    {
      Log.e(TAG, "getZoznZast >>"+ mSQLException.toString());
      throw mSQLException;
    }
  }

}

package dbPackage;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Riso Janitor on 29. 11. 2016.
 */

public class DataBaseSelects {
    protected static final String TAG = "DataBaseSelects";
    private DataBaseHelper myDbHelper;
    private SQLiteDatabase mDb;

    public DataBaseSelects(Context context) {
//        myDbHelper = new DataBaseHelper(context);
//        mDb=myDbHelper.getReadDB();

        try {
            myDbHelper.createDataBase();
//            myDbHelper.openDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        } catch (SQLException sqle) {
            throw sqle;
        }


    }

    public Cursor getZastavkySql()
    {
        try
        {
            String sql ="SELECT zastavky FROM zastavky order by zastavky";

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getZastavkySql >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

//    public Cursor getZastavkySql(){
//        return myDbHelper.getZastavkySql();
//    }

    public void close()
    {
        myDbHelper.close();
    }
}

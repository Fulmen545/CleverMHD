package dbPackage;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Riso Janitor on 19. 11. 2016.
 */

public class DatabaseHandler {

    private final Context mContext;
    private SQLiteDatabase mDb;
    private AssetDatabaseHelper mDbHelper;
    protected static final String TAG = "DatabaseHandler";

    /**
     * Konštruktor pre triedu DatabaseHandler
     * @param mContext
     * @param name
     */
    public DatabaseHandler(Context mContext, String name) {

        this.mContext = mContext;
        mDbHelper= new AssetDatabaseHelper(mContext,name, null, 1);

    }

    /**
     * Metóda, ktorá prenesie databáza z počítača na telefón. Používaná
     * pri vytváraní aplikácie
     * @return Prenesenú databázu
     * @throws SQLException
     */
    public DatabaseHandler createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.importIfNotExist();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    /**
     * Metóda na vytvorenie databázy pred jej staihnutím
     * @return Vytvorenú databázu
     */
    public DatabaseHandler createReadDB(){
        mDbHelper.getReadDB();
        return this;
    }

    /**
     * Metóda na otvorenie databázy
     * @return Otvorenú databázu
     * @throws SQLException
     */
    public DatabaseHandler open() throws SQLException
    {
        try
        {

            mDbHelper.openDataBase();
//            mDbHelper.close();
            mDb = mDbHelper.getReadDB();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    /**
     * Metóda na získanie zastávok z databázy
     * @return Dáta z DB
     */
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

    /**
     * Metóda na získanie linky a smeru z databázy
     * @return Dáta z DB
     */
    public Cursor getLinkySql()
    {
        try
        {
            String sql ="SELECT distinct linka, smer FROM trvanie order by linka";

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getLinkySql >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    /**
     * Metóda na získanie linky a smeru pre konkrétnu zastávku z databázy
     * @param zastavka
     * @return Dáta z DB
     */
    public Cursor getSpecificLinka(String zastavka)
    {
        try
        {
            String sql ="SELECT distinct linka, smer FROM trvanie where odkial='"+ zastavka +"'";

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getSpecificLinka >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    /**
     * Metóda na získanie zastávky pre konkrétnu linku a smer z databázy
     * @param linka
     * @param smer
     * @return Dáta z DB
     */
    public Cursor getSpecificZastavka(String linka, String smer)
    {
        try
        {
            String sql ="SELECT distinct kam FROM trvanie where linka='"+linka+"' and smer='"+ smer +"' order by id";

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getSpecificZastavka >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    /**
     * Metóda na získanie zastávok a trvanie medzi nimi z databázy
     * @param linka
     * @param smer
     * @param odkial
     * @return Dáta z DB
     */
    public Cursor getTrvanie(String linka, String smer, String odkial)
    {
        try
        {
            String sql ="select trvanie, kam from trvanie where linka ='"+linka+"' and smer ='"+smer+"' and odkial='"+odkial+"' order by id";

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTrvanie >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    /**
     * Metóda na získanie zastávky pre konkrétnu linku a smer z databázy
     * @param linka
     * @param smer
     * @return Dáta z DB
     */
    public Cursor getZoznZast(String linka, String smer)
    {
        try
        {
            String sql ="select distinct kam from trvanie where linka ='"+linka+"' and smer ='"+smer+"' order by id";

            Cursor mCur = mDb.rawQuery(sql, null);
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

    /**
     * Metóda na získanie typu dna pre konkrétnu linku z databázy
     * @param linka
     * @return Dáta z DB
     */
    public Cursor getCountDen(String linka)
    {
        try
        {
            String sql ="select distinct typ_dna from spojenia where linka ='"+linka+"'";

            Cursor mCur = mDb.rawQuery(sql, null);
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

            Cursor mCur = mDb.rawQuery(sql, null);
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

    /**
     * Metóda na získanie priameho spojenia z databázy
     * @param odkial
     * @param kam
     * @param den
     * @param time
     * @param limit
     * @return Dáta z DB
     */
    public Cursor getSpojeniePriame(String odkial, String kam, int den, String time, int limit)
    {
        try
        {
            String sql ="select s.linka, s.zastavka, kam, s.cas, trvanie from spojenia s join trvanie a on (s.linka=a.linka) " +
                    "where odkial in ('"+odkial+"') and kam in ('"+kam+"') and trvanie is not null " +
                    "and s.zastavka=a.odkial and s.smer=a.smer and s.typ_dna like '%"+den+"%' and s.cas>'"+time+"' order by s.cas limit "+limit;

            Cursor mCur = mDb.rawQuery(sql, null);
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

    /**
     * Metóda na získanie prestupného spojenia z databázy
     * @param odkial
     * @param kam
     * @param den
     * @param time
     * @return Dáta z DB
     */
    public Cursor getSpojeniePrestupne(String odkial, String kam, int den, String time)
    {
        try
        {

            String sql = "select t1.linka as FinLinka1, t1.odkial as FinZas1, s1.cas as FinCas1, t1.trvanie as FinTrvanie1, " +
                    "t2.linka as FinLinka2, t2.odkial as FinZastavka2, t2.kam as FinKam, s2.cas as FinCas2, t2.trvanie as FinTrvanie2 from \n" +
                    "trvanie t1 join trvanie t2 on (t1.kam=t2.odkial)\n" +
                    " join spojenia s1 on (t1.linka=s1.linka and t1.smer=s1.smer and t1.odkial=s1.zastavka) \n" +
                    "join spojenia s2 on (t2.linka=s2.linka and t2.smer=s2.smer and t2.odkial=s2.zastavka) \n" +
                    "where t1.odkial in ('"+odkial+"') and t2.kam in ('"+kam+"') and t1.trvanie not null and t2.trvanie not null " +
                    "and s1.typ_dna like '%"+den+"%' and s1.cas>'"+time+"' and s1.cas<strftime('%H:%M','"+time+"','+2 hour') and t1.linka!=t2.linka " +
                    "and s2.cas>'"+time+"' and s2.cas<strftime('%H:%M','"+time+"','+2 hour') and s2.typ_dna like '%"+den+"%' order by FinCas1";

            Cursor mCur = mDb.rawQuery(sql, null);
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

    /**
     * Metóda na vloženie spojenia do databázy
     * @param nazov
     * @param odkial1
     * @param odkial2
     * @param odkial3
     * @param kam1
     * @param kam2
     * @param kam3
     */
    public void insertSpojenie (String nazov, String odkial1, String odkial2, String odkial3, String kam1, String kam2, String kam3){
        try {
            String sql = "insert into ulozene (nazov, odkial1, odkial2, odkial3, kam1, kam2, kam3) values ('"+nazov+"', '"+odkial1+"', '"+ odkial2+ "', '"+
                    odkial3+ "', '"+ kam1 +"', '"+ kam2 + "', '"+ kam3 + "')";
            this.mDb.execSQL(sql);

        } catch (SQLException mSQLException)
        {
            Log.e(TAG, "insertSpojenie >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    /**
     * Metóda na získanie uloženého spojenia z databázy
     * @return Dáta z DB
     */
    public Cursor getUlozSpoj()
    {
        try
        {
            String sql ="SELECT * FROM ulozene order by id";

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

    /**
     * Metóda na vymazanie uloženého spojenia z databázy
     * @param nazov
     */
    public void deleteSpojenie (String nazov){
        try {
            String sql = "delete from ulozene where nazov in ('"+nazov+"')";
            this.mDb.execSQL(sql);

        } catch (SQLException mSQLException)
        {
            Log.e(TAG, "insertSpojenie >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
}

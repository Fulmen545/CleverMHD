package dbPackage;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Riso Janitor on 19. 11. 2016.
 */

public class AssetDatabaseHelper extends SQLiteOpenHelper {

    private String dbName = "ke.db";
    private String db_path;
    private Context context;
    private SQLiteDatabase mDataBase;
    private static String TAG = "AssetDatabaseHelper";


    public AssetDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.dbName = name;
        this.context = context;
        db_path = "/data/data/" + context.getPackageName() + "/databases/";
    }

    /**
     * Metóda, ktorá skontroluje, či databáza existuje
     *
     * @return true ak existuje, false ak nie
     */
    public boolean checkExist() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = db_path + dbName;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            e.printStackTrace();
            // database does't exist yet.

        } catch (Exception ep) {
            ep.printStackTrace();
        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }


    /**
     * Vytvorí prázdnu databázu pre aplikáciu a potom ju vymení za vloženú databázu
     * */
    public void importIfNotExist() throws IOException {

        boolean dbExist = checkExist();

        if (dbExist) {
            // do nothing - database already exist
        } else {

            this.getReadableDatabase();

            try {

                copyDatabase();
                Log.i(TAG, "createDatabase database created");

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Metóda na získanie databázy
     */
    public SQLiteDatabase getReadDB (){
        try {
            copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mDataBase;
    }

    /**
     * Metóda na kopírovanie databázy
     * @throws IOException
     */
    private void copyDatabase() throws IOException {
        InputStream is = context.getAssets().open(dbName);

        OutputStream os = new FileOutputStream(db_path + dbName);

        byte[] buffer = new byte[4096];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
        os.flush();
        os.close();
        is.close();
        this.close();
    }

    /**
     * Metóda na otvorenie databázy
     * @return Boolean hodnotu
     * @throws SQLException
     */
    public boolean openDataBase() throws SQLException
    {
        String mPath = db_path + dbName;
        //Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CONFLICT_FAIL);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    /**
     * Metóda na zatvorenie databázy
     */
    @Override
    public synchronized void close()
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

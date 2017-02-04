package yaohf.com.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBOpenHelper extends ToSDCardSQLiteOpenHelper {


    private static final String DBNAME = "hframe_app.db";
    private static final int VERSION = 1;
    private static final String DIR = "hframe";

    public DBOpenHelper(Context context) {

        super(context, DIR, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBUser.SQL_CREATE_TABLE.toString());
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {

        }
    }

}

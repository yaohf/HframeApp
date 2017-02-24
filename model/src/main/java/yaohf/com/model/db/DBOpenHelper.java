package yaohf.com.model.db;

import android.content.Context;

public class DBOpenHelper extends ToSDCardSQLiteOpenHelper {


    private static final String DBNAME = "hframe_app.db";
    private static final int VERSION = 1;
    private static final String DIR = "hframe";

    private static DBOpenHelper instance;

    public static DBOpenHelper getInstance( Context context)
    {
        if(instance == null)
        {
            synchronized (DBOpenHelper.class)
            {
                if(instance == null)
                {
                    instance = new DBOpenHelper(context);
                }
            }
        }
        return instance;
    }

    private DBOpenHelper(Context context) {

        super(context, DIR, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(net.sqlcipher.database.SQLiteDatabase db) {
        db.execSQL(DBUser.SQL_CREATE_TABLE.toString());
    }

    @Override
    public void onUpgrade(net.sqlcipher.database.SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {

        }
    }
}

package yaohf.com.model.db;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import net.sqlcipher.database.SQLiteException;

import java.io.File;
import java.io.IOException;

/**
 * Created by viqgd on 2016/10/19.
 */

public abstract class ToSDCardSQLiteOpenHelper {
    private static final String TAG = ToSDCardSQLiteOpenHelper.class.getSimpleName();
    private final Context mContext;
    private final String mName;
    private final String mPath;//要放到SDCard下的文件夹路径
    private final SQLiteDatabase.CursorFactory mFactory;
    private final int mNewVersion;
    private SQLiteDatabase mDatabase = null;
    private boolean mIsInitializing = false;

    private static final String PASSWROD = "12345456";

    public ToSDCardSQLiteOpenHelper(Context context,String path,String name, SQLiteDatabase.CursorFactory factory, int version) {
        if (version < 1) throw new IllegalArgumentException("Version must be >= 1, was " + version);
        mContext = context;
        mPath=path;
        mName = name;
        mFactory = factory;
        mNewVersion = version;
    }

    public synchronized SQLiteDatabase getWritableDatabase() {
        if (mDatabase != null && mDatabase.isOpen() && !mDatabase.isReadOnly()) {
            return mDatabase;  // The database is already open for business
        }
        if (mIsInitializing) {
            throw new IllegalStateException("getWritableDatabase called recursively");
        }
        boolean success = false;
        SQLiteDatabase db = null;
        try {
            mIsInitializing = true;
            if (mName == null) {
                db = SQLiteDatabase.create(null,PASSWROD);
            } else {
                SQLiteDatabaseHook hook = new SQLiteDatabaseHook(){

                    @Override
                    public void preKey(SQLiteDatabase database) {

                    }

                    @Override
                    public void postKey(SQLiteDatabase database) {
//                        database.rawExecSQL("PRAGMA cipher_migrate;");  //最关键的一句！！！解密
                        database.rawExecSQL("PRAGMA cipher_use_hmac = OFF;");
                    }
                };

                String path = getDatabasePath(mPath,mName).getPath();
                db = SQLiteDatabase.openOrCreateDatabase(path,PASSWROD,mFactory,hook);
            }
            int version = db.getVersion();
            if (version != mNewVersion) {
                db.beginTransaction();
                try {
                    if (version == 0) {
                        onCreate(db);
                    } else {
                        onUpgrade(db, version, mNewVersion);
                    }
                    db.setVersion(mNewVersion);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }
            onOpen(db);
            success = true;
            return db;
        } finally {
            mIsInitializing = false;
            if (success) {
                if (mDatabase != null) {
                    try { mDatabase.close(); } catch (Exception e) { }
                }
                mDatabase = db;
            } else {
                if (db != null) db.close();
            }
        }
    }

    public synchronized SQLiteDatabase getReadableDatabase() {
        if (mDatabase != null && mDatabase.isOpen()) {
            return mDatabase;  // The database is already open for business
        }
        if (mIsInitializing) {
            throw new IllegalStateException("getReadableDatabase called recursively");
        }
        try {
            return getWritableDatabase();
        } catch (SQLiteException e) {
            if (mName == null) throw e;  // Can't open a temp database read-only!
            Log.e(TAG, "Couldn't open " + mName + " for writing (will try read-only):", e);
        }
        SQLiteDatabase db = null;
        try {
            mIsInitializing = true;
            String path = getDatabasePath(mPath,mName).getPath();
            db = SQLiteDatabase.openDatabase(path,PASSWROD, mFactory, SQLiteDatabase.OPEN_READWRITE);
            if (db.getVersion() != mNewVersion) {
                throw new SQLiteException("Can't upgrade read-only database from version " +
                        db.getVersion() + " to " + mNewVersion + ": " + path);
            }
            onOpen(db);
            Log.w(TAG, "Opened " + mName + " in read-only mode");
            mDatabase = db;
            return mDatabase;
        } finally {
            mIsInitializing = false;
            if (db != null && db != mDatabase) db.close();
        }
    }

    public synchronized void close() {
        if (mIsInitializing) throw new IllegalStateException("Closed during initialization");
        if (mDatabase != null && mDatabase.isOpen()) {
            mDatabase.close();
            mDatabase = null;
        }
    }

    public abstract void onCreate(SQLiteDatabase db);
    public abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    public void onOpen(SQLiteDatabase db) {}


    public File getDatabasePath(String dbpath, String dbName)
    {
        if (dbpath==null) {
            dbpath="";
        }
        File path = new File(Environment.getExternalStorageDirectory(),dbpath);//创建目录
        File f = new File(path.getPath(),dbName);// 创建文件
        if (!path.exists()) {// 目录存在返回false
            path.mkdirs();// 创建一个目录
        }
        if (!f.exists()) {// 文件存在返回false
            try {
                f.createNewFile();//创建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }
}

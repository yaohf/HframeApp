package yaohf.com.model.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import yaohf.com.model.bean.UserInfo;
import yaohf.com.tool.L;

/**
 * user 对象操作表
 */
public class DBUser implements DBInterface<UserInfo> {

    private Context mContext;
    private DBOpenHelper dbHelp;

    public DBUser(Context context) {
        mContext = context;
        if (dbHelp == null) {
            this.dbHelp = new DBOpenHelper(context);
            SQLiteDatabase db = dbHelp.getWritableDatabase();
            while (db.isDbLockedByCurrentThread()
                    || db.isDbLockedByOtherThreads()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 表名
     */
    public static final String TABLE_NAME = "user_info";

	/*
     * 表字段
	 */
    /**
     * 用户名
     */
    private static final String USER_NAME = "userName";
    /**
     * 密码
     */
    private static final String PWD = "pwd";
    /**
     * 真实姓名
     */
    private static final String REAL_NAME = "realName";
    /**
     * 头像
     */
    private static final String PHOTO = "photo";
    /**
     * 性别
     */
    private static final String SEX = "sex";

    /**
     * 建表语句
     */
    public static StringBuilder SQL_CREATE_TABLE = new StringBuilder()
            .append("CREATE TABLE IF NOT EXISTS ")
            .append(TABLE_NAME)
            .append("(")
            .append(USER_NAME)
            .append(" TEXT PRIMARY KEY NOT NULL,")
            .append(PWD)
            .append(" TEXT NOT NULL,")
            .append(REAL_NAME)
            .append(" TEXT,")
            .append(PHOTO)
            .append(" TEXT,")
            .append(SEX)
            .append(" INT")
            .append(")");
    /**
     * 删表语句
     */
    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_NAME;


    @Override
    public void addInfo(UserInfo item) {
        L.v(item);
        if (getInfo(item) != null)
            return;

        StringBuilder sql = new StringBuilder()
                .append("insert into ")
                .append(TABLE_NAME)
                .append("(")
                .append(USER_NAME)
                .append(",")
                .append(PWD)
                .append(",")
                .append(REAL_NAME)
                .append(",")
                .append(PHOTO)
                .append(",")
                .append(SEX)
                .append(")")
                .append("values(?,?,?,?,?)");

        synchronized (dbHelp) {
            SQLiteDatabase db = dbHelp.getWritableDatabase();
            if (db.isOpen()) {
                try {
                    db.beginTransaction();
                    db.execSQL(sql.toString(), new Object[]{item.getUserName(), item.getPassMd5(), item.getRealName(), item.getPhoto(), item.getSex()});
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    L.v(e.getMessage());
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                    db.close();
                    db = null;
                }
            }
        }
    }

    @Override
    public void deleteInfo(UserInfo item) {
        L.v(item);
        String sql = "delete from " + TABLE_NAME + " where " + USER_NAME + "=?";
        synchronized (dbHelp) {
            SQLiteDatabase db = dbHelp.getWritableDatabase();
            if (db.isOpen()) {
                try {
                    db.beginTransaction();
                    db.execSQL(sql, new Object[]{item.getUserName()});
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    L.v(e.getMessage());
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                    db.close();
                    db = null;
                }
            }
        }

    }

    @Override
    public UserInfo getInfo(UserInfo item) {
        L.v(item);
        String sql = "select * from " + TABLE_NAME + " where " + USER_NAME + "=?";
        UserInfo info = null;
        synchronized (dbHelp) {
            SQLiteDatabase db = dbHelp.getWritableDatabase();
            if (db.isOpen()) {
                Cursor cur = db.rawQuery(sql, new String[]{item.getUserName()});
                if (cur == null)
                    return null;
                if (cur.moveToNext()) {
                    info = new UserInfo();
                    info.setUserName(cur.getString(cur.getColumnIndex(USER_NAME)));
                    info.setPassMd5(cur.getString(cur.getColumnIndex(PWD)));
                    info.setPhoto(cur.getString(cur.getColumnIndex(PHOTO)));
                    info.setRealName(cur.getString(cur.getColumnIndex(REAL_NAME)));
                    info.setSex(cur.getInt(cur.getColumnIndex(SEX)));
                }
                cur.close();
                cur = null;
            }
            db.close();
            db = null;
        }
        return info;
    }

    @Override
    public void updateInfo(UserInfo item) {
        L.v(item);
        StringBuilder sql = new StringBuilder()
                .append("update  ")
                .append(TABLE_NAME)
                .append(" set")
                .append(USER_NAME)
                .append("=?,")
                .append(PWD)
                .append("=?,")
                .append(REAL_NAME)
                .append("=?,")
                .append(PHOTO)
                .append("=?,")
                .append(SEX)
                .append("=?")
                .append("where ")
                .append(USER_NAME)
                .append("=?");
        synchronized (dbHelp) {
            SQLiteDatabase db = dbHelp.getWritableDatabase();
            if (db.isOpen()) {
                try {
                    db.beginTransaction();
                    db.execSQL(sql.toString(), new Object[]{item.getUserName(), item.getPassMd5(), item.getRealName(), item.getPhoto(), item.getSex()});
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    L.v(e.getMessage());
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                    db.close();
                    db = null;
                }
            }
        }
    }
}

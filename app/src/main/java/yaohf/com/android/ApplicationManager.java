package yaohf.com.android;

import android.app.Application;

import net.sqlcipher.database.SQLiteDatabase;

import yaohf.com.api.net.VolleyManager;
import yaohf.com.core.AppAction;
import yaohf.com.core.AppActionImpl;
import yaohf.com.tool.crash.Cockroach;

/**
 * Application类，应用级别的操作都放这里
 */
public class ApplicationManager extends Application {

    private AppAction appAction;

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyManager.getInstance(this);
        appAction = new AppActionImpl(this);
        SQLiteDatabase.loadLibs(this);
        //注册捕获crash error
        Cockroach.install(new Cockroach.ExceptionHandler() {
            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
    public AppAction getAppAction() {
        return appAction;
    }
}
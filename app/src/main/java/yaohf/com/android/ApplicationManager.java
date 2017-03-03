package yaohf.com.android;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;

import net.sqlcipher.database.SQLiteDatabase;

import yaohf.com.api.net.VolleyManager;
import yaohf.com.core.AppAction;
import yaohf.com.core.AppActionImpl;
import yaohf.com.tool.crash.Cockroach;
import yaohf.com.tool.screen.ScreenRecorder;

/**
 * Application类，应用级别的操作都放这里
 */
public class ApplicationManager extends Application {

    private static ApplicationManager application;

    public static ApplicationManager getInstance() {
        return application;
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }

    private int result;

    public ScreenRecorder getmRecorder() {
        return mRecorder;
    }

    public void setmRecorder(ScreenRecorder mRecorder) {
        this.mRecorder = mRecorder;
    }

    private Intent intent;
    private MediaProjectionManager mMediaProjectionManager;

    private ScreenRecorder mRecorder;


    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public MediaProjectionManager getmMediaProjectionManager() {
        return mMediaProjectionManager;
    }

    public void setmMediaProjectionManager(MediaProjectionManager mMediaProjectionManager) {
        this.mMediaProjectionManager = mMediaProjectionManager;
    }

    private AppAction appAction;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
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
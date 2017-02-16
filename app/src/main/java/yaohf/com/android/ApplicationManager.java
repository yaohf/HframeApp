package yaohf.com.android;

import android.app.Application;

import yaohf.com.api.manager.VolleyManager;
import yaohf.com.core.AppAction;
import yaohf.com.core.AppActionImpl;


/**
 * Application类，应用级别的操作都放这里
 * @version 1.0
 */
public class ApplicationManager extends Application {

    private AppAction appAction;

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyManager.getInstance(this);
        appAction = new AppActionImpl(this);
    }

    public AppAction getAppAction() {
        return appAction;
    }
}

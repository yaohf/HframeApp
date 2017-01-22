package yaohf.com.api;

import yaohf.com.api.manager.IRequestManager;
import yaohf.com.api.manager.VolleyRequestManager;

/**
 * Created by viqgd on 2017/1/14.
 */

public class RequestFactory {


    /**
     * 返回使用何种网络请求的api接口对像
     * @return
     */
    public static IRequestManager getIRequestManager() {
//        return HttpRequestManager.getInstance();
        return VolleyRequestManager.getInstance();
    }
}

package yaohf.com.api;

import yaohf.com.api.manager.VolleyRequestManager;


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

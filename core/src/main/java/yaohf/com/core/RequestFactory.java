package yaohf.com.core;

/**
 * Created by viqgd on 2017/1/14.
 */

public class RequestFactory {


    /**
     * 返回使用何种网络请求的api接口对像
     * @return
     */
    public static IRequestManager getIRequestManager() {
        return HttpRequestManager.getInstance();
    }
}

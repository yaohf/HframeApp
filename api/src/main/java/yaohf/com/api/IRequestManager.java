package yaohf.com.api;

import java.util.Map;

/**
 * Created by viqgd on 2017/1/13.
 */

public interface IRequestManager<T> {
    /**
     * 同步　get
     * @param url
     * @param params
     * @return
     */
    T synchroGet(String url,Map<String,String> params);

    /**
     * 同步　post
     * @param url
     * @param params
     * @return
     */
    T synchroPost(String url, final Map<String,String> params);

    /**
     * 异步 get
     * @param url
     * @param params
     * @param callback
     * @return
     */
    void get(String url,Map<String,String> params,IRequestCallback callback);

    /**
     * 异步　post
     * @param url
     * @param params
     * @param callback
     * @return
     */
    void post(String url, final Map<String,String> params, IRequestCallback callback);



//    void put(String url, final Map<String,String> params,IRequestCallback callback);

//    void delete(String url,final Map<String,String> params,IRequestCallback callback);
}


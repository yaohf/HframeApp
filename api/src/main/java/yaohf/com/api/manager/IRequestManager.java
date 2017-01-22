package yaohf.com.api.manager;

import java.util.Map;

import yaohf.com.api.IRequestCallback;

/**
 * Created by viqgd on 2017/1/13.
 */

public interface IRequestManager {

    void get(String url,IRequestCallback callback);

    void post(String url, final Map<String,String> params, IRequestCallback callback);

    void put(String url, final Map<String,String> params,IRequestCallback callback);

    void delete(String url,final Map<String,String> params,IRequestCallback callback);
}


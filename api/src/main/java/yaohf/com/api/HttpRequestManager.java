package yaohf.com.api;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import yaohf.com.api.net.HttpEngine;
import yaohf.com.api.utils.L;
import yaohf.com.model.CouponBO;

/**
 * Created by viqgd on 2017/1/13.
 */

public class HttpRequestManager implements IRequestManager {

    private static HttpRequestManager instance;
    private HttpEngine httpEngine;

    private HttpRequestManager() {
        httpEngine = HttpEngine.getInstance();
    }

    public static HttpRequestManager getInstance() {
        if (instance == null)
            instance = new HttpRequestManager();
        return instance;
    }


    @Override
    public String get(String url) {
        return null;
    }

    @Override
    public String post(String url, String requestBodyJson) {
        L.v("url>>" + url);
        Type type = new TypeToken<ApiResponse<List<CouponBO>>>() {
        }.getType();
        String result = null;
        try {
            result = httpEngine.postHandle(url, requestBodyJson, type);
            L.v("result>>" + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String put(String url, String requestBodyJson) {
        return null;
    }

    @Override
    public String delete(String url, String requestBodyJson) {
        return null;
    }
}

package yaohf.com.api.manager;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import yaohf.com.api.ApiResponse;
import yaohf.com.api.IRequestCallback;
import yaohf.com.api.IRequestManager;
import yaohf.com.api.net.HttpEngine;
import yaohf.com.tool.JsonUtil;
import yaohf.com.tool.L;

/**
 * http Manager
 * @param <T>
 */
public class HttpRequestManager<T> implements IRequestManager {

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
    public T synchroGet(String url, Map params) {
        L.v("start>>" + url);
        Type type = new TypeToken<ApiResponse<List<T>>>() {
        }.getType();
        T result = null;
        try {
            result = httpEngine.getHandle(url, params, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        L.v("end");
        return result;
    }

    @Override
    public T synchroPost(String url, Map params) {
        L.v("start>>" + url);
        Type type = new TypeToken<ApiResponse<List<T>>>() {
        }.getType();

        String requestBodyJson = JsonUtil.getJsonStrs(params);
        T result = null;
        try {
            result = httpEngine.postHandle(url, requestBodyJson, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        L.v("end");
        return result;
    }



    @Override
    public void get(String url,Map params,IRequestCallback callback) {
        L.v("start>>" + url);
        Type type = new TypeToken<ApiResponse<List<T>>>() {
        }.getType();
        T result = null;
        try {
            result = httpEngine.getHandle(url, params, type);
            callback.onSuccess(result);
        } catch (IOException e) {
            callback.onFailure("http io  error",e.getMessage());
            e.printStackTrace();
        }
        L.v("end");
    }

    @Override
    public void post(String url, Map params, IRequestCallback callback) {
        L.v("start>>" + url);
        Type type = new TypeToken<ApiResponse<List<T>>>() {
        }.getType();

        String requestBodyJson = JsonUtil.getJsonStrs(params);
        T result = null;
        try {
            result = httpEngine.postHandle(url, requestBodyJson, type);
            callback.onSuccess(result);
        } catch (IOException e) {
            callback.onFailure("http io  error",e.getMessage());
            e.printStackTrace();
        }
        L.v("end");
    }
}

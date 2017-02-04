package yaohf.com.api.manager;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import yaohf.com.api.ApiResponse;
import yaohf.com.api.IRequestCallback;
import yaohf.com.api.net.HttpEngine;
import yaohf.com.model.bean.UserInfo;
import yaohf.com.tool.JsonUtil;
import yaohf.com.tool.L;

/**
 * Created by viqgd on 2017/1/13.
 */

public class HttpRequestManager implements IRequestManager{

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
    public void get(String url,IRequestCallback callback) {

    }

    @Override
    public void post(String url, Map<String,String> params, IRequestCallback callback) {
        L.v("url>>" + url);
        Type type = new TypeToken<ApiResponse<List<UserInfo>>>() {
        }.getType();

        String requestBodyJson = JsonUtil.getJsonStrs(params);
        String result = null;
        try {
            result = httpEngine.postHandle(url, requestBodyJson, type);
            if(JsonUtil.isJsonType(result))
            {
                callback.onSuccess(result);
            }else{
                callback.onFailure("json error",result);
            }
            L.v("result>>" + result);
        } catch (IOException e) {
            callback.onFailure("http io  error",e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void put(String url,  Map<String,String> params,IRequestCallback callback) {
    }

    @Override
    public void delete(String url,  Map<String,String> params,IRequestCallback callback) {
    }


}

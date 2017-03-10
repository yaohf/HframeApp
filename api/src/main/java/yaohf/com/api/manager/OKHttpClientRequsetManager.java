package yaohf.com.api.manager;

import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.Map;

import yaohf.com.api.HttpUtils;
import yaohf.com.api.IRequestCallback;
import yaohf.com.api.IRequestManager;
import yaohf.com.api.net.OkHttpClientManager;

/**
 * Created by viqgd on 2017/2/21.
 */

public class OKHttpClientRequsetManager  implements IRequestManager{


    private static OKHttpClientRequsetManager instance;

    private OKHttpClientRequsetManager(){}

    public static  OKHttpClientRequsetManager getInstance()
    {
        if(instance == null)
        {
            synchronized (OKHttpClientRequsetManager.class)
            {
                if(instance == null)
                {
                    instance = new OKHttpClientRequsetManager();
                }
            }
        }
        return instance;
    }

    @Override
    public Object synchroGet(String url, Map params) {
        String data = url + "?" + HttpUtils.getJoinParams(params);
        String result = null;
        try {
            result = OkHttpClientManager.getAsString(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Object synchroPost(String url, Map params)  {
        String result = null;
        try {
            result = OkHttpClientManager.post(url,params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void get(String url, Map params, final IRequestCallback callback) {

        String data = url + "?" + HttpUtils.getJoinParams(params);
        OkHttpClientManager.getAsyn(data, new OkHttpClientManager.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                callback.onFailure(e.getMessage(),request.toString());
            }

            @Override
            public void onResponse(Object response) {
                callback.onSuccess(response);
            }
        });
    }

    @Override
    public void post(String url, Map params, final IRequestCallback callback) {
        OkHttpClientManager.postAsyn(url, new OkHttpClientManager.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                callback.onFailure(e.getMessage(),request.toString());
            }

            @Override
            public void onResponse(Object response) {
                callback.onSuccess(response);
            }
        },params);
    }
}

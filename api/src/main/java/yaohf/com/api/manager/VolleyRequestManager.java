package yaohf.com.api.manager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import yaohf.com.api.HttpUtils;
import yaohf.com.api.IRequestCallback;
import yaohf.com.api.IRequestManager;
import yaohf.com.api.net.VolleyManager;
import yaohf.com.tool.JsonUtil;
import yaohf.com.tool.L;

/**
 * Volley 实现
 */

public class VolleyRequestManager<T> implements IRequestManager{

    private static VolleyRequestManager instance;

    private VolleyRequestManager() {

    }

    public static VolleyRequestManager getInstance() {
        if(instance == null)
        {
            synchronized (VolleyRequestManager.class)
            {
                if (instance == null)
                    instance = new VolleyRequestManager();
            }
        }
        return instance;
    }



    @Override
    public T synchroGet(String url, Map params)  {
        L.v("start >>" + url);
        String data = url;
        if(params != null)
        {
            data  = url + "?" + HttpUtils.getJoinParams(params);
        }
        RequestFuture future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(data, future,future);
        VolleyManager.getRequestQueue().add(request);
        T result = null;
        try{
            result = (T) future.get();
            future.get(3000, TimeUnit.SECONDS);//添加请求超时
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        L.v("end >>" + result);
        return result;
    }

    @Override
    public T synchroPost(String url, Map params) {
        L.v("start >>" + url);
        String data = url;
        if(params != null)
        {
            data  = url + "?" + HttpUtils.getJoinParams(params);
        }
        RequestFuture future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(Request.Method.POST,data, future,future);
        T result = null;
        try{
            result = (T) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        VolleyManager.getRequestQueue().add(request);
        L.v("end");
        return result;
    }

    @Override
    public void get(String url, Map params, final IRequestCallback callback) {
        final String data = url + "?" + HttpUtils.getJoinParams(params);
        StringRequest request = new StringRequest(Request.Method.GET, data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (JsonUtil.isJsonType(response))
                    callback.onSuccess(response);
                else
                    callback.onFailure("json error", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure("volley", error.getMessage());
            }
        });
        VolleyManager.getRequestQueue().add(request);

    }

    @Override
    public void post(String url, final Map params, final IRequestCallback callback) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (JsonUtil.isJsonType(response))
                    callback.onSuccess(response);
                else
                    callback.onFailure("json error", response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFailure("volley", error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        VolleyManager.getRequestQueue().add(request);

    }


}

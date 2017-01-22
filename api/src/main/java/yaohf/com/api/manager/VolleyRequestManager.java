package yaohf.com.api.manager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

import yaohf.com.api.IRequestCallback;

/**
 * Volley 实现
 */

public class VolleyRequestManager implements IRequestManager {

    private static VolleyRequestManager instance;

    private VolleyRequestManager() {

    }

    public static VolleyRequestManager getInstance() {
        if (instance == null)
            instance = new VolleyRequestManager();
        return instance;
    }

    @Override
    public void get(final String url, final IRequestCallback callback) {
        final String str = null;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFailure("volley", error.getMessage());
                    }
                });

    }

    @Override
    public void post(String url, final Map<String, String> params, final IRequestCallback callback) {


        final String str = null;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
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
    }

    @Override
    public void put(String url, final Map<String, String> params, IRequestCallback callback) {
    }

    @Override
    public void delete(String url, final Map<String, String> params, IRequestCallback callback) {
    }
}

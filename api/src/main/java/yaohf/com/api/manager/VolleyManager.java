package yaohf.com.api.manager;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * VolleyManager Volley 连接
 */

public class VolleyManager {
    private static RequestQueue queue ;
    private static  VolleyManager instance;

    private  Context mContext;
    public static  VolleyManager getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new VolleyManager(context);
        }
        return instance;
    }
    private VolleyManager(Context context)
    {
        mContext = context;
        queue = Volley.newRequestQueue(mContext);
    }
    public static RequestQueue getRequestQueue()
    {
        if(queue == null)
            return null;
        return queue;
    }



}

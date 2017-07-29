package yaohf.com.api;

import okhttp3.OkHttpClient;
import yaohf.com.api.net.OkHttpClientManager;

/**
 * Created by viqgd on 2017/3/16.
 */

public class OkHttpUtils {

    private static OkHttpUtils instance;
    private static  OkHttpClient mHttpClient;

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if(okHttpClient == null) {
            mHttpClient = new OkHttpClient();
        }else
        {
            mHttpClient = okHttpClient;
        }
    }


    public static OkHttpUtils initClient(OkHttpClient okHttpClient)
    {
        if(instance == null)
        {
            synchronized (OkHttpClientManager.class)
            {
                if(instance == null)
                {
                    instance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return instance;
    }
    public static OkHttpClient getOkHttpClient()
    {
        return  mHttpClient;
    }



}

package yaohf.com.api.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import yaohf.com.api.OkHttpUtils;
import yaohf.com.tool.ImageUtils;
import yaohf.com.tool.L;

/**
 * Created by viqgd on 2017/2/21.
 */

public class OkHttpClientManager {

    private volatile static OkHttpClientManager instance;
    private OkHttpClient mHttpClient;
    private Handler mHanlder;
    private Gson mGson;

    private Platform mPlatform;

    private  OkHttpClientManager() {
        if(mHttpClient == null) {
            mHttpClient = OkHttpUtils.getOkHttpClient();
        }
        mHanlder = new Handler(Looper.getMainLooper());
        mGson = new Gson();
        mPlatform = Platform.get();
    }


    public static OkHttpClientManager getInstance()
    {
        if(instance == null)
        {
            synchronized (OkHttpClientManager.class)
            {
                if(instance == null)
                {
                    instance = new OkHttpClientManager();
                }
            }
        }
        return instance;
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return Response
     */
    private Response _getAsyn(String url) throws IOException
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return 字符串
     */
    private String _getAsString(String url) throws IOException
    {
        Response execute = _getAsyn(url);
        return execute.body().string();
    }


    /**
     * 异步的get请求
     *
     * @param url
     * @param callback
     */
    private void _getAsyn(String url, final ResultCallback callback)
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        deliveryResult(callback, request);
    }


    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return
     */
    private Response _post(String url, Param... params) throws IOException
    {
        Request request = buildPostRequest(url, params);
        Response response = mHttpClient.newCall(request).execute();
        return response;
    }
    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return
     */
    private String _postMap(String url, Map<String,String> params) throws IOException
    {
        Param [] par = map2Params(params);
        Request request = buildPostRequest(url, par);
        Response response = mHttpClient.newCall(request).execute();
        return response.body().string();
    }


    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return 字符串
     */
    private String _postAsString(String url, Param... params) throws IOException
    {
        Response response = _post(url, params);
        return response.body().string();
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final ResultCallback callback, Param... params)
    {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final ResultCallback callback, Map<String, String> params)
    {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        deliveryResult(callback, request);
    }


    /**
     * 同步基于post的文件上传
     *
     * @param params
     * @return
     */
    private Response _post(String url, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        return mHttpClient.newCall(request).execute();
    }

    private Response _post(String url, File file, String fileKey) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        return mHttpClient.newCall(request).execute();
    }

    private Response _post(String url, File file, String fileKey, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        return mHttpClient.newCall(request).execute();
    }

    /**
     * 异步基于post的文件上传
     *
     * @param url
     * @param callback
     * @param files
     * @param fileKeys
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件不带参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File file, String fileKey) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件且携带其他form参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @param params
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File file, String fileKey, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        deliveryResult(callback, request);
    }
    /**
     * 异步下载文件
     *
     * @param url
     * @param destFileDir 本地文件存储的文件夹
     * @param callback
     */
    private void _downloadAsyn(final String url, final String destFileDir, final ResultCallback callback)
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = mHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedStringCallback(call.request(), e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try
                {
                    is = response.body().byteStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    File file = new File(destFileDir, getFileName(url));
                    fos = new FileOutputStream(file);
                    while ((len = bis.read(buf)) != -1)
                    {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    sendSuccessResultCallback(file.getAbsolutePath(), callback);
                } catch (IOException e)
                {
                    sendFailedStringCallback(response.request(), e, callback);
                } finally
                {
                    try
                    {
                        if (is != null) is.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        if (fos != null) fos.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 加载图片
     *
     * @param view
     * @param url
     * @throws IOException
     */
    private void _displayImage(final ImageView view, final String url, final int errorResId)
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {
                setErrorResId(view, errorResId);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                try
                {
                    is = response.body().byteStream();
                    ImageUtils.ImageSize actualImageSize = ImageUtils.getImageSize(is);
                    ImageUtils.ImageSize imageViewSize = ImageUtils.getImageViewSize(view);
                    int inSampleSize = ImageUtils.calculateInSampleSize(actualImageSize, imageViewSize);
                    try
                    {
                        is.reset();
                    } catch (IOException e)
                    {
                        response = _getAsyn(url);
                        is = response.body().byteStream();
                    }

                    BitmapFactory.Options ops = new BitmapFactory.Options();
                    ops.inJustDecodeBounds = false;
                    ops.inSampleSize = inSampleSize;
                    final Bitmap bm = BitmapFactory.decodeStream(is, null, ops);
                    mHanlder.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            view.setImageBitmap(bm);
                        }
                    });
                } catch (Exception e)
                {
                    setErrorResId(view, errorResId);

                } finally
                {
                    if (is != null) try
                    {
                        is.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setErrorResId(final ImageView view, final int errorResId)
    {
        mHanlder.post(new Runnable()
        {
            @Override
            public void run()
            {
                view.setImageResource(errorResId);
            }
        });
    }



    private String getFileName(String path)
    {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }



    private Request buildMultipartFormRequest(String url, File[] files,String [] filekeys,Param [] params)
    {
        params = validateParam(params);
        MultipartBody.Builder builder = new MultipartBody.Builder("AaB03x")
                .setType(MultipartBody.FORM);
        for(Param p : params)
        {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + p.key + "\""), RequestBody.create(null,p.value));
        }
        if(files !=null)
        {
            RequestBody fileBody = null;
            final int count = files.length;
            for(int i = 0; i < count; i++)
            {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)),file);
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + filekeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }
        RequestBody body = builder.build();
        return new  Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

    private String guessMimeType(String path)
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null)
        {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private Param[] validateParam(Param[] params) {
        if(params == null) return new Param[0];
        else return params;
    }


    /**
     * 异步处理
     * @param callback
     * @param request
     */
    private void deliveryResult(final ResultCallback callback,Request request)
    {
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedStringCallback(call.request(),e,callback);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                L.v("response >>" + str);
                if(callback.mType == String.class)
                {
                    sendSuccessResultCallback(str,callback);
                }else
                {
//                    Object obj = mGson.fromJson(str,callback.mType);
                    sendSuccessResultCallback(str,callback);
                }
            }
        });
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback)
    {
        mHanlder.post(new Runnable() {
            @Override
            public void run() {
                if(callback != null)
                    callback.onError(request,e);
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback)
    {
        mHanlder.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null)
                {
                    callback.onResponse(object);
                }
            }
        });
    }

    /**
     * Post 入参请求
     * @param url
     * @param params
     * @return
     */
    private Request buildPostRequest(String url, Param[] params)
    {
        if (params == null)
        {
            params = new Param[0];
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params)
        {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }


    private Param[] map2Params(Map<String, String> params)
    {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries)
        {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }


    //*************对外公布的方法************


    public static Response getAsyn(String url) throws IOException
    {
        return getInstance()._getAsyn(url);
    }


    public static String getAsString(String url) throws IOException
    {
        return getInstance()._getAsString(url);
    }

    public static void getAsyn(String url, ResultCallback callback)
    {
        getInstance()._getAsyn(url, callback);
    }

    public static Response post(String url, Param... params) throws IOException
    {
        return getInstance()._post(url, params);
    }
    public static String post(String url, Map<String,String> params) throws IOException
    {
        return getInstance()._postMap(url, params);
    }

    public static String postAsString(String url, Param... params) throws IOException
    {
        return getInstance()._postAsString(url, params);
    }

    public static void postAsyn(String url, final ResultCallback callback, Param... params)
    {
        getInstance()._postAsyn(url, callback, params);
    }


    public static void postAsyn(String url, final ResultCallback callback, Map<String, String> params)
    {
        getInstance()._postAsyn(url, callback, params);
    }


    public static Response post(String url, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        return getInstance()._post(url, files, fileKeys, params);
    }

    public static Response post(String url, File file, String fileKey) throws IOException
    {
        return getInstance()._post(url, file, fileKey);
    }

    public static Response post(String url, File file, String fileKey, Param... params) throws IOException
    {
        return getInstance()._post(url, file, fileKey, params);
    }

    public static void postAsyn(String url, ResultCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        getInstance()._postAsyn(url, callback, files, fileKeys, params);
    }


    public static void postAsyn(String url, ResultCallback callback, File file, String fileKey) throws IOException
    {
        getInstance()._postAsyn(url, callback, file, fileKey);
    }


    public static void postAsyn(String url, ResultCallback callback, File file, String fileKey, Param... params) throws IOException
    {
        getInstance()._postAsyn(url, callback, file, fileKey, params);
    }

    public static void displayImage(final ImageView view, String url, int errorResId) throws IOException
    {
        getInstance()._displayImage(view, url, errorResId);
    }


    public static void displayImage(final ImageView view, String url)
    {
        getInstance()._displayImage(view, url, -1);
    }

    public static void downloadAsyn(String url, String destDir, ResultCallback callback)
    {
        getInstance()._downloadAsyn(url, destDir, callback);
    }

    //****************************



    /**
     * CallBack 返回处理，解析Json
     * @param <T>
     */
    public static abstract class ResultCallback<T>
    {
        Type mType;

        public ResultCallback()
        {


//            mType = getSuperclassTypeParameter(getClass());
        }

//        static Type getSuperclassTypeParameter(Class<?> subclass)
//        {
//
//            Type superclass = subclass.getGenericSuperclass();
//            if (superclass instanceof Class)
//            {
//                throw new RuntimeException("Missing type parameter.");
//            }
//            ParameterizedType parameterized = (ParameterizedType) superclass;
//            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
//        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }

    /**
     * Post 请求参数
     */
    public static class Param
    {
        public Param()
        {
        }

        public Param(String key, String value)
        {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;
    }

}

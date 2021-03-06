package yaohf.com.api.net;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import yaohf.com.api.HttpUtils;
import yaohf.com.tool.L;


/**
 * Http引擎处理类
 * @version 1.0
 */
public class HttpEngine{
    private final static String TAG = "HttpEngine";
    private final static String REQUEST_POST = "POST";
    private final static String REQUEST_GET = "GET";
    private final static int TIME_OUT = 30000 * 10;

    private static HttpEngine instance = null;

    private HttpEngine() {

    }

    public static HttpEngine getInstance() {
        if (instance == null) {
            instance = new HttpEngine();
        }
        return instance;
    }


    /**
     * http get request
     * @param url　请求地址
     * @param paramsMap　请求参数
     * @param typeOfT  返回实体类型
     */
    public <T> T getHandle(String url ,Map paramsMap, Type typeOfT) throws IOException {
        String data = url + "?" + HttpUtils.getJoinParams(paramsMap);
        // 打印出请求
        L.v("request: " + data);
        HttpURLConnection connection = getConnection(url,REQUEST_GET);
        connection.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
        connection.connect();
        if (connection.getResponseCode() == 200) {
            // 获取响应的输入流对象
            InputStream is = connection.getInputStream();
            // 创建字节输出流对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 定义读取的长度
            int len = 0;
            // 定义缓冲区
            byte buffer[] = new byte[1024];
            // 按照缓冲区的大小，循环读取
            while ((len = is.read(buffer)) != -1) {
                // 根据读取的长度写入到os对象中
                baos.write(buffer, 0, len);
            }
            // 释放资源
            is.close();
            is = null;
            baos.close();
            baos = null;

            connection.disconnect();
            connection = null;
            // 返回字符串
            final String result = new String(baos.toByteArray());
            // 打印出结果
            L.v(TAG, "response: " + result);
            Gson gson = new Gson();
            return gson.fromJson(result, typeOfT);
        } else {
            connection.disconnect();
            connection = null;
            return null;
        }
    }

    /**
     *  http post requset
     * @param requestUrl　请求地址
     * @param paramsMap　请求参数
     * @param typeOfT 返回实体类型
     */
    public <T> T postHandle(String requestUrl, String paramsMap, Type typeOfT) throws IOException {
        String data = paramsMap;
        // 打印出请求
        L.v("requestUrl>>" + requestUrl);
        HttpURLConnection connection = getConnection(requestUrl,REQUEST_POST);
        connection.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
        connection.connect();
        OutputStream os = connection.getOutputStream();
        os.write(data.getBytes());
        os.flush();
        os.close();
        os = null;
        if (connection.getResponseCode() == 200) {
            // 获取响应的输入流对象
            InputStream is = connection.getInputStream();
            // 创建字节输出流对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 定义读取的长度
            int len = 0;
            // 定义缓冲区
            byte buffer[] = new byte[1024];
            // 按照缓冲区的大小，循环读取
            while ((len = is.read(buffer)) != -1) {
                // 根据读取的长度写入到os对象中
                baos.write(buffer, 0, len);
            }
            // 释放资源
            is.close();
            baos.close();
            connection.disconnect();
            // 返回字符串
            final String result = new String(baos.toByteArray());
            // 打印出结果
            L.v("result>>" + result);
            Gson gson = new Gson();
            return gson.fromJson(result, typeOfT);
        } else {
            connection.disconnect();
            return null;
        }
    }

    // 获取connection
    private HttpURLConnection getConnection(String requsetUrl,String requsetType ) {
        HttpURLConnection connection = null;
        // 初始化connection
        try {
            // 根据地址创建URL对象
            URL url = new URL(requsetUrl);
            // 根据URL对象打开链接
            connection = (HttpURLConnection) url.openConnection();
            // 设置请求的方式
            connection.setRequestMethod(requsetType);
            // 发送POST请求必须设置允许输入，默认为true
            connection.setDoInput(true);
            // 发送POST请求必须设置允许输出
            connection.setDoOutput(true);
            // 设置不使用缓存
            connection.setUseCaches(false);
            // 设置请求的超时时间
            connection.setReadTimeout(TIME_OUT);
            connection.setConnectTimeout(TIME_OUT);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Response-Type", "json");
            connection.setChunkedStreamingMode(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

}

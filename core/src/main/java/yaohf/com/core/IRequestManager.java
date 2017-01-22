package yaohf.com.core;

/**
 * Created by viqgd on 2017/1/13.
 */

public interface IRequestManager {

    <T> T get(String url);

    <T> T post(String url, String requestBodyJson);

    <T> T put(String url, String requestBodyJson);

    <T> T delete(String url, String requestBodyJson);
}


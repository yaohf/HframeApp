package yaohf.com.core;

/**
 * Created by viqgd on 2017/1/13.
 */

public interface IRequestCallback <T> {
    /**
     * 成功时调用
     *
     * @param data 返回的数据
     */
    public void onSuccess(T data);

    /**
     * 失败时调用
     *
     * @param errorEvent 错误码
     * @param message    错误信息
     */
    public void onFailure(String errorEvent, String message);



}
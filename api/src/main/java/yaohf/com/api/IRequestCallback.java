package yaohf.com.api;

public abstract class IRequestCallback <T> {
    /**
     * 成功时调用
     *
     * @param data 返回的数据
     */
    public abstract void onSuccess(T data);

    /**
     * 失败时调用
     *
     * @param errorEvent 错误码
     * @param message    错误信息
     */
    public  abstract void onFailure(String errorEvent, String message);

    public void onBeforeLoading() {
        // empty body
    }



}
package yaohf.com.core;

import yaohf.com.api.IRequestCallback;

/**
 * 接收app层的各种Action
 */
public interface AppAction {

    /**
     * 登录
     *
     * @param loginName 登录名
     * @param password  密码
     * @param callback  回调监听器
     */
    public void login(String loginName, String password, IRequestCallback callback);

}

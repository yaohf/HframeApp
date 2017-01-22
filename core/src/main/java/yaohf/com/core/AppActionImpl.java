/**
 * Copyright (C) 2015. Keegan小钢（http://keeganlee.me）
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package yaohf.com.core;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yaohf.com.core.utils.JsonUtil;

/**
 * AppAction接口的实现类
 *
 * @version 1.0
 */
public class AppActionImpl implements AppAction {

    private final static int LOGIN_OS = 1; // 表示Android
    private final static int PAGE_SIZE = 20; // 默认每页20条

    private Context context;
    private IRequestManager requestManager;

    public AppActionImpl(Context context) {
        this.context = context;
        requestManager = RequestFactory.getIRequestManager();
    }

    @Override
    public void sendSmsCode(final String phoneNum, final IRequestCallback callback) {
        // 参数检查
        if (TextUtils.isEmpty(phoneNum)) {
            if (callback != null) {
                callback.onFailure(ErrorEvent.PARAM_NULL, "手机号为空");
            }
            return;
        }
        Pattern pattern = Pattern.compile("1\\d{10}");
        Matcher matcher = pattern.matcher(phoneNum);
        if (!matcher.matches()) {
            if (callback != null) {
                callback.onFailure(ErrorEvent.PARAM_ILLEGAL, "手机号不正确");
            }
            return;
        }

    }

    @Override
    public void register(final String phoneNum, final String code, final String password, final IRequestCallback callback) {
        // 参数检查
        if (TextUtils.isEmpty(phoneNum)) {
            if (callback != null) {
                callback.onFailure(ErrorEvent.PARAM_NULL, "手机号为空");
            }
            return;
        }
        if (TextUtils.isEmpty(code)) {
            if (callback != null) {
                callback.onFailure(ErrorEvent.PARAM_NULL, "验证码为空");
            }
            return;
        }
        if (TextUtils.isEmpty(password)) {
            if (callback != null) {
                callback.onFailure(ErrorEvent.PARAM_NULL, "密码为空");
            }
            return;
        }
        Pattern pattern = Pattern.compile("1\\d{10}");
        Matcher matcher = pattern.matcher(phoneNum);
        if (!matcher.matches()) {
            if (callback != null) {
                callback.onFailure(ErrorEvent.PARAM_ILLEGAL, "手机号不正确");
            }
            return;
        }


    }

    @Override
    public void login(final String loginName, final String password, final IRequestCallback callback) {
        // 参数检查
        if (TextUtils.isEmpty(loginName)) {
            if (callback != null) {
                callback.onFailure(ErrorEvent.PARAM_NULL, "登录名为空");
            }
            return;
        }
        if (TextUtils.isEmpty(password)) {
            if (callback != null) {
                callback.onFailure(ErrorEvent.PARAM_NULL, "密码为空");
            }
            return;
        }


    }

    @Override
    public void listCoupon(final int currentPage, final  IRequestCallback callback) {
        // 参数检查
        if (currentPage < 0) {
            if (callback != null) {
                callback.onFailure(ErrorEvent.PARAM_ILLEGAL, "当前页数小于零");
            }
        }


    }

    @Override
    public void test(final String url, final String requsetJson, final IRequestCallback callback) {

        // 请求Api
        new AsyncTask<String,String,String> ()
        {
            @Override
            protected String doInBackground(String... strs) {

                return requestManager.post(url, requsetJson);
            }

            @Override
            protected void onPostExecute(String response) {
                if (callback != null && response != null) {
                    if (JsonUtil.isJsonType(response)) {
                        callback.onSuccess(response);
                    } else {
                        callback.onFailure("json error", response);
                    }
                }
            }
        }.execute();
    }

}

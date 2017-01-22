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

import java.util.HashMap;
import java.util.Map;

import yaohf.com.api.Api;
import yaohf.com.api.ErrorEvent;
import yaohf.com.api.IRequestCallback;
import yaohf.com.api.RequestFactory;
import yaohf.com.api.manager.IRequestManager;

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
        final Map<String,String> params = new HashMap<String,String>();
        params.put("username",loginName);
        params.put("password",password);
        new AsyncTask<Void,Void,Void> ()
        {
            @Override
            protected Void doInBackground(Void... strs) {
                return requestManager.post(Api.TEST_HTTP_URL, params,callback);
            }
        }.execute();
    }

}

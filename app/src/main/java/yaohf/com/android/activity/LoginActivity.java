/**
 * Copyright (C) 2015. Keegan小钢（http://keeganlee.me）
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package yaohf.com.android.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import yaohf.com.android.R;
import yaohf.com.api.IRequestCallback;
import yaohf.com.api.utils.L;


/**
 * 登录
 * @version 1.0
 */
public class LoginActivity extends BaseActivity {

    private EditText phoneEdit;
    private EditText passwordEdit;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 初始化View
        initViews();
    }
    @Override
    protected void activityHanlderMessage(Message m) {

    }
    // 初始化View
    private void initViews() {
        phoneEdit = (EditText) findViewById(R.id.edit_phone);
        passwordEdit = (EditText) findViewById(R.id.edit_password);
        loginBtn = (Button) findViewById(R.id.btn_login);
    }
    // 准备登录
    public void toLogin(View view) {
        String loginName = phoneEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        loginBtn.setEnabled(false);

        this.appAction.login(loginName,password, new IRequestCallback() {
            @Override
            public void onSuccess(Object data) {
                L.v("data>>" + data);
                Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(String errorEvent, String message) {
                L.v("message>>" + message);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
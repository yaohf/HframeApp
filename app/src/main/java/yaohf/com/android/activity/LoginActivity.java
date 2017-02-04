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
package yaohf.com.android.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import yaohf.com.android.R;
import yaohf.com.api.IRequestCallback;
import yaohf.com.model.bean.UserInfo;
import yaohf.com.model.db.DBUser;
import yaohf.com.tool.EncryptUtil;
import yaohf.com.tool.L;


/**
 * 登录
 * @version 1.0
 */
public class LoginActivity extends BaseActivity {

    private EditText phoneEdit;
    private EditText passwordEdit;
    private Button loginBtn;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 初始化View
        initViews();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            verifyStoragePermissions(this);
        }
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



        DBUser dbUser = new DBUser(mContext);
        UserInfo info = new UserInfo(loginName, EncryptUtil.makeMD5(password), "", "姚益", 1);
        dbUser.addInfo(info);

        this.appAction.login(loginName, password, new IRequestCallback() {
            @Override
            public void onSuccess(Object data) {
                L.v("data>>" + data);
                Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
                startActivity(RecyclerActivity.class, null);
                finish();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                L.v("message>>" + message);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                startActivity(RecyclerActivity.class, null);
                finish();
            }
        });
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }


    }
}
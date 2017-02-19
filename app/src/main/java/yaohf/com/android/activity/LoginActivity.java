package yaohf.com.android.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Message;
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
import yaohf.com.tool.permission.FramePermission;
import yaohf.com.tool.permission.PermissionNo;
import yaohf.com.tool.permission.PermissionYes;


/**
 * 登录
 *
 * @version 1.0
 */
public class LoginActivity extends BaseActivity {

    private EditText phoneEdit;
    private EditText passwordEdit;
    private Button loginBtn;

    private static final int SDCARD_REQEST_CODE = 100;

    private static final  String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

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

    public void requsetSDCardPermission() {
        FramePermission.with(this)
                .requestCode(SDCARD_REQEST_CODE)
                .permission(PERMISSIONS_STORAGE)
                .send();
    }


    @PermissionYes(SDCARD_REQEST_CODE)
    private void requestSdcardSuccess(){
        L.v("GRANT ACCESS SDCARD!");
    }
    @PermissionNo(SDCARD_REQEST_CODE)
    private void requestSdcardFailed(){
        Toast.makeText(mContext, "DENY ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }



    // 初始化View
    private void initViews() {
        phoneEdit = findById(R.id.edit_phone);
        passwordEdit = findById(R.id.edit_password);
        loginBtn = findById(R.id.btn_login);
    }

    // 准备登录
    public void toLogin(View view) {
        String loginName = phoneEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        requsetSDCardPermission();

        DBUser dbUser = new DBUser(mContext);
        UserInfo info = new UserInfo(loginName, EncryptUtil.makeMD5(password), "", "姚益", 1);
        dbUser.addInfo(info);

        this.appAction.login(loginName, password, new IRequestCallback() {
            @Override
            public void onSuccess(Object data) {
                L.v("data>>" + data);
                startActivity(RecyclerActivity.class, null);
                finish();
                L.v("end");
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                L.v("start");
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                startActivity(RecyclerActivity.class, null);
                finish();
                L.v("end");
            }
        });

    }
}
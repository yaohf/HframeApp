package yaohf.com.android.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AnimRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;
import java.util.List;

import yaohf.com.android.ApplicationManager;
import yaohf.com.android.R;
import yaohf.com.android.stackFragment.KeyCallBack;
import yaohf.com.android.stackFragment.RootFragment;
import yaohf.com.android.stackFragment.StackManager;
import yaohf.com.android.stackFragment.test.Fragment1;
import yaohf.com.core.AppAction;
import yaohf.com.tool.L;
import yaohf.com.tool.permission.FramePermission;
import yaohf.com.widget.dialog.BaseProgressDialog;


/**
 * Activity抽象基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    // 上下文实例
    public Context mContext;
    // 应用全局的实例
    public ApplicationManager application;
    // 核心层的Action实例
    public AppAction appAction;

    //Frament Stack 栈管理
    public StackManager manager;
    //接键处理
    public KeyCallBack callBack;

    public BaseProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        application = (ApplicationManager) this.getApplication();
        appAction = application.getAppAction();
        //包含Fragment ，加载Stack 管理Frament
        if(getRootFragment() != null)
        {
            initFragment(savedInstanceState);
            clearFragmentManagerInsideFragments(this);
        }
        initProgress();
    }

    private void initProgress()
    {
        if(mProgress != null)
            mProgress = null;
        mProgress = new BaseProgressDialog(this, R.style.CustomDialog,
                getString(R.string.loading));
        mProgress.setCancelable(true);
        mProgress.setCanceledOnTouchOutside(false);
    }

    /**
     * 显示圈圈
     */
    public void showProgress() {
        if (mProgress != null && !mProgress.isShowing()) {
            mProgress.showProgress();
        }
    }

    /**
     *
     * @Description: 设置等待文本内容 String
     * @param @param msg
     * @return void
     * @throws
     */
    public void setMessage(String msg) {
        mProgress.setMessage(msg);
    }

    /**
     * 不显示圈圈
     */
    public void dismissProgress() {
        if(mContext == null)
            return;
        if(isFinishing())
            return;
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismissProgress();
        }
    }

    /**
     *
     * @Description: 设置等待文本内容 int 类型
     * @param @param id
     * @return void
     * @throws
     */
    public void setMessage(int id) {
        // mProgress.setMessage(getString(id));
        mProgress.setMessage(getString(id));
    }

    /**
     * frameLayout frament 根布局，可自行添加多个子Layout
     * RootFrament open close Frament
     * @param savedInstanceState
     */
    private void initFragment(Bundle savedInstanceState){

        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frameLayout.setId(R.id.framLayoutId);
        setContentView(frameLayout);
        RootFragment fragment = getRootFragment();

        manager = new StackManager(this);
        manager.setFragment(fragment);
        onCreateNow(savedInstanceState);
    }

    /**
     * Set the bottom of the fragment
     *
     * @return fragment
     */
    public  RootFragment getRootFragment(){
     return null;
    }

    /**
     * Set page switch animation
     *
     * @param nextIn  The next page to enter the animation
     * @param nextOut The next page out of the animation
     * @param quitIn  The current page into the animation
     * @param quitOut Exit animation for the current page
     */
    public void setAnim(@AnimRes int nextIn, @AnimRes int nextOut, @AnimRes int quitIn, @AnimRes int quitOut) {
        manager.setAnim(nextIn, nextOut, quitIn, quitOut);
    }

    /**
     * Rewriting onCreate method
     *
     * @param savedInstanceState savedInstanceState
     */
    public void onCreateNow(Bundle savedInstanceState) {

    }

    /**
     * 处理用户选择后的回调，包括允许权限和拒绝。
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        FramePermission.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    /**
     * 清理 FragmentManager 中的 Fragment。<br/>
     * 解决在系统设置中更改权限后，App 被 kill 掉重启时的 Fragment 状态错误问题。
     *
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void clearFragmentManagerInsideFragments(Activity activity) {
        if (activity instanceof FragmentActivity) {
            FragmentManager manager = ((FragmentActivity) activity).getSupportFragmentManager();
            int count = manager.getBackStackEntryCount();
            List<Fragment> list = manager.getFragments();
            int fragmentCount = list == null ? 0 : list.size();
            if (list != null) {
                for (Fragment fragment : list) {
                    manager.beginTransaction().remove(fragment).commit();
                }
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(getRootFragment() == null) {
            return super.onKeyDown(keyCode, event);
        }
        switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
//                    manager.onBackPressed();
                    manager.closeAllFragment();
                    return true;
                default:
                    if (callBack != null) {
                        return callBack.onKeyDown(keyCode, event);
                    }
                    break;
            }
        return super.onKeyDown(keyCode, event);
    }



    /**
     * Set button to click callback
     *
     * @param callBack callback
     */
    public void setKeyCallBack(KeyCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     *　简化FindVViewById
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T findById(int id)
    {
        return  (T)findViewById(id);
    }


    public class MyHandler extends Handler {

        private final WeakReference<Activity> mReference;
        public MyHandler(Activity mActivity) {
            super();
            mReference = new WeakReference<Activity>(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity activity =  mReference.get();
            if(activity != null)
            {
                activityHanlderMessage(msg);
            }
        }
    }

    public void startActivity(Class<?> activity,Bundle bundle)
    {
        Intent mIntent = new Intent(mContext,activity);
        if(bundle != null)
        {
//            mIntent.putExtras(bundle);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(mIntent,bundle);
            }
        }else {
            startActivity(mIntent);
        }
    }


    protected abstract void activityHanlderMessage(Message m);
    protected  MyHandler mHandler = new MyHandler(this);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHandler != null)
        {
            mHandler.removeCallbacksAndMessages(null);
        }
        mHandler = null;
    }

    protected void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}

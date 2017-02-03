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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AnimRes;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

import yaohf.com.android.ApplicationManager;
import yaohf.com.android.R;
import yaohf.com.android.stackFragment.KeyCallBack;
import yaohf.com.android.stackFragment.RootFragment;
import yaohf.com.android.stackFragment.StackManager;
import yaohf.com.core.AppAction;


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
        }
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


    @Override
    public final boolean onKeyDown(int keyCode, KeyEvent event) {
        if(getRootFragment() == null) {
            return super.onKeyDown(keyCode, event);
        }
        switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    manager.onBackPressed();
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
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T findById(int id)
    {
        return  (T)findViewById(id);
    }


    public  class MyHandler extends Handler {

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
            mIntent.putExtras(bundle);
        }
        startActivity(mIntent);
        finish();
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

}

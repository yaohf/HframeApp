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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.lang.ref.WeakReference;

import yaohf.com.android.ApplicationManager;
import yaohf.com.core.AppAction;


/**
 * Activity抽象基类
 * @version 1.0
 */
public abstract class BaseActivity extends FragmentActivity {
    // 上下文实例
    public Context context;
    // 应用全局的实例
    public ApplicationManager application;
    // 核心层的Action实例
    public AppAction appAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        application = (ApplicationManager) this.getApplication();
        appAction = application.getAppAction();
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

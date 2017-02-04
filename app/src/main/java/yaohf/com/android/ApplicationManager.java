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
package yaohf.com.android;

import android.app.Application;

import yaohf.com.api.manager.VolleyManager;
import yaohf.com.core.AppAction;
import yaohf.com.core.AppActionImpl;


/**
 * Application类，应用级别的操作都放这里
 * @version 1.0
 */
public class ApplicationManager extends Application {

    private AppAction appAction;

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyManager.getInstance(this);
        appAction = new AppActionImpl(this);
    }

    public AppAction getAppAction() {
        return appAction;
    }
}

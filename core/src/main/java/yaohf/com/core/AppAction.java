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

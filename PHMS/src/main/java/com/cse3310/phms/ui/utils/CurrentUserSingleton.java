/*
 * Copyright (c) 2014 Personal-Health-Monitoring-System
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cse3310.phms.ui.utils;

import com.cse3310.phms.model.User;

/**
 * This Singleton(restricts instantiation to one object) class helps
 * keep track of the current user while the application is running.
 */
public class CurrentUserSingleton {
    private static CurrentUserSingleton mInstance = null;
    private User mCurrentUser;

    private CurrentUserSingleton() {}

    public static CurrentUserSingleton getInstance() {
        if(mInstance == null) {
            mInstance = new CurrentUserSingleton();
        }
        return mInstance;
    }

    public User getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(User mCurrentUser) {
        this.mCurrentUser = mCurrentUser;
    }
}

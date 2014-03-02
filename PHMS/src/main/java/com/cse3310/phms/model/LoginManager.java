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

package com.cse3310.phms.model;

import com.cse3310.phms.model.utils.BCrypt;
import com.cse3310.phms.ui.utils.Database;

public class LoginManager {

    private LoginManager() {}

    public static boolean register(String userName, String password, PersonalInfo personalInfo) {
        if (!isUserNameExists(userName)) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            personalInfo.save();
            User newUser = new User(userName, hashedPassword);
            newUser.setPersonalInfo(personalInfo);
            newUser.save();
            return true;
        } else {
            return false;
        }
    }

    public static boolean login(String userName, String password) {
        User user = Database.getUserByUserName(userName);

        // check if username is valid
        if (user == null) { return false; }

        // check if password is valid
        return BCrypt.checkpw(password, user.getPassword());
    }

    public static void logOut() {
        // todo: to be implemented
    }

    private static boolean isUserNameExists(String userName) {
        User user = Database.getUserByUserName(userName);
        return user != null;
    }
}

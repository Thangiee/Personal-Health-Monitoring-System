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
import com.cse3310.phms.ui.utils.UserSingleton;
import com.cse3310.phms.ui.utils.DatabaseHandler;


public class LoginManager {

    // restrict instantiation of this class.
    private LoginManager() {}

    /**
     * Register a new user and set the new user as the current user.
     *
     * @param userName the user name for the registering account
     * @param password the password for the registering account
     * @param personalInfo the personal info to associate with the account
     * @return true if registration is successful, else return false if failed
     * @see com.cse3310.phms.ui.utils.UserSingleton
     */
    public static boolean register(String userName, String password, PersonalInfo personalInfo) {
        if (!isUserNameExists(userName)) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // create new user
            personalInfo.save();
            User newUser = new User(userName, hashedPassword);
            newUser.setPersonalInfo(personalInfo);
            newUser.save(); // save user to database

            // set current user to the new user
            UserSingleton.getInstance().setCurrentUser(newUser);
            return true;
        }

        return false;
    }

    /**
     * Log in and set the the logging in user as the current user.
     *
     * @param userName the user name used to log in
     * @param password the password used to log in
     * @return true if log in is successful, otherwise, return false
     * @see com.cse3310.phms.ui.utils.UserSingleton
     */
    public static boolean login(String userName, String password) {
        User user = DatabaseHandler.getUserByUserName(userName);

        // check if username is valid
        if (user == null) { return false; }

        // check if password is valid
        if (BCrypt.checkpw(password, user.getHashedPassword())) {
            // Valid password! Set current user to the logging in user.
            UserSingleton.getInstance().setCurrentUser(user);
            return true;
        }

        return false;
    }

    /**
     * Log out and set the current user to null.
     * @see com.cse3310.phms.ui.utils.UserSingleton
     */
    public static void logOut() {
        UserSingleton.getInstance().setCurrentUser(null);
    }

    /**
     * check if the user name exists in the database.
     *
     * @param userName the user name to check
     * @return true if the username exits in the db, otherwise, return false.
     */
    public static boolean isUserNameExists(String userName) {
        User user = DatabaseHandler.getUserByUserName(userName);
        return user != null;
    }
}

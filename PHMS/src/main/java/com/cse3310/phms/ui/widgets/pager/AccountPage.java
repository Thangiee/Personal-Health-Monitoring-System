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

package com.cse3310.phms.ui.widgets.pager;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import co.juliansuarez.libwizardpager.wizard.model.ModelCallbacks;
import co.juliansuarez.libwizardpager.wizard.model.Page;
import co.juliansuarez.libwizardpager.wizard.model.ReviewItem;

import java.util.ArrayList;

public class AccountPage extends Page{
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY= "password";

    public AccountPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return AccountInfoFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Username", mData.getString(USERNAME_KEY), getKey(), -1));
//        dest.add(new ReviewItem("Password", mData.getString(PASSWORD_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(USERNAME_KEY))
                && !TextUtils.isEmpty(mData.getString(PASSWORD_KEY));
    }

    public AccountPage setValue(String value) {
        mData.putString(SIMPLE_DATA_KEY, value);
        return this;
    }
}

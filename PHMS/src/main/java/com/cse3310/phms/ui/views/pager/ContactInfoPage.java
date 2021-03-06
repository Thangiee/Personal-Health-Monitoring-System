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

package com.cse3310.phms.ui.views.pager;

import android.support.v4.app.Fragment;
import co.juliansuarez.libwizardpager.wizard.model.ModelCallbacks;
import co.juliansuarez.libwizardpager.wizard.model.Page;
import co.juliansuarez.libwizardpager.wizard.model.ReviewItem;
import com.cse3310.phms.ui.fragments.RegContactInfoFragment;

import java.util.ArrayList;

public class ContactInfoPage extends Page{
    public static final String EMAIL_KEY = "Email";
    public static final String PHONE_KEY= "Phone";


    public ContactInfoPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return RegContactInfoFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Email", mData.getString(EMAIL_KEY), getKey(), -1));
        dest.add(new ReviewItem("Phone", mData.getString(PHONE_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return mData.getBoolean(VALID_KEY);
    }

    public ContactInfoPage setValue(String value) {
        mData.putString(SIMPLE_DATA_KEY, value);
        return this;
    }
 }

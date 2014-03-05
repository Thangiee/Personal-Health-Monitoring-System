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
import android.util.Log;
import android.widget.Toast;
import co.juliansuarez.libwizardpager.wizard.model.ModelCallbacks;
import co.juliansuarez.libwizardpager.wizard.model.Page;
import co.juliansuarez.libwizardpager.wizard.model.ReviewItem;
import com.cse3310.phms.ui.fragments.RegPersonalInfoFragment;

import java.util.ArrayList;

public class PersonalInfoPage extends Page{
    public static final String FIRST_KEY = "first name";
    public static final String LAST_KEY= "last name";
    public static final String AGE_KEY= "age";
    public static final String GENDER_KEY= "gender";
    public static final String WEIGHT_KEY= "weight";
    public static final String HEIGHT_KEY= "height";

    public PersonalInfoPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return RegPersonalInfoFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("First name", mData.getString(FIRST_KEY), getKey(), -1));
        dest.add(new ReviewItem("Last name", mData.getString(LAST_KEY), getKey(), -1));
        dest.add(new ReviewItem("Age", String.valueOf(mData.getInt(AGE_KEY)), getKey(), -1));
        dest.add(new ReviewItem("Gender", mData.getString(GENDER_KEY), getKey(), -1));
        dest.add(new ReviewItem("Weight", mData.getDouble(WEIGHT_KEY) + " lbs", getKey(), -1));

        int inches = mData.getInt(HEIGHT_KEY);
        dest.add(new ReviewItem("Height", inches /12 + "\'" + inches % 12 + "\"", getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(FIRST_KEY))
                && !TextUtils.isEmpty(mData.getString(LAST_KEY))
                && !TextUtils.isEmpty(mData.getString(GENDER_KEY))
                && !isZero(mData.getDouble(WEIGHT_KEY))
                && !isZero(mData.getInt(AGE_KEY));
    }

    public PersonalInfoPage setValue(String value) {
        mData.putString(SIMPLE_DATA_KEY, value);
        return this;
    }

    private boolean isZero(int value) {
       return value == 0;
    }

    private boolean isZero(double value) {
       return value == 0.0;
    }
 }

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

package com.cse3310.phms.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.cse3310.phms.ui.fragments.BlankFragment;

public class DietScreenAdapter extends FragmentPagerAdapter {
    public static enum DAY{
        YESTERDAY("Yesterday"), TODAY("Today"), TOMORROW("Tomorrow");

        public String title;
        DAY(String title) {
            this.title = title;
        }
    }

    public DietScreenAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new BlankFragment();
    }

    @Override
    public int getCount() {
        return DAY.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return DAY.values()[position].title;
    }
}

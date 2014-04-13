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

package com.cse3310.phms.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.cse3310.phms.R;
import com.cse3310.phms.ui.utils.Events;
import com.viewpagerindicator.TabPageIndicator;
import de.greenrobot.event.EventBus;

public class TabsIndicatorFragment<T extends FragmentPagerAdapter> extends SherlockFragment {

    protected T mAdapter;
    private int mCurrentPosition;

    public static <T extends FragmentPagerAdapter> TabsIndicatorFragment newInstance(T adapter) {
        TabsIndicatorFragment<T> fragment = new TabsIndicatorFragment<T>();
        fragment.mAdapter = adapter;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabs_indicator, container, false);

        ViewPager mPager = (ViewPager) view.findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        TabPageIndicator mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                EventBus.getDefault().postSticky(new Events.SwitchTabEvent(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }
}

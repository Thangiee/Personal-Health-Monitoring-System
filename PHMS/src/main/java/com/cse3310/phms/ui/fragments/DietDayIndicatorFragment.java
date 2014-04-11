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
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.cse3310.phms.R;
import com.cse3310.phms.ui.adapters.DietScreenAdapter;
import com.cse3310.phms.ui.utils.Events;
import com.viewpagerindicator.TitlePageIndicator;
import de.greenrobot.event.EventBus;

import java.util.Calendar;
import java.util.Date;

import static com.cse3310.phms.ui.adapters.DietScreenAdapter.DAY;

public class DietDayIndicatorFragment extends SherlockFragment {
    private Date selectedDay = Calendar.getInstance().getTime();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_pager_indicator, container, false);

        final DietScreenAdapter mAdapter = new DietScreenAdapter(getChildFragmentManager());

        final ViewPager mPager = (ViewPager) view.findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        final TitlePageIndicator mIndicator = (TitlePageIndicator) view.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.setCurrentItem(DAY.TODAY.ordinal()); // set start up page to Today {@see DietScreenAdapter.DAY}
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Calendar today = Calendar.getInstance();

                switch (DAY.values()[position]) {
                    case YESTERDAY:
                        today.add(Calendar.DATE, -1);   // minus a day
                        break;
                    case TODAY:
                        break;
                    case TOMORROW:
                        today.add(Calendar.DATE, 1);    // add a day
                        break;
                }
                selectedDay = today.getTime();

                // post an event to indicate that a different day is selected.
                EventBus.getDefault().postSticky(new Events.SwitchDayEvent());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return view;
    }

    public Date getSelectedDay() {
        return selectedDay;
    }
}

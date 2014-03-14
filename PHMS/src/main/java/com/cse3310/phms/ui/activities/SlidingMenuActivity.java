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

package com.cse3310.phms.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import com.actionbarsherlock.view.MenuItem;
import com.cse3310.phms.R;
import com.cse3310.phms.ui.utils.Events;
import com.jeremyfeinstein.slidingmenu.lib.actionbar.ActionBarSlideIcon;
import de.greenrobot.event.EventBus;

/**
 * See https://github.com/jfeinstein10/SlidingMenu for documentations on
 * SlidingMenu.
 */
public class SlidingMenuActivity extends BaseActivity {

    private boolean doubleBackToExitPressedOnce = false;

    public SlidingMenuActivity() {
    }

    public SlidingMenuActivity(int mTitleRes) {
        super(mTitleRes);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSlidingMenu().setActionBarSlideIcon(new ActionBarSlideIcon(
                this, R.drawable.ic_navigation_drawer, R.string.open_content_desc, R.string.close_content_desc));
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    // change the title in the action bar after selecting an item
    // in the sliding menu.
    public void onEvent(Events.SlidingMenuItemSelectedEvent event) {
        setTitle(event.newTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // open the slide menu when the app icon is clicked
            case android.R.id.home:
                toggle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}

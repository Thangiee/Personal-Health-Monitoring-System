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
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.cse3310.phms.R;
import com.cse3310.phms.ui.fragments.HomeScreenFragment_;
import com.cse3310.phms.ui.fragments.SlideMenuListFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.actionbar.ActionBarSlideIcon;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * See https://github.com/jfeinstein10/SlidingMenu for documentations on
 * SlidingMenu.
 */
public class SlidingMenuActivity extends SlidingFragmentActivity{
    private int mTitleRes;
    protected SlideMenuListFragment mFrag;
    private boolean doubleBackToExitPressedOnce = false;

    public SlidingMenuActivity() {
        super();
    }

    public SlidingMenuActivity(int titleRes) {
        mTitleRes = titleRes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_layout_frame);

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_front_container, new HomeScreenFragment_()).commit();

        if (mTitleRes == 0) {
            mTitleRes = R.string.app_name;
        }
        setTitle(mTitleRes);

        // set the Behind View
        // this is the view behind the list when the slide menu is open
        setBehindContentView(R.layout.back_layout_frame);
        if (savedInstanceState == null) {
            FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
            mFrag = new SlideMenuListFragment();
            t.replace(R.id.frag_back_container, mFrag);
            t.commit();
        } else {
            mFrag = (SlideMenuListFragment)this.getSupportFragmentManager().findFragmentById(R.id.frag_back_container);
        }

        // customize the SlidingMenu
        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidth(50);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffset(260);
        sm.setFadeDegree(1.0f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        setSlidingActionBarEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSlidingMenu().setActionBarSlideIcon(new ActionBarSlideIcon(
                this, R.drawable.ic_navigation_drawer, R.string.open_content_desc, R.string.close_content_desc));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // open the slide menu when the app icon is clicked
            case android.R.id.home:
                toggle();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // create all the icons on the action bar
        getSupportMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
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

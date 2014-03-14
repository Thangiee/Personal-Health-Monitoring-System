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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.cse3310.phms.R;
import com.cse3310.phms.ui.fragments.HomeScreenFragment_;
import com.cse3310.phms.ui.fragments.SlideMenuListFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.Collection;

public abstract class BaseActivity extends SlidingFragmentActivity
        implements AdapterView.OnItemClickListener, View.OnKeyListener, View.OnTouchListener {

    private static final int SEARCH_ICON = 0;
    private int mTitleRes;
    private ArrayAdapter<String> suggestionAdapter;
    private AutoCompleteTextView autoCompTextView;
    protected InputMethodManager inputManager;
    protected SlideMenuListFragment mFrag;

    protected BaseActivity() {
        super();
    }

    protected BaseActivity(int mTitleRes) {
        this.mTitleRes = mTitleRes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_layout_frame);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_front_container, new HomeScreenFragment_()).commit();
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); // use to show and hide keyboard
        suggestionAdapter = new ArrayAdapter<String>(this, R.layout.search_drop_down);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.base_action_bar, menu);
        MenuItem menuItem = menu.getItem(SEARCH_ICON).setActionView(R.layout.act_layout_search); // switch to this menu after clicking the search icon

        // setup search suggestions
        autoCompTextView = (AutoCompleteTextView) menuItem.getActionView().findViewById(R.id.act_search_txt_auto_complete);
        autoCompTextView.setAdapter(suggestionAdapter);
        autoCompTextView.setOnItemClickListener(this);
        autoCompTextView.setOnKeyListener(this);
        autoCompTextView.setOnTouchListener(this);
        return true;
    }

    // handler for the icons in the action bar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getBaseContext(), "settings", Toast.LENGTH_SHORT).show(); //TODO: to be implemented
                return true;
            case R.id.about_us:
                Toast.makeText(getBaseContext(), "about us", Toast.LENGTH_SHORT).show(); //TODO: to be implemented
                return true;
            case R.id.search:
                inputManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.RESULT_HIDDEN); // show keyboard when search icon is clicked
            default:
                return false;
        }
    }

    // handler for when a suggestion is click
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        inputManager.hideSoftInputFromWindow(autoCompTextView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS); // hide keyboard
        Toast.makeText(getBaseContext(), "searching for " + autoCompTextView.getText(), Toast.LENGTH_SHORT).show();
    }

    // handler for when the search key on the keyboard is pressed
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            Toast.makeText(getBaseContext(), "searching for " + autoCompTextView.getText(), Toast.LENGTH_SHORT).show();

            //hide keyboard
            inputManager.hideSoftInputFromWindow(autoCompTextView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            autoCompTextView.dismissDropDown(); // hide suggestion
            return true;
        }
        return false;
    }

    // handler for the clear input button to the right of the search text view.
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            // some magic...
            if (event.getX() >= (autoCompTextView.getRight() - autoCompTextView
                    .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                Toast.makeText(getBaseContext(), "deleting..." , Toast.LENGTH_SHORT).show();
                autoCompTextView.setText(""); // set search text view to empty string.
                return true;
            }
        }
        return false;
    }

    public void setSuggestions(Collection<String> suggestions) {
        suggestionAdapter.clear();
        suggestionAdapter.addAll(suggestions);
        suggestionAdapter.notifyDataSetChanged();
    }

    public void setSuggestions(String[] suggestions) {
        suggestionAdapter.clear();
        suggestionAdapter.addAll(suggestions);
        suggestionAdapter.notifyDataSetChanged();
    }
}

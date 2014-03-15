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
import com.cse3310.phms.ui.utils.Keyboard;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.Collection;

public abstract class BaseActivity extends SlidingFragmentActivity {

    private static final int SEARCH_ICON = 0;
    private int mTitleRes;
    private ArrayAdapter<String> suggestionAdapter;

    protected AutoCompleteTextView autoCompTextView;
    protected InputMethodManager inputManager;
    protected MenuItem searchMenuItem;

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
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); // use to show and hide keyboard
        suggestionAdapter = new ArrayAdapter<String>(this, R.layout.search_drop_down);

        // turn off sliding by default.
        getSlidingMenu().setSlidingEnabled(false);

        // set the Behind View
        // this is the view behind the list when the slide menu is open
        setBehindContentView(R.layout.back_layout_frame);

        if (mTitleRes == 0) {
            mTitleRes = R.string.app_name;
        }
        setTitle(mTitleRes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.overflow_menu, menu);
        getSupportMenuInflater().inflate(R.menu.search_menu, menu);
        // set search field view to switch to after clicking the search icon
        searchMenuItem = menu.getItem(SEARCH_ICON).setActionView(R.layout.act_layout_search);

        autoCompTextView = (AutoCompleteTextView) searchMenuItem.getActionView().findViewById(R.id.act_search_txt_auto_complete);
        setUpAutoCompleteTextView();
        return true;
    }

    private void setUpAutoCompleteTextView() {
        autoCompTextView.setAdapter(suggestionAdapter);

        // listener for close/open of search field
        autoCompTextView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                v.requestFocus();
                Keyboard.show(inputManager, autoCompTextView);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                Keyboard.hide(inputManager, autoCompTextView);
            }
        });

        // listener for when a suggestion is click
        autoCompTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Keyboard.hide(inputManager, autoCompTextView);
                doSearch();
            }
        });

        // listener for when the search key on the keyboard is pressed
        autoCompTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Keyboard.hide(inputManager, autoCompTextView);
                    autoCompTextView.dismissDropDown(); // hide suggestion
                    doSearch();
                    return true;
                }
                return false;
            }
        });

        // handler for the clear input button to the right of the search text view.
        autoCompTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // some magic...
                    if (event.getX() >= (autoCompTextView.getRight() - autoCompTextView
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        autoCompTextView.setText(""); // set search text view to empty string.
                        return true;
                    }
                }
                return false;
            }
        });
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
            default:
                return false;
        }
    }

    public void setSuggestions(Collection<String> suggestions) {
        suggestionAdapter.clear();
        suggestionAdapter.addAll(suggestions);
        suggestionAdapter.notifyDataSetChanged();
    }

    public abstract void doSearch();
}

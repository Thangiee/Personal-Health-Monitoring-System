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
import android.widget.Button;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cse3310.phms.R;
import com.cse3310.phms.model.utils.MyDateFormatter;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

@EFragment(R.layout.home_screen)
public class HomeScreenFragment extends SherlockFragment{
    @ViewById(R.id.frag_enterDateHere_lbl)
    TextView dateView;
    @ViewById(R.id.initialize_test_data)
    Button testButton;
    Date date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = new Date();
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        // set title
        getSherlockActivity().getSupportActionBar().setTitle("Home");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.overflow_menu, menu);
    }

    @AfterViews
    void OnAfterViews() {
        dateView.setText(String.valueOf("Todays is:\n " + MyDateFormatter.formatDate(date.getTime())));
    }

}

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

import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.cse3310.phms.R;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.search_screen_header)
public class SearchScreenHeaderFragment extends SherlockFragment{
    @ViewById(R.id.search_header_counter)
    TextView counterTextView;

    private int resultsFound = 0;

    @AfterViews
    void OnAfterViews() {
        counterTextView.setText(String.valueOf(resultsFound));
    }

    public void setResultsFound(int resultsFound) {
        this.resultsFound = resultsFound;
    }
}

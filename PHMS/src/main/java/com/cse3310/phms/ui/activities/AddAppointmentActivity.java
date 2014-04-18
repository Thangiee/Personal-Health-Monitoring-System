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
import android.support.v4.app.NavUtils;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.andreabaccega.widget.FormEditText;
import com.cse3310.phms.R;
import com.cse3310.phms.model.utils.MyDateFormatter;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

@EActivity(R.layout.add_appointment_form)
public class AddAppointmentActivity extends SherlockFragmentActivity {
    @ViewById(R.id.add_appointment_select_btn)  TextView mButtonTextView;
    @ViewById(R.id.add_appointment_doc_name)    FormEditText mNameEditText;
    @ViewById(R.id.add_appointment_doc_Phone)   FormEditText mPhoneEditText;
    @ViewById(R.id.add_appointment_location)    FormEditText mLocationEditText;
    @ViewById(R.id.add_appointment_time)        FormEditText mTimeEditText;

    private Date mSelectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // enable the up/home button in the actionbar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSelectedDate = (Date) getIntent().getSerializableExtra("date");
    }

    @AfterViews
    void onSetupViews() {
        if (mSelectedDate != null) {
            String formattedTime = MyDateFormatter.formatDate(mSelectedDate.getTime()) + " - " +
                                  MyDateFormatter.formatTime(mSelectedDate.getTime());
            mTimeEditText.setText(formattedTime);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Click(R.id.add_appointment_select_btn)
    void handleSelectButtonClick() {
        Toast.makeText(this, ":)", Toast.LENGTH_SHORT).show();
    }

    @Click(android.R.id.home)
    void handleHomeButtonClick() {
        Toast.makeText(this, "up", Toast.LENGTH_SHORT).show();
        NavUtils.navigateUpFromSameTask(this); // go back to previous activity when clicking the actionbar home
    }

}

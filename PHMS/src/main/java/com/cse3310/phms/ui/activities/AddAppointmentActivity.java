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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.EditText;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.andreabaccega.widget.FormEditText;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Appointment;
import com.cse3310.phms.model.DoctorInfo;
import com.cse3310.phms.model.utils.MyDateFormatter;
import com.cse3310.phms.ui.utils.UserSingleton;
import org.androidannotations.annotations.*;

import java.util.Date;
import java.util.List;

@EActivity(R.layout.add_appointment_form)
public class AddAppointmentActivity extends SherlockFragmentActivity {
    @ViewById(R.id.add_appointment_select_btn)  TextView mButtonTextView;
    @ViewById(R.id.add_appointment_doc_name)    FormEditText mNameEditText;
    @ViewById(R.id.add_appointment_doc_Phone)   FormEditText mPhoneEditText;
    @ViewById(R.id.add_appointment_location)    FormEditText mLocationEditText;
    @ViewById(R.id.add_appointment_time)        FormEditText mTimeEditText;
    @ViewById(R.id.add_appointment_purpose)     EditText mPurposeEditText;

    private DoctorInfo mSelectedDoctor;
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
        getSupportMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Click(R.id.add_appointment_select_btn)
    void handleSelectButtonClick() {
        final List<DoctorInfo> doctorInfoList = UserSingleton.INSTANCE.getCurrentUser().getDoctors();
        final CharSequence[] items = new CharSequence[doctorInfoList.size()];

        // get all of the user's doctor name
        int i = 0;
        for (DoctorInfo doctorInfo : doctorInfoList) {
            items[i++] = doctorInfo.getFullName();
        }

        // create a dialog with doctor names
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Doctor");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // update the views with the info from the selected doctor from the dialog
                mSelectedDoctor = doctorInfoList.get(item);
                updateAppointmentViews(mSelectedDoctor);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @OptionsItem(android.R.id.home)
    void handleHomeButtonClick() {
        NavUtils.navigateUpFromSameTask(this); // go back to previous activity when clicking the actionbar home
    }

    @OptionsItem(R.id.save_icon)
    void handleSaveIconClick() {
        if (mSelectedDoctor != null) {
            Appointment appointment = new Appointment();
            appointment.setDoctorInfo(mSelectedDoctor)
                    .setTime(mSelectedDate.getTime())
                    .setPurpose(mPurposeEditText.getText().toString());

            appointment.save(); // save to DB
            finish(); // close the activity
        }
    }

    private void updateAppointmentViews(DoctorInfo doctorInfo) {
        mNameEditText.setText(doctorInfo.getFullName());
        mPhoneEditText.setText(doctorInfo.getPhone());

        String location = doctorInfo.getHospital() + " - " + doctorInfo.getAddress();
        mLocationEditText.setText(location);
    }
}
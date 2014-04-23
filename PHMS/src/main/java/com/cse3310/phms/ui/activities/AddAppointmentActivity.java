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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Appointment;
import com.cse3310.phms.model.DoctorInfo;
import com.cse3310.phms.model.Reminder;
import com.cse3310.phms.model.utils.MyDateFormatter;
import com.cse3310.phms.ui.services.ReminderAlarm;
import com.cse3310.phms.ui.utils.UserSingleton;
import com.cse3310.phms.ui.views.AppointmentView;
import com.doomonafireball.betterpickers.timepicker.TimePickerBuilder;
import com.doomonafireball.betterpickers.timepicker.TimePickerDialogFragment;
import org.androidannotations.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EActivity(R.layout.add_appointment_form)
public class AddAppointmentActivity extends SherlockFragmentActivity
        implements TimePickerDialogFragment.TimePickerDialogHandler {
    @ViewById(R.id.add_appointment_select_btn)  TextView mDoctorButtonTextView;
    @ViewById(R.id.add_appointment_time_btn)    TextView mTimeButtonTextView;
    @ViewById(R.id.add_appointment_purpose)     EditText mPurposeEditText;
    @ViewById(R.id.appointment_view)            AppointmentView mAppointmentView;

    private DoctorInfo mSelectedDoctor;
    private Date mSelectedDate;
    private long appointmentTime;

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
            appointmentTime = mSelectedDate.getTime();
        }
        mAppointmentView.setVisibility(View.GONE);
        mTimeButtonTextView.setText(MyDateFormatter.formatTime(mSelectedDate.getTime()));
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
                mAppointmentView.setVisibility(View.VISIBLE);
                mAppointmentView.setText(
                        mSelectedDoctor.getFullName(),
                        mSelectedDoctor.getPhone(),
                        MyDateFormatter.formatDate(appointmentTime),
                        mSelectedDoctor.getHospital() + " - " + mSelectedDoctor.getAddress()
                );
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Click(R.id.add_appointment_time_btn)
    void handleTimeButtonClick() {
        TimePickerBuilder tpb = new TimePickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment_Light);
        tpb.show();
    }

    @OptionsItem(android.R.id.home)
    void handleHomeButtonClick() {
        NavUtils.navigateUpFromSameTask(this); // go back to previous activity when clicking the actionbar home
    }

    @OptionsItem(R.id.save_icon)
    void handleSaveIconClick() {
        if (mSelectedDoctor != null) {
            Appointment appointment = new Appointment(mSelectedDoctor, appointmentTime);

            // use default text if no purpose text is enter
            if (!mPurposeEditText.getText().toString().isEmpty()) {
                appointment.setPurpose(mPurposeEditText.getText().toString());
            }

            appointment.save(); // save to DB
            Reminder reminder = new Reminder(appointment, TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES));
            reminder.save();
            new ReminderAlarm(this, reminder, R.drawable.ic_action_calendar_day); // set alarm for this appointment

            Toast.makeText(this, "Appointment saved", Toast.LENGTH_SHORT).show();
            finish(); // close the activity
        }
    }

    @Override
    public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
        long mills = TimeUnit.HOURS.toMillis(hourOfDay) + TimeUnit.MINUTES.toMillis(minute);
        appointmentTime = mSelectedDate.getTime() + mills;
        mTimeButtonTextView.setText(MyDateFormatter.formatTime(appointmentTime));
    }
}

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

import android.util.Log;
import com.actionbarsherlock.app.SherlockFragment;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Appointment;
import com.cse3310.phms.model.DoctorInfo;
import com.cse3310.phms.model.Remindable;
import com.cse3310.phms.model.utils.MyDateFormatter;
import com.cse3310.phms.ui.services.ReminderAlarm;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import java.util.Calendar;
import java.util.Date;

@EFragment(R.layout.reminder_screen)
public class ReminderScreenFragment extends SherlockFragment {

    @Click(R.id.alarm)
    void test() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        Log.d(">>>>>>>>>>>", "" + date.getTime());
        DoctorInfo doctorInfo = new DoctorInfo();
        doctorInfo.setLastName("ASSHOLE");

        Remindable remindable = new Appointment(doctorInfo, date.getTime() + 5000);
        Log.d(">>>>>>>>>>>", MyDateFormatter.formatTime(date.getTime()));
        new ReminderAlarm(getActivity(), remindable, R.drawable.ic_action_calendar_day);
    }
}

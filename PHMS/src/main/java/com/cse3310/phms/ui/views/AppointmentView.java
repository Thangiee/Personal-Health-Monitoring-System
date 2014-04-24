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

package com.cse3310.phms.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Appointment;
import com.cse3310.phms.model.DoctorInfo;
import com.cse3310.phms.model.utils.MyDateFormatter;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.appointment_info)
public class AppointmentView extends RelativeLayout {

    @ViewById(R.id.appointment_card_name)
    protected TextView nameTextView;
    @ViewById(R.id.appointment_card_phone)
    protected TextView phoneTextView;
    @ViewById(R.id.appointment_card_time)
    protected TextView timeTextView;
    @ViewById(R.id.appointment_card_location)
    protected TextView locationTextView;

    public AppointmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAppointment(Appointment appointment) {
        DoctorInfo doctorInfo = appointment.getDoctorInfo();
        String name = doctorInfo.getFirstName() + " " + doctorInfo.getLastName();
        String phone = doctorInfo.getPhone();
        String time = MyDateFormatter.formatDate(appointment.getTime())
                + " " + MyDateFormatter.formatTime(appointment.getTime());
        String location = doctorInfo.getHospital() + " at " + doctorInfo.getAddress();

        setText(name, phone, time, location);
    }

    public void setText(String name, String phone, String time, String location) {
        nameTextView.setText(name);
        phoneTextView.setText(phone);
        timeTextView.setText(time);
        locationTextView.setText(location);
    }
}

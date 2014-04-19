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

package com.cse3310.phms.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.cse3310.phms.model.utils.MyDateFormatter;
import com.cse3310.phms.ui.utils.UserSingleton;

@Table(name = "Appointment")
public class Appointment extends Model implements Remindable{
    @Column private DoctorInfo mDoctorInfo;
    @Column private String purpose;
    @Column private long time;
    @Column private User user;  // used as a foreign key

    // require
    public Appointment() {
        user = UserSingleton.INSTANCE.getCurrentUser();
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param doctorInfo the mDoctorInfo
     * @param time the time
     */
    public Appointment(DoctorInfo doctorInfo, long time) {
        this(doctorInfo, time, "purpose not specified");
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param doctorInfo the mDoctorInfo
     * @param purpose the purpose
     * @param time the time
     */
    public Appointment(DoctorInfo doctorInfo, long time, String purpose) {
        this.mDoctorInfo = doctorInfo;
        this.purpose = purpose;
        this.time = time;
        user = UserSingleton.INSTANCE.getCurrentUser();
    }

    /**
     * Gets mDoctorInfo.
     *
     * @return the mDoctorInfo
     */
    public DoctorInfo getDoctorInfo() {
        return mDoctorInfo;
    }

    /**
     * Sets mDoctorInfo.
     *
     * @param doctorInfo the mDoctorInfo
     * @return this appointment object
     */
    public Appointment setDoctorInfo(DoctorInfo doctorInfo) {
        this.mDoctorInfo = doctorInfo;
        return this;
    }

    /**
     * Gets purpose for visiting.
     *
     * @return the purpose
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets purpose for visiting.
     *
     * @param purpose the purpose
     * @return this appointment object
     */
    public Appointment setPurpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    /**
     * Gets time of the appointment.
     *
     * @return the time in milliseconds
     */
    public long getTime() {
        return time;
    }

    /**
     * Sets time of the appointment.
     *
     * @param time the time in milliseconds
     * @return this appointment object
     */
    public Appointment setTime(long time) {
        this.time = time;
        return this;
    }

    @Override
    public long reminderTime() {
        return this.time;
    }

    @Override
    public String reminderMessage() {
        return String.format(
                "Appointment with Dr.%s at %s.\nPurpose: %s",
                mDoctorInfo.getLastName(),
                MyDateFormatter.formatTime(time),
                purpose);
    }
}

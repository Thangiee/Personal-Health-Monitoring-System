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

@Table(name = "Appointment")
public class Appointment extends Model {
    @Column private Doctor doctor;
    @Column private String purpose;
    @Column private long time;

    /**
     * Instantiates a new Appointment.
     */
    public Appointment() {}

    /**
     * Instantiates a new Appointment.
     *
     * @param doctor the doctor
     * @param purpose the purpose
     * @param time the time
     */
    public Appointment(Doctor doctor, String purpose, long time) {
        this.doctor = doctor;
        this.purpose = purpose;
        this.time = time;
    }

    /**
     * Gets doctor.
     *
     * @return the doctor
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Sets doctor.
     *
     * @param doctor the doctor
     * @return this appointment object
     */
    public Appointment setDoctor(Doctor doctor) {
        this.doctor = doctor;
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
}

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
import com.cse3310.phms.ui.utils.UserSingleton;

@Table(name = "VitalSign")
public class Vitals extends Model {

    @Column private double bloodPressure;
    @Column private double glucoseLevel;
    @Column private double cholesterol;
    @Column private double bodyTemp;
    @Column private double pulse;


    @Column private User user; // used as a foreign key

    public Vitals(Vitals vitals) {
        this.user = UserSingleton.INSTANCE.getCurrentUser();
        bloodPressure = vitals.getBloodPressure();
        glucoseLevel = vitals.getGlucoseLevel();
        cholesterol = vitals.getCholesterol();
        bodyTemp = vitals.getBodyTemp();
        pulse = vitals.getPulse();

    }

    public Vitals(){
        this.user = UserSingleton.INSTANCE.getCurrentUser();
    }


    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public double getBodyTemp() {
        return bodyTemp;
    }

    public void setBodyTemp(double bodyTemp) {
        this.bodyTemp = bodyTemp;
    }

    public double getPulse() {
        return pulse;
    }

    public void setPulse(double pulse) {
        this.pulse = pulse;
    }


    /**
     * Gets cholesterol.
     *
     * @return the cholesterol as a unit of mg/dL
     */
    public double getCholesterol() {
        return cholesterol;
    }

    /**
     * Sets cholesterol.
     *
     * @param cholesterol the cholesterol as a unit of mg/dL
     * @return the this object
     */
    public Vitals setCholesterol(final double cholesterol) {
        this.cholesterol = cholesterol;
        return this;
    }

    /**
     * Gets glucose level.
     *
     * @return the glucose level as a unit of mg/dL
     */
    public double getGlucoseLevel() {
        return glucoseLevel;
    }

    /**
     * Sets glucose level.
     *
     * @param glucoseLevel the glucose level as a unit of mg/dL
     * @return the this object
     */
    public Vitals setGlucoseLevel(final double glucoseLevel) {
        this.glucoseLevel = glucoseLevel;
        return this;
    }

    /**
     * Gets blood pressure.
     *
     * @return the blood pressure as a unit of mmHg
     */
    public double getBloodPressure() {
        return bloodPressure;
    }

    /**
     * Sets blood pressure.
     *
     * @param bloodPressure the blood pressure as a unit of mmHg
     * @return the this object
     */
    public Vitals setBloodPressure(final double bloodPressure) {
        this.bloodPressure = bloodPressure;
        return this;
    }
}

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

@Table(name = "Medication")
public class Medication extends Model implements Remindable, Notifiable{

    @Column private long time;
    @Column private String medicationName;
    @Column private String frequencyType;
    @Column private String dosageType;
    @Column private double dosage;
    @Column private double frequency;
    @Column private User user; // used as a foreign key

    public Medication(Medication medication) {
        this.user = UserSingleton.INSTANCE.getCurrentUser();
        medicationName = medication.getMedicationName();
        frequencyType = medication.getFrequencyType();
        dosageType = medication.getDosageType();
        dosage = medication.getDosage();
        frequency = medication.getFrequency();
        time = medication.getTime();
        dosageType = medication.getDosageType();
        frequencyType = medication.getFrequencyType();
        frequency = medication.getFrequency();
    }

    public Medication(){
        this.user = UserSingleton.INSTANCE.getCurrentUser();
    }

    /**
     * Gets user that owns the medication.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    public Medication setTime(long time) {
        this.time = time;
        return this;
    }

    @Override
    public long reminderTime() {
        return 0;
    }

    @Override
    public String reminderTitle() {
        return null;
    }

    @Override
    public String reminderMessage() {
        return null;
    }

    public String getMedicationName(){
        return this.medicationName;
    }

    public double getDosage(){
        return this.dosage;
    }

    public void setDosage(double dosage){
        this.dosage = dosage;
    }

    public void setMedicationName(String medicationName){
        this.medicationName = medicationName;
    }

    public long getTime() {
        return time;
    }

    public double getFrequency(){
        return this.frequency;
    }

    public void setFrequency(double frequency){
        this.frequency = frequency;
    }

    public String getFrequencyType(){
        return this.frequencyType;
    }

    public void setFrequencyType(String frequencyType){
        this.frequencyType = frequencyType;
    }

    public String getDosageType(){
        return this.dosageType;
    }

    public void setDosageType(String dosageType){
        this.dosageType = dosageType;
    }
}

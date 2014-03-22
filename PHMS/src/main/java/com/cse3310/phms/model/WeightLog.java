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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Table(name = "WeightLog")
public class WeightLog extends Model{
    @Column private double weight;
    @Column private User user;
    @Column private long time;
    private Date date;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d,yyyy", Locale.ENGLISH);
    private SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:s,a", Locale.ENGLISH);

    /**
     * Instantiates a new Weight log.
     */
    public WeightLog() {
        super();
    }

    /**
     * Instantiates a new Weight log.
     *
     * @param weight the weight in lbs
     */
    public WeightLog(double weight) {
        this.weight = weight;
        this.user = UserSingleton.INSTANCE.getCurrentUser();
        this.time = Calendar.getInstance().getTimeInMillis();
        date = new Date(time);

        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Gets weight in lbs
     *
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Gets user that owns the weight log.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets date of the weight log.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets formatted date of the weight log.
     * Ex. March 10,2014
     *
     * @return the format date
     */
    public String getFormatDate() {
        return dateFormat.format(date);
    }

    /**
     * Gets formatted time of the weight log.
     * Ex. 2:53:54,PM
     *
     * @return the format time
     */
    public String getFormatTime() {
        return timeFormat.format(date);
    }
}
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

@Table(name = "Reminder")
public class Reminder extends Model{
    @Column boolean isActive;
    @Column long time;
    @Column long earlyTime;
    @Column String title;
    @Column String message;
    @Column User user;

    public Reminder() {
        super();
    }

    public Reminder(Remindable remindable) {
        this(remindable, 0);
    }

    public Reminder(Remindable remindable, long earlyTime) {
        isActive = true;
        time = remindable.reminderTime();
        title = remindable.reminderTitle();
        message = remindable.reminderMessage();
        this.earlyTime = earlyTime;
        user = UserSingleton.INSTANCE.getCurrentUser();
    }

    public void cancel() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public long getAbsTime() {
        return time;
    }

    public long getReminderTime() {
        return time - earlyTime;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}

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

package com.cse3310.phms.ui.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Remindable;

public class ReminderAlarm extends BroadcastReceiver {
    private final String REMINDER_BUNDLE = "MyReminderBundle";

    public ReminderAlarm() {
    }

    public ReminderAlarm(Context context, Remindable remindable, int icon) {
        final int id = (int) remindable.reminderTime();

        Bundle bundle = new Bundle();
        bundle.putString("title", remindable.reminderTitle());
        bundle.putString("message", remindable.reminderMessage());
        bundle.putInt("icon", icon);

        Intent intent = new Intent(context, ReminderAlarm.class);
        intent.putExtra(REMINDER_BUNDLE, bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, remindable.reminderTime(), pendingIntent);    // set the alarm
    }

    public static void cancelReminder(Context context, Remindable remindable) {
        Intent intent = new Intent(context, ReminderAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) remindable.reminderTime(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Create an Intent for the BroadcastReceiver
        Intent buttonIntent = new Intent(context, AlarmButtonReceiver.class);

        //Create the PendingIntent
        PendingIntent btnPendingIntent = PendingIntent.getBroadcast(context, 1, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // get sound for notification
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        Bundle bundle = intent.getBundleExtra(REMINDER_BUNDLE);
        String title = bundle.getString("title");
        String message = bundle.getString("message");
        int icon = bundle.getInt("icon");

        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(icon)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setContentTitle(title)
                .addAction(R.drawable.ic_check_light, "Dismiss", btnPendingIntent); //Pass this PendingIntent to addAction method of Intent Builder//

        Notification notification = new Notification.BigTextStyle(builder)
                .bigText(message)
                .build();

        notification.defaults|= Notification.DEFAULT_VIBRATE; // enable vibration
        notification.flags |= Notification.FLAG_INSISTENT;  // repeat sound?

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1 , notification);    // fire the notification
    }
}

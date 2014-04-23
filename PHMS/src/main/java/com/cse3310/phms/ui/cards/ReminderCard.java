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

package com.cse3310.phms.ui.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Reminder;
import com.cse3310.phms.model.utils.MyDateFormatter;
import com.cse3310.phms.ui.services.ReminderAlarm;
import com.cse3310.phms.ui.utils.Events;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;

public class ReminderCard extends Card{

    private Reminder mReminder;

    public ReminderCard(Context context, Reminder reminder) {
        super(context, R.layout.card_inner_reminder);
        mReminder = reminder;
    }


    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        String formattedTime = MyDateFormatter.formatTime(mReminder.getAbsTime())
                + " | " + MyDateFormatter.formatDate(mReminder.getAbsTime());

        TextView titleTextView = (TextView) view.findViewById(R.id.inner_card_reminder_title);
        TextView timeTextView = (TextView) view.findViewById(R.id.inner_card_reminder_time);
        TextView messageTextView = (TextView) view.findViewById(R.id.inner_card_reminder_msg);
        ImageView imageButton = (ImageView) view.findViewById(R.id.inner_card_reminder_btn);

        titleTextView.setText(mReminder.getTitle());
        timeTextView.setText(formattedTime);
        messageTextView.setText(mReminder.getMessage());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReminderAlarm.cancelReminder(getContext(), mReminder);
                EventBus.getDefault().post(new Events.RemoveReminderCardEvent(ReminderCard.this));
            }
        });
    }

    public Reminder getReminder() {
        return mReminder;
    }
}

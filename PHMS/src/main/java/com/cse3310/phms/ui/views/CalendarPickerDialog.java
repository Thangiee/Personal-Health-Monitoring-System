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

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.cse3310.phms.R;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class CalendarPickerDialog extends Dialog {
    private CalendarPickerView calendarPickerView;
    private View.OnClickListener clickListener;
    private Date minDate;
    private Date maxDate;

    public CalendarPickerDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_calendar_picker);
        setTitle("Pick a Date");

        TextView doneTextView = (TextView) findViewById(R.id.calendar_picker_done_tv_btn);
        doneTextView.setOnClickListener(clickListener);

        Calendar today = Calendar.getInstance();
        calendarPickerView = (CalendarPickerView) findViewById(R.id.calendar_view);

        if (minDate == null || maxDate == null) {
            Calendar beginningOfYear = Calendar.getInstance();
            beginningOfYear.set(today.get(Calendar.YEAR), Calendar.JANUARY, 1);
            Calendar endOfYear = Calendar.getInstance();
            endOfYear.set(today.get(Calendar.YEAR), Calendar.DECEMBER, 31);
            endOfYear.add(Calendar.DATE, 1);

            minDate = beginningOfYear.getTime();
            maxDate = endOfYear.getTime();
        }

        calendarPickerView.init(minDate, maxDate).withSelectedDate(today.getTime());
    }

    public CalendarPickerView getCalendarPickerView() {
        return calendarPickerView;
    }

    public void setOnClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setDateRange(Date minDate, Date maxDate) {
        this.minDate = minDate;
        this.maxDate = maxDate;
    }
}

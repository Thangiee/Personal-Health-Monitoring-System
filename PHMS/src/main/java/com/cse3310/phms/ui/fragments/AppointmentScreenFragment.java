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

package com.cse3310.phms.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Appointment;
import com.cse3310.phms.ui.activities.AddAppointmentActivity_;
import com.cse3310.phms.ui.cards.AppointmentCard;
import com.cse3310.phms.ui.utils.DatabaseHandler;
import com.cse3310.phms.ui.views.CardListDialogFragment_;
import com.squareup.timessquare.CalendarPickerView;
import it.gmariotti.cardslib.library.internal.Card;
import org.androidannotations.annotations.*;
import org.apache.commons.lang.time.DateUtils;

import java.util.*;

@EFragment(R.layout.appointment_screen)
public class AppointmentScreenFragment extends SherlockFragment implements CalendarPickerView.OnDateSelectedListener {
    private boolean isAddMode = false;
    private Calendar mSelectedMonth;

    @ViewById(R.id.calendar_view)
    CalendarPickerView mCalendarPickerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mSelectedMonth = Calendar.getInstance();
    }

    @Override
    public void onResume() {
        updateCalendarPickerView();
        super.onResume();
    }

    @Override
    public void onPause() {
        mCalendarPickerView.setOnDateSelectedListener(null);
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.add_menu, menu);
        inflater.inflate(R.menu.overflow_menu, menu);
    }

    @OptionsItem(R.id.add_icon)
    void menuAddAppointment() {
        Toast.makeText(getActivity(), "Select a date to add a new appointment.", Toast.LENGTH_LONG).show();
        isAddMode = true;
    }

    @Click(R.id.appointment_previous_btn)
    void handlePreviousButtonClick() {
        mSelectedMonth.add(Calendar.MONTH, -1);
        updateCalendarPickerView();
    }

    @Click(R.id.appointment_next_btn)
    void handleNextButtonClick() {
        mSelectedMonth.add(Calendar.MONTH, 1);
        updateCalendarPickerView();
    }

    private void updateCalendarPickerView() {
        int year = mSelectedMonth.get(Calendar.YEAR);
        int month = mSelectedMonth.get(Calendar.MONTH);
        int numDays = mSelectedMonth.getActualMaximum(Calendar.DATE) + 1;

        // get the first day of the month
        Calendar minDate = Calendar.getInstance();
        minDate.set(Calendar.YEAR, year);
        minDate.set(Calendar.MONTH, month);
        minDate.set(Calendar.DATE, 1);

        // get the last day of the month
        Calendar maxDate = Calendar.getInstance();
        maxDate.set(Calendar.YEAR, year);
        maxDate.set(Calendar.MONTH, month);
        maxDate.set(Calendar.DATE, numDays);

        // set range of calendar and highlight current date
        mCalendarPickerView.init(minDate.getTime(), maxDate.getTime());
        mCalendarPickerView.setOnDateSelectedListener(this);

        // get all dates that have an appointment from the current selected month and year
        Set<Date> dates = new HashSet<Date>();
        for (Appointment appointment : DatabaseHandler.getAllRows(Appointment.class)) {
            Date date = new Date(appointment.getTime());
            if (isSameMonthAndYear(mSelectedMonth, date))
                dates.add(date);
        }

        // highlight those dates that have a least one appointment
        mCalendarPickerView.highlightDates(dates);
    }

    private boolean isSameMonthAndYear(Calendar month, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return month.get(Calendar.MONTH) == cal.get(Calendar.MONTH) &&
                month.get(Calendar.YEAR) == cal.get(Calendar.YEAR);
    }

    @Override
    public void onDateSelected(Date date) {
        if (isAddMode) {
            // start an activity to add new appointment
            isAddMode = false;
            Intent intent = new Intent(getActivity(), AddAppointmentActivity_.class);
            intent.putExtra("date", date);
            startActivity(intent);
        } else { // use has not click the add icon i.e. not in add mode
            final List<Card> cardList = new ArrayList<Card>();
            cardList.clear();

            // get appointment with the same date as the selected date
            for (Appointment appointment : DatabaseHandler.getAllRows(Appointment.class)) {
                if (DateUtils.isSameDay(date, new Date(appointment.getTime()))) {
                    cardList.add(new AppointmentCard(getActivity(), appointment));
                }
            }

            // open up a dialog with a list of appointments for dates that have appointment
            if (!cardList.isEmpty()) {
                CardListDialogFragment_.newInstance(cardList).show(getFragmentManager(), "tag");
            }
        }
    }

    @Override
    public void onDateUnselected(Date date) {

    }
}

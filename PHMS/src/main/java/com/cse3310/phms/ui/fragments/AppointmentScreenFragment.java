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

import android.os.Bundle;
import android.util.Log;
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
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang.time.DateUtils;

import java.util.*;

@EFragment(R.layout.appointment_screen)
public class AppointmentScreenFragment extends SherlockFragment implements CalendarPickerView.OnDateSelectedListener {
    private boolean isAddMode = false;

    @ViewById(R.id.calendar_view)
    CalendarPickerView mCalendarPickerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @AfterViews
    void onSetupCalendarPickerView() {
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 2);

        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.MONTH, -2);

        Date today = new Date();
        mCalendarPickerView.init(minDate.getTime(), nextYear.getTime()).withSelectedDate(today);
        mCalendarPickerView.setOnDateSelectedListener(this);

        Set<Date> dates = new HashSet<Date>();
        Log.d("aaaaaaaa", "" + dates.size());

        for (Appointment appointment : DatabaseHandler.getAllRows(Appointment.class)) {
            dates.add(new Date(appointment.getTime()));
        }

        mCalendarPickerView.highlightDates(dates);
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

    @Override
    public void onDateSelected(Date date) {
        if (isAddMode) {
            AddAppointmentActivity_.intent(getActivity()).start();
            isAddMode = false;
        } else {
            final List<Card> cardList = new ArrayList<Card>();
            cardList.clear();

            for (Appointment appointment : DatabaseHandler.getAllRows(Appointment.class)) {
                if (DateUtils.isSameDay(date, new Date(appointment.getTime()))) {
                    cardList.add(new AppointmentCard(getActivity(), appointment));
                }
            }

            if (!cardList.isEmpty()) {
                CardListDialogFragment_.newInstance(cardList).show(getFragmentManager(), "tag");
            }
        }
    }

    @Override
    public void onDateUnselected(Date date) {

    }
}

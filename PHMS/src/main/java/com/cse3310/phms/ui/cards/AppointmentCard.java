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
import com.cse3310.phms.R;
import com.cse3310.phms.model.Appointment;
import com.cse3310.phms.ui.views.AppointmentView_;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;

public class AppointmentCard extends Card{

    private Appointment mAppointment;

    public AppointmentCard(Context context, Appointment appointment) {
        super(context, R.layout.card_inner_appointment);
        mAppointment = appointment;
        addCardExpand(new TextAreaCardExpand(context, "Purpose: " + mAppointment.getPurpose()));
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);
        AppointmentView_ appointmentView = (AppointmentView_) view.findViewById(R.id.appointment_view);
        appointmentView.setAppointment(mAppointment);

        ViewToClickToExpand viewToClickToExpand = ViewToClickToExpand.builder().setupView(getCardView());
        setViewToClickToExpand(viewToClickToExpand);    // click anywhere on the card to expand
    }

    public Appointment getAppointment() {
        return mAppointment;
    }
}

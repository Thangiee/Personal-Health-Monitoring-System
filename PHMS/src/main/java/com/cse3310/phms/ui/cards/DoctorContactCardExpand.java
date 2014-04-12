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
import android.widget.TextView;
import com.cse3310.phms.R;
import com.cse3310.phms.model.DoctorInfo;
import it.gmariotti.cardslib.library.internal.CardExpand;

public class DoctorContactCardExpand extends CardExpand{
    private DoctorInfo mDoctorInfo;

    public DoctorContactCardExpand(Context context, DoctorInfo doctorInfo) {
        super(context, R.layout.contact_doctor_card_expand);
        mDoctorInfo = doctorInfo;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        TextView emailTextView = (TextView) view.findViewById(R.id.doctor_card_expand_email);
        TextView phoneTextView = (TextView) view.findViewById(R.id.doctor_card_expand_phone);
        TextView hospitalTextView = (TextView) view.findViewById(R.id.doctor_card_expand_hospital);
        TextView addressTextView = (TextView) view.findViewById(R.id.doctor_card_expand_address);

        emailTextView.setText(mDoctorInfo.getEmail());
        phoneTextView.setText(mDoctorInfo.getPhone());
        hospitalTextView.setText(mDoctorInfo.getHospital());
        addressTextView.setText(mDoctorInfo.getAddress());
    }
}

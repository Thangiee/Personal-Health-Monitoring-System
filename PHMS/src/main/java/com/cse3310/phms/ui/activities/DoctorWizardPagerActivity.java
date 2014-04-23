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

package com.cse3310.phms.ui.activities;

import android.os.Bundle;
import com.cse3310.phms.model.DoctorInfo;
import com.cse3310.phms.ui.cards.DoctorContactCard;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.views.wiziard_model.DoctorWizardModel;
import de.greenrobot.event.EventBus;

import static co.juliansuarez.libwizardpager.wizard.model.Page.SIMPLE_DATA_KEY;
import static com.cse3310.phms.ui.views.wiziard_model.DoctorWizardModel.*;

public class DoctorWizardPagerActivity extends BaseWizardPagerActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String actionBarTile = super.editMode ? "Edit Doctor Contact" : "Create Doctor Contact";
        getActionBar().setTitle(actionBarTile);
    }

    @Override
    public void onSetupWizardModel() {
        super.mWizardModel = new DoctorWizardModel(this);
    }

    @Override
    public void onSubmit() {
        // extract all the information from the process
        String fName = onGetPage(F_NAME_KEY).getData().getString(SIMPLE_DATA_KEY);
        String lName = onGetPage(L_NAME_KEY).getData().getString(SIMPLE_DATA_KEY);
        String eMail = onGetPage(EMAIL_KEY).getData().getString(SIMPLE_DATA_KEY);
        String phone = onGetPage(PHONE_KEY).getData().getString(SIMPLE_DATA_KEY);
        String hospital = onGetPage(HOSPITAL_KEY).getData().getString(SIMPLE_DATA_KEY);
        String hospitalAddr = onGetPage(HOSPITAL_ADDR_KEY).getData().getString(SIMPLE_DATA_KEY);

        // create the Doctor object
        DoctorInfo doctorInfo = new DoctorInfo(hospital, hospitalAddr);
        doctorInfo.setFirstName(fName)
                .setLastName(lName)
                .setEmail(eMail)
                .setPhone(phone);


        // if editing, remove the old card and add the new edited card
        if (super.editMode) {
            DoctorContactCard oldDoctorContactCard = ((DoctorWizardModel) mWizardModel).getDoctorContactCard();
            // post an event to ContactScreenFragment to remove the old card
            EventBus.getDefault().post(new Events.RemoveDoctorCardEvent(oldDoctorContactCard));
        }

        // post an event to ContactScreenFragment to add a new card
        EventBus.getDefault().post(new Events.AddDoctorCardEvent(new DoctorContactCard(this, doctorInfo)));
        finish();
    }
}

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
import com.cse3310.phms.model.Info;
import com.cse3310.phms.ui.cards.ContactCard;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.views.ContactWizardModel;
import de.greenrobot.event.EventBus;

import static co.juliansuarez.libwizardpager.wizard.model.Page.SIMPLE_DATA_KEY;
import static com.cse3310.phms.ui.views.ContactWizardModel.*;

public class ContactWizardPagerActivity extends BaseWizardPagerActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String actionBarTile = super.editMode ? "Edit Emergency Contact" : "Create Emergency Contact";
        getActionBar().setTitle(actionBarTile);
    }

    @Override
    public void onSetupWizardModel() {
        super.mWizardModel = new ContactWizardModel(this);
    }

    @Override
    public void onSubmit() {
        // extract all the information from the process
        String fName = onGetPage(F_NAME_KEY).getData().getString(SIMPLE_DATA_KEY);
        String lName = onGetPage(L_NAME_KEY).getData().getString(SIMPLE_DATA_KEY);
        String eMail = onGetPage(EMAIL_KEY).getData().getString(SIMPLE_DATA_KEY);
        String phone = onGetPage(PHONE_KEY).getData().getString(SIMPLE_DATA_KEY);

        // create the object
        Info contactInfo = new Info().setFirstName(fName).setLastName(lName).setEmail(eMail).setPhone(phone);

        // if editing, remove the old card and add the new edited card
        if (super.editMode) {
            ContactCard oldContactCard = ((ContactWizardModel) mWizardModel).getContactCard();
            // post an event to ContactScreenFragment to remove the old card
            EventBus.getDefault().post(new Events.RemoveContactCardEvent(oldContactCard));
        }

        // post an event to ContactScreenFragment to add a new card
        EventBus.getDefault().post(new Events.AddContactCardEvent(new ContactCard(this, contactInfo)));
        finish();
    }
}

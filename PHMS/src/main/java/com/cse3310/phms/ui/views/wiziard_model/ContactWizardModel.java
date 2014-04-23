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

package com.cse3310.phms.ui.views.wiziard_model;

import android.content.Context;
import android.text.InputType;
import co.juliansuarez.libwizardpager.wizard.model.AbstractWizardModel;
import co.juliansuarez.libwizardpager.wizard.model.PageList;
import com.andreabaccega.formedittextvalidator.EmailValidator;
import com.andreabaccega.formedittextvalidator.PersonNameValidator;
import com.andreabaccega.formedittextvalidator.Validator;
import com.cse3310.phms.model.Info;
import com.cse3310.phms.ui.cards.ContactCard;
import com.cse3310.phms.ui.utils.validators.PhoneValidator;
import com.cse3310.phms.ui.views.pager.EditTextPage;
import de.greenrobot.event.EventBus;

public class ContactWizardModel extends AbstractWizardModel {
    public static final String F_NAME_KEY = "Contact First Name";
    public static final String L_NAME_KEY = "Contact Last Name";
    public static final String EMAIL_KEY = "Email";
    public static final String PHONE_KEY = "Phone Number";
    private ContactCard mContactCard;

    public ContactWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        Validator nameValidator = new PersonNameValidator("Not a valid name");
        EmailValidator emailValidator = new EmailValidator("Invalid e-mail address");
        PhoneValidator phoneValidator = new PhoneValidator(10);

        // Check if a card was pass to this class to determine if the user
        // adding or editing a card.
        mContactCard = EventBus.getDefault().removeStickyEvent(ContactCard.class);
        if (mContactCard != null) {     // if a card was passed, the user pressed the edit button.
            Info contactInfo = mContactCard.getContactInfo();
            // since we are editing the card, let pre-set all the values.
            return new PageList(
                    new EditTextPage(this, F_NAME_KEY, nameValidator).setValue(contactInfo.getFirstName()).setInputType(InputType.TYPE_CLASS_TEXT),
                    new EditTextPage(this, L_NAME_KEY, nameValidator).setValue(contactInfo.getLastName()).setInputType(InputType.TYPE_CLASS_TEXT),
                    new EditTextPage(this, EMAIL_KEY, emailValidator).setValue(contactInfo.getEmail()).setInputType(InputType.TYPE_CLASS_TEXT),
                    new EditTextPage(this, PHONE_KEY, phoneValidator).setValue(contactInfo.getPhone()).setInputType(InputType.TYPE_CLASS_TEXT)
            );
        }

        return new PageList(
                new EditTextPage(this, F_NAME_KEY, nameValidator).setInputType(InputType.TYPE_CLASS_TEXT),
                new EditTextPage(this, L_NAME_KEY, nameValidator).setInputType(InputType.TYPE_CLASS_TEXT),
                new EditTextPage(this, EMAIL_KEY, emailValidator).setInputType(InputType.TYPE_CLASS_TEXT),
                new EditTextPage(this, PHONE_KEY, phoneValidator)
        );
    }

    public ContactCard getContactCard() {
        return mContactCard;
    }
}

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

import android.content.Context;
import android.text.InputType;
import co.juliansuarez.libwizardpager.wizard.model.AbstractWizardModel;
import co.juliansuarez.libwizardpager.wizard.model.PageList;
import com.andreabaccega.formedittextvalidator.EmailValidator;
import com.andreabaccega.formedittextvalidator.PersonNameValidator;
import com.andreabaccega.formedittextvalidator.Validator;
import com.cse3310.phms.model.EStorage;
import com.cse3310.phms.ui.cards.UrlCard;
import com.cse3310.phms.ui.utils.validators.PhoneValidator;
import com.cse3310.phms.ui.views.pager.EditTextPage;
import de.greenrobot.event.EventBus;

/**
 * Created by Owner on 4/16/14.
 */
public class UrlWizardModel extends AbstractWizardModel {
    public static final String URL_LINK = "Web Address";
    public static final String URL_TITLE = "Web Title";
    private UrlCard mUrlCard;

    public UrlWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        Validator nameValidator = new PersonNameValidator("Not a valid name");
        EmailValidator emailValidator = new EmailValidator("Invalid e-mail address");
        PhoneValidator phoneValidator = new PhoneValidator(10);

        // Check if a card was pass to this class to determine if the user
        // adding or editing a card.
        mUrlCard = EventBus.getDefault().removeStickyEvent(UrlCard.class);
        if (mUrlCard != null) {     // if a card was passed, the user pressed the edit button.
            EStorage UrlInfo = mUrlCard.getUrlInfo();
            // since we are editing the card, let pre-set all the values.
            return new PageList(
                    new EditTextPage(this, URL_LINK).setValue(UrlInfo.getUrl()).setInputType(InputType.TYPE_CLASS_TEXT),
                    new EditTextPage(this, URL_TITLE).setValue(UrlInfo.getTitle()).setInputType(InputType.TYPE_CLASS_TEXT)
            );
        }

        return new PageList(
                new EditTextPage(this, URL_LINK).setInputType(InputType.TYPE_CLASS_TEXT),
                new EditTextPage(this, URL_TITLE).setInputType(InputType.TYPE_CLASS_TEXT)
        );
    }

    public UrlCard getUrlCard() {
        return mUrlCard;
    }
}

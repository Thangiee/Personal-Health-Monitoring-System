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
import co.juliansuarez.libwizardpager.wizard.model.AbstractWizardModel;
import co.juliansuarez.libwizardpager.wizard.model.PageList;
import com.cse3310.phms.ui.views.pager.AccountInfoPage;
import com.cse3310.phms.ui.views.pager.ContactInfoPage;
import com.cse3310.phms.ui.views.pager.PersonalInfoPage;

public class RegistrationWizardModel extends AbstractWizardModel {
    public RegistrationWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new AccountInfoPage(this, "Account Registration").setRequired(true),
                new PersonalInfoPage(this, "Personal Info").setRequired(true),
                new ContactInfoPage(this, "Contact Info").setRequired(true)
        );
    }
}

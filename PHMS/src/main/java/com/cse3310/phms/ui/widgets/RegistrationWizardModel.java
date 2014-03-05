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

package com.cse3310.phms.ui.widgets;

import android.content.Context;
import co.juliansuarez.libwizardpager.wizard.model.AbstractWizardModel;
import co.juliansuarez.libwizardpager.wizard.model.PageList;
import com.cse3310.phms.ui.widgets.pager.AccountInfoPage;
import com.cse3310.phms.ui.widgets.pager.PersonalInfoPage;

public class RegistrationWizardModel extends AbstractWizardModel {
    public RegistrationWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new AccountInfoPage(this, "Account").setRequired(true),
                new PersonalInfoPage(this, "Personal Info").setRequired(true)
//                new TextPage(this, "First Name").setRequired(true),
//                new TextPage(this, "Last Name").setRequired(true),
//                new NumberPage(this, "Age").setRequired(true),
//                new SingleFixedChoicePage(this, "Gender")
//                        .setChoices(PersonalInfo.Gender.MALE.name(), PersonalInfo.Gender.FEMALE.name()).setRequired(true),
//                new NumberPage(this, "Height").setRequired(true),
//                new NumberPage(this, "Weight").setRequired(true)
        );
    }
}

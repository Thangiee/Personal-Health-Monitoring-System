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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import com.cse3310.phms.model.LoginManager;
import com.cse3310.phms.model.PersonalInfo;
import com.cse3310.phms.ui.widgets.RegistrationWizardModel;
import com.cse3310.phms.ui.widgets.pager.AccountInfoPage;
import com.cse3310.phms.ui.widgets.pager.ContactInfoPage;
import com.cse3310.phms.ui.widgets.pager.PersonalInfoPage;

public class RegistrationWizardPagerActivity extends BaseWizardPagerActivity{
    @Override
    public void onSetup() {
        super.mWizardModel = new RegistrationWizardModel(this);
    }

    @Override
    public void onSubmit() {
        String username = onGetPage("Account Registration").getData().getString(AccountInfoPage.USERNAME_KEY);
        String password = onGetPage("Account Registration").getData().getString(AccountInfoPage.PASSWORD_KEY);

        // check if username is taken.
        if (LoginManager.isUserNameExists(username)) {
            alertRegistrationFailed();
            return;
        }

        // extract all the information from the registration process and
        // use it to create a new user object.
        PersonalInfo info = new PersonalInfo("ads", "ass")
                .setAge(onGetPage("Personal Info").getData().getInt(PersonalInfoPage.AGE_KEY))
                .setGender(PersonalInfo.Gender.valueOf(onGetPage("Personal Info").getData().getString(PersonalInfoPage.GENDER_KEY)))
                .setWeight(onGetPage("Personal Info").getData().getDouble(PersonalInfoPage.WEIGHT_KEY))
                .setHeight(onGetPage("Personal Info").getData().getInt(PersonalInfoPage.HEIGHT_KEY));
        info.setFirstName(onGetPage("Personal Info").getData().getString(PersonalInfoPage.FIRST_KEY))
                .setLastName(onGetPage("Personal Info").getData().getString(PersonalInfoPage.LAST_KEY))
                .setEmail(onGetPage("Contact Info").getData().getString(ContactInfoPage.EMAIL_KEY))
                .setPhone(onGetPage("Contact Info").getData().getString(ContactInfoPage.PHONE_KEY));

        LoginManager.register(username, password, info);    // add the new user to the DB

        Intent intent = new Intent(this, SlidingMenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void alertRegistrationFailed() {
        new AlertDialog.Builder(this)
                .setTitle("Registration Error")
                .setMessage("The username is already taken. Please try a different username.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}

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
import com.activeandroid.ActiveAndroid;
import com.cse3310.phms.model.*;
import com.cse3310.phms.ui.views.pager.AccountInfoPage;
import com.cse3310.phms.ui.views.pager.ContactInfoPage;
import com.cse3310.phms.ui.views.pager.PersonalInfoPage;
import com.cse3310.phms.ui.views.wiziard_model.RegistrationWizardModel;

public class RegistrationWizardPagerActivity extends BaseWizardPagerActivity{
    @Override
    public void onSetupWizardModel() {
        super.mWizardModel = new RegistrationWizardModel(this);
    }

    @Override
    public void onSubmit() {
        String username = onGetPage("Account Registration").getData().getString(AccountInfoPage.USERNAME_KEY).toLowerCase();
        String password = onGetPage("Account Registration").getData().getString(AccountInfoPage.PASSWORD_KEY);

        // check if username is taken.
        if (LoginManager.isUserNameExists(username)) {
            alertRegistrationFailed();
            return;
        }

        // extract all the information from the registration process and
        // use it to create a new user object.
        PersonalInfo info = new PersonalInfo()
                .setAge(Integer.parseInt(onGetPage("Personal Info").getData().getString(PersonalInfoPage.AGE_KEY)))
                .setGender(PersonalInfo.Gender.valueOf(onGetPage("Personal Info").getData().getString(PersonalInfoPage.GENDER_KEY)))
                .setWeight(Double.parseDouble(onGetPage("Personal Info").getData().getString(PersonalInfoPage.WEIGHT_KEY)))
                .setHeight(onGetPage("Personal Info").getData().getInt(PersonalInfoPage.HEIGHT_KEY));
        info.setFirstName(onGetPage("Personal Info").getData().getString(PersonalInfoPage.FIRST_KEY))
                .setLastName(onGetPage("Personal Info").getData().getString(PersonalInfoPage.LAST_KEY))
                .setEmail(onGetPage("Contact Info").getData().getString(ContactInfoPage.EMAIL_KEY))
                .setPhone(onGetPage("Contact Info").getData().getString(ContactInfoPage.PHONE_KEY));

        LoginManager.register(username, password, info);    // add the new user to the DB
        initializeDemoData();
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


    private void initializeDemoData() {
        //User user = UserSingleton.INSTANCE.getCurrentUser();
        ActiveAndroid.beginTransaction();
        try {
            Food food = new Food("Applesauce");
            food.save();
            food = new Food("Apple juice");
            food.save();
            food = new Food("Apple");
            food.save();
            food = new Food("Beer");
            food.save();
            food = new Food("Bread");
            food.save();
            food = new Food("Bacon");
            food.save();
            food = new Food("corn");
            food.save();
            food = new Food("cheese");
            food.save();
            food = new Food("cake");
            food.save();
            food = new Food("cookies");
            food.save();
            food = new Food("chicken");
            food.save();
            DoctorInfo doctorInfo = new DoctorInfo("Sacred Heart Hospital", "Dallas");
            doctorInfo.setFirstName("John").setLastName("Dorian").setPhone("123456789").setEmail("JohnD@google.com");
            doctorInfo.save();
            Health urlInfo1 = new Health();
            urlInfo1.setUrl("http://www.nhl.com").setTitle("Hockey Chizz");
            urlInfo1.save();
            Recipe urlInfo2 = new Recipe();
            urlInfo2.setUrl("http://www.google.com").setTitle("Search Mode");
            urlInfo2.save();
            DietDesc urlInfo3 = new DietDesc();
            urlInfo3.setUrl("http://www.espn.com").setTitle("Sports Stuff and things");
            urlInfo3.save();

            //Test Medications
            Medication medication = new Medication("Tylenol", 2, 2, "Times(Per Day)","(Pills)");
            medication.save();
            medication = new Medication("Asprin", 1, 3, "Times(Per Day)","(Pills)");
            medication.save();
            medication = new Medication("Zetia", 1, 1, "Times(Per Day)","(Pills)");
            medication.save();
            medication = new Medication("Prozac", 1, 1, "Times(Per Week)","(Pills)");
            medication.save();
            medication = new Medication("Mucinex", 2, 2, "Times(Per Day)","(oz)");
            medication.save();


            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}

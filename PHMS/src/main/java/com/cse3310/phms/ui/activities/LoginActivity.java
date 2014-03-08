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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Button;
import com.andreabaccega.widget.FormEditText;
import com.cse3310.phms.R;
import com.cse3310.phms.model.LoginManager;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.act_login_screen)
public class LoginActivity extends Activity {

    @ViewById(R.id.act_login_screen_et_username)
    FormEditText mUsernameEditText;

    @ViewById(R.id.act_login_screen_et_password)
    FormEditText mPasswordEditText;

    @ViewById(R.id.act_login_screen_btn_login)
    Button mLoginButton;

    @Click(R.id.act_login_screen_btn_login)
    void clickedLoginButton() {
        String username = mUsernameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        if (LoginManager.login(username, password)) {
            Intent intent = new Intent(this, SlidingMenuActivity.class);
            startActivity(intent);
            finish();
        } else {
            alertLoginFailed();
        }
    }

    @Click(R.id.act_login_screen_tv_sign_up)
    void clickedSignUpTextView() {
        Intent intent = new Intent(this, RegistrationWizardPagerActivity.class);
        startActivity(intent);
    }

    private void alertLoginFailed() {
        new AlertDialog.Builder(this)
                .setTitle("Login Error")
                .setMessage("Incorrect username or password. Please try again.")
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

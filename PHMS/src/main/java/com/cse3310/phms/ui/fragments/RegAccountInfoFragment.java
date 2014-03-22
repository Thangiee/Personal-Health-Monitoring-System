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

package com.cse3310.phms.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import co.juliansuarez.libwizardpager.wizard.ui.PageFragmentCallbacks;
import com.actionbarsherlock.app.SherlockFragment;
import com.andreabaccega.widget.FormEditText;
import com.cse3310.phms.R;
import com.cse3310.phms.ui.adapters.TextWatcherAdapter;
import com.cse3310.phms.ui.utils.validators.MinimumLengthValidator;
import com.cse3310.phms.ui.utils.validators.NoSpaceValidator;
import com.cse3310.phms.ui.views.pager.AccountInfoPage;

public class RegAccountInfoFragment extends SherlockFragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private AccountInfoPage mPage;
    private FormEditText mUsername;
    private FormEditText mPassword;
    private boolean[] validFields;

    public static RegAccountInfoFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        RegAccountInfoFragment fragment = new RegAccountInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RegAccountInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (AccountInfoPage) mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.cse3310.phms.R.layout.frag_account_sign_up_page, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mUsername = (FormEditText) rootView.findViewById(R.id.frag_account_sign_up_page_et_username);
        mUsername.setText(mPage.getData().getString(AccountInfoPage.USERNAME_KEY));

        mPassword = (FormEditText) rootView.findViewById(R.id.frag_account_sign_up_page_et_password);
        mPassword.setText(mPage.getData().getString(AccountInfoPage.PASSWORD_KEY));

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mPage.getData().putBooleanArray("valid", validFields);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        validFields = mPage.getData().getBooleanArray("valid");
        if (validFields == null) {
            validFields = new boolean[2];
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    private boolean allFieldsValid() {
        for (boolean b : validFields) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUsername.addValidator(new NoSpaceValidator());
        mUsername.addValidator(new MinimumLengthValidator(3));
        mUsername.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                mPage.getData().putString(AccountInfoPage.USERNAME_KEY, mUsername.getText().toString());
                validFields[0] = mUsername.testValidity();
                mPage.getData().putBoolean(AccountInfoPage.VALID_KEY, allFieldsValid());
                mPage.notifyDataChanged();
            }
        });

        mPassword.addValidator(new NoSpaceValidator());
        mPassword.addValidator(new MinimumLengthValidator(6));
        mPassword.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                mPage.getData().putString(AccountInfoPage.PASSWORD_KEY, mPassword.getText().toString());
                validFields[1] = mPassword.testValidity();
                mPage.getData().putBoolean(AccountInfoPage.VALID_KEY, allFieldsValid());
                mPage.notifyDataChanged();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setMenuVisibility(isVisibleToUser);
        if (mUsername != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (!isVisibleToUser) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }
}

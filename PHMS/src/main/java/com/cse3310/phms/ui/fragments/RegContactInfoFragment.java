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
import com.cse3310.phms.ui.utils.validators.PhoneValidator;
import com.cse3310.phms.ui.views.pager.AccountInfoPage;
import com.cse3310.phms.ui.views.pager.ContactInfoPage;

import static com.cse3310.phms.ui.views.pager.ContactInfoPage.EMAIL_KEY;
import static com.cse3310.phms.ui.views.pager.ContactInfoPage.PHONE_KEY;

public class RegContactInfoFragment extends SherlockFragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private ContactInfoPage mPage;
    private FormEditText mEmail;
    private FormEditText mPhone;
    private boolean[] validFields = new boolean[2];


    public static RegContactInfoFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        RegContactInfoFragment fragment = new RegContactInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RegContactInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (ContactInfoPage) mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wizard_contact_info_sign_up, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mEmail = (FormEditText) rootView.findViewById(R.id.frag_contact_info_sign_up_ed_email);
        mEmail.setText(mPage.getData().getString(EMAIL_KEY));

        mPhone= (FormEditText) rootView.findViewById(R.id.frag_contact_info_sign_up_ed_phone);
        mPhone.setText(mPage.getData().getString(PHONE_KEY));

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
        mEmail.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                mPage.getData().putString(EMAIL_KEY, mEmail.getText().toString());
                validFields[0] = mEmail.testValidity();
                mPage.getData().putBoolean(AccountInfoPage.VALID_KEY, allFieldsValid());
                mPage.notifyDataChanged();
            }
        });

        mPhone.addValidator(new PhoneValidator(10));
        mPhone.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                mPage.getData().putString(PHONE_KEY, mPhone.getText().toString());
                validFields[1] = mPhone.testValidity();
                mPage.getData().putBoolean(AccountInfoPage.VALID_KEY, allFieldsValid());
                mPage.notifyDataChanged();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setMenuVisibility(isVisibleToUser);
        if (mEmail != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (!isVisibleToUser) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }
}

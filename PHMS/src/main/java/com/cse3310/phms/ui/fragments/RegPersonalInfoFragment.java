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
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import co.juliansuarez.libwizardpager.wizard.ui.PageFragmentCallbacks;
import com.andreabaccega.widget.FormEditText;
import com.cse3310.phms.R;
import com.cse3310.phms.model.PersonalInfo;
import com.cse3310.phms.ui.adapters.TextWatcherAdapter;
import com.cse3310.phms.ui.widgets.pager.PersonalInfoPage;

import static com.cse3310.phms.ui.widgets.pager.PersonalInfoPage.*;

public class RegPersonalInfoFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private PersonalInfoPage mPage;
    private FormEditText mFirstName;
    private FormEditText mLastName;
    private FormEditText mAge;
    private RadioButton mGender;
    private FormEditText mWeight;
    private Spinner mFeetSpinner;
    private Spinner mInchSpinner;

    public static RegPersonalInfoFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        RegPersonalInfoFragment fragment = new RegPersonalInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RegPersonalInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (PersonalInfoPage) mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_person_info_sign_up_page, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mFirstName = (FormEditText) rootView.findViewById(R.id.frag_person_Info_sign_up_page_et_first_name);
        mFirstName.setText(mPage.getData().getString(FIRST_KEY));

        mLastName = (FormEditText) rootView.findViewById(R.id.frag_person_Info_sign_up_page_et_last_name);
        mLastName.setText(mPage.getData().getString(LAST_KEY));

        mAge = (FormEditText) rootView.findViewById(R.id.frag_person_Info_sign_up_page_et_age);

        mGender = (RadioButton) rootView.findViewById(R.id.rb_gender_male);
        mPage.getData().putString(GENDER_KEY, PersonalInfo.Gender.MALE.name()); // default value

        mWeight = (FormEditText) rootView.findViewById(R.id.frag_person_Info_sign_up_page_et_weight);

        mFeetSpinner = (Spinner) rootView.findViewById(R.id.frag_person_Info_sign_up_page_sp_ft);
        ArrayAdapter<CharSequence> ftAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.ft, android.R.layout.simple_spinner_item);
        ftAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFeetSpinner.setAdapter(ftAdapter);

        mInchSpinner = (Spinner) rootView.findViewById(R.id.frag_person_Info_sign_up_page_sp_in);
        ArrayAdapter<CharSequence> inAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.in, android.R.layout.simple_spinner_item);
        ftAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mInchSpinner.setAdapter(inAdapter);

        return rootView;
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFirstName.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                mPage.getData().putString(FIRST_KEY, mFirstName.getText().toString());
                mPage.notifyDataChanged();
                mFirstName.testValidity();
            }
        });

        mLastName.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                mPage.getData().putString(LAST_KEY, mLastName.getText().toString());
                mPage.notifyDataChanged();
                mLastName.testValidity();
            }
        });

        mAge.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!mAge.testValidity()) {
                    mPage.getData().putInt(AGE_KEY, 0);
                } else {
                    mPage.getData().putInt(AGE_KEY, Integer.parseInt(s.toString()));
                }
                mPage.notifyDataChanged();
            }
        });

        mGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPage.getData().putString(GENDER_KEY, PersonalInfo.Gender.MALE.name());
                } else {
                    mPage.getData().putString(GENDER_KEY, PersonalInfo.Gender.FEMALE.name());
                }
                mPage.notifyDataChanged();
            }
        });

        mWeight.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!mWeight.testValidity()) {
                    mPage.getData().putDouble(WEIGHT_KEY, 0.0);
                } else {
                    mPage.getData().putDouble(WEIGHT_KEY, Double.parseDouble(s.toString()));
                }
                mPage.notifyDataChanged();
            }
        });

        mFeetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int inches = Integer.parseInt(parent.getItemAtPosition(position).toString()) * 12;
                inches += Integer.parseInt(mInchSpinner.getSelectedItem().toString());

                mPage.getData().putInt(HEIGHT_KEY, inches);
                mPage.notifyDataChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mInchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int inches = Integer.parseInt(parent.getItemAtPosition(position).toString());
                inches += Integer.parseInt(mFeetSpinner.getSelectedItem().toString()) * 12;

                mPage.getData().putInt(HEIGHT_KEY, inches);
                mPage.notifyDataChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setMenuVisibility(isVisibleToUser);
        if (mFirstName != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (!isVisibleToUser) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }
}

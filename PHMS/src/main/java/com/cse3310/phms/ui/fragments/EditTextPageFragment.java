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
import co.juliansuarez.libwizardpager.wizard.model.Page;
import co.juliansuarez.libwizardpager.wizard.ui.PageFragmentCallbacks;
import com.actionbarsherlock.app.SherlockFragment;
import com.andreabaccega.formedittextvalidator.Validator;
import com.andreabaccega.widget.FormEditText;
import com.cse3310.phms.R;
import com.cse3310.phms.ui.adapters.TextWatcherAdapter;
import com.cse3310.phms.ui.views.pager.EditTextPage;

import static co.juliansuarez.libwizardpager.wizard.model.Page.SIMPLE_DATA_KEY;

public class EditTextPageFragment extends SherlockFragment{
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private EditTextPage mPage;
    private boolean[] validFields = new boolean[1];
    private Validator[] validators;
    private FormEditText mFormEditText;

    public static EditTextPageFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        EditTextPageFragment fragment = new EditTextPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EditTextPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (EditTextPage) mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_edit_txt_page, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mFormEditText = (FormEditText) rootView.findViewById(R.id.frag_edit_txt_field);
        mFormEditText.setText(mPage.getData().getString(Page.SIMPLE_DATA_KEY));

        // set validators for formEditText
        if (validators != null) {
            for (Validator validator : validators) {
                mFormEditText.addValidator(validator);
            }
        }

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
            validFields = new boolean[1];
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
        mFormEditText.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                mPage.getData().putString(SIMPLE_DATA_KEY, mFormEditText.getText().toString());
                validFields[0] = mFormEditText.testValidity();
                mPage.getData().putBoolean(EditTextPage.VALID_KEY, allFieldsValid());
                mPage.notifyDataChanged();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setMenuVisibility(isVisibleToUser);
        if (mFormEditText != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (!isVisibleToUser) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }

    public EditTextPageFragment setValidators(Validator... validators) {
        this.validators = validators;
        return this;
    }
}

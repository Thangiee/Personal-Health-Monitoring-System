package com.cse3310.phms.ui.views.pager;

import android.support.v4.app.Fragment;
import co.juliansuarez.libwizardpager.wizard.model.ModelCallbacks;
import co.juliansuarez.libwizardpager.wizard.model.Page;
import co.juliansuarez.libwizardpager.wizard.model.ReviewItem;
import com.andreabaccega.formedittextvalidator.Validator;
import com.cse3310.phms.ui.fragments.EditTextPageFragment;

import java.util.ArrayList;

public class EditTextPage extends Page{
    private Validator[] validators;

    public EditTextPage(ModelCallbacks callbacks, String title, Validator... validators) {
        super(callbacks, title);
        this.validators = validators;
    }

    @Override
    public Fragment createFragment() {
        EditTextPageFragment pageFragment = EditTextPageFragment.create(getKey());
        pageFragment.setValidators(validators);
        return pageFragment;
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem(mTitle , mData.getString(SIMPLE_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return mData.getBoolean(VALID_KEY);
    }
}

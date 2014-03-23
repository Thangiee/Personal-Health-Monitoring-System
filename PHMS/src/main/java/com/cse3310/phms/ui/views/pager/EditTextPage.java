package com.cse3310.phms.ui.views.pager;

import android.support.v4.app.Fragment;
import co.juliansuarez.libwizardpager.wizard.model.ModelCallbacks;
import co.juliansuarez.libwizardpager.wizard.model.Page;
import co.juliansuarez.libwizardpager.wizard.model.ReviewItem;
import com.andreabaccega.formedittextvalidator.Validator;
import com.cse3310.phms.ui.fragments.EditTextPageFragment;

import java.util.ArrayList;

public class EditTextPage extends Page{
    private Validator validator;

    public EditTextPage(ModelCallbacks callbacks, String title, Validator validator) {
        super(callbacks, title);
        this.validator = validator;
    }

    @Override
    public Fragment createFragment() {
        return EditTextPageFragment.create(getKey(), validator);
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

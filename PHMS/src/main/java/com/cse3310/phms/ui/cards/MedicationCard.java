package com.cse3310.phms.ui.cards;

import android.content.Context;
import android.view.View;
import com.cse3310.phms.model.Medication;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Zach on 4/13/2014.
 */
public class MedicationCard extends Card{
    protected String mBtnTitle, mSubTitle;
    protected View.OnClickListener mBtnClickListener;

    private Medication medication = new Medication();

    public MedicationCard(Context context, Medication medication) {
        super(context);

        this.medication = medication;
        addCardExpand(new MedicationCardExpand(getContext(), this.medication)); // add the view to expand when the card is clicked
    }


    public void setSubTitle(String subTitle) {
        this.mSubTitle = subTitle;
    }

    public void setButtonTitle(String title) {
        this.mBtnTitle = title;
    }

    public void setBtnClickListener(View.OnClickListener mBtnClickListener) {
        this.mBtnClickListener = mBtnClickListener;
    }

    public Medication getMedication(){
        return this.medication;
    }
}

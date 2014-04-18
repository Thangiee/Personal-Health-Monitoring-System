package com.cse3310.phms.ui.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Medication;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;

/**
 * Created by Zach on 4/13/2014.
 */
public class MedicationCard extends Card{
    protected String mBtnTitle, mSubTitle;
    protected View.OnClickListener mBtnClickListener;

    private Medication medication;

    public MedicationCard(Context context, Medication medication) {
        super(context);

        this.medication = medication;
        addCardExpand(new MedicationCardExpand(getContext(), this.medication)); // add the view to expand when the card is clicked
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        TextView title = (TextView) view.findViewById(R.id.card_inner_txt_title);
        TextView subTitle = (TextView) view.findViewById(R.id.card_inner_txt_sub);
        TextView mButton = (TextView) view.findViewById(R.id.card_inner_txt_btn);

        title.setText(mTitle);
        subTitle.setText(mSubTitle);
        mButton.setText(mBtnTitle);
        mButton.setClickable(true);
        mButton.setOnClickListener(mBtnClickListener);

        ViewToClickToExpand viewToClickToExpand = ViewToClickToExpand.builder().setupView(getCardView());
        setViewToClickToExpand(viewToClickToExpand);    // click anywhere on the card to expand
    }

    public void setSubTitle(String subTitle) {
        this.mSubTitle = subTitle;
    }

    public void setTitle(String title) {
        super.setTitle(title);
        this.mTitle = title;
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

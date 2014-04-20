package com.cse3310.phms.ui.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Vitals;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;

/**
 * Created by E&N on 4/20/2014.
 */
public class VitalsCard extends Card{
    protected String mBtnTitle, mSubTitle;
    protected View.OnClickListener mBtnClickListener;
    private Vitals vitals;

    public VitalsCard(Context context, Vitals vitals) {
        super(context, R.layout.card_inner_vitals);

        this.vitals = vitals;
        addCardExpand(new VitalsCardExpand(getContext(), this.vitals)); // add the view to expand when the card is clicked
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        TextView title = (TextView) view.findViewById(R.id.vitals_inner_txt_title);
        TextView mButton = (TextView) view.findViewById(R.id.vitals_inner_txt_btn);

        title.setText(mTitle);
//        subTitle.setText(mSubTitle);
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

    public Vitals getVitals(){
        return this.vitals;
    }
}

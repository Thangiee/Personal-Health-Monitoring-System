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

package com.cse3310.phms.ui.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.cse3310.phms.R;
import com.cse3310.phms.model.EStorage;
import it.gmariotti.cardslib.library.internal.Card;


/**
 * Created by Owner on 4/15/14.
 */
public class UrlCard extends Card {
    private EStorage mUrlInfo;
    private String mTitle;
    private String mSubtitle;
    private ImageButton mButton;
    protected View.OnClickListener mBtnClickListener;



    public UrlCard(Context context, EStorage urlInfo) {
        super(context, R.layout.card_inner_url);
        mUrlInfo = urlInfo;
        System.out.println(urlInfo.getTitle());
       // addCardExpand(new ContactCardExpand(context, mContactInfo));
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        TextView title = (TextView) view.findViewById(R.id.card_inner_txt_title);
        title.setText(mUrlInfo.getTitle());
        setTitle(title.getText().toString());
        TextView subTitle = (TextView) view.findViewById(R.id.card_inner_txt_sub);
        subTitle.setText(mUrlInfo.getUrl());
        setSubTitle(subTitle.getText().toString());
        ImageButton mButton = (ImageButton) view.findViewById(R.id.card_inner_txt_btn);

        title.setText(mTitle);
        subTitle.setText(mSubtitle);
        mButton.setClickable(true);
        mButton.setOnClickListener(mBtnClickListener);

       // ViewToClickToExpand viewToClickToExpand = ViewToClickToExpand.builder().setupView(getCardView());
      // setViewToClickToExpand(viewToClickToExpand);    // click anywhere on the card to expand
    }

    public void setTitle(String Title){this.mTitle = Title;}
    public void setSubTitle(String subTitle) {
        this.mSubtitle = subTitle;
    }



    public void setBtnClickListener(View.OnClickListener mBtnClickListener) {

    this.mBtnClickListener = mBtnClickListener;
    }

    public EStorage getUrlInfo()
    {
        return mUrlInfo;
    }
}


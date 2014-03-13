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
import android.widget.TextView;
import com.cse3310.phms.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;

public class FoodCard extends Card{

    protected String mTitle;
    protected String mSubTitle;
    protected String mBtnTitle;
    protected View.OnClickListener mBtnClickListener;

    public FoodCard(Context context) {
        super(context, R.layout.card_inner_food);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        TextView title = (TextView) view.findViewById(R.id.card_inner_txt_title);
        TextView subTitle = (TextView) view.findViewById(R.id.card_inner_txt_sub);
        TextView btnTitle = (TextView) view.findViewById(R.id.card_inner_txt_btn);

        title.setText(mTitle);
        subTitle.setText(mSubTitle);
        btnTitle.setText(mBtnTitle);
        btnTitle.setClickable(true);

        btnTitle.setOnClickListener(mBtnClickListener);

        ViewToClickToExpand viewToClickToExpand = ViewToClickToExpand.builder().setupView(getCardView());
        setViewToClickToExpand(viewToClickToExpand);
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setSubTitle(String subTitle) {
        this.mSubTitle = subTitle;
    }

    public void setBtnTitle(String btnTitle) {
        this.mBtnTitle = btnTitle;
    }

    public void setBtnClickListener(View.OnClickListener mBtnClickListener) {
        this.mBtnClickListener = mBtnClickListener;
    }
}

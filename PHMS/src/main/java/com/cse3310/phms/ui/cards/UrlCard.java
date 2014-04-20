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
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.cse3310.phms.R;
import com.cse3310.phms.model.EStorage;
import com.cse3310.phms.ui.activities.WebViewActivity;
import com.cse3310.phms.ui.utils.Events;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;


/**
 * Created by Owner on 4/15/14.
 */
public class UrlCard extends Card {
    private EStorage mUrlInfo;

    public UrlCard(Context context, EStorage urlInfo) {
        super(context, R.layout.card_inner_url);
        mUrlInfo = urlInfo;
        setSwipeable(true);
        setTitle(mUrlInfo.getTitle()); // use for searching
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        TextView title = (TextView) view.findViewById(R.id.card_inner_txt_title);
        TextView subTitle = (TextView) view.findViewById(R.id.card_inner_txt_sub);
        ImageButton mButton = (ImageButton) view.findViewById(R.id.card_inner_img_btn);

        title.setText(mUrlInfo.getTitle());
        subTitle.setText(mUrlInfo.getUrl());

        mButton.setClickable(true);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("urlpass", mUrlInfo.getUrl());
                mContext.startActivity(intent);
            }
        });

        setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                EventBus.getDefault().postSticky(new Events.RemoveUrlCardEvent(UrlCard.this));
            }
        });
    }


    public EStorage getUrlInfo() {
        return mUrlInfo;
    }
}


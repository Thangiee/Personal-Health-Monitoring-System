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
import it.gmariotti.cardslib.library.internal.CardHeader;

public class DIdYouKnowCard extends Card {

    private String mInnerTitle;

    public DIdYouKnowCard(Context context, String innerTitle) {
        this(context, R.layout.card_inner_did_you_know, innerTitle);
    }

    public DIdYouKnowCard(Context context, int innerLayout, String innerTitle) {
        super(context, innerLayout);
        mInnerTitle = innerTitle;
        init();
    }

    private void init() {
        CardHeader cardHeader = new DidYouKnowHeader(getContext(), R.layout.card_inner_did_you_know_header);
        cardHeader.setTitle("Did You Know?");
        addCardHeader(cardHeader);
        setShadow(false);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        TextView innerTitle = (TextView) view.findViewById(R.id.card_inner_did_you_know_text);
        innerTitle.setText(mInnerTitle);
    }

    class DidYouKnowHeader extends CardHeader {

        public DidYouKnowHeader(Context context, int innerLayout) {
            super(context, innerLayout);
        }
    }
}

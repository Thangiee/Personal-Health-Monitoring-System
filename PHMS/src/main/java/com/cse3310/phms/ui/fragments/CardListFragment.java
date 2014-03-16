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

package com.cse3310.phms.ui.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.cse3310.phms.R;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.frag_card_list)
public class CardListFragment extends SherlockFragment {
    private static int idCounter = 0;
    private List<Card> mCardList = new ArrayList<Card>();

    @ViewById(R.id.frag_diet_food_list)
    CardListView mCardListView;

    @AfterViews
    void onAfterViews() {
        // Set up adapter
        CardArrayAdapter adapter = new CardArrayAdapter(getActivity(), new ArrayList<Card>(mCardList));
        adapter.setEnableUndo(true); // Enable undo controller!
        // Set up animation adapter
        AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(adapter);
        animCardArrayAdapter.setAbsListView(mCardListView);
        if (mCardListView != null) {
            mCardListView.setExternalAdapter(animCardArrayAdapter, adapter);
        }
    }

    public void addCard(Card card) {
        card.setSwipeable(true);
        card.setId("" + idCounter++);
        mCardList.add(card);
    }

    public void addCards(List<Card> cards) {
        for (Card card : cards) {
            addCard(card);
        }
    }
}

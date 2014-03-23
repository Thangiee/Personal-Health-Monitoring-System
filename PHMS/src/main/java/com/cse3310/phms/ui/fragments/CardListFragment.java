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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.cse3310.phms.R;
import com.cse3310.phms.ui.utils.Events;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EFragment(R.layout.frag_card_list)
public class CardListFragment extends SherlockFragment {
    private static int idCounter = 0;
    private Set<String> mSuggestionSet;
    private List<Card> mCardList = new ArrayList<Card>();
    private boolean mChangeSearchPriorities = true;
    private CardArrayAdapter adapter;

    @ViewById(R.id.frag_diet_food_list)
    CardListView mCardListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSuggestionSet = new HashSet<String>(mCardList.size()); // use to collect unique food to be use as search suggestions.
        for (Card card : mCardList) {
            mSuggestionSet.add(card.getTitle());
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        if (mChangeSearchPriorities) {
            update();
        }
        super.onResume();
    }

    @AfterViews
    void setupAdapter() {
        // Set up adapter
        adapter = new CardArrayAdapter(getActivity(), new ArrayList<Card>(mCardList));
        adapter.setEnableUndo(true); // Enable undo controller!
        // Set up animation adapter
        AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(adapter);
        animCardArrayAdapter.setAbsListView(mCardListView);
        if (mCardListView != null) {
            mCardListView.setExternalAdapter(animCardArrayAdapter, adapter);
        }
    }

    public void initializeCard(Card card) {
        card.setSwipeable(true);
        card.setId("" + idCounter++);
        mCardList.add(card);
    }

    public void initializeCards(List<Card> cards) {
        for (Card card : cards) {
            initializeCard(card);
        }
    }

    public void addCard(Card card) {
        adapter.add(card);
        mSuggestionSet.add(card.getTitle());
        initializeCard(card);
    }

    public void removeCard(Card card) {
        adapter.remove(card);
        mSuggestionSet.remove(card.getTitle());
        mCardList.remove(card);
    }

    public void update() {
        // change the suggestions for search to unique intake food name. Sending to BaseActivity
        EventBus.getDefault().postSticky(new Events.SetSuggestionEvent(mSuggestionSet));
        // setup the cards that will be searched if the user decide to search Sending to SearchCardsActivity
        EventBus.getDefault().postSticky(new Events.initCardsToSearchEvent(mCardList));
        adapter.notifyDataSetChanged();
    }

    public void setChangeSearchPriorities(boolean changeSearchPriorities) {
        this.mChangeSearchPriorities = changeSearchPriorities;
    }
}

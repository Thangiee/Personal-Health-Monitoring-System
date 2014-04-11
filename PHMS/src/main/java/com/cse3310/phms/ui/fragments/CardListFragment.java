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
import android.widget.TextView;
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

@EFragment(R.layout.card_list)
public class CardListFragment extends SherlockFragment {
    private static int idCounter = 0;
    private Set<String> mSuggestionSet;
    protected List<Card> mCardList = new ArrayList<Card>();
    private boolean mChangeSuggestions = true;
    private boolean mShowHintOnEmpty = false;
    private CardArrayAdapter adapter;

    @ViewById(R.id.frag_card_list_view)
    CardListView mCardListView;
    @ViewById(R.id.card_list_hint_tv)
    TextView emptyListHintTextView;

    public static CardListFragment_ newInstance(List<Card> cardList, boolean showHintOnEmpty) {
        return newInstance(cardList, showHintOnEmpty, true);
    }

    public static CardListFragment_ newInstance(List<Card> cardList, boolean showHintOnEmpty, boolean changeSuggestions) {
        CardListFragment_ fragment = new CardListFragment_();
        fragment.mCardList = new ArrayList<Card>(cardList);

        Bundle args = new Bundle();
        args.putBoolean("hint", showHintOnEmpty);
        args.putBoolean("suggestions", changeSuggestions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mShowHintOnEmpty = getArguments().getBoolean("hint");
        mChangeSuggestions = getArguments().getBoolean("suggestions");

        mSuggestionSet = new HashSet<String>(mCardList.size()); // use to collect unique food to be use as search suggestions.
        for (Card card : mCardList) {
            card.setId("" + idCounter++);
            mSuggestionSet.add(card.getTitle());
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        if (mChangeSuggestions) {
            update();
        }

        if (mShowHintOnEmpty) {
            // make hint message visible when mCardList is empty
            emptyListHintTextView.setVisibility(mCardList.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        }
        super.onResume();
    }

    @AfterViews
    void setupAdapter() {
        // Set up adapter
        adapter = new CardArrayAdapter(getActivity(), mCardList);
        adapter.setEnableUndo(true); // Enable undo controller!
        // Set up animation adapter
        AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(adapter);
        animCardArrayAdapter.setAbsListView(mCardListView);
        if (mCardListView != null) {
            mCardListView.setExternalAdapter(animCardArrayAdapter, adapter);
        }
    }

    public void addCards(List<Card> cards) {
        for (Card card : cards) {
            card.setId("" + idCounter++);
            mSuggestionSet.add(card.getTitle());
        }

        adapter.addAll(cards);
    }

    public void removeCard(Card card) {
        adapter.remove(card);
    }

    public void clearCards() {
        adapter.clear();
    }

    public void update() {
        // change the suggestions for search to unique intake food name. Sending to BaseActivity
        EventBus.getDefault().postSticky(new Events.SetSuggestionEvent(mSuggestionSet));
        // setup the cards that will be searched if the user decide to search Sending to SearchCardsActivity
        EventBus.getDefault().postSticky(new Events.initCardsToSearchEvent(mCardList));
        adapter.notifyDataSetChanged();
    }
}

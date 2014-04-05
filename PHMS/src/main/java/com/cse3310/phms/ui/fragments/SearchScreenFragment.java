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
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.SherlockFragment;
import com.cse3310.phms.R;
import com.cse3310.phms.ui.utils.Comparators.CardComparator;
import it.gmariotti.cardslib.library.internal.Card;
import org.androidannotations.annotations.EFragment;

import java.util.Collections;
import java.util.List;

@EFragment(R.layout.search_screen)
public class SearchScreenFragment extends SherlockFragment{

    private List<Card> matchCardList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Collections.sort(matchCardList, CardComparator.NAME_SORT);
        CardListFragment_ cardListFragment = new CardListFragment_();
        cardListFragment.initializeCards(matchCardList);
        cardListFragment.setChangeSearchProperties(false); // don't change the suggestion or list of card to search on startup

        SearchScreenHeaderFragment_ screenHeaderFragment = new SearchScreenHeaderFragment_();
        screenHeaderFragment.setResultsFound(matchCardList.size());

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.search_screen_header_container, screenHeaderFragment);
        transaction.add(R.id.search_screen_result_list_container, cardListFragment).commit();
    }

    public void setMatchCardList(List<Card> matchCardList) {
        this.matchCardList = matchCardList;
    }
}

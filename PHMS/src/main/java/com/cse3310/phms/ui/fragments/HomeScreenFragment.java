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
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cse3310.phms.R;
import com.cse3310.phms.model.HealthFact;
import com.cse3310.phms.ui.cards.DIdYouKnowCard;
import com.cse3310.phms.ui.utils.DatabaseHandler;
import com.cse3310.phms.ui.utils.UserSingleton;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@EFragment(R.layout.home_screen)
public class HomeScreenFragment extends SherlockFragment{
    @ViewById(R.id.home_title)              TextView mTitleTextView;
    @ViewById(R.id.card_home_image_view)    CardView mHomeImageCardView;
    @ViewById(R.id.home_screen_layout)      LinearLayout mLinearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        // set title
        getSherlockActivity().getSupportActionBar().setTitle("Home");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.overflow_menu, menu);
    }

    @AfterViews
    void OnAfterViews() {
        mTitleTextView.setText("Welcome, " + UserSingleton.INSTANCE.getCurrentUser().getPersonalInfo().getFirstName() + "!");
        mHomeImageCardView.setCard(new Card(getActivity(), R.layout.home_feed_image));

        List<HealthFact> healthFactList = DatabaseHandler.getAllRows(HealthFact.class);
        int size = (int) (healthFactList.size() * .50);

        Random random = new Random();
        Set<Integer> integerSet = new LinkedHashSet<Integer>(size);
        while (integerSet.size() < size) {
            integerSet.add(random.nextInt(healthFactList.size()));
        }

        for (int i : integerSet) {
            Card card = new DIdYouKnowCard(getActivity(), healthFactList.get(i).getFact());
            CardView cardView = new CardView(getActivity());
            cardView.setCard(card);
            ViewGroup.LayoutParams layoutParams = cardView.getInternalMainCardLayout().getLayoutParams();

            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(24, 24, 24, 24);
            }
            mLinearLayout.addView(cardView);
        }

    }

}

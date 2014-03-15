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
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Food;
import com.cse3310.phms.ui.activities.BaseActivity;
import com.cse3310.phms.ui.cards.FoodCard;
import com.cse3310.phms.ui.cards.FoodCardExpand;
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

@EFragment(R.layout.frag_diet_screen)
public class DietScreenFragment extends SherlockFragment {
    private static int idCounter = 0;
    private List<FoodCard> mFoodCards = new ArrayList<FoodCard>();
    private List<Food> mFoodList;

    @ViewById(R.id.frag_diet_food_list)
    CardListView mCardListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().registerSticky(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        for (Food food : mFoodList) {
            addFoodCard(food);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @AfterViews
    void onAfterViews() {
        // Set up adapter
        CardArrayAdapter adapter = new CardArrayAdapter(getActivity(), new ArrayList<Card>(mFoodCards));
        adapter.setEnableUndo(true); // Enable undo controller!
        // Set up animation adapter
        AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(adapter);
        animCardArrayAdapter.setAbsListView(mCardListView);

        if (mCardListView != null) {
            mCardListView.setExternalAdapter(animCardArrayAdapter, adapter);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // change suggestions to items in this screen
        Set<String> suggestions = new HashSet<String>(mFoodCards.size());
        for (FoodCard card : mFoodCards) {
            suggestions.add(card.getTitle());
        }

        BaseActivity parentActivity = ((BaseActivity) getActivity());
        parentActivity.setSuggestions(suggestions);
//        parentActivity.setCardsToSearch(new ArrayList<Card>(mFoodCards));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(Events.initListEvent<Food> event) {
        this.mFoodList = event.list;
    }

    public void addFoodCard(Food food) {
        FoodCard card = new FoodCard(getActivity());
        card.setSwipeable(true);
        card.setId("" + idCounter++);
        card.setTitle(food.getName());
        card.setSubTitle("" + food.getCalories());
        card.setBtnTitle("Edit");

        card.setBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Click on edit", Toast.LENGTH_SHORT).show();
            }
        });
        FoodCardExpand cardExpand = new FoodCardExpand(getActivity(), food);
        card.addCardExpand(cardExpand);
        mFoodCards.add(card);
    }
}

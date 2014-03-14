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
import com.cse3310.phms.ui.activities.SlidingMenuActivity;
import com.cse3310.phms.ui.cards.FoodCard;
import com.cse3310.phms.ui.cards.FoodCardExpand;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
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
    private List<Card> mFoodCards = new ArrayList<Card>();

    @ViewById(R.id.frag_diet_food_list)
    CardListView mCardListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addFoodCard(new Food("Egg").setCalories(200).setFat(30).setFiber(52));
        addFoodCard(new Food("Chess").setCalories(250));
        addFoodCard(new Food("water").setCalories(0));
        addFoodCard(new Food("Egg").setCalories(200));
        addFoodCard(new Food("Egg").setCalories(200).setFat(30).setFiber(52));
        addFoodCard(new Food("Chess").setCalories(250));
        addFoodCard(new Food("water").setCalories(0));
        addFoodCard(new Food("Egg").setCalories(200));
        addFoodCard(new Food("Egg").setCalories(200).setFat(30).setFiber(52));
        addFoodCard(new Food("Chess").setCalories(250));
        addFoodCard(new Food("water").setCalories(0));
        addFoodCard(new Food("Egg").setCalories(200));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @AfterViews
    void onAfterViews() {
        // Set up adapter
        CardArrayAdapter adapter = new CardArrayAdapter(getActivity(), mFoodCards);
        adapter.setEnableUndo(true); // Enable undo controller!
        // Set up animation adapter
        AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(adapter);
        animCardArrayAdapter.setAbsListView(mCardListView);

        if (mCardListView != null) {
            mCardListView.setExternalAdapter(animCardArrayAdapter, adapter);
        }

        Set<String> suggestions = new HashSet<String>(mFoodCards.size());
        for (Card card : mFoodCards) {
            suggestions.add(card.getTitle());
        }
        ((SlidingMenuActivity) getActivity()).setSuggestions(suggestions);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
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

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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Food;
import com.cse3310.phms.model.User;
import com.cse3310.phms.ui.activities.AddFoodActivity_;
import com.cse3310.phms.ui.cards.FoodCard;
import com.cse3310.phms.ui.cards.FoodCardExpand;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.utils.UserSingleton;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.diet_screen)
public class DietScreenFragment extends SherlockFragment {
    private List<Card> cardList = new ArrayList<Card>();
    CardListFragment_ cardListFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        EventBus.getDefault().register(this);

        User user = UserSingleton.INSTANCE.getCurrentUser();
        List<Food> foodList = user.getDiet().getFoods();
        for (Food food : foodList) {
            cardList.add(createFoodCard(food));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Fragment dietHeaderFragment = new DietScreenHeaderFragment_();
        cardListFragment = new CardListFragment_();
        cardListFragment.initializeCards(cardList); // add cards to show in the card list fragment

        // fragments within fragment
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.diet_screen_header_container, dietHeaderFragment);   // add header fragment
        transaction.add(R.id.diet_screen_food_list_container, cardListFragment).commit();   // add fragment to show the cards
        return super.onCreateView(inflater, container, savedInstanceState);
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

    @OptionsItem(R.id.add_icon)
    void menuAddFoodIntake() {
        AddFoodActivity_.intent(this).startForResult(1);
    }

    public void onEvent(Events.AddCardEvent<FoodCard> event) {
        cardListFragment.addCard(event.card);
        cardListFragment.update();
    }

    private FoodCard createFoodCard(Food food) {
        FoodCard card = new FoodCard(getActivity());
        card.setTitle(food.getName());
        card.setSubTitle("" + food.getCalories());
        card.setButtonTitle("Edit");

        card.setBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Click on edit", Toast.LENGTH_SHORT).show();
            }
        });
        FoodCardExpand cardExpand = new FoodCardExpand(getActivity(), food);
        card.addCardExpand(cardExpand);
        return card;
    }
}

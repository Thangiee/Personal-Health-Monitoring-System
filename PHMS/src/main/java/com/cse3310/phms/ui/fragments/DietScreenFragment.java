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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Food;
import com.cse3310.phms.model.User;
import com.cse3310.phms.ui.activities.AddFoodActivity_;
import com.cse3310.phms.ui.activities.FoodWizardPagerActivity;
import com.cse3310.phms.ui.cards.FoodCard;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.utils.UserSingleton;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.apache.commons.lang.time.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.cse3310.phms.ui.utils.Comparators.FoodCardComparator.*;

@EFragment(R.layout.diet_screen) // Using Android Annotation; same as using inflater in onCreateView
public class DietScreenFragment extends SherlockFragment {
    private List<Card> cardList = new ArrayList<Card>();
    private CardListFragment_ cardListFragment;
    private DietScreenHeaderFragment_ dietHeaderFragment;
    private DietDayIndicatorFragment dayIndicatorFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);    // add this to be able to add other icon to the action bar menu
        EventBus.getDefault().register(this);

        populateCardList(new Date()); // add today's foods to list
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Collections.sort(cardList, getComparator(NAME_SORT, BRAND_SORT)); // sort by food name then by brand name

        final FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        dayIndicatorFragment = new DietDayIndicatorFragment();
        dietHeaderFragment = new DietScreenHeaderFragment_();
        cardListFragment = new CardListFragment_();
        cardListFragment.setShowEmptyListHint(true);
        cardListFragment.initializeCards(cardList); // add cards to show in the CardListFragment

        transaction.add(R.id.diet_screen_day_indicator_container, dayIndicatorFragment);
        // add header fragment
        transaction.add(R.id.diet_screen_header_container, dietHeaderFragment);
        // add fragment to display the cards
        transaction.add(R.id.diet_screen_food_list_container, cardListFragment);
        transaction.commit(); // do the transactions

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        // add together nutrition of all the food cards
        dietHeaderFragment.calculateTotal(cardList);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_menu, menu);    // add the add icon to the action bar menu
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    // start the add food activity after clicking the add icon in the action bar menu
    @OptionsItem(R.id.add_icon)
    void menuAddFoodIntake() {
        // called activity must use Android annotation to use this format
        // otherwise do it the normal way:
        // Intent intent = new Intent(this, ActivityToStart.class);
        // startActivity(intent);
        AddFoodActivity_.intent(this).start();
    }

    // helper method to initialize cardList with food cards from a specify date
    private void populateCardList(Date date) {
        User user = UserSingleton.INSTANCE.getCurrentUser();
        cardList.clear();

        for (Food food : user.getDiet().getFoods()) { // iterate over all the food in the user's diet
            // check if the day the food was added is the same
            // as the current selected day
            if (DateUtils.isSameDay(new Date((long) food.getTime()), date)) {
                cardList.add(createFoodCard(food)); // if so, create a card for that food and add it to the list
            }
        }
    }

    // helper method to create and set up a food card
    private FoodCard createFoodCard(final Food food) {
        final FoodCard foodCard = new FoodCard(getActivity(), food);
        foodCard.setTitle(food.getName());
        foodCard.setSubTitle(food.getBrand());
        foodCard.setButtonTitle("Edit");
        foodCard.setSwipeable(true);

        // setup what to do when edit button is clicked on the card
        foodCard.setBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(foodCard);
                startActivity(new Intent(getActivity(), FoodWizardPagerActivity.class));
            }
        });

        // remove the food associated with the user's diet when the card is swap away.
        foodCard.setOnSwipeListener(new Card.OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                EventBus.getDefault().postSticky(new Events.RemoveFoodCardEvent(foodCard));
            }
        });

        // add the removed food back to the user's diet when the undo button is click.
        foodCard.setOnUndoSwipeListListener(new Card.OnUndoSwipeListListener() {
            @Override
            public void onUndoSwipe(Card card) {
                UserSingleton.INSTANCE.getCurrentUser().getDiet().addFood(new Food(food));
                dietHeaderFragment.add(foodCard.getFood());         // update the nutrition counter
            }
        });
        return foodCard;
    }

    //===========================================
    //              EventBus Listener
    //===========================================

    // add the food to the user's diet and
    // add a card of that food to be display in the cardListFragment
    public void onEvent(Events.AddFoodCardEvent event) {
        Food newFood = new Food(event.foodCard.getFood());
        newFood.setTime(dayIndicatorFragment.getSelectedDay().getTime());

        // add the food to the user's diet / save to the db
        UserSingleton.INSTANCE.getCurrentUser().getDiet().addFood(newFood);

        // add the new food card to cardList
        cardList.add(createFoodCard(newFood));

        // sort cardList using a comparator
        Collections.sort(cardList, getComparator(NAME_SORT, BRAND_SORT)); // sort by food name then by brand name

        // make the cardListFragment display the updated cardList
        cardListFragment.clearCards();
        cardListFragment.addCards(cardList);
        cardListFragment.update();

        // update the nutrition counter
        dietHeaderFragment.add(newFood);
    }

    // delete the food to the user's diet and
    // delete the card of that food from the cardListFragment
    public void onEvent(Events.RemoveFoodCardEvent event) {
        UserSingleton.INSTANCE.getCurrentUser().getDiet().removeFood(event.foodCard.getFood());
        cardList.remove(event.foodCard);
        cardListFragment.removeCard(event.foodCard);
        cardListFragment.update();

        // update the nutrition counter
        dietHeaderFragment.minus(event.foodCard.getFood());
    }

    // re-initialise cardListFragment with cards from the
    // selected date indicated by the dayIndicatorFragment
    public void onEvent(Events.SwitchDayEvent event) {
        populateCardList(dayIndicatorFragment.getSelectedDay());
        // sort by food name then by brand name
        Collections.sort(cardList, getComparator(NAME_SORT, BRAND_SORT));
        cardListFragment.clearCards(); // clear old cards
        cardListFragment.initializeCards(cardList); // add new cards
        dietHeaderFragment.calculateTotal(cardList); // update the nutrition counter

        // refresh the cardListFragment to get undo listener
        // to work properly after switching to a different day.
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.detach(cardListFragment);
        transaction.attach(cardListFragment);
        transaction.commit();
    }
}

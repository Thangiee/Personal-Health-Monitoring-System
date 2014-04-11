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

package com.cse3310.phms.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Food;
import com.cse3310.phms.ui.cards.FoodCard;
import com.cse3310.phms.ui.fragments.CardListFragment_;
import com.cse3310.phms.ui.utils.DatabaseHandler;
import com.cse3310.phms.ui.utils.Events;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

import java.util.*;

import static com.cse3310.phms.ui.utils.Comparators.FoodCardComparator.*;

/**
 * See Android Annotations for writing less code
 * https://github.com/excilys/androidannotations/wiki#introduction
 */
@EActivity
public class AddFoodActivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Food Intake");
        EventBus.getDefault().registerSticky(this);

        List<Food> foodList = DatabaseHandler.getAllRows(Food.class); // get all the food in the DB

        Set<String> nameSet = new HashSet<String>(foodList.size());
        List<Card> cardList = new ArrayList<Card>(nameSet.size());
        // create a foodCard for each of the food.
        for (final Food food : foodList) {
            if (nameSet.add(food.getName())) {
                cardList.add(createFoodCard(food));
            }
        }
        Collections.sort(cardList, getComparator(NAME_SORT, BRAND_SORT));

        CardListFragment_ cardListFragment = CardListFragment_.newInstance(cardList, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_front_container, cardListFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.add_menu, menu); // add the plus icon to the action bar
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OptionsItem(R.id.add_icon)
    void menuAddButton() {
        // start a new activity to add create new food after clicking on the add icon
        Intent intent = new Intent(this, FoodWizardPagerActivity.class);
        startActivity(intent);
        finish();   // kill the current activity
    }

    private FoodCard createFoodCard(final Food food) {
        final FoodCard foodCard = new FoodCard(this, food);
        foodCard.setTitle(food.getName());
        foodCard.setSubTitle(food.getBrand());
        foodCard.setButtonTitle("Add");
        foodCard.setSwipeable(false);

        // setup what to do when the add button is clicked on the card
        foodCard.setBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddFoodActivity.this,
                        "Added " + food.getName() + " to food intakes", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().postSticky(new Events.AddFoodCardEvent(foodCard));
            }
        });

        return foodCard;
    }
}

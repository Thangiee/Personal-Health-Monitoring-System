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
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Food;
import com.cse3310.phms.ui.cards.FoodCard;
import com.cse3310.phms.ui.cards.FoodCardExpand;
import com.cse3310.phms.ui.fragments.CardListFragment_;
import com.cse3310.phms.ui.utils.DatabaseHandler;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.utils.UserSingleton;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

import java.util.List;

@EActivity
public class AddFoodActivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Food Intake");
        EventBus.getDefault().registerSticky(this);

        CardListFragment_ cardListFragment = new CardListFragment_();
        // enable the up/home button in the actionbar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FoodCard foodCard;
        List<Food> foodList = DatabaseHandler.getAllRows(Food.class); // get all the food in the DB
        // create a card for each of the food.
        for (final Food food : foodList) {
            foodCard = new FoodCard(this);
            foodCard.setTitle(food.getName());
            foodCard.setSubTitle("sub title");
            foodCard.setButtonTitle("Add");

            final FoodCard finalFoodCard = foodCard;
            foodCard.setBtnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AddFoodActivity.this,
                            "Added " + food.getName() + " to today's diet", Toast.LENGTH_SHORT).show();
                    UserSingleton.INSTANCE.getCurrentUser().getDiet().addFood(food);
                    EventBus.getDefault().postSticky(new Events.AddCardEvent<FoodCard>(finalFoodCard));
                }
            });

            FoodCardExpand foodCardExpand = new FoodCardExpand(this, food);
            foodCard.addCardExpand(foodCardExpand);
            cardListFragment.initializeCard(foodCard);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_front_container, cardListFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OptionsItem(R.id.add_icon)
    void menuAddButton() {
        Intent intent = new Intent(this, NewFoodWizardPagerActivity.class);
        startActivity(intent);
        finish();
    }

    @OptionsItem(android.R.id.home)
    void menuActionBarHome() {
        NavUtils.navigateUpFromSameTask(this); // go back to previous activity when clicking the actionbar home
    }
}

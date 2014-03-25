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

import android.os.Bundle;
import co.juliansuarez.libwizardpager.wizard.model.Page;
import com.cse3310.phms.model.Food;
import com.cse3310.phms.ui.cards.FoodCard;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.views.FoodWizardModel;
import de.greenrobot.event.EventBus;

import static com.cse3310.phms.ui.views.FoodWizardModel.*;

public class FoodWizardPagerActivity extends BaseWizardPagerActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (super.editMode) {
            getActionBar().setTitle("Edit Food");
        } else {
            getActionBar().setTitle("Create New Food");
        }
    }

    @Override
    public void onSetupWizardModel() {
        super.mWizardModel = new FoodWizardModel(this);
    }

    @Override
    public void onSubmit() {
        // extract all the information from the process and
        // use it to create a new food object.
        String name = onGetPage(NAME_KEY).getData().getString(Page.SIMPLE_DATA_KEY);
        double calories = Double.parseDouble(onGetPage(CAL_KEY).getData().getString(Page.SIMPLE_DATA_KEY));
        double protein = Double.parseDouble(onGetPage(PROTEIN_KEY).getData().getString(Page.SIMPLE_DATA_KEY));
        double fat = Double.parseDouble(onGetPage(FAT_KEY).getData().getString(Page.SIMPLE_DATA_KEY));
        double fiber = Double.parseDouble(onGetPage(FIBER_KEY).getData().getString(Page.SIMPLE_DATA_KEY));
        double sugars = Double.parseDouble(onGetPage(SUGAR_KEY).getData().getString(Page.SIMPLE_DATA_KEY));
        double servings = Double.parseDouble(onGetPage(SERVING_KEY).getData().getString(Page.SIMPLE_DATA_KEY));

        Food newFood = new Food(name);
        newFood.setCalories(calories)
                .setProtein(protein)
                .setFat(fat)
                .setFiber(fiber)
                .setSugars(sugars)
                .setNumOfServings(servings).save();

        if (super.editMode) {
            FoodCard oldFoodCard = ((FoodWizardModel) mWizardModel).getFoodCard();
            EventBus.getDefault().post(new Events.RemoveFoodCardEvent(oldFoodCard));
        }

        EventBus.getDefault().post(new Events.AddFoodCardEvent(new FoodCard(this, newFood)));
        finish();
    }
}

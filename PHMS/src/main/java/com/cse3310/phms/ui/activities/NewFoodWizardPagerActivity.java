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

import co.juliansuarez.libwizardpager.wizard.model.Page;
import com.cse3310.phms.model.Food;
import com.cse3310.phms.ui.cards.FoodCard;
import com.cse3310.phms.ui.cards.FoodCardExpand;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.views.NewFoodWizardModel;
import de.greenrobot.event.EventBus;

import static com.cse3310.phms.ui.views.NewFoodWizardModel.*;

public class NewFoodWizardPagerActivity extends BaseWizardPagerActivity{
    @Override
    public void onSetup() {
        setTitle("Create New Food");
        super.mWizardModel = new NewFoodWizardModel(this);
    }

    @Override
    public void onSubmit() {
        // extract all the information from the process and
        // use it to create a new food object.
        String name = onGetPage(NAME).getData().getString(Page.SIMPLE_DATA_KEY);
        double calories = Double.parseDouble(onGetPage(CAL).getData().getString(Page.SIMPLE_DATA_KEY));
        double protein = Double.parseDouble(onGetPage(PROTEIN).getData().getString(Page.SIMPLE_DATA_KEY));
        double fat = Double.parseDouble(onGetPage(FAT).getData().getString(Page.SIMPLE_DATA_KEY));
        double fiber = Double.parseDouble(onGetPage(FIBER).getData().getString(Page.SIMPLE_DATA_KEY));
        double sugars = Double.parseDouble(onGetPage(SUGAR).getData().getString(Page.SIMPLE_DATA_KEY));
        double servings = Double.parseDouble(onGetPage(SERVING).getData().getString(Page.SIMPLE_DATA_KEY));

        Food food = new Food(name);
        food.setCalories(calories)
                .setProtein(protein)
                .setFat(fat)
                .setFiber(fiber)
                .setSugars(sugars)
                .setNumOfServings(servings);

        food.save();

        FoodCard foodCard = new FoodCard(this);
        foodCard.setTitle(name);
        foodCard.setSubTitle("subtitle");
        foodCard.setButtonTitle("Edit");
        foodCard.addCardExpand(new FoodCardExpand(this, food));
        // todo : implement onclicklistener

        EventBus.getDefault().post(new Events.AddCardEvent<FoodCard>(foodCard));
        finish();
    }
}

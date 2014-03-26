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

package com.cse3310.phms.ui.views;

import android.content.Context;
import co.juliansuarez.libwizardpager.wizard.model.AbstractWizardModel;
import co.juliansuarez.libwizardpager.wizard.model.PageList;
import com.cse3310.phms.model.Food;
import com.cse3310.phms.ui.cards.FoodCard;
import com.cse3310.phms.ui.views.pager.EditTextPage;
import de.greenrobot.event.EventBus;

import static android.text.InputType.TYPE_CLASS_TEXT;

public class FoodWizardModel extends AbstractWizardModel {
    public static final String NAME_KEY = "Food Name";
    public static final String CAL_KEY = "Calories (kcal)";
    public static final String PROTEIN_KEY = "Proteins (g)";
    public static final String FAT_KEY = "Fats (g)";
    public static final String FIBER_KEY = "Fibers (g)";
    public static final String SUGAR_KEY = "Sugars (g)";
    public static final String SERVING_KEY = "Number of Servings";
    public static final String BRAND_KEY = "Brand";
    private FoodCard foodCard;

    public FoodWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        // Check if a foodCard was pass to this class to determine if the user
        // press the add or edit button on the food card.
        foodCard = EventBus.getDefault().removeStickyEvent(FoodCard.class);
        if (foodCard != null) {     // a foodCard was passed; therefore the user pressed the edit button.
            Food food = foodCard.getFood();
            // since we are editing the foodCard, let pre-set all the values.
            return new PageList(
                    new EditTextPage(this, NAME_KEY).setValue(food.getName()).setInputType(TYPE_CLASS_TEXT).setRequired(true),
                    new EditTextPage(this, BRAND_KEY).setValue(food.getBrand()).setInputType(TYPE_CLASS_TEXT),
                    new EditTextPage(this, CAL_KEY).setValue(String.valueOf(food.getCalories())).setRequired(true),
                    new EditTextPage(this, PROTEIN_KEY).setValue(String.valueOf(food.getProtein())).setRequired(true),
                    new EditTextPage(this, FAT_KEY).setValue(String.valueOf(food.getFat())).setRequired(true),
                    new EditTextPage(this, FIBER_KEY).setValue(String.valueOf(food.getFiber())).setRequired(true),
                    new EditTextPage(this, SUGAR_KEY).setValue(String.valueOf(food.getSugars())).setRequired(true),
                    new EditTextPage(this, SERVING_KEY).setValue(String.valueOf(food.getNumOfServings())).setRequired(true)
            );
        }

        // else no foodCard was pass so the user is adding a new food card.
        return new PageList(
                new EditTextPage(this, NAME_KEY).setInputType(TYPE_CLASS_TEXT).setRequired(true), // change input type to text
                new EditTextPage(this, BRAND_KEY).setInputType(TYPE_CLASS_TEXT).setValue(""),
                new EditTextPage(this, CAL_KEY).setRequired(true),
                new EditTextPage(this, PROTEIN_KEY).setRequired(true),
                new EditTextPage(this, FAT_KEY).setRequired(true),
                new EditTextPage(this, FIBER_KEY).setRequired(true),
                new EditTextPage(this, SUGAR_KEY).setRequired(true),
                new EditTextPage(this, SERVING_KEY).setRequired(true)
        );
    }

    public FoodCard getFoodCard() {
        return foodCard;
    }
}

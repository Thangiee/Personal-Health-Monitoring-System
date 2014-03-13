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

package com.cse3310.phms.ui.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Food;
import it.gmariotti.cardslib.library.internal.CardExpand;

public class FoodCardExpand extends CardExpand{
    private Food mFood;

    public FoodCardExpand(Context context, Food food) {
        super(context, R.layout.frag_food_card_expand);
        this.mFood = food;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        if (view == null) return;

        TextView caloriesTextView = (TextView) view.findViewById(R.id.frag_food_expand_txt_cal);
        TextView numOfServingsTextView = (TextView) view.findViewById(R.id.frag_food_expand_txt_ser);
        TextView proteinTextView = (TextView) view.findViewById(R.id.frag_food_expand_txt_protein);
        TextView fatTextView = (TextView) view.findViewById(R.id.frag_food_expand_txt_fat);
        TextView fiberTextView = (TextView) view.findViewById(R.id.frag_food_expand_txt_fiber);
        TextView sugarsTextView = (TextView) view.findViewById(R.id.frag_food_expand_txt_sugar);

        caloriesTextView.setText(String.valueOf(mFood.getCalories()));
        numOfServingsTextView.setText(String.valueOf(mFood.getNumOfServings()));
        proteinTextView.setText(String.valueOf(mFood.getProtein()));
        fatTextView.setText(String.valueOf(mFood.getFat()));
        fiberTextView.setText(String.valueOf(mFood.getFiber()));
        sugarsTextView.setText(String.valueOf(mFood.getSugars()));
    }
}

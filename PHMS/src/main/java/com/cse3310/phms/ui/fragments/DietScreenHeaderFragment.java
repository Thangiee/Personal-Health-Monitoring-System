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

import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Food;
import com.cse3310.phms.model.User;
import com.cse3310.phms.ui.utils.UserSingleton;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.diet_screen_header)
public class DietScreenHeaderFragment extends SherlockFragment{
    @ViewById(R.id.diet_screen_header_cal)      TextView caloriesTextView;
    @ViewById(R.id.diet_screen_header_fat)      TextView fatsTextView;
    @ViewById(R.id.diet_screen_header_fiber)    TextView fibersTextView;
    @ViewById(R.id.diet_screen_header_proteins) TextView proteinsTextView;
    @ViewById(R.id.diet_screen_header_sugar)    TextView sugarsTextView;

    private double caloriesTotal = 0;
    private double fatsTotal = 0;
    private double fibersTotal = 0;
    private double proteinsTotal = 0;
    private double sugarsTotal = 0;

    @AfterViews
    void calculateTotal() {
        User user = UserSingleton.INSTANCE.getCurrentUser();
        List<Food> foodList = user.getDiet().getFoods(user.getId());

        for (Food food : foodList) {
            add(food);
        }

        setHeaderValues();
    }

    void add(Food food) {
        caloriesTotal += food.getCalories();
        fatsTotal += food.getFat();
        fibersTotal += food.getFiber();
        proteinsTotal += food.getProtein();
        sugarsTotal += food.getSugars();
        setHeaderValues();
    }

    void minus(Food food) {
        caloriesTotal -= food.getCalories();
        fatsTotal -= food.getFat();
        fibersTotal -= food.getFiber();
        proteinsTotal -= food.getProtein();
        sugarsTotal -= food.getSugars();
        setHeaderValues();
    }

    private void setHeaderValues() {
        caloriesTextView.setText(String.valueOf(caloriesTotal));
        fatsTextView.setText(String.valueOf(fatsTotal));
        fibersTextView.setText(String.valueOf(fibersTotal));
        proteinsTextView.setText(String.valueOf(proteinsTotal));
        sugarsTextView.setText(String.valueOf(sugarsTotal));
    }
}

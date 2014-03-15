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
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Food;
import com.cse3310.phms.ui.fragments.DietScreenFragment_;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.utils.Keyboard;
import com.cse3310.phms.ui.utils.UserSingleton;
import de.greenrobot.event.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchCardsActivity extends BaseActivity{
    private String searchWord;
    private List<Food> mFoodList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().registerSticky(this);

        mFoodList = UserSingleton.getInstance().getCurrentUser().getDiet().getFoods();
        // change suggestions to items in this screen
        Set<String> suggestions = new HashSet<String>(mFoodList.size());
        for (Food food : mFoodList) {
            suggestions.add(food.getName());
        }
        setSuggestions(suggestions);

        List<Food> match = new ArrayList<Food>();
        for (Food food : mFoodList) {
            if (food.getName().toLowerCase().contains(searchWord.toLowerCase())) {
                match.add(food);
                System.out.println(food.getName());
            }
        }

        EventBus.getDefault().postSticky(new Events.initListEvent<Food>(match));
        SherlockFragment fragment = new DietScreenFragment_();
        FragmentTransaction fragTran = getSupportFragmentManager().beginTransaction();
        fragTran.replace(R.id.frag_front_container, fragment);
        fragTran.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        searchMenuItem.expandActionView();
        autoCompTextView.setText(searchWord);
        autoCompTextView.dismissDropDown();
        Keyboard.hide(inputManager, autoCompTextView);
        return true;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void doSearch() {
        final String word = autoCompTextView.getText().toString().toLowerCase();
        List<Food> match = new ArrayList<Food>();

        for (Food food : mFoodList) {
            if (food.getName().toLowerCase().contains(word)) {
                match.add(food);
                System.out.println(food.getName());
            }
        }

        EventBus.getDefault().postSticky(new Events.initListEvent<Food>(match));
        SherlockFragment fragment = new DietScreenFragment_();
        FragmentTransaction fragTran = getSupportFragmentManager().beginTransaction();
        fragTran.replace(R.id.frag_front_container, fragment);
        fragTran.commit();
        finish();
    }

    public void onEvent(Events.SearchEvent event) {
        this.searchWord = event.searchWord;
    }
}

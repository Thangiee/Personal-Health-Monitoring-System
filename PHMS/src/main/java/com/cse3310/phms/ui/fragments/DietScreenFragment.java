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

import android.view.MenuItem;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Food;
import com.cse3310.phms.ui.cards.FoodCard;
import com.cse3310.phms.ui.cards.FoodCardExpand;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.frag_diet_screen)
public class DietScreenFragment extends SherlockFragment {
    private static int idCounter = 0;
    private List<Card> mFoodCards = new ArrayList<Card>();

    @ViewById(R.id.frag_diet_food_list)
    CardListView mCardListView;

    @AfterViews
    void onAfterViews() {
        addFoodCard(new Food("Egg").setCalories(200).setFat(30).setFiber(52));
        addFoodCard(new Food("Chess").setCalories(250));
        addFoodCard(new Food("water").setCalories(0));
        addFoodCard(new Food("Egg").setCalories(200));

        CardArrayAdapter adapter = new CardArrayAdapter(getActivity(), mFoodCards);
        //Enable undo controller!
        adapter.setEnableUndo(true);

        if (mCardListView != null) {
            mCardListView.setAdapter(adapter);
        }
    }

    public void addFoodCard(Food food) {
        FoodCard card = new FoodCard(getActivity());
        card.setSwipeable(true);
        card.setId("" + idCounter++);

        CardHeader header = new CardHeader(getActivity());
        header.setTitle(food.getName());
        //Add a popup menu. This method set OverFlow button to visible
        header.setPopupMenu(R.menu.edit_del_menu, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard baseCard, MenuItem menuItem) {
                Toast.makeText(getActivity(), "Click on " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        card.addCardHeader(header);

        FoodCardExpand cardExpand = new FoodCardExpand(getActivity(), food);
        card.addCardExpand(cardExpand);

        mFoodCards.add(card);
    }
}

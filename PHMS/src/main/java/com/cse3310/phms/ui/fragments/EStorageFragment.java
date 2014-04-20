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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cse3310.phms.R;
import com.cse3310.phms.model.*;
import com.cse3310.phms.ui.activities.UrlWizardPagerActivity;
import com.cse3310.phms.ui.cards.UrlCard;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.utils.UserSingleton;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 4/8/14.
 */
@EFragment(R.layout.estorage_screen)
public class EStorageFragment extends SherlockFragment {
    private static final String[] TABS = new String[]{"HEALTH ARTICLES", "RECIPES", "DIET DESC."};
    private static final int HEALTH_TAB = 0;
    private static final int RECIPE_TAB = 1;
    private static final int DIET_TAB = 2;
    private CardListFragment_ mCardListFragment;
    private TabsIndicatorFragment mTabsIndicatorFragment;
    private List<Card> mHealthCardList = new ArrayList<Card>();
    private List<Card> mRecipeCardList = new ArrayList<Card>();
    private List<Card> mDietCardList = new ArrayList<Card>();
    private EventBus localBus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);    // add this to be able to add other icon to the action bar menu
        EventBus.getDefault().registerSticky(this);
        localBus = new EventBus();
        populateCardLists();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentManager fm = getChildFragmentManager();
        mTabsIndicatorFragment = TabsIndicatorFragment.newInstance(new EStorageScreenAdapter(fm), localBus);
        mCardListFragment = CardListFragment_.newInstance(mHealthCardList, true, true, true);

        final FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.webview_screen_tabs_container, mTabsIndicatorFragment);
        transaction.add(R.id.webview_screen_content_container, mCardListFragment);
        transaction.commit();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        localBus.register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        localBus.unregister(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_menu, menu);    // add the add icon to the action bar menu
        super.onCreateOptionsMenu(menu, inflater);
    }

    @OptionsItem(R.id.add_icon)
    void menuAddContact() {
        Intent intent;
        if (mTabsIndicatorFragment.getCurrentPosition() == HEALTH_TAB) {
            intent = new Intent(getActivity(), UrlWizardPagerActivity.class);
            intent.putExtra("tabInt", HEALTH_TAB);
        } else if (mTabsIndicatorFragment.getCurrentPosition() == RECIPE_TAB) {
            intent = new Intent(getActivity(), UrlWizardPagerActivity.class);
            intent.putExtra("tabInt", RECIPE_TAB);
        } else {
            intent = new Intent(getActivity(), UrlWizardPagerActivity.class);
            intent.putExtra("tabInt", DIET_TAB);
        }

        startActivity(intent);
    }

    private void populateCardLists() {

        User user = UserSingleton.INSTANCE.getCurrentUser();
        mHealthCardList.clear();
        for (Health healthInfo : user.getHealth()) {
            //mHealthCardList.add(new UrlCard(getActivity(), (EStorage)healthInfo));
            mHealthCardList.add(createUrlCard(healthInfo));
        }

        mRecipeCardList.clear();
        for (Recipe recipeInfo : user.getRecipe()) {
            //mRecipeCardList.add(new UrlCard(getActivity(), (EStorage)recipeInfo));
            mRecipeCardList.add(createUrlCard(recipeInfo));
        }

        mDietCardList.clear();
        for (DietDesc dietInfo : user.getDietDesc()) {
            // mDietCardList.add(new UrlCard(getActivity(), dietInfo));
            mDietCardList.add(createUrlCard(dietInfo));
        }
    }

    private UrlCard createUrlCard(final EStorage estorage) {
        return new UrlCard(getActivity(), estorage);
    }

    //===============================================================
    //                      EventBus Listeners
    //===============================================================
    // ===> see DietScreenFragment's EventBus Listeners for details <===


    public void onEvent(Events.AddUrlCardEvent event) {

        UrlCard newUrlCard = new UrlCard(getActivity(), event.urlCard.getUrlInfo());
        int whatTab = mTabsIndicatorFragment.getCurrentPosition();

        if (whatTab == 0) {
            Health newUrl = new Health();
            newUrl.setTitle(newUrlCard.getUrlInfo().getTitle());
            newUrl.setUrl(newUrlCard.getUrlInfo().getUrl());
            newUrl.save();
            mHealthCardList.add(createUrlCard(newUrl));
            // make the cardListFragment display the updated cardList

            mCardListFragment.clearCards();
            mCardListFragment.addCards(mHealthCardList);
            mCardListFragment.update();
        } else if (whatTab == 1) {
            Recipe newUrl = new Recipe();
            newUrl.setTitle(newUrlCard.getUrlInfo().getTitle());
            newUrl.setUrl(newUrlCard.getUrlInfo().getUrl());
            newUrl.save();
            mRecipeCardList.add(createUrlCard(newUrl));
            // make the cardListFragment display the updated cardList

            mCardListFragment.clearCards();
            mCardListFragment.addCards(mRecipeCardList);
            mCardListFragment.update();
        } else {
            DietDesc newUrl = new DietDesc();
            newUrl.setTitle(newUrlCard.getUrlInfo().getTitle());
            newUrl.setUrl(newUrlCard.getUrlInfo().getUrl());
            newUrl.save();
            mDietCardList.add(createUrlCard(newUrl));
            // make the cardListFragment display the updated cardList

            mCardListFragment.clearCards();
            mCardListFragment.addCards(mDietCardList);
            mCardListFragment.update();
        }

    }

    public void onEvent(Events.RemoveUrlCardEvent event) {
        event.urlCard.getUrlInfo().delete();
        mHealthCardList.remove(event.urlCard);
        mCardListFragment.removeCard(event.urlCard);
        mCardListFragment.update();
    }

    public void onEvent(Events.SwitchTabEvent event) {
        populateCardLists();

        if (mCardListFragment != null) {
            mCardListFragment.clearCards();
        }

        if (event.position == 0) {
            mCardListFragment.addCards(mHealthCardList);
        } else if (event.position == 1) {
            mCardListFragment.addCards(mRecipeCardList);
        } else {
            mCardListFragment.addCards(mDietCardList);
        }

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.detach(mCardListFragment);
        transaction.attach(mCardListFragment);
        transaction.commit();
    }

    //===============================================================
    //                      INNER Adapter Class
    //===============================================================
    private class EStorageScreenAdapter extends FragmentPagerAdapter {
        public EStorageScreenAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return BlankFragment.newInstance(R.color.transparent);
        }

        @Override
        public int getCount() {
            return TABS.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TABS[position];
        }
    }
}

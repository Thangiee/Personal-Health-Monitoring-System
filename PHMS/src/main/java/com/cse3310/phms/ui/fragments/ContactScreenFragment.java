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
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cse3310.phms.R;
import com.cse3310.phms.model.DoctorInfo;
import com.cse3310.phms.model.Info;
import com.cse3310.phms.model.User;
import com.cse3310.phms.ui.activities.ContactWizardPagerActivity;
import com.cse3310.phms.ui.activities.DoctorWizardPagerActivity;
import com.cse3310.phms.ui.cards.ContactCard;
import com.cse3310.phms.ui.cards.DoctorContactCard;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.utils.UserSingleton;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.contact_screen)
public class ContactScreenFragment extends SherlockFragment {
    private static final String[] TABS = new String[] { "Doctor", "Emergency" };
    private static final int DOC_TAB = 0;
    private static final int EMERGENCY_TAB = 1;
    private CardListFragment_ mCardListFragment;
    private TabsIndicatorFragment mTabsIndicatorFragment;
    private List<Card> mDoctorCardList  = new ArrayList<Card>();
    private List<Card> mContactCardList = new ArrayList<Card>();
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
        mTabsIndicatorFragment = TabsIndicatorFragment.newInstance(new ContactScreenAdapter(fm), localBus);
        mCardListFragment = CardListFragment_.newInstance(mDoctorCardList, true);

        final FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.contact_screen_tabs_container, mTabsIndicatorFragment);
        transaction.add(R.id.contact_screen_content_container, mCardListFragment);
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
        if (mTabsIndicatorFragment.getCurrentPosition() == DOC_TAB)
            intent = new Intent(getActivity(), DoctorWizardPagerActivity.class);
        else
            intent = new Intent(getActivity(), ContactWizardPagerActivity.class);

        startActivity(intent);
    }

    private void populateCardLists() {
        User user = UserSingleton.INSTANCE.getCurrentUser();
        mDoctorCardList.clear();
        for (DoctorInfo doctorInfo : user.getDoctors()) {
            mDoctorCardList.add(new DoctorContactCard(getActivity(), doctorInfo));
        }

        mContactCardList.clear();
        for (Info contact : user.getContacts()) {
            mContactCardList.add(new ContactCard(getActivity(), contact));
        }
    }

    //===============================================================
    //                      EventBus Listeners
    //===============================================================
    // ===> see DietScreenFragment's EventBus Listeners for details <===

    public void onEvent(Events.AddDoctorCardEvent event) {
        DoctorContactCard newContactCard = new DoctorContactCard(getActivity() ,event.doctorContactCard.getDoctorInfo());
        newContactCard.getDoctorInfo().save();

        mDoctorCardList.add(event.doctorContactCard);

        mCardListFragment.clearCards();
        mCardListFragment.addCards(mDoctorCardList);
        mCardListFragment.update();
    }

    public void onEvent(Events.RemoveDoctorCardEvent event) {
        try {
            event.doctorContactCard.getDoctorInfo().delete();
            mDoctorCardList.remove(event.doctorContactCard);
            mCardListFragment.removeCard(event.doctorContactCard);
            mCardListFragment.update();
        } catch (SQLiteConstraintException e) {
            Toast.makeText(getActivity(), "Can't delete, other components depend on this entry.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onEvent(Events.AddContactCardEvent event) {
        ContactCard newContactCard = new ContactCard(getActivity() ,event.contactCard.getContactInfo());
        newContactCard.getContactInfo().save();

        mContactCardList.add(event.contactCard);

        mCardListFragment.clearCards();
        mCardListFragment.addCards(mContactCardList);
        mCardListFragment.update();
    }

    public void onEvent(Events.RemoveContactCardEvent event) {
        event.contactCard.getContactInfo().delete();
        mContactCardList.remove(event.contactCard);
        mCardListFragment.removeCard(event.contactCard);
        mCardListFragment.update();
    }

    public void onEvent(Events.SwitchTabEvent event) {
        populateCardLists();

        if (mCardListFragment != null) {
            mCardListFragment.clearCards();
        }

        if (event.position == 0) {
            mCardListFragment.addCards(mDoctorCardList);
        } else {
            mCardListFragment.addCards(mContactCardList);
        }

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.detach(mCardListFragment);
        transaction.attach(mCardListFragment);
        transaction.commit();
    }

    //===============================================================
    //                      INNER Adapter Class
    //===============================================================
    private class ContactScreenAdapter extends FragmentPagerAdapter {
        public ContactScreenAdapter(FragmentManager fm) {
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

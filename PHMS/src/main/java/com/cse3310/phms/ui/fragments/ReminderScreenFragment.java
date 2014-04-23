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

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Reminder;
import com.cse3310.phms.model.User;
import com.cse3310.phms.ui.cards.ReminderCard;
import com.cse3310.phms.ui.utils.Comparators.ReminderCardComparator;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.utils.UserSingleton;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@EFragment(R.layout.reminder_screen)
public class ReminderScreenFragment extends SherlockFragment {
    private List<Card> mCardList = new ArrayList<Card>();
    private CardListFragment_ mCardListFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);    // add this to be able to add other icon to the action bar menu
        populateCardList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        mCardListFragment = CardListFragment.newInstance(mCardList, false);
        transaction.add(R.id.reminder_card_list_container, mCardListFragment).commit();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        EventBus.getDefault().register(this);
        super.onResume();
        // set title
        getSherlockActivity().getSupportActionBar().setTitle("Reminders");
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.overflow_menu, menu);
    }

    private void populateCardList() {
        User user = UserSingleton.INSTANCE.getCurrentUser();
        mCardList.clear();

        Calendar now = Calendar.getInstance();
        for (Reminder reminder : user.getReminders()) {
            // if reminder has not pass and has not been cancel
            if (now.getTimeInMillis() < reminder.getAbsTime() && reminder.isActive()) {
                mCardList.add(new ReminderCard(getActivity(), reminder));
            }
        }

        Collections.sort(mCardList, ReminderCardComparator.BY_TIME);
    }

    public void onEvent(Events.RemoveReminderCardEvent event) {
        mCardList.remove(event.reminderCard);
        mCardListFragment.removeCard(event.reminderCard);
        mCardListFragment.update();
    }
}

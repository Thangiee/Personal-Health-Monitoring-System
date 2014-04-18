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

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cse3310.phms.R;
import com.cse3310.phms.ui.fragments.CardListFragment_;
import com.cse3310.phms.ui.utils.Events;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import java.util.List;

@EFragment(R.layout.dialog_appointment_list)
public class CardListDialogFragment extends DialogFragment {

    public static CardListDialogFragment_ newInstance(List<Card> cardList, String title) {
        CardListDialogFragment_ fragment = new CardListDialogFragment_();
        EventBus.getDefault().postSticky(new Events.PostCardListEvent(cardList));
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(getArguments().getString("title"));  // set the title

        // cardlist from static factory
        Events.PostCardListEvent event = EventBus.getDefault().removeStickyEvent(Events.PostCardListEvent.class);
        List<Card> cardList = event.cardList;

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        CardListFragment_ fragment = CardListFragment_.newInstance(cardList, false, false, true);
        transaction.add(R.id.appointment_card_list, fragment).commit();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Click(R.id.dialog_card_list_dismiss_btn)
    void handleDismissButtonClick() {
        this.dismiss();
    }
}

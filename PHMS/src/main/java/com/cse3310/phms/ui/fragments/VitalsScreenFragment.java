package com.cse3310.phms.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cse3310.phms.R;
import com.cse3310.phms.model.User;
import com.cse3310.phms.model.Vitals;
import com.cse3310.phms.ui.activities.VitalsWizardPagerActivity;
import com.cse3310.phms.ui.cards.VitalsCard;
import com.cse3310.phms.ui.utils.Comparators.CardComparator;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.utils.UserSingleton;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;

import java.text.SimpleDateFormat;
import java.util.*;

//import com.cse3310.phms.ui.activities.AddVitalsActivity_;

/**
 * Created by Zach on 4/13/14.
 */

@EFragment(R.layout.vitals_screen)
public class VitalsScreenFragment extends SherlockFragment{
    private CardListFragment cardListFragment;
    private List<Card> cardList = new ArrayList<Card>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);    // add this to be able to add other icon to the action bar menu
        EventBus.getDefault().register(this);
        populateCardList(new Date());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        cardListFragment = CardListFragment.newInstance(cardList, true, true, true);
        transaction.add(R.id.vitals_screen_vitals_list_container, cardListFragment);

        transaction.commit();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.overflow_menu, menu);
        inflater.inflate(R.menu.add_menu, menu);    // add the add icon to the action bar menu
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        // set title
        getSherlockActivity().getSupportActionBar().setTitle("Vital Signs");
    }

    @OptionsItem(R.id.add_icon)
    void menuAddVitals() {
        Intent intent = new Intent(getActivity(), VitalsWizardPagerActivity.class);
        startActivity(intent);
    }


    // helper method to initialize cardList with vitals cards from a specify date
    private void populateCardList(Date date) {
        User user = UserSingleton.INSTANCE.getCurrentUser();
        cardList.clear();

        for (Vitals vitals : user.getDiet().getVitals()) { // iterate over all the vitals in the user's diet
            cardList.add(createVitalsCard(vitals));
        }
    }

    // helper method to create and set up a vitals card
    private VitalsCard createVitalsCard(final Vitals vitals) {
        final VitalsCard vitalsCard = new VitalsCard(getActivity(), vitals);
        Calendar currDate = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy");
        String formattedDate=df.format(currDate.getTime());
        vitalsCard.setTitle("Date: "+vitals.getDate());
        vitalsCard.setButtonTitle("Edit");
        vitalsCard.setSwipeable(false);

        // setup what to do when edit button is clicked on the card
        vitalsCard.setBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(vitalsCard);
                startActivity(new Intent(getActivity(), VitalsWizardPagerActivity.class));
            }
        });

        // remove the vitals
        vitalsCard.setOnSwipeListener(new Card.OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                EventBus.getDefault().postSticky(new Events.RemoveVitalsCardEvent(vitalsCard));
            }
        });


        return vitalsCard;
    }

    //===========================================
    //              EventBus Listener
    //===========================================

    // add the vitals to the user's diet and
    // add a card of that vitals to be display in the cardListFragment
    public void onEvent(Events.AddVitalsCardEvent event) {
        Vitals newVitals = new Vitals(event.vitalsCard.getVitals());


        // add the vitals to the user's diet / save to the db
        newVitals.save();

        // add the new food card to cardList
        cardList.add(createVitalsCard(newVitals));

        // sort cardList using a comparator
        Collections.sort(cardList, CardComparator.NAME_SORT); // sort by vitals name then by brand name

        // make the cardListFragment display the updated cardList
        cardListFragment.clearCards();
        cardListFragment.addCards(cardList);
        cardListFragment.update();
    }

    // delete the vitals to the user's diet and
    // delete the card of that vitals from the cardListFragment
    public void onEvent(Events.RemoveVitalsCardEvent event) {
        UserSingleton.INSTANCE.getCurrentUser().getDiet().removeVitals(event.vitalsCard.getVitals());
        cardList.remove(event.vitalsCard);
        cardListFragment.removeCard(event.vitalsCard);
        cardListFragment.update();
    }
}

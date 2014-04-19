package com.cse3310.phms.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Medication;
import com.cse3310.phms.model.User;
import com.cse3310.phms.ui.activities.AddMedicationActivity_;
import com.cse3310.phms.ui.activities.MedicationWizardPagerActivity;
import com.cse3310.phms.ui.cards.MedicationCard;
import com.cse3310.phms.ui.utils.Comparators.CardComparator;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.utils.UserSingleton;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Zach on 4/13/14.
 */

@EFragment(R.layout.medication_screen)
public class MedicationScreenFragment extends SherlockFragment{
    private CardListFragment_ cardListFragment;
    private List<Card> cardList = new ArrayList<Card>();
    private DietDayIndicatorFragment dayIndicatorFragment;
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
        cardListFragment = CardListFragment_.newInstance(cardList, true);
        dayIndicatorFragment = new DietDayIndicatorFragment();
        transaction.add(R.id.medication_screen_medication_list_container, cardListFragment);

        transaction.commit();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_menu, menu);    // add the add icon to the action bar menu
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @OptionsItem(R.id.add_icon)
    void menuAddMedication() {
        // called activity must use Android annotation to use this format
        // otherwise do it the normal way:
        // Intent intent = new Intent(this, ActivityToStart.class);
        // startActivity(intent);
        AddMedicationActivity_.intent(this).start();
    }


    // helper method to initialize cardList with medication cards from a specify date
    private void populateCardList(Date date) {
        User user = UserSingleton.INSTANCE.getCurrentUser();
        cardList.clear();

        for (Medication medication : user.getDiet().getMedications()) { // iterate over all the medication in the user's diet
            cardList.add(createMedicationCard(medication));
        }
    }

    // helper method to create and set up a medication card
    private MedicationCard createMedicationCard(final Medication medication) {
        final MedicationCard medicationCard = new MedicationCard(getActivity(), medication);
        medicationCard.setTitle(medication.getMedicationName());
        medicationCard.setButtonTitle("Edit");
        medicationCard.setSwipeable(true);

        // setup what to do when edit button is clicked on the card
        medicationCard.setBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(medicationCard);
                startActivity(new Intent(getActivity(), MedicationWizardPagerActivity.class));
            }
        });

        // remove the medication
        medicationCard.setOnSwipeListener(new Card.OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                EventBus.getDefault().postSticky(new Events.RemoveMedicationCardEvent(medicationCard));
            }
        });


        return medicationCard;
    }

    //===========================================
    //              EventBus Listener
    //===========================================

    // add the medication to the user's diet and
    // add a card of that medication to be display in the cardListFragment
    public void onEvent(Events.AddMedicationCardEvent event) {
        Medication newMedication = new Medication(event.medicationCard.getMedication());
        newMedication.setTime(dayIndicatorFragment.getSelectedDay().getTime());

        // add the medication to the user's diet / save to the db
        newMedication.save();

        // add the new food card to cardList
        cardList.add(createMedicationCard(newMedication));

        // sort cardList using a comparator
        Collections.sort(cardList, CardComparator.NAME_SORT); // sort by medication name then by brand name

        // make the cardListFragment display the updated cardList
        cardListFragment.clearCards();
        cardListFragment.addCards(cardList);
        cardListFragment.update();
    }

    // delete the medication to the user's diet and
    // delete the card of that medication from the cardListFragment
    public void onEvent(Events.RemoveMedicationCardEvent event) {
        UserSingleton.INSTANCE.getCurrentUser().getDiet().removeMedication(event.medicationCard.getMedication());
        cardList.remove(event.medicationCard);
        cardListFragment.removeCard(event.medicationCard);
        cardListFragment.update();
    }
}

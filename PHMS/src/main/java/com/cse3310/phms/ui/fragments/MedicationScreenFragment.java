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
import com.cse3310.phms.ui.activities.AddMedicationActivity;
import com.cse3310.phms.ui.utils.Events;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zach on 4/13/14.
 */

@EFragment(R.layout.medication_screen)
public class MedicationScreenFragment extends SherlockFragment{
    private CardListFragment_ cardListFragment;
    private List<Card> cardList = new ArrayList<Card>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);    // add this to be able to add other icon to the action bar menu
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        cardListFragment = CardListFragment_.newInstance(cardList, true);

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

    // add the food to the user's diet and
    // add a card of that food to be display in the cardListFragment
    public void onEvent(Events.AddMedicationCardEvent event) {


    }

    @OptionsItem(R.id.add_icon)
    void menuAddMedication() {
        // called activity must use Android annotation to use this format
        // otherwise do it the normal way:
        // Intent intent = new Intent(this, ActivityToStart.class);
        // startActivity(intent);
        AddMedicationActivity_.intent(this).start();
    }

}

package com.cse3310.phms.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Medication;
import com.cse3310.phms.ui.cards.MedicationCard;
import com.cse3310.phms.ui.fragments.CardListFragment_;
import com.cse3310.phms.ui.utils.DatabaseHandler;
import com.cse3310.phms.ui.utils.Events;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

import java.util.*;

import static com.cse3310.phms.ui.utils.Comparators.MedicationCardComparator.*;

/**
 * Created by Zach on 4/13/2014.
 */

@EActivity
public class AddMedicationActivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Medication");
        EventBus.getDefault().registerSticky(this);

        List<Medication> medicationList = DatabaseHandler.getAllRows(Medication.class); // get all the medication in the DB

        Set<String> nameSet = new HashSet<String>(medicationList.size());
        List<Card> cardList = new ArrayList<Card>(nameSet.size());
        // create a medicationCard for each of the medications.
        for (final Medication medication : medicationList) {
            if (nameSet.add(medication.getMedicationName())) {
                cardList.add(createMedicationCard(medication));
            }
        }
        Collections.sort(cardList, getComparator(NAME_SORT));

        CardListFragment_ cardListFragment = CardListFragment_.newInstance(cardList, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_front_container, cardListFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.add_menu, menu); // add the plus icon to the action bar
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OptionsItem(R.id.add_icon)
    void menuAddButton() {
        // start a new activity to add create new medications after clicking on the add icon
        Intent intent = new Intent(this, MedicationWizardPagerActivity.class);
        startActivity(intent);
        finish();   // kill the current activity
    }


    private MedicationCard createMedicationCard(final Medication medication) {
        final MedicationCard medicationCard = new MedicationCard(this, medication);
        medicationCard.setTitle(medication.getMedicationName());
        medicationCard.setButtonTitle("Add");
        medicationCard.setSwipeable(false);

        // setup what to do when the add button is clicked on the card
        medicationCard.setBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddMedicationActivity.this,
                        "Added " + medication.getMedicationName() + " to the list of medications.", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().postSticky(new Events.AddMedicationCardEvent(medicationCard));
            }
        });

        return medicationCard;
    }

}

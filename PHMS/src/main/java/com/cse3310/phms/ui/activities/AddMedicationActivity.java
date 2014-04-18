package com.cse3310.phms.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Medication;
import com.cse3310.phms.ui.cards.MedicationCard;
import com.cse3310.phms.ui.utils.Events;
import de.greenrobot.event.EventBus;

/**
 * Created by Zach on 4/13/2014.
 */
public class AddMedicationActivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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

//    @OptionsItem(R.id.add_icon)
//    void menuAddButton() {
//        // start a new activity to add create new food after clicking on the add icon
//        Intent intent = new Intent(this, FoodWizardPagerActivity.class);
//        startActivity(intent);
//        finish();   // kill the current activity
//    }


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

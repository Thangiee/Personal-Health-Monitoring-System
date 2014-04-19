package com.cse3310.phms.ui.activities;

import android.os.Bundle;
import com.cse3310.phms.model.Medication;
import com.cse3310.phms.ui.cards.MedicationCard;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.views.FoodWizardModel;
import com.cse3310.phms.ui.views.MedicationWizardModel;
import de.greenrobot.event.EventBus;

import static co.juliansuarez.libwizardpager.wizard.model.Page.SIMPLE_DATA_KEY;
import static com.cse3310.phms.ui.views.FoodWizardModel.CAL_KEY;
import static com.cse3310.phms.ui.views.FoodWizardModel.NAME_KEY;

/**
 * Created by Zach on 4/17/2014.
 */
public class MedicationWizardPagerActivity extends BaseWizardPagerActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String actionBarTile = super.editMode ? "Edit Medication" : "Add New Medication";
        getActionBar().setTitle(actionBarTile);
    }

    @Override
    public void onSetupWizardModel() {
        super.mWizardModel = new FoodWizardModel(this);
    }

    @Override
    public void onSubmit() {
        // extract all the information from the process and
        // use it to create a new food object.
        String name = onGetPage(NAME_KEY).getData().getString(SIMPLE_DATA_KEY);
        double dosage  = Double.parseDouble(onGetPage(CAL_KEY).getData().getString(SIMPLE_DATA_KEY));

        // create the food object
        Medication medication = new Medication(name);
        medication.setDosage(dosage);

        // if editing, remove the old card and add the new edited card
        if (super.editMode) {
            MedicationCard oldMedication = ((MedicationWizardModel) mWizardModel).getMedicationCard();
            // post an event to DietScreenFragment to remove the old card
            EventBus.getDefault().post(new Events.RemoveMedicationCardEvent(oldMedication));
        }

        // post an event to DietScreenFragment to add a new card
        EventBus.getDefault().post(new Events.AddMedicationCardEvent(new MedicationCard(this, medication)));
        finish();
    }
}

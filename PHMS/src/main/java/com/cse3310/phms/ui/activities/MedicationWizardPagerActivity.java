package com.cse3310.phms.ui.activities;

import android.os.Bundle;
import android.util.Log;
import com.cse3310.phms.model.Medication;
import com.cse3310.phms.ui.cards.MedicationCard;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.views.MedicationWizardModel;
import de.greenrobot.event.EventBus;

import static co.juliansuarez.libwizardpager.wizard.model.Page.SIMPLE_DATA_KEY;
import static com.cse3310.phms.ui.views.MedicationWizardModel.*;

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
        super.mWizardModel = new MedicationWizardModel(this);
    }

    @Override
    public void onSubmit() {
        // extract all the information from the process and
        // use it to create a new medication object.
        String name = onGetPage(NAME_KEY).getData().getString(SIMPLE_DATA_KEY);
        double dosage  = Double.parseDouble(onGetPage(DOSAGE_KEY).getData().getString(SIMPLE_DATA_KEY));
        String dosageType = onGetPage(DOSAGE_TYPE_KEY).getData().getString(SIMPLE_DATA_KEY);
        Double frequency = Double.parseDouble(onGetPage(FREQUENCY_KEY).getData().getString(SIMPLE_DATA_KEY));
        String frequencyType = onGetPage(FREQUENCY_TYPE_KEY).getData().getString(SIMPLE_DATA_KEY);

        Log.d("DEBUG", "" + frequency + "-" + frequencyType);
        // create the medication object
        Medication medication = new Medication();
        medication.setMedicationName(name);
        medication.setDosage(dosage);
        medication.setDosageType(dosageType);
        medication.setFrequency(frequency);
        medication.setFrequencyType(frequencyType);

        Log.d("DEBUG", "medication:" + medication.getFrequency() + "-" + medication.getFrequencyType());

        // if editing, remove the old card and add the new edited card
        if (super.editMode) {
            MedicationCard oldMedication = ((MedicationWizardModel) mWizardModel).getMedicationCard();
            EventBus.getDefault().post(new Events.RemoveMedicationCardEvent(oldMedication));
        }

        EventBus.getDefault().post(new Events.AddMedicationCardEvent(new MedicationCard(this, medication)));
        finish();
    }
}

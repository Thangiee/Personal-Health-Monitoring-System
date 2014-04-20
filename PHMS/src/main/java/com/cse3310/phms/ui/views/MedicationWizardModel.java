package com.cse3310.phms.ui.views;

import android.content.Context;
import co.juliansuarez.libwizardpager.wizard.model.AbstractWizardModel;
import co.juliansuarez.libwizardpager.wizard.model.PageList;
import com.cse3310.phms.model.Medication;
import com.cse3310.phms.ui.cards.MedicationCard;
import com.cse3310.phms.ui.views.pager.EditTextPage;
import de.greenrobot.event.EventBus;

import static android.text.InputType.TYPE_CLASS_TEXT;

/**
 * Created by Zach on 4/17/2014.
 */
public class MedicationWizardModel extends AbstractWizardModel{
    private MedicationCard medicationCard;
    public static final String NAME_KEY = "Medication Name";
    public static final String FREQUENCY_KEY = "Frequency";
    public static final String DOSAGE_KEY = "Dosage";
    public static final String DOSAGE_TYPE_KEY = "Dosage Type";
    public static final String FREQUENCY_TYPE_KEY = "Frequency Type";

    public MedicationWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        medicationCard = EventBus.getDefault().removeStickyEvent(MedicationCard.class);
        if (medicationCard != null) {     // a medicationCard was passed; therefore the user pressed the edit button.
            Medication medication = medicationCard.getMedication();

            return new PageList(
                    new EditTextPage(this, NAME_KEY).setValue(medication.getMedicationName()).setInputType(TYPE_CLASS_TEXT),
                    new EditTextPage(this, DOSAGE_KEY).setValue(String.valueOf(medication.getDosage())),
                    new EditTextPage(this, DOSAGE_TYPE_KEY).setValue(medication.getDosageType()).setInputType(TYPE_CLASS_TEXT),
                    new EditTextPage(this, FREQUENCY_KEY).setValue(String.valueOf(medication.getFrequency())),
                    new EditTextPage(this, FREQUENCY_TYPE_KEY).setValue(medication.getFrequencyType()).setInputType(TYPE_CLASS_TEXT)
            );
        }
        else{
            return new PageList(
                    new EditTextPage(this, NAME_KEY).setInputType(TYPE_CLASS_TEXT), // change input type to text
                    new EditTextPage(this, DOSAGE_KEY),
                    new EditTextPage(this, DOSAGE_TYPE_KEY).setValue("(Pills/Oz)").setInputType(TYPE_CLASS_TEXT),
                    new EditTextPage(this, FREQUENCY_KEY),
                    new EditTextPage(this, FREQUENCY_TYPE_KEY).setValue("(Times per day)").setInputType(TYPE_CLASS_TEXT)
            );
        }
    }

    public MedicationCard getMedicationCard(){
        return this.medicationCard;
    }
}

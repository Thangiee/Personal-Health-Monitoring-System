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
    public static final String DOSAGE_KEY = "Dosage";

    public MedicationWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        medicationCard = EventBus.getDefault().removeStickyEvent(MedicationCard.class);
        if (medicationCard != null) {     // a foodCard was passed; therefore the user pressed the edit button.
            Medication medication = medicationCard.getMedication();

            return new PageList(
                    new EditTextPage(this, NAME_KEY).setValue(medication.getMedicationName()).setInputType(TYPE_CLASS_TEXT).setRequired(true),
                    new EditTextPage(this, DOSAGE_KEY).setValue(String.valueOf(medication.getDosage())).setRequired(true)
            );
        }
        else{
            return new PageList(
                    new EditTextPage(this, NAME_KEY).setInputType(TYPE_CLASS_TEXT).setRequired(true), // change input type to text
                    new EditTextPage(this, DOSAGE_KEY).setRequired(true)
            );
        }
    }

    public MedicationCard getMedicationCard(){
        return this.medicationCard;
    }
}

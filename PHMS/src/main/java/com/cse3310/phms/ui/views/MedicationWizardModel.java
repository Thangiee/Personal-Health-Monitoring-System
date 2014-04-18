package com.cse3310.phms.ui.views;

import android.content.Context;
import co.juliansuarez.libwizardpager.wizard.model.AbstractWizardModel;
import co.juliansuarez.libwizardpager.wizard.model.PageList;
import com.cse3310.phms.model.Medication;
import com.cse3310.phms.ui.cards.MedicationCard;
import de.greenrobot.event.EventBus;

/**
 * Created by Zach on 4/17/2014.
 */
public class MedicationWizardModel extends AbstractWizardModel{
    private MedicationCard medicationCard;
    public static final String NAME_KEY = "Medication Name";


    public MedicationWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        medicationCard = EventBus.getDefault().removeStickyEvent(MedicationCard.class);
        if (medicationCard != null) {     // a foodCard was passed; therefore the user pressed the edit button.
            Medication medication = medicationCard.getMedication();
        }

        return null;
    }

    public MedicationCard getMedicationCard(){
        return this.medicationCard;
    }
}

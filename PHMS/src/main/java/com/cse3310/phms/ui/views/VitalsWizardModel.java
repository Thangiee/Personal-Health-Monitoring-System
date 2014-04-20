package com.cse3310.phms.ui.views;

import android.content.Context;
import co.juliansuarez.libwizardpager.wizard.model.AbstractWizardModel;
import co.juliansuarez.libwizardpager.wizard.model.PageList;
import com.cse3310.phms.model.Vitals;
import com.cse3310.phms.ui.cards.VitalsCard;
import com.cse3310.phms.ui.views.pager.EditTextPage;
import de.greenrobot.event.EventBus;

import static android.text.InputType.TYPE_CLASS_TEXT;

/**
 * Created by Zach on 4/17/2014.
 */
public class VitalsWizardModel extends AbstractWizardModel{
    private VitalsCard vitalsCard;
    public static final String NAME_KEY = "Vitals Name";
    public static final String DOSAGE_KEY = "Dosage";

    public VitalsWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        vitalsCard = EventBus.getDefault().removeStickyEvent(VitalsCard.class);
        if (vitalsCard != null) {     // a foodCard was passed; therefore the user pressed the edit button.
            Vitals vitals = vitalsCard.getVitals();

            return new PageList(
                    new EditTextPage(this, NAME_KEY).setValue(vitals.getVitalsName()).setInputType(TYPE_CLASS_TEXT).setRequired(true),
                    new EditTextPage(this, DOSAGE_KEY).setValue(String.valueOf(vitals.getCholesterol())).setRequired(true)
            );
        }
        else{
            return new PageList(
                    new EditTextPage(this, NAME_KEY).setInputType(TYPE_CLASS_TEXT).setRequired(true), // change input type to text
                    new EditTextPage(this, DOSAGE_KEY).setRequired(true)
            );
        }
    }

    public VitalsCard getVitalsCard(){
        return this.vitalsCard;
    }
}

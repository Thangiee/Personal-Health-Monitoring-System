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
    public static final String CHOLESTEROL_KEY = "Cholesterol Level", GLUCOSE_KEY = "Glucose Level";
    public static final String BLOOD_KEY = "Blood Pressure", BODY_KEY="Body Temperature", PULSE_KEY = "Pulse Rate";
    public VitalsWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        vitalsCard = EventBus.getDefault().removeStickyEvent(VitalsCard.class);
        if (vitalsCard != null) {     // a foodCard was passed; therefore the user pressed the edit button.
            Vitals vitals = vitalsCard.getVitals();

            return new PageList(
                    new EditTextPage(this, BLOOD_KEY).setValue(String.valueOf(vitals.getBloodPressure())).setRequired(true),
                    new EditTextPage(this, GLUCOSE_KEY).setValue(String.valueOf(vitals.getCholesterol())).setRequired(true),
                    new EditTextPage(this, CHOLESTEROL_KEY).setValue(String.valueOf(vitals.getCholesterol())).setRequired(true),
                    new EditTextPage(this, BODY_KEY).setValue(String.valueOf(vitals.getCholesterol())).setRequired(true),
                    new EditTextPage(this, PULSE_KEY).setValue(String.valueOf(vitals.getCholesterol())).setRequired(true)

            );
        }
        else{
            return new PageList(
                    new EditTextPage(this, BLOOD_KEY).setRequired(true),
                    new EditTextPage(this, GLUCOSE_KEY).setRequired(true),
                    new EditTextPage(this, CHOLESTEROL_KEY).setRequired(true),
                    new EditTextPage(this, BODY_KEY).setRequired(true),
                    new EditTextPage(this, PULSE_KEY).setRequired(true)

            );
        }
    }

    public VitalsCard getVitalsCard(){
        return this.vitalsCard;
    }
}

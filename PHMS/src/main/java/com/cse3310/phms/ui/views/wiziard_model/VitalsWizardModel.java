
package com.cse3310.phms.ui.views.wiziard_model;


import android.content.Context;
import android.text.InputType;
import co.juliansuarez.libwizardpager.wizard.model.AbstractWizardModel;
import co.juliansuarez.libwizardpager.wizard.model.PageList;
import com.andreabaccega.formedittextvalidator.DateValidator;
import com.cse3310.phms.model.Vitals;
import com.cse3310.phms.ui.cards.VitalsCard;
import com.cse3310.phms.ui.views.pager.EditTextPage;
import de.greenrobot.event.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Zach on 4/17/2014.
 */
public class VitalsWizardModel extends AbstractWizardModel{
    private VitalsCard vitalsCard;
    public static final String CHOLESTEROL_KEY = "Cholesterol Level", GLUCOSE_KEY = "Glucose Level", DATE_KEY = "Date" ;
    public static final String BLOOD_KEY = "Blood Pressure", BODY_KEY="Body Temperature", PULSE_KEY = "Resting Pulse Rate";
    Calendar currDate;
    public VitalsWizardModel(Context context) {
        super(context);
    }
    DateValidator dateValidator = new DateValidator("Not a valid Date.", "SimpleDateFormat");

    @Override
    protected PageList onNewRootPageList() {
        vitalsCard = EventBus.getDefault().removeStickyEvent(VitalsCard.class);
        if (vitalsCard != null) {     // a foodCard was passed; therefore the user pressed the edit button.
            Vitals vitals = vitalsCard.getVitals();
            currDate = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy");
            String formattedDate=df.format(currDate.getTime());
            return new PageList(

                    new EditTextPage(this, DATE_KEY, dateValidator).setValue(formattedDate).setInputType(InputType.TYPE_CLASS_DATETIME),
                    new EditTextPage(this, BLOOD_KEY).setValue(String.valueOf(vitals.getBloodPressure())).setRequired(true),
                    new EditTextPage(this, GLUCOSE_KEY).setValue(String.valueOf(vitals.getCholesterol())).setRequired(true),
                    new EditTextPage(this, CHOLESTEROL_KEY).setValue(String.valueOf(vitals.getCholesterol())).setRequired(true),
                    new EditTextPage(this, BODY_KEY).setValue(String.valueOf(vitals.getCholesterol())).setRequired(true),
                    new EditTextPage(this, PULSE_KEY).setValue(String.valueOf(vitals.getCholesterol())).setRequired(true)

            );
        }
        else{
            return new PageList(

                    //new EditTextPage(this, DATE_KEY, dateValidator).setValue(vitals.getDate()).setInputType(InputType.TYPE_CLASS_DATETIME),
                    //new EditTextPage(this, DATE_KEY).setInputType(InputType.TYPE_CLASS_DATETIME),
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

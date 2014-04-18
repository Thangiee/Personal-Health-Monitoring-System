package com.cse3310.phms.ui.cards;

import android.content.Context;
import com.cse3310.phms.model.Medication;
import it.gmariotti.cardslib.library.internal.CardExpand;

/**
 * Created by Zach on 4/17/2014.
 */
public class MedicationCardExpand extends CardExpand{
    Medication medication = new Medication();

    public MedicationCardExpand(Context context, Medication medication) {
        super(context);
        this.medication = medication;
    }


}

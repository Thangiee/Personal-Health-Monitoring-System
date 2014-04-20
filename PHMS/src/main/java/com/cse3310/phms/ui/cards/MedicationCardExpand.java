package com.cse3310.phms.ui.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Medication;
import it.gmariotti.cardslib.library.internal.CardExpand;

/**
 * Created by Zach on 4/17/2014.
 */
public class MedicationCardExpand extends CardExpand{
    Medication medication;

    public MedicationCardExpand(Context context, Medication medication) {
        super(context, R.layout.medication_card_expand);
        this.medication = medication;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        if (view == null) return;

        TextView dosageTextView = (TextView) view.findViewById(R.id.frag_dosage_expand_count);
        TextView dosageTypeTextView = (TextView) view.findViewById(R.id.frag_dosage_type_expand_lbl);
        TextView frequencyTextView = (TextView) view.findViewById(R.id.frag_frequency_expand_count);
        TextView frequencyTypeTextView = (TextView) view.findViewById(R.id.frag_frequency_type_expand_lbl);



        dosageTextView.setText(String.valueOf(medication.getDosage()));
        dosageTypeTextView.setText(medication.getDosageType());
        frequencyTextView.setText(String.valueOf(medication.getFrequency()));
        frequencyTypeTextView.setText(medication.getFrequencyType());
    }
}

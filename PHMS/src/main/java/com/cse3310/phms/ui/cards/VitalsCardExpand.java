package com.cse3310.phms.ui.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Vitals;
import it.gmariotti.cardslib.library.internal.CardExpand;
/**
 * Created by E&N on 4/20/2014.
 */
public class VitalsCardExpand extends CardExpand{
    Vitals vitals;

    public VitalsCardExpand(Context context, Vitals vitals) {
        super(context, R.layout.vitals_card_expand);
        this.vitals = vitals;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        if (view == null) return;
        TextView cholesterolTextView = (TextView) view.findViewById(R.id.frag_dosage_expand_count);
        TextView bloodTextView = (TextView) view.findViewById(R.id.frag_dosage_expand_count);
        TextView glucoseTextView = (TextView) view.findViewById(R.id.frag_dosage_expand_count);
        TextView pulseTextView = (TextView) view.findViewById(R.id.frag_dosage_expand_count);
        TextView bodyTextView = (TextView) view.findViewById(R.id.frag_dosage_expand_count);

        cholesterolTextView.setText(String.valueOf(vitals.getCholesterol()));
        bloodTextView.setText(String.valueOf(vitals.getBloodPressure()));
        glucoseTextView.setText(String.valueOf(vitals.getGlucoseLevel()));
        pulseTextView.setText(String.valueOf(vitals.getPulse()));
        bodyTextView.setText(String.valueOf(vitals.getBodyTemp()));

    }
}

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
        TextView cholesterolTextView = (TextView) view.findViewById(R.id.frag_vital_expand_txt_c);
        TextView bloodTextView = (TextView) view.findViewById(R.id.frag_vital_expand_txt_bp);
        TextView glucoseTextView = (TextView) view.findViewById(R.id.frag_vital_expand_txt_gl);
        TextView pulseTextView = (TextView) view.findViewById(R.id.frag_vital_expand_txt_p);
        TextView bodyTextView = (TextView) view.findViewById(R.id.frag_vital_expand_txt_bt);

        cholesterolTextView.setText(String.valueOf(vitals.getCholesterol())+"mg/dL");
        bloodTextView.setText(String.valueOf(vitals.getBloodPressure())+"mmHg");
        glucoseTextView.setText(String.valueOf(vitals.getGlucoseLevel())+"mg/dL");
        pulseTextView.setText(String.valueOf(vitals.getPulse())+"bpm");
        bodyTextView.setText(String.valueOf(vitals.getBodyTemp())+"∘F");

    }
}

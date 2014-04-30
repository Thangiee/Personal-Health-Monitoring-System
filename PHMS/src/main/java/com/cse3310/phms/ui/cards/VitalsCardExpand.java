package com.cse3310.phms.ui.cards;

import android.content.Context;
import android.graphics.Color;
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
        if(vitals.getCholesterol()>=240)
            cholesterolTextView.setTextColor(Color.RED);
        if(vitals.getCholesterol()<240 && vitals.getCholesterol()>=200)
            cholesterolTextView.setTextColor(Color.YELLOW);
        else
            cholesterolTextView.setTextColor(Color.GREEN);

        bloodTextView.setText(String.valueOf(vitals.getBloodPressure()) + "mmHg");
        if(vitals.getBloodPressure()>=140)
            bloodTextView.setTextColor(Color.RED);
        if(vitals.getBloodPressure()<120 && vitals.getCholesterol()>=139)
            bloodTextView.setTextColor(Color.YELLOW);
        else
            bloodTextView.setTextColor(Color.GREEN);

        glucoseTextView.setText(String.valueOf(vitals.getGlucoseLevel())+"mg/dL");
        if(vitals.getGlucoseLevel()>=70 && vitals.getGlucoseLevel()<105)
            glucoseTextView.setTextColor(Color.RED);
        else
            glucoseTextView.setTextColor(Color.GREEN);

        pulseTextView.setText(String.valueOf(vitals.getPulse())+"bpm");
        if(vitals.getPulse()>=100)
            pulseTextView.setTextColor(Color.YELLOW);
        else
            pulseTextView.setTextColor(Color.GREEN);

        bodyTextView.setText(String.valueOf(vitals.getBodyTemp())+" ∘F");
        if(vitals.getBodyTemp()>=100 && vitals.getBodyTemp()<=97)
            bodyTextView.setTextColor(Color.YELLOW);
        else
            bodyTextView.setTextColor(Color.GREEN);

    }
}

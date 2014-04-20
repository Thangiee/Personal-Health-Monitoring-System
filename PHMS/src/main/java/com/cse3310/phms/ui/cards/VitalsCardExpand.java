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

        TextView dosageTextView = (TextView) view.findViewById(R.id.frag_dosage_expand_count);

        dosageTextView.setText(String.valueOf(vitals.getCholesterol()));
    }
}

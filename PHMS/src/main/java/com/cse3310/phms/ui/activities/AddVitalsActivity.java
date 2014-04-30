package com.cse3310.phms.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Vitals;
import com.cse3310.phms.ui.cards.VitalsCard;
import com.cse3310.phms.ui.fragments.CardListFragment_;
import com.cse3310.phms.ui.utils.DatabaseHandler;
import com.cse3310.phms.ui.utils.Events;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

import java.io.StringBufferInputStream;
import java.util.*;

import static com.cse3310.phms.ui.utils.Comparators.FoodCardComparator.*;

//import com.cse3310.phms.ui.fragments.CardListFragment;

/**
 * Created by E&N on 4/20/2014.
 */
@EActivity
public class AddVitalsActivity extends BaseActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Vitals");
        EventBus.getDefault().registerSticky(this);

        List<Vitals> vitalsList = DatabaseHandler.getAllRows(Vitals.class); // get all the food in the DB

        Set<String> nameSet = new HashSet<String>(vitalsList.size());
        List<Card> cardList = new ArrayList<Card>(nameSet.size());
        // create a vitalCard for each of the vitals
        // .
        for (final Vitals vitals : vitalsList) {
            if (nameSet.add(vitals.getDate())) {
                cardList.add(createVitalsCard(vitals));
            }
        }
        Collections.sort(cardList, getComparator(NAME_SORT, BRAND_SORT));

        CardListFragment_ cardListFragment = CardListFragment_.newInstance(cardList, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_front_container, cardListFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.add_menu, menu); // add the plus icon to the action bar
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OptionsItem(R.id.add_icon)
    void menuAddButton() {
        // start a new activity to add create new vital after clicking on the add icon
        Intent intent = new Intent(this, VitalsWizardPagerActivity.class);
        startActivity(intent);
        finish();   // kill the current activity
    }


    private VitalsCard createVitalsCard(final Vitals vitals) {
        final VitalsCard vitalsCard = new VitalsCard(this, vitals);
        vitalsCard.setTitle("Past Entry: " + vitals.getDate());
        vitalsCard.setButtonTitle("Add");
        vitalsCard.setSwipeable(true);

        // setup what to do when the add button is clicked on the card
        vitalsCard.setBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddVitalsActivity.this,
                        "Added Selected Vital Card .", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().postSticky(new Events.AddVitalsCardEvent(vitalsCard));
            }
        });

        return vitalsCard;
    }
}

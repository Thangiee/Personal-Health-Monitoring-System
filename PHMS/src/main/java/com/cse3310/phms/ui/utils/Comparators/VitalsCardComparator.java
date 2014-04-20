package com.cse3310.phms.ui.utils.Comparators;
import it.gmariotti.cardslib.library.internal.Card;

import java.util.Comparator;

/**
 * Created by E&N on 4/20/2014.
 */
public enum VitalsCardComparator implements Comparator<Card> {
    NAME_SORT {
        @Override
        public int compare(Card card, Card card2) {
            return card.getTitle().toLowerCase().compareTo(card2.getTitle().toLowerCase());
        }
    }
}

package com.cse3310.phms.ui.utils.Comparators;

import it.gmariotti.cardslib.library.internal.Card;

import java.util.Comparator;

/**
 * Created by Zach on 4/18/2014.
 */
public enum MedicationCardComparator implements Comparator<Card> {
    NAME_SORT {
        @Override
        public int compare(Card card, Card card2) {
            return card.getTitle().toLowerCase().compareTo(card2.getTitle().toLowerCase());
        }
    }
}

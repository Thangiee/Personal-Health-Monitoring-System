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
    };

    public static Comparator<Card> getComparator(final MedicationCardComparator... multipleOptions) {
        return new Comparator<Card>() {
            @Override
            public int compare(Card lhs, Card rhs) {
                for (MedicationCardComparator comparator : multipleOptions) {
                    int result = comparator.compare(lhs, rhs);
                    if (result != 0) {
                        return result;
                    }
                }
                return 0;
            }
        };
    }

}



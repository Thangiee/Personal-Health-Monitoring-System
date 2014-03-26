package com.cse3310.phms.ui.utils.Comparators;

import com.cse3310.phms.ui.cards.FoodCard;
import it.gmariotti.cardslib.library.internal.Card;

import java.util.Comparator;

public enum FoodCardComparator implements Comparator<Card> {
    TITLE_SORT {
        @Override
        public int compare(Card lhs, Card rhs) {
            return lhs.getTitle().compareTo(rhs.getTitle());
        }
    },

    CALORIES_SORT {
        @Override
        public int compare(Card lhs, Card rhs) {
            return ((FoodCard) lhs).getFood().getCalories() > ((FoodCard) rhs).getFood().getCalories() ? 1 : -1;
        }
    };


    public static Comparator<Card> getComparator(final FoodCardComparator... multipleOptions) {
        return new Comparator<Card>() {
            public int compare(Card o1, Card o2) {
                for (FoodCardComparator option : multipleOptions) {
                    int result = option.compare(o1, o2);
                    if (result != 0) {
                        return result;
                    }
                }
                return 0;
            }
        };
    }
}

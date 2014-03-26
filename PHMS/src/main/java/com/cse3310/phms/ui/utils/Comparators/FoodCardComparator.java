package com.cse3310.phms.ui.utils.Comparators;

import com.cse3310.phms.ui.cards.FoodCard;
import it.gmariotti.cardslib.library.internal.Card;

import java.util.Comparator;

public enum FoodCardComparator implements Comparator<Card> {
    NAME_SORT {
        @Override
        public int compare(Card lhs, Card rhs) {
            return lhs.getTitle().toLowerCase().compareTo(rhs.getTitle().toLowerCase());
        }
    },

    BRAND_SORT {
        @Override
        public int compare(Card lhs, Card rhs) {
            try {
                return ((FoodCard) lhs).getFood().getBrand().compareTo(((FoodCard) rhs).getFood().getBrand());
            } catch (NullPointerException e) {
                return -1;
            }
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
            @Override
            public int compare(Card lhs, Card rhs) {
                for (FoodCardComparator comparator : multipleOptions) {
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

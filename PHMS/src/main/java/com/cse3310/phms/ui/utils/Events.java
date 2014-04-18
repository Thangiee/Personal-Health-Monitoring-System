/*
 * Copyright (c) 2014 Personal-Health-Monitoring-System
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cse3310.phms.ui.utils;

import com.cse3310.phms.ui.cards.ContactCard;
import com.cse3310.phms.ui.cards.DoctorContactCard;
import com.cse3310.phms.ui.cards.FoodCard;
import com.cse3310.phms.ui.cards.MedicationCard;
import it.gmariotti.cardslib.library.internal.Card;

import java.util.Collection;
import java.util.List;

public class Events {
    public static class SlidingMenuItemSelectedEvent {
        public String newTitle;

        public SlidingMenuItemSelectedEvent(String newTitle) {
            this.newTitle = newTitle;
        }
    }

    public static class initCardsToSearchEvent {
        public Collection<Card> cardsToSearch;

        public initCardsToSearchEvent(Collection<Card> cardsToSearch) {
            this.cardsToSearch = cardsToSearch;
        }
    }

    public static class initSearchWordEvent {
        public String searchWord;

        public initSearchWordEvent(String searchWord) {
            this.searchWord = searchWord;
        }
    }

    public static class SetSuggestionEvent {
        public Collection<String> suggestions;

        public SetSuggestionEvent(Collection<String> suggestions) {
            this.suggestions = suggestions;
        }
    }

    public static class AddFoodCardEvent {
        public FoodCard foodCard;

        public AddFoodCardEvent(FoodCard foodCard) {
            this.foodCard = foodCard;
        }
    }

    public static class AddMedicationCardEvent {
        public MedicationCard medicationCard;

        public AddMedicationCardEvent(MedicationCard medicationCard) {
            this.medicationCard = medicationCard;
        }
    }

    public static class RemoveMedicationCardEvent {
        public MedicationCard medicationCard;

        public RemoveMedicationCardEvent(MedicationCard medicationCard) {
            this.medicationCard = medicationCard;
        }
    }

    public static class RemoveFoodCardEvent {
        public FoodCard foodCard;

        public RemoveFoodCardEvent(FoodCard foodCard) {
            this.foodCard = foodCard;
        }
    }

    public static class AddDoctorCardEvent {
        public DoctorContactCard doctorContactCard;

        public AddDoctorCardEvent(DoctorContactCard doctorContactCard) {
            this.doctorContactCard = doctorContactCard;
        }
    }

    public static class RemoveDoctorCardEvent {
        public DoctorContactCard doctorContactCard;

        public RemoveDoctorCardEvent(DoctorContactCard doctorContactCard) {
            this.doctorContactCard = doctorContactCard;
        }
    }

    public static class AddContactCardEvent {
        public ContactCard contactCard;

        public AddContactCardEvent(ContactCard contactCard) {
            this.contactCard = contactCard;
        }
    }

    public static class RemoveContactCardEvent {
        public ContactCard contactCard;

        public RemoveContactCardEvent(ContactCard contactCard) {
            this.contactCard = contactCard;
        }
    }

    public static class SwitchDayEvent { }

    public static class SwitchTabEvent {
        public int position;

        public SwitchTabEvent(int position) {
            this.position = position;
        }
    }

    public static class PostCardListEvent {
        public List<Card> cardList;

        public PostCardListEvent(List<Card> cardList) {
            this.cardList = cardList;
        }
    }
}

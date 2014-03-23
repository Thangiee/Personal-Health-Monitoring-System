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

import it.gmariotti.cardslib.library.internal.Card;

import java.util.Collection;

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

    public static class AddCardEvent <T extends Card>{
        public T card;

        public AddCardEvent(T card) {
            this.card = card;
        }
    }
}

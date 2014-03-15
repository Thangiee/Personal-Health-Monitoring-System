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

import java.util.List;

public class Events {
    public static class SlidingMenuItemSelectedEvent {
        public String newTitle;

        public SlidingMenuItemSelectedEvent(String newTitle) {
            this.newTitle = newTitle;
        }
    }

    public static class SearchEvent {
        public String searchWord;

        public SearchEvent(String searchWord) {
            this.searchWord = searchWord;
        }
    }

    public static class initListEvent<T> {
        public List<T> list;

        public initListEvent(List<T> list) {
            this.list = list;
        }
    }
}

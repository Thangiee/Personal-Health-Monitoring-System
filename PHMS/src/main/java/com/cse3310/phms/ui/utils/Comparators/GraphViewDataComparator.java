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

package com.cse3310.phms.ui.utils.Comparators;

import com.jjoe64.graphview.GraphView;

import java.util.Comparator;

public enum GraphViewDataComparator implements Comparator<GraphView.GraphViewData> {
    X_AXIS_SORT {
        @Override
        public int compare(GraphView.GraphViewData lhs, GraphView.GraphViewData rhs) {
            return lhs.getX() > rhs.getX() ? 1 : -1;
        }
    },

    Y_AXIS_SORT {
        @Override
        public int compare(GraphView.GraphViewData lhs, GraphView.GraphViewData rhs) {
            return lhs.getY() > rhs.getY() ? 1 : -1;
        }
    }
}

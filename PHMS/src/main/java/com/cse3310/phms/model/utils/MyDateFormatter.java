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

package com.cse3310.phms.model.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MyDateFormatter {
    private static SimpleDateFormat dateFormat;
    private static SimpleDateFormat timeFormat;

    static {
        dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        timeFormat = new SimpleDateFormat("h:mma", Locale.ENGLISH);

        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Gets formatted date of the weight log.
     * Ex. March 10, 2014
     *
     * @return the format date
     */
    public static String formatDate(long milliseconds) {
        return dateFormat.format(new Date(milliseconds));
    }

    /**
     * Gets formatted time of the weight log.
     * Ex. 2:53PM
     *
     * @return the format time
     */
    public static String formatTime(long milliseconds) {
        return timeFormat.format(new Date(milliseconds));
    }
}

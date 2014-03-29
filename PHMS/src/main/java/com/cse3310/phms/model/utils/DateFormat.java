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

public class DateFormat {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/d", Locale.ENGLISH);
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:s,a", Locale.ENGLISH);

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static String getFormattedDate(Date date) {
        return dateFormat.format(date);
    }

    public static String getFormattedTime(Date date) {
        return timeFormat.format(date);
    }
}

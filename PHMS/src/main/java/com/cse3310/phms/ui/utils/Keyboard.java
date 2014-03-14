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

import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Keyboard {

    public static void toggle(InputMethodManager imm){
        if (imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        } else {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public static void hide(InputMethodManager imm, View v){
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void show(InputMethodManager imm, View v){
        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
    }
}

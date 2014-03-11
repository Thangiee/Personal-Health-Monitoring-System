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

package com.cse3310.phms.ui.utils.validators;

import android.widget.EditText;
import com.andreabaccega.formedittextvalidator.Validator;

public class NotZeroValidator extends Validator{


    public NotZeroValidator(String _customErrorMessage) {
        super(_customErrorMessage);
    }

    @Override
    public boolean isValid(EditText editText) {
        String text = editText.getText().toString();

        if (text.equals("."))
            text = "0.0";

        double input = Double.parseDouble(text);
        return !(input == 0.0);
    }
}

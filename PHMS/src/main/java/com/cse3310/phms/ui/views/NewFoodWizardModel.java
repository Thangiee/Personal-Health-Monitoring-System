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

package com.cse3310.phms.ui.views;

import android.content.Context;
import co.juliansuarez.libwizardpager.wizard.model.AbstractWizardModel;
import co.juliansuarez.libwizardpager.wizard.model.NumberPage;
import co.juliansuarez.libwizardpager.wizard.model.PageList;
import co.juliansuarez.libwizardpager.wizard.model.TextPage;
import com.cse3310.phms.ui.utils.validators.MinimumLengthValidator;
import com.cse3310.phms.ui.utils.validators.NoSpaceValidator;
import com.cse3310.phms.ui.views.pager.EditTextPage;

public class NewFoodWizardModel extends AbstractWizardModel {
    public static final String NAME = "Food Name";
    public static final String CAL = "Calories (kcal)";
    public static final String PROTEIN = "Proteins (g)";
    public static final String FAT = "Fats (g)";
    public static final String FIBER = "Fibers (g)";
    public static final String SUGAR = "Sugars (g)";
    public static final String SERVING = "Number of Servings";

    public NewFoodWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new TextPage(this, NAME).setRequired(true),
                new EditTextPage(this, CAL, new MinimumLengthValidator(4), new NoSpaceValidator()).setRequired(true),
                new NumberPage(this, PROTEIN).setRequired(true),
                new NumberPage(this, FAT).setRequired(true),
                new NumberPage(this, FIBER).setRequired(true),
                new NumberPage(this, SUGAR).setRequired(true),
                new NumberPage(this, SERVING).setRequired(true)
        );
    }
}

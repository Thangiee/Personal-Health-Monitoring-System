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

package com.cse3310.phms.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import com.cse3310.phms.model.EStorage;
import com.cse3310.phms.ui.cards.UrlCard;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.views.UrlWizardModel;
import de.greenrobot.event.EventBus;

import static co.juliansuarez.libwizardpager.wizard.model.Page.SIMPLE_DATA_KEY;
import static com.cse3310.phms.ui.views.UrlWizardModel.URL_LINK;
import static com.cse3310.phms.ui.views.UrlWizardModel.URL_TITLE;

/**
 * Created by Owner on 4/15/14.
 */
public class UrlWizardPagerActivity extends BaseWizardPagerActivity {

    int type;
    private EStorage urlInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String actionBarTile = super.editMode ? "Edit Estorage Information" : "Create Estorage Information";
        getActionBar().setTitle(actionBarTile);
        Intent intent = getIntent();
        type = intent.getExtras().getInt("tabInt");
        System.out.println("Type is" + type);
    }

    @Override
    public void onSetupWizardModel() {
        super.mWizardModel = new UrlWizardModel(this);
    }

    @Override
    public void onSubmit() {
        // extract all the information from the process
        String urlLink = onGetPage(URL_LINK).getData().getString(SIMPLE_DATA_KEY);
        String urlTitle = onGetPage(URL_TITLE).getData().getString(SIMPLE_DATA_KEY);


       EStorage urlInfo = new EStorage().setUrl(urlLink).setTitle(urlTitle);

        // if editing, remove the old card and add the new edited card
        if (super.editMode) {
            UrlCard oldUrlCard = ((UrlWizardModel) mWizardModel).getUrlCard();
            // post an event to ContactScreenFragment to remove the old card
            EventBus.getDefault().post(new Events.RemoveUrlCardEvent(oldUrlCard));
        }

        // post an event to ContactScreenFragment to add a new card

        System.out.println("check1_2");
        EventBus.getDefault().post(new Events.AddUrlCardEvent(new UrlCard(this, urlInfo)));
        System.out.println("check1_3");
        finish();
    }
}

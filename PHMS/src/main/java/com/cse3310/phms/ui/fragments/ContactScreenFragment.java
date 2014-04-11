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

package com.cse3310.phms.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.cse3310.phms.R;
import com.cse3310.phms.ui.adapters.ContactScreenAdapter;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.contact_screen)
public class ContactScreenFragment extends SherlockFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentManager fm = getChildFragmentManager();
        TabsIndicatorFragment tabsIndicatorFragment = TabsIndicatorFragment.newInstance(new ContactScreenAdapter(fm));

        final FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.contact_screen_content_container, tabsIndicatorFragment);
        transaction.commit();

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

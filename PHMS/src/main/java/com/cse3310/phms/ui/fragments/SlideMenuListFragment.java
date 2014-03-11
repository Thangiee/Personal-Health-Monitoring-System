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
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;
import com.ami.fundapter.BindDictionary;
import com.ami.fundapter.FunDapter;
import com.ami.fundapter.extractors.StringExtractor;
import com.cse3310.phms.R;
import com.cse3310.phms.model.User;
import com.cse3310.phms.ui.utils.DrawerItem;
import com.cse3310.phms.ui.utils.UserSingleton;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class SlideMenuListFragment extends SherlockListFragment {
    private List<DrawerItem> drawerItems = new ArrayList<DrawerItem>() {{
        add(new DrawerItem(R.layout.frag_home_screen, "Home"));
        add(new DrawerItem(R.layout.frag_diet_screen, "Diet"));
        add(new DrawerItem(R.layout.frag_diet_screen, "test"));
    }};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BindDictionary<DrawerItem> dict = new BindDictionary<DrawerItem>();
        dict.addStringField(R.id.frag_list_item_tv_title,
               new StringExtractor<DrawerItem>() {
                   @Override
                   public String getStringValue(DrawerItem drawerItem, int i) {
                       return drawerItem.title;
                   }
               });

        FunDapter<DrawerItem> adapter = new FunDapter<DrawerItem>(getActivity(), drawerItems, R.layout.frag_list_item, dict);
        setListAdapter(adapter);

        getListView().setItemChecked(0, true); // set home in sliding menu as default on start up
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container,savedInstanceState);
        View view = inflater.inflate(R.layout.frag_list_sliding_menu, container, false);

        // set the sliding menu header to show the current user's username and email.
        User user = UserSingleton.getInstance().getCurrentUser();
        TextView usernameHeader = (TextView) view.findViewById(R.id.frag_list_sliding_menu_tv_header_username);
        usernameHeader.setText(user.getUsername());
        TextView emailHeader = (TextView) view.findViewById(R.id.frag_list_sliding_menu_tv_header_email);
        emailHeader.setText(user.getPersonalInfo().getEmail());

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        selectItem(position);
//        EventBus.getDefault().postSticky(new EventBusDemoEvent.ButtonClickEvent(position));
        ((SlidingFragmentActivity) getActivity()).getSlidingMenu().toggle(); // close sliding menu after clicking an item
    }

    private void selectItem(int position) {
        SherlockFragment fragment = null;
        FragmentTransaction fragTran = getActivity().getSupportFragmentManager().beginTransaction();

        switch (drawerItems.get(position).layoutId) {
            case R.layout.frag_home_screen:
                Toast.makeText(getActivity(), "you clicked home", Toast.LENGTH_SHORT).show();
                fragment = new HomeScreenFragment_();
                break;
            case R.layout.frag_diet_screen:
                Toast.makeText(getActivity(), "you clicked diet", Toast.LENGTH_SHORT).show();
                fragment = new DietScreenFragment_();
                break;
        }

        if (fragment != null) {
            fragTran.replace(R.id.frag_front_container, fragment);
            fragTran.commit();
        }
    }
}

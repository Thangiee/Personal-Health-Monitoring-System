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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;
import com.ami.fundapter.BindDictionary;
import com.ami.fundapter.FunDapter;
import com.ami.fundapter.extractors.StringExtractor;
import com.ami.fundapter.interfaces.StaticImageLoader;
import com.cse3310.phms.R;
import com.cse3310.phms.model.User;
import com.cse3310.phms.ui.utils.DrawerItem;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.utils.UserSingleton;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import de.greenrobot.event.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SlideMenuListFragment extends SherlockListFragment {
    private List<DrawerItem> drawerItems = new ArrayList<DrawerItem>() {{ // list of items to be display in the sliding menu
        add(new DrawerItem(R.layout.home_screen, "Home", R.drawable.ic_action_home));
        add(new DrawerItem(R.layout.diet_screen, "Diet", R.drawable.ic_action_restaurant));
        add(new DrawerItem(R.layout.weight_log_screen, "Weight Logs", R.drawable.ic_action_line_chart));
        add(new DrawerItem(R.layout.medication_screen, "Medication", R.drawable.ic_action_pill));
        add(new DrawerItem(R.layout.appointment_screen, "Appointment", R.drawable.ic_action_calendar_day));
        add(new DrawerItem(R.layout.vitals_screen, "Vital Signs", R.drawable.ic_action_warning));
        add(new DrawerItem(R.layout.contact_screen, "Contacts", R.drawable.ic_action_users));
        add(new DrawerItem(R.layout.estorage_screen, "eStorage", R.drawable.ic_action_database));
        add(new DrawerItem(R.layout.reminder_screen, "Reminders", R.drawable.ic_action_alarm));
    }};

    private int lastPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.list_sliding_menu, container, false);

        // set the sliding menu header to show the current user's username and email.
        User user = UserSingleton.INSTANCE.getCurrentUser();
        TextView usernameHeader = (TextView) view.findViewById(R.id.frag_list_sliding_menu_tv_header_username);
        usernameHeader.setText(Character.toUpperCase(user.getUsername().charAt(0)) + user.getUsername().substring(1)); // first char to upper case
        TextView emailHeader = (TextView) view.findViewById(R.id.frag_list_sliding_menu_tv_header_email);
        emailHeader.setText(user.getPersonalInfo().getEmail());
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // see FunDapter library at https://github.com/amigold/FunDapter
        BindDictionary<DrawerItem> dict = new BindDictionary<DrawerItem>();
        // setup the text for the items in the sliding menu
        dict.addStringField(R.id.frag_list_item_tv_title,
               new StringExtractor<DrawerItem>() {
                   @Override
                   public String getStringValue(DrawerItem drawerItem, int i) {
                       return drawerItem.title;
                   }
               });

        // setup the icon for the items in the sliding menu
        dict.addStaticImageField(R.id.list_item_icon, new StaticImageLoader<DrawerItem>() {
            @Override
            public void loadImage(DrawerItem item, ImageView imageView, int position) {
                if (item.imageId == DrawerItem.DEFAULT) {
                    imageView.setImageResource(R.drawable.ic_launcher);
                } else {
                    imageView.setImageResource(item.imageId);
                }
            }
        });

        FunDapter<DrawerItem> adapter = new FunDapter<DrawerItem>(getActivity(), drawerItems, R.layout.list_item, dict);
        setListAdapter(adapter);

        getListView().setItemChecked(0, true); // set home in sliding menu as default on start up
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        selectItem(position);
        lastPosition = position;
        ((SlidingFragmentActivity) getActivity()).getSlidingMenu().toggle(); // close sliding menu after clicking an item
    }

    private void selectItem(int position) {
        SherlockFragment fragment = null;
        FragmentTransaction fragTran = getActivity().getSupportFragmentManager().beginTransaction();

        // decide which screen to be switch to base on drawer item the user clicked
        switch (drawerItems.get(position).layoutId) {
            case R.layout.home_screen:
                fragment = new HomeScreenFragment_();
                break;
            case R.layout.diet_screen:
                fragment = new DietScreenFragment_();
                break;
            case R.layout.weight_log_screen:
                fragment = new WeightLogScreenFragment_();
                break;
            case R.layout.contact_screen:
                fragment = new ContactScreenFragment_();
                break;
            case R.layout.estorage_screen:
                fragment = new EStorageFragment_();
                break;
            case R.layout.medication_screen:
                fragment = new MedicationScreenFragment_();
                break;
            case R.layout.appointment_screen:
                fragment = new AppointmentScreenFragment_();
                break;
            case R.layout.reminder_screen:
                fragment = new ReminderScreenFragment_();
                break;
            case R.layout.vitals_screen:
                fragment = new VitalsScreenFragment_();
                break;
        }

        // if the screen to switch to is the same as the current screen,
        // do nothing/don't recreate that screen.
        if (lastPosition == position) {
            return;
        }

        if (fragment != null) {
            // switch the screen!
            fragTran.replace(R.id.frag_front_container, fragment);
            fragTran.commit();
        }
    }
}

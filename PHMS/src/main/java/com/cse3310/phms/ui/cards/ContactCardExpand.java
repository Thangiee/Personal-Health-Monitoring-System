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

package com.cse3310.phms.ui.cards;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.cse3310.phms.R;
import com.cse3310.phms.model.Info;
import com.cse3310.phms.ui.activities.ContactWizardPagerActivity;
import com.cse3310.phms.ui.utils.Events;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.CardExpand;

public class ContactCardExpand extends CardExpand{
    private Info mContactInfo;

    public ContactCardExpand(Context context, Info contactInfo) {
        super(context, R.layout.contact_card_expand);
        mContactInfo = contactInfo;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        TextView emailTextView = (TextView) view.findViewById(R.id.contact_card_expand_email);
        TextView phoneTextView = (TextView) view.findViewById(R.id.contact_card_expand_phone);

        emailTextView.setText(String.valueOf(mContactInfo.getEmail()));
        phoneTextView.setText(String.valueOf(mContactInfo.getPhone()));

        setOnClickListener(view);
    }

    private void setOnClickListener(View view) {
        ImageButton phoneImageButton = (ImageButton) view.findViewById(R.id.phone_img_btn);
        ImageButton chatImageButton = (ImageButton) view.findViewById(R.id.chat_img_btn);
        ImageButton editImageButton = (ImageButton) view.findViewById(R.id.edit_img_btn);
        ImageButton trashImageButton = (ImageButton) view.findViewById(R.id.trash_img_btn);

        phoneImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open up the phone app and put in the phone number so it is ready for the user to call.
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + mContactInfo.getPhone()));
                getContext().startActivity(callIntent);
            }
        });

        chatImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open up the sms app and put in the phone number so it is ready for the user send a text message.
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("smsto:" + mContactInfo.getPhone()));
                getContext().startActivity(smsIntent);
            }
        });

        editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start an activity to edit the card info.
                EventBus.getDefault().postSticky(getParentCard());
                Intent intent = new Intent(getContext(), ContactWizardPagerActivity.class);
                getContext().startActivity(intent);
            }
        });

        trashImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open up a dialog to confirm deleting the contact.
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirm Deletion")
                        .setMessage("This contact will be deleted.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EventBus.getDefault().post(new Events.RemoveContactCardEvent((ContactCard) getParentCard()));
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }
}

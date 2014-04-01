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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.andreabaccega.formedittextvalidator.EmptyValidator;
import com.andreabaccega.widget.FormEditText;
import com.cse3310.phms.R;
import com.cse3310.phms.model.User;
import com.cse3310.phms.model.WeightLog;
import com.cse3310.phms.model.utils.DateFormat;
import com.cse3310.phms.ui.adapters.TextWatcherAdapter;
import com.cse3310.phms.ui.utils.Comparators.GraphViewDataComparator;
import com.cse3310.phms.ui.utils.UserSingleton;
import com.cse3310.phms.ui.utils.validators.NotZeroValidator;
import com.cse3310.phms.ui.views.CalendarPickerDialog;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import java.util.*;

import static android.view.View.inflate;
import static com.jjoe64.graphview.GraphView.GraphViewData;

@EFragment(R.layout.weight_log_screen)
public class WeightLogScreenFragment extends SherlockFragment{
    private GraphViewSeries graphViewSeries;
    private LineGraphView graphView;
    private List<GraphViewData> data;
    private Map<String, WeightLog> weightLogs = new LinkedHashMap<String, WeightLog>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @AfterViews
    void onSetupGraphView() {
        User user = UserSingleton.INSTANCE.getCurrentUser();
        data = new ArrayList<GraphViewData>();

        for (WeightLog weightLog : user.getDiet().getWeightLogs()) {
            weightLogs.put(DateFormat.getFormattedDate(weightLog.getDate()), weightLog);
            data.add(new GraphViewData(weightLog.getDate().getTime(), weightLog.getWeight()));
        }

        Collections.sort(data, GraphViewDataComparator.X_AXIS_SORT);
        graphViewSeries = new GraphViewSeries(data.toArray(new GraphViewDataInterface[data.size()]));

        graphView = new LineGraphView(getActivity(), "");
        graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
            @Override
            public String formatLabel(double v, boolean xAxis) {
                if(xAxis) {
                    return DateFormat.getFormattedDate(new Date((long) v));
                }
                return null;
            }
        });

        graphView.setManualYAxis(true);
        graphView.addSeries(graphViewSeries);
        graphView.setDrawDataPoints(true);
        graphView.setDataPointsRadius(5f);
        graphView.getGraphViewStyle().setNumHorizontalLabels(6);
        graphView.getGraphViewStyle().setNumVerticalLabels(5);
        graphView.setDrawBackground(true);
        graphView.setBackgroundColor(Color.argb(100, 102, 255, 102));
        int lightGray = getResources().getColor(R.color.gray);
        graphView.getGraphViewStyle().setGridColor(lightGray);
        graphView.getGraphViewStyle().setHorizontalLabelsColor(lightGray);
        graphView.getGraphViewStyle().setVerticalLabelsColor(lightGray);

        if (data.size() != 0) {
            graphView.setManualYAxisBounds(getYMax(data) + 25, getYMin(data) - 25);
        } else {
            graphView.setManualYAxisBounds(100, 0);
        }

        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.weight_log_graph);
        layout.addView(graphView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.overflow_menu, menu);
    }

    @Click(R.id.weight_log_screen_log_btn)
    void onClick() {
        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.MONTH, -3);
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.DATE, 1);

        final CalendarPickerDialog calendarPickerDialog = new CalendarPickerDialog(getActivity());
        calendarPickerDialog.setDateRange(minDate.getTime(), maxDate.getTime());

        calendarPickerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openWeightLogDialog(calendarPickerDialog.getCalendarPickerView().getSelectedDate());
                calendarPickerDialog.dismiss();
            }
        });

        calendarPickerDialog.show();
    }

    private double getYMin(List<GraphViewData> data) {
        return Collections.min(data, GraphViewDataComparator.Y_AXIS_SORT).getY();
    }

    public double getYMax(List<GraphViewData> data) {
        return Collections.max(data, GraphViewDataComparator.Y_AXIS_SORT).getY();
    }

    public void addLog(WeightLog log) {
        String key = DateFormat.getFormattedDate(log.getDate());

        // remove the old log with the same date as the
        // new log and delete it from the DB
        if (weightLogs.containsKey(key)) {
            weightLogs.remove(key).delete();
        }

        log.save(); // save to the DB
        weightLogs.put(key, log);   // add to the hashmap

        // re-initialize the data array with weight logs
        data.clear();
        for (WeightLog weightLog : weightLogs.values()) {
            data.add(new GraphViewData(weightLog.getDate().getTime(), weightLog.getWeight()));
        }

        // sort the x-axis by date
        Collections.sort(data, GraphViewDataComparator.X_AXIS_SORT);
        graphViewSeries.resetData(data.toArray(new GraphViewDataInterface[data.size()]));
        graphView.setManualYAxisBounds(getYMax(data) + 25, getYMin(data) - 25);
    }

    private void openWeightLogDialog(final Date date) {
        final View view = inflate(getActivity(), R.layout.weight_log_dialog, null);
        final FormEditText formEditText = (FormEditText) view.findViewById(R.id.weight_log_dialog_et);
        final boolean[] isValid = {false};
        formEditText.addValidator(new NotZeroValidator("Can't weight 0"));
        formEditText.addValidator(new EmptyValidator("Can't be empty"));
        formEditText.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable editable) {
                isValid[0] = formEditText.testValidity();
            }
        });

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Weight")

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isValid[0]) {
                            addLog(new WeightLog(Double.parseDouble(formEditText.getText().toString()))
                                    .setDate(date));
                            dialogInterface.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "Weight log was NOT recorded", Toast.LENGTH_SHORT).show();
                        }
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }
}

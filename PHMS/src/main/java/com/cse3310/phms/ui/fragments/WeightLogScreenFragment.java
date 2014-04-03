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
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.andreabaccega.formedittextvalidator.EmptyValidator;
import com.andreabaccega.widget.FormEditText;
import com.cse3310.phms.R;
import com.cse3310.phms.model.User;
import com.cse3310.phms.model.WeightLog;
import com.cse3310.phms.ui.adapters.TextWatcherAdapter;
import com.cse3310.phms.ui.utils.Comparators.GraphViewDataComparator;
import com.cse3310.phms.ui.utils.UserSingleton;
import com.cse3310.phms.ui.utils.validators.NotZeroValidator;
import com.cse3310.phms.ui.views.CalendarPickerDialog;
import com.jjoe64.graphview.*;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.*;

import static android.view.View.inflate;
import static com.jjoe64.graphview.GraphView.GraphViewData;

@EFragment(R.layout.weight_log_screen)
public class WeightLogScreenFragment extends SherlockFragment{
    private GraphViewSeries weightSeries;
    private GraphViewSeries targetSeries;
    private LineGraphView graphView;
    private List<GraphViewData> data;
    private GraphViewData[] targetData;
    private Map<String, WeightLog> weightLogs = new LinkedHashMap<String, WeightLog>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d", Locale.ENGLISH);
    private User user = UserSingleton.INSTANCE.getCurrentUser();

    @ViewById(R.id.weight_log_current)      TextView currentWeightTextView;
    @ViewById(R.id.weight_log_target)       TextView targetWeightTextView;
    @ViewById(R.id.weight_log_bmi)          TextView bmiTextView;
    @ViewById(R.id.weight_log_status)       TextView weightStatusTextView;
    @ViewById(R.id.weight_log_min_weight)   TextView minWeightTextView;
    @ViewById(R.id.weight_log_max_weight)   TextView maxWeightTextView;
    @ViewById(R.id.weight_log_last_7_days)  TextView last7DaysTextView;
    @ViewById(R.id.weight_log_last_30_days) TextView last30DaysTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @AfterViews
    void onSetupLayoutView() {
        data = new ArrayList<GraphViewData>();

        for (WeightLog weightLog : user.getDiet().getWeightLogs()) {
            weightLogs.put(dateFormat.format(weightLog.getDate()), weightLog);
            data.add(new GraphViewData(weightLog.getDate().getTime(), weightLog.getWeight()));
        }

        if (data.isEmpty()) {
            Calendar yesterday = Calendar.getInstance();
            yesterday.add(Calendar.DATE, -1);
            double currentWeight = user.getPersonalInfo().getWeight();

            WeightLog yesterdayWeightLog = new WeightLog(currentWeight).setDate(yesterday.getTime());
            yesterdayWeightLog.save();

            data.add(new GraphViewData(yesterdayWeightLog.getDate().getTime(), yesterdayWeightLog.getWeight()));
        }

        Collections.sort(data, GraphViewDataComparator.X_AXIS_SORT);
        weightSeries = new GraphViewSeries("Current", null, data.toArray(new GraphViewDataInterface[data.size()]));
        weightSeries.getStyle().thickness = 6;

        double targetWeight = user.getDiet().getTargetWeight();
        targetData = new GraphViewData[2];
        targetData[0] = new GraphViewData(data.get(0).getX(), targetWeight);
        targetData[1] = new GraphViewData(data.get(data.size()-1).getX(), targetWeight);
        targetSeries = new GraphViewSeries("Target", null, targetData);
        targetSeries.getStyle().color = getResources().getColor(R.color.my_app_green);
        targetSeries.getStyle().thickness = 2;

        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        graphView = new LineGraphView(getActivity(), "");
        graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
            @Override
            public String formatLabel(double v, boolean xAxis) {
                if(xAxis) {
                    return dateFormat.format(new Date((long) v));
                }
                return null;
            }
        });

        graphView.addSeries(weightSeries);
        graphView.addSeries(targetSeries);
        graphView.setShowLegend(true);
        graphView.getGraphViewStyle().setLegendWidth(200);
        graphView.setLegendAlign(GraphView.LegendAlign.TOP);
        graphView.setManualYAxis(true);
        graphView.setDrawDataPoints(true);
        graphView.setDataPointsRadius(8f);
        graphView.getGraphViewStyle().setNumHorizontalLabels(6);
        graphView.getGraphViewStyle().setNumVerticalLabels(5);
        graphView.getGraphViewStyle().setGridColor(Color.rgb(200, 200, 200));
        int lightGray = getResources().getColor(R.color.gray);
        graphView.getGraphViewStyle().setHorizontalLabelsColor(lightGray);
        graphView.getGraphViewStyle().setVerticalLabelsColor(lightGray);

        if (data.size() != 0) {
            graphView.setManualYAxisBounds(getYMax(data) + 25, getYMin(data) - 25);
        } else {
            graphView.setManualYAxisBounds(100, 0);
        }

        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.weight_log_graph);
        layout.addView(graphView);

        setupStatisticsViews();
    }

    private void setupStatisticsViews() {
        double feet = user.getPersonalInfo().getHeight() / 12;
        double currentWeight = data.get(data.size() - 1).getY();
        double maxWeight = getYMax(data);
        double minWeight = getYMin(data);
        double bmi = (currentWeight * 4.88) / (feet * feet);
        double targetWeight = user.getDiet().getTargetWeight();
        String weightStatus = "";

        if (bmi < 18.5) {
            weightStatus = "Underweight";
        } else if (bmi <= 25) {
            weightStatus = "Normal";
        } else if (bmi <= 30) {
            weightStatus = "Overweight";
        } else if (bmi > 30) {
            weightStatus = "Obese";
        }

        if (targetWeight > 0) {
            targetWeightTextView.setText(String.valueOf(targetWeight));
        }

        currentWeightTextView.setText(String.valueOf(currentWeight));
        bmiTextView.setText(String.format("%.2f", bmi));
        weightStatusTextView.setText(weightStatus);
        maxWeightTextView.setText(String.valueOf(maxWeight));
        minWeightTextView.setText(String.valueOf(minWeight));
        last7DaysTextView.setText(String.valueOf(currentWeight - getWeightDaysAgo(7)));
        last30DaysTextView.setText(String.valueOf(currentWeight - getWeightDaysAgo(30)));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.overflow_menu, menu);
    }

    @Click(R.id.weight_log_screen_log_btn)
    void onClickLogBtn() {
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

    @Click(R.id.weight_log_screen_target_btn)
    void onClickTargetBtn() {
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
                .setTitle("Enter Target Weight")

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isValid[0]) {
                            user.getDiet().setTargetWeight(Double.parseDouble(formEditText.getText().toString()));
                            user.getDiet().save();
                            targetWeightTextView.setText(String.valueOf(formEditText.getText().toString()));
                            updateTargetGraphLine();
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

    private void updateTargetGraphLine() {
        double targetWeight = user.getDiet().getTargetWeight();
        targetData[0] = new GraphViewData(data.get(0).getX(), targetWeight);
        targetData[1] = new GraphViewData(data.get(data.size()-1).getX(), targetWeight);
        targetSeries.resetData(targetData);
    }

    private double getWeightDaysAgo(int days) {
        Calendar thatDate = Calendar.getInstance();
        thatDate.add(Calendar.DATE, -1*days - 1);
        Date farthestDate = new Date();

        double weight = 0;
        for (WeightLog weightLog : weightLogs.values()) {
            if (weightLog.getDate().after(thatDate.getTime()) && weightLog.getDate().before(farthestDate)) {
                weight = weightLog.getWeight();
                farthestDate = weightLog.getDate();
            }
        }

        return weight;
    }

    private double getYMin(List<GraphViewData> data) {
        return Collections.min(data, GraphViewDataComparator.Y_AXIS_SORT).getY();
    }

    private double getYMax(List<GraphViewData> data) {
        return Collections.max(data, GraphViewDataComparator.Y_AXIS_SORT).getY();
    }

    private void addLog(WeightLog log) {
        String key = dateFormat.format(log.getDate());

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
        weightSeries.resetData(data.toArray(new GraphViewDataInterface[data.size()]));
        graphView.setManualYAxisBounds(getYMax(data) + 25, getYMin(data) - 25);

        updateTargetGraphLine();

        // update the stats
        setupStatisticsViews();
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
                .setTitle("Record Weight")

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isValid[0]) {
                            addLog(new WeightLog(Double.parseDouble(formEditText.getText().toString())).setDate(date));
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

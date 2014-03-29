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

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.cse3310.phms.R;
import com.cse3310.phms.model.User;
import com.cse3310.phms.model.WeightLog;
import com.cse3310.phms.model.utils.DateFormat;
import com.cse3310.phms.ui.utils.Comparators.GraphViewDataComparator;
import com.cse3310.phms.ui.utils.UserSingleton;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import java.util.*;

import static com.jjoe64.graphview.GraphView.GraphViewData;

@EFragment(R.layout.weight_log_screen)
public class WeightLogScreenFragment extends SherlockFragment{
    private GraphViewSeries graphViewSeries;
    private LineGraphView graphView;
    private List<GraphViewData> data;
    private Map<String, WeightLog> weightLogs = new LinkedHashMap<String, WeightLog>();

    @AfterViews
    void onAfterViews() {
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
        graphView.setManualYAxisBounds(getYMax(data) + 50, getYMin(data) - 50);
        graphView.addSeries(graphViewSeries);
        graphView.setDrawDataPoints(true);
        graphView.setDataPointsRadius(15f);
        graphView.getGraphViewStyle().setNumHorizontalLabels(6);
        graphView.getGraphViewStyle().setNumVerticalLabels(6);
        graphView.setDrawBackground(true);
        graphView.setBackgroundColor(Color.argb(100, 102, 255, 102));

        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.weight_log_graph);
        layout.addView(graphView);
    }

    @Click(R.id.button1)
    void onClick() {
        GregorianCalendar calendar = new GregorianCalendar(2014, Calendar.APRIL, 2);
        addLog(new WeightLog(400).setDate(calendar.getTime()));
        Toast.makeText(getActivity(), "log", Toast.LENGTH_SHORT).show();
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
        graphView.setManualYAxisBounds(getYMax(data) + 50, getYMin(data) - 50);
    }
}

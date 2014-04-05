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

package com.cse3310.phms.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.cse3310.phms.ui.utils.DatabaseHandler;

import java.util.List;

@Table(name = "Diet")
public class Diet extends Model{
    @Column private double targetWeight;

    /**
     * Add a new weightLog object.
     *
     * @param weight the weight in lbs
     */
    public void addWeightLog(double weight) {
        WeightLog weightLog = new WeightLog(weight);
        weightLog.save();
    }

    public void addWeightLog(WeightLog weightLog) {
        weightLog.save();
    }

    /**
     * Gets weightLogs.
     *
     * @return list of weight logs
     */
    public List<WeightLog> getWeightLogs() {
        return getMany(WeightLog.class, "user");
    }

    /**
     * Gets foods.
     *
     * @return list of foods
     */
    public List<Food> getFoods() {
        return DatabaseHandler.getAllById(Food.class, "diet", getId());
    }

    /**
     * Add food.
     *
     * @param food the food object be added
     */
    public void addFood(Food food) {
        food.save();
    }

    /**
     * Remove food.
     *
     * @param food the food object be removed
     */
    public void removeFood(Food food) {
        new Delete().from(Food.class).where("Id = ?", food.getId()).execute();
    }

    /**
     * Gets target weight.
     *
     * @return the target weight
     */
    public double getTargetWeight() {
        return targetWeight;
    }

    /**
     * Sets target weight.
     *
     * @param targetWeight the target weight
     */
    public void setTargetWeight(double targetWeight) {
        this.targetWeight = targetWeight;
    }
}

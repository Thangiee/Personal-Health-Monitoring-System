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
import com.cse3310.phms.model.utils.ManyToManyTable;
import com.cse3310.phms.ui.utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

import static com.cse3310.phms.model.utils.ManyToManyTable.DietAndFood;

@Table(name = "Diet")
public class Diet extends Model{
    @Column private String decription;

    /**
     * Add a new weightLog object.
     *
     * @param weight the weight in lbs
     */
    public void addWeightLog(double weight) {
        WeightLog weightLog = new WeightLog(weight);
        weightLog.save();
    }

    /**
     * Gets weightLogs.
     *
     * @return list of weight logs
     */
    public List<WeightLog> getWeightLogs() {
        return getMany(WeightLog.class, "User");
    }

    /**
     * Gets foods.
     *
     * @return list of foods
     */
    public List<Food> getFoods(long userId) {
        List<Food> foodList = new ArrayList<Food>();
        List<DietAndFood> dietAndFoodList = DatabaseHandler.getAllRows(DietAndFood.class);

        // Poor performance?
        for (DietAndFood dietAndFood : dietAndFoodList) {
            if (dietAndFood.getDietId() == userId) {
                foodList.addAll(DatabaseHandler.getAllById(Food.class, dietAndFood.getFoodId()));
            }
        }
        return foodList;
    }

    /**
     * Add food.
     *
     * @param food the food object be added
     */
    public void addFood(Food food) {
        DietAndFood dietFood = new DietAndFood(this, food);
        dietFood.save();
    }

    /**
     * Remove food.
     *
     * @param food the food object be removed
     * @return true if the food is successfully remove, else return false.
     */
    public boolean removeFood(Food food) {
        List<ManyToManyTable.DietAndFood> dietAndFoodList = DatabaseHandler.getAllRows(DietAndFood.class);
        for (DietAndFood dietAndFood : dietAndFoodList) {
            if (dietAndFood.getFoodId() == food.getId() && dietAndFood.getDietId() == this.getId()) {
                DietAndFood.delete(DietAndFood.class, dietAndFood.getId());
                return true;
            }
        }
        return false;
    }
}

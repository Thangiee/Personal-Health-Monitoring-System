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

package com.cse3310.phms.ui.utils;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.cse3310.phms.model.Diet;
import com.cse3310.phms.model.Food;
import com.cse3310.phms.model.User;
import com.cse3310.phms.model.utils.ManyToManyTable;

import java.util.List;

public class DatabaseHandler {

    // restrict instantiation of this class.
    private DatabaseHandler() {}

    public static <T extends Model> List<T> getAllRows(Class<T> table) {
        return new Select().all().from(table).execute();
    }

    public static <T extends Model> List<T> getAllById(Class<T> table, String columnTitle, long id) {
        return new Select().all().from(table).where(columnTitle + " = ?", id).execute();
    }

    public static <T extends Model> T getById(Class<T> table, String columnTitle, long id) {
        return new Select().all().from(table).where(columnTitle + " = ?", id).executeSingle();
    }

    public static User getUserByUserName(final String userName) {
        return new Select().from(User.class).where("UserName = ?", userName).executeSingle();
    }

    //TODO: buggy, return wrong id
    public static List<Food> getFoodsByDiet(Diet diet) {
        return new Select().all().from(Food.class).innerJoin(ManyToManyTable.DietAndFood.class)
                .on("DietAndFood.Food = Food.id").where("DietAndFood.Diet = ?", diet.getId()).execute();
    }
}

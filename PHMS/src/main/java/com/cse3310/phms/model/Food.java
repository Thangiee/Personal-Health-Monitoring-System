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
import com.cse3310.phms.ui.utils.UserSingleton;

@Table(name = "Food")
public class Food extends Model{

    @Column private String name;
    @Column private double calories;
    @Column private double numOfServings;
    @Column private double protein;
    @Column private double fat;
    @Column private double fiber;
    @Column private double sugars;
    @Column private String brand;
    @Column private double time;
    @Column private Diet diet;

    /**
     * Instantiates a new Food.
     */
    public Food() {
        super();
        numOfServings = 1;
        diet = UserSingleton.INSTANCE.getCurrentUser().getDiet();
    }

    /**
     * Instantiates a new Food.
     *
     * @param name the name
     */
    public Food(String name) {
        this.name = name;
        numOfServings = 1;
        diet = UserSingleton.INSTANCE.getCurrentUser().getDiet();
    }

    /**
     * Instantiates a copy Food.
     *
     * @param thatFood food to be copy
     */
    public Food(Food thatFood) {
        name = thatFood.getName();
        calories = thatFood.getCalories();
        numOfServings = thatFood.getNumOfServings();
        protein = thatFood.getProtein();
        fat = thatFood.getFat();
        fiber = thatFood.getFiber();
        sugars = thatFood.getSugars();
        brand = thatFood.getBrand();
        diet = thatFood.getDiet();
        time = thatFood.getTime();
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets calories in kcal.
     *
     * @return the calories
     */
    public double getCalories() {
        return calories;
    }

    /**
     * Sets calories in kcal.
     *
     * @param calories the calories
     * @return the calories
     */
    public Food setCalories(double calories) {
        this.calories = calories;
        return this;
    }

    /**
     * Gets sugars in grams.
     *
     * @return the sugars
     */
    public double getSugars() {
        return sugars;
    }

    /**
     * Sets sugars in grams.
     *
     * @param sugars the sugars
     * @return the sugars
     */
    public Food setSugars(double sugars) {
        this.sugars = sugars;
        return this;
    }

    /**
     * Gets serving size.
     *
     * @return the serving size
     */
    public double getNumOfServings() {
        return numOfServings;
    }

    /**
     * Sets serving size.
     *
     * @param numOfServings the serving size
     * @return the serving size
     */
    public Food setNumOfServings(double numOfServings) {
        this.numOfServings = numOfServings;
        return this;
    }

    /**
     * Gets fiber in grams.
     *
     * @return the fiber
     */
    public double getFiber() {
        return fiber;
    }

    /**
     * Sets fiber in grams.
     *
     * @param fiber the fiber
     * @return the fiber
     */
    public Food setFiber(double fiber) {
        this.fiber = fiber;
        return this;
    }

    /**
     * Gets fat in grams.
     *
     * @return the fat
     */
    public double getFat() {
        return fat;
    }

    /**
     * Sets fat in grams.
     *
     * @param fat the fat
     * @return the fat
     */
    public Food setFat(double fat) {
        this.fat = fat;
        return this;
    }

    /**
     * Gets protein in grams.
     *
     * @return the protein
     */
    public double getProtein() {
        return protein;
    }

    /**
     * Sets protein in grams.
     *
     * @param protein the protein
     * @return the protein
     */
    public Food setProtein(double protein) {
        this.protein = protein;
        return this;
    }

    /**
     * Gets brand.
     *
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets brand.
     *
     * @param brand the brand
     * @return the brand
     */
    public Food setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    /**
     * Gets time.
     *
     * @return the time time in milliseconds since epoch
     */
    public double getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time in milliseconds since epoch
     */
    public Food setTime(double time) {
        this.time = time;
        return this;
    }


    public Diet getDiet() {
        return diet;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", calories=" + calories +
                ", numOfServings=" + numOfServings +
                ", protein=" + protein +
                ", fat=" + fat +
                ", fiber=" + fiber +
                ", sugars=" + sugars +
                '}';
    }
}

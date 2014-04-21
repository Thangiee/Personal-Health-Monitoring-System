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
import com.cse3310.phms.ui.utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

@Table(name = "User")
public class User extends Model {

    @Column private String username;
    @Column private String hashedPassword;
    @Column private PersonalInfo personalInfo;
    @Column private Diet diet;

    /**
     * Instantiates a new User.
     */
    public User() {
        super();
    }

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param hashedPassword the hashed password
     */
    public User(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        diet = new Diet();
        diet.save();
    }

    /**
     * Gets diet.
     *
     * @return the diet
     */
    public Diet getDiet() {
        return diet;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     * @return the username
     */
    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Gets hashed password.
     *
     * @return the hashed password
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Sets hashed password.
     *
     * @param hashedPassword the hashed password
     * @return the hashed password
     */
    public User setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
        return this;
    }

    /**
     * Gets personal info.
     *
     * @return the personal info
     */
    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    /**
     * Sets personal info.
     *
     * @param personalInfo the personal info
     * @return the personal info
     */
    public User setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
        return this;
    }

    public List<DoctorInfo> getDoctors() {
        return DatabaseHandler.getAllById(DoctorInfo.class, "user", this.getId());
    }

    public List<Info> getContacts() {
        return DatabaseHandler.getAllById(Info.class, "user", this.getId());
    }

    public List<Appointment> getAppointments() {
        return DatabaseHandler.getAllById(Appointment.class, "user", this.getId());
    }

    public List<EStorage> getEStorage() {
        return DatabaseHandler.getAllById(EStorage.class, "user", this.getId());
    }

    public List<Health> getHealth() {
        return DatabaseHandler.getAllById(Health.class, "user", this.getId());
    }

    public List<Recipe> getRecipe() {
        return DatabaseHandler.getAllById(Recipe.class, "user", this.getId());
    }

    public List<DietDesc> getDietDesc() {
        return DatabaseHandler.getAllById(DietDesc.class, "user", this.getId());
    }

    public List<Remindable> getReminders() {
        List<Remindable> remindableList = new ArrayList<Remindable>(getAppointments());
        return remindableList;
    }
}

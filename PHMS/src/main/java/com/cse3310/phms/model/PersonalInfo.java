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

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "PersonalInfo")
public class PersonalInfo extends Info{

    public enum Gender {MALE, FEMALE}

    @Column(name = "Gender")
    private String gender;
    @Column(name = "Age")
    private int age;

    public PersonalInfo() {
        super();
    }

    public PersonalInfo(String firstName, String lastName, String email, String phone, Gender gender) {
        super(firstName, lastName, email, phone);
        this.gender = gender.toString();
    }

    public static void main(String[] args) {
    }
}

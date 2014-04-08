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

@Table(name = "Doctor")
public class Doctor extends Model {
    @Column private String hospital;
    @Column private String address;
    @Column private Info contactInfo;

    /**
     * Instantiates a new Doctor.
     */
    public Doctor() {}

    /**
     * Instantiates a new Doctor.
     *
     * @param hospital the hospital
     * @param address the address
     * @param contactInfo the contact info
     */
    public Doctor(String hospital, String address, Info contactInfo) {
        this.hospital = hospital;
        this.address = address;
        this.contactInfo = contactInfo;
    }

    /**
     * Gets hospital name.
     *
     * @return the hospital name
     */
    public String getHospital() {
        return hospital;
    }

    /**
     * Sets hospital name.
     *
     * @param hospital the hospital name
     * @return the this doctor object
     */
    public Doctor setHospital(String hospital) {
        this.hospital = hospital;
        return this;
    }

    /**
     * Gets address of the hospital.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address of the hospital.
     *
     * @param address the address
     * @return the this doctor object
     */
    public Doctor setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * Gets contact info.
     *
     * @return the contact info
     */
    public Info getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets contact info.
     *
     * @param contactInfo the contact info
     * @return the this doctor object
     */
    public Doctor setContactInfo(Info contactInfo) {
        this.contactInfo = contactInfo;
        return this;
    }
}

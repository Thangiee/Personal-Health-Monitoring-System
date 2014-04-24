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
import com.cse3310.phms.R;
import com.cse3310.phms.ui.utils.UserSingleton;

@Table(name = "DoctorInfo")
public class DoctorInfo extends Info {
    @Column private String hospital;
    @Column private String address;
    @Column private int photoId;

    /**
     * Default constructor is required for ActiveAndroid
     */
    public DoctorInfo() {
        super();
    }

    /**
     * Instantiates a new DoctorInfo.
     *
     * @param hospital the hospital
     * @param address the address
     */
    public DoctorInfo(String hospital, String address) {
        this.hospital = hospital;
        this.address = address;
        photoId = R.drawable.ic_action_user;
        super.user = UserSingleton.INSTANCE.getCurrentUser();
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
    public DoctorInfo setHospital(String hospital) {
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
    public DoctorInfo setAddress(String address) {
        this.address = address;
        return this;
    }

    public DoctorInfo setPhotoId(int photoId) {
        this.photoId = photoId;
        return this;
    }

    public int getPhotoId() {
        return photoId;
    }
}

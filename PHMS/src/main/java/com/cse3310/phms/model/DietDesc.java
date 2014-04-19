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

import com.activeandroid.annotation.Table;

/**
 * Created by Owner on 4/17/14.
 */
@Table(name = "DietDesc")
public class DietDesc extends EStorage {


    public DietDesc()
    {
       // super();
       // user = UserSingleton.INSTANCE.getCurrentUser();
    }

   /* public DietDesc(String url, String title)
    {
        this.url = url;
        this.title = title;
        super.user = UserSingleton.INSTANCE.getCurrentUser();
    }

    public String getTitle() {
        return title;
    }

    public DietDesc setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public DietDesc setUrl(String url) {
        this.url = url;
        return this;
    }
    @Override
    public String toString()
    {
        String temp = "Title-" + title + "," + url;
        return temp;
    }*/
}

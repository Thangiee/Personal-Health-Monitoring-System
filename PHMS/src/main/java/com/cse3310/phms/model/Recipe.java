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

@Table(name = "Recipe")
public class Recipe extends EStorage{

    public Recipe()
    {
        //super();
       // user = UserSingleton.INSTANCE.getCurrentUser();
    }
/*
    public Recipe(String url, String title)
    {
        this.url = url;
        this.title = title;
        super.user = UserSingleton.INSTANCE.getCurrentUser();
    }

    public Recipe(EStorage thatUrl)
    {
        this.url = thatUrl.getUrl();
        this.title = thatUrl.getTitle();
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public EStorage setTitle(String title) {
        this.title = title;
        return this;
    }

    public EStorage setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString()
    {
        String temp = "Title-" + title + "," + url;
        return temp;
    }
*/

}

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
@Table(name = "Health")
public class Health extends EStorage {
/*
    @Column(name = "Title")
    private String title;
    @Column (name = "WebUrl")
    private String url;*/
    private static int Tab1_type = 0;
    private static int Tab2_type = 1;
    private static int Tab3_type = 2;
   // @Column User user;

    public Health()
    {
      //  super();
       // user = UserSingleton.INSTANCE.getCurrentUser();
    }
    public Health(EStorage eStorage)
    {
        super.setTitle(eStorage.getTitle());
        super.setUrl(eStorage.getUrl());
    }
/*
    public Health(String url, String title)
    {
        this.url = url;
        this.title = title;
        super.user = UserSingleton.INSTANCE.getCurrentUser();
    }

    public Health(Health thatUrl)
    {
        this.url = thatUrl.getUrl();
        this.title = thatUrl.getTitle();
    }
    public String getTitle() {return title;}
    public String getUrl(){return url;}

    public Health setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public Health setUrl(String url)
    {
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

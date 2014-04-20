package com.cse3310.phms.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.cse3310.phms.ui.utils.UserSingleton;

/**
 * Created by Owner on 4/6/14.
 */

@Table(name = "EStorage")
public class EStorage extends Model {

    @Column(name = "Title")
    private String title;
    @Column (name = "WebUrl")
    private String url;
    private static int Tab1_type = 0;
    private static int Tab2_type = 1;
    private static int Tab3_type = 2;
    @Column User user;


    public EStorage()
    {
        super();
        user = UserSingleton.INSTANCE.getCurrentUser();
    }

    public EStorage(String url, String title)
    {
        this.url = url;
        this.title = title;
        user = UserSingleton.INSTANCE.getCurrentUser();
    }

    public EStorage(EStorage thatUrl)
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
        String temp = "EStorage{" + "url='"+ url + '\''+
                ", title='" + title + '\'' + '}';
        return temp;
    }
}

package com.aluxian.loremsql.models;

import android.widget.ListView;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ToDoItem extends RealmObject {

    private String title;
    private String description;

    public String getTitle() {
        return title;


    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

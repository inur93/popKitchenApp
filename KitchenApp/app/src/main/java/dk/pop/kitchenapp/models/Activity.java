package dk.pop.kitchenapp.models;

import java.util.Date;

/**
 * Created by dickow on 9/21/16.
 */

public abstract class Activity {
    private String title;
    private String description;
    private Date date;
    private Kitchen kitchen;

    public Activity(Kitchen kitchen){
        this.kitchen = kitchen;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }
}

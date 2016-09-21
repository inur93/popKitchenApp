package dk.pop.kitchenapp.models;

import java.util.ArrayList;

/**
 * Created by dickow on 9/21/16.
 */

public class Kitchen {
    private String name;
    private ArrayList<Activity> activities;

    public Kitchen(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }
}

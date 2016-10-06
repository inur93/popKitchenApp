package dk.pop.kitchenapp.models;

import java.util.ArrayList;

/**
 * Created by dickow on 9/21/16.
 */

public class Kitchen {
    private String name;
    private ArrayList<GroupActivity> activities;

    public Kitchen(){
        this.activities = new ArrayList<>();
    }

    public Kitchen(String name){
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<GroupActivity> getActivities() {
        return activities;
    }
}

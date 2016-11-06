package dk.pop.kitchenapp.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dickow on 9/21/16.
 */

public class Person implements Serializable{
    private String googleId;
    private String displayName;
    private boolean active;
    private int roomNumber;

    private HashMap<String, Kitchen> kitchens;
    private HashMap<String, GroupActivity> activities;

    public Person(){
        this.kitchens = new HashMap<>();
        this.activities = new HashMap<>();
    }

    public Person(String googleId, String displayName, boolean active){
        this();
        this.googleId = googleId;
        this.displayName = displayName;
        this.active = active;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getDisplayName(){return this.displayName;}

    public void setDisplayName(String displayName){this.displayName = displayName;}

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public ArrayList<Kitchen> getKitchensList() {
        ArrayList<Kitchen> listKitchens = new ArrayList<>();
        for (Kitchen kitch :
                this.kitchens.values()) {
                listKitchens.add(kitch);
        }
        return listKitchens;
    }

    public HashMap<String, Kitchen> getKitchens(){return this.kitchens;}

    public void setKitchens(HashMap<String, Kitchen> kitchens) {
        this.kitchens = kitchens;
    }

    public void setActivities(HashMap<String, GroupActivity> activities) { this.activities = activities;}

    public HashMap<String, GroupActivity> getActivities(){return this.activities;}

    public ArrayList<GroupActivity> getActivitiesList(){
        ArrayList<GroupActivity> listActivities = new ArrayList<>();
        for (GroupActivity act :
                this.activities.values()) {
            listActivities.add(act);
        }
        return listActivities;
    }
}

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

    private HashMap<String, String> kitchens;
    private HashMap<String, String> activities;

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

    public HashMap<String, String> getKitchens(){return this.kitchens;}

    public void setKitchens(HashMap<String, String> kitchens) {
        this.kitchens = kitchens;
    }

    public void setActivities(HashMap<String, String> activities) { this.activities = activities;}

    public HashMap<String, String> getActivities(){return this.activities;}
}

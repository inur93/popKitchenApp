package dk.pop.kitchenapp.models;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by dickow on 9/21/16.
 */

public class Kitchen implements Serializable{
    private String name;
    private HashMap<String, String> activities;
    private HashMap<String, String> persons;
    private HashMap<String, String> admins;
    private HashMap<String, String> pendingRequests;

    public HashMap<String, String> getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(HashMap<String, String> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, String> getActivities() {
        return activities;
    }

    public void setActivities(HashMap<String, String> activities) {
        this.activities = activities;
    }

    public HashMap<String, String> getPersons() {
        return persons;
    }

    public void setPersons(HashMap<String, String> persons) {
        this.persons = persons;
    }

    public HashMap<String, String> getAdmins() {
        return admins;
    }

    public void setAdmins(HashMap<String, String> admins) {
        this.admins = admins;
    }

    public Kitchen(){
    }

    public Kitchen(String name){
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }


}

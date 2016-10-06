package dk.pop.kitchenapp.data;

import java.util.ArrayList;

import dk.pop.kitchenapp.models.Activity;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;

/**
 * Created by dickow on 10/2/16.
 */
public class DataStorage {
    private static DataStorage ourInstance = new DataStorage();

    private Kitchen kitchen;
    private Person person;
    private ArrayList<Activity> kitchenActivities;
    private ArrayList<Activity> personalActivities;

    public static DataStorage getInstance() {
        return ourInstance;
    }

    private DataStorage() {
    }

    public static DataStorage getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(DataStorage ourInstance) {
        DataStorage.ourInstance = ourInstance;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ArrayList<Activity> getKitchenActivities() {
        return kitchenActivities;
    }

    public void setKitchenActivities(ArrayList<Activity> kitchenActivities) {
        this.kitchenActivities = kitchenActivities;
    }

    public ArrayList<Activity> getPersonalActivities() {
        return personalActivities;
    }

    public void setPersonalActivities(ArrayList<Activity> personalActivities) {
        this.personalActivities = personalActivities;
    }
}

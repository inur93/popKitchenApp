package dk.pop.kitchenapp.models;

import java.util.ArrayList;

/**
 * Created by dickow on 9/21/16.
 */

public class Person {
    private String googleId;
    private boolean active;
    private int roomNumber;
    private ArrayList<Kitchen> kitchens;
    private ArrayList<DinnerGroupActivity> dinnerActivities;
    private ArrayList<ExpenseGroupActivity> expenseActivities;
    private ArrayList<CleaningGroupActivity> cleaningActivities;

    public Person(){
        this.kitchens = new ArrayList<Kitchen>();
        this.dinnerActivities = new ArrayList<DinnerGroupActivity>();
        this.expenseActivities = new ArrayList<ExpenseGroupActivity>();
        this.cleaningActivities = new ArrayList<CleaningGroupActivity>();
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

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

    public ArrayList<Kitchen> getKitchens() {
        return kitchens;
    }

    public void setKitchens(ArrayList<Kitchen> kitchens) {
        this.kitchens = kitchens;
    }

    public ArrayList<DinnerGroupActivity> getDinnerActivities() {
        return dinnerActivities;
    }

    public void setDinnerActivities(ArrayList<DinnerGroupActivity> dinnerActivities) {
        this.dinnerActivities = dinnerActivities;
    }

    public ArrayList<ExpenseGroupActivity> getExpenseActivities() {
        return expenseActivities;
    }

    public void setExpenseActivities(ArrayList<ExpenseGroupActivity> expenseActivities) {
        this.expenseActivities = expenseActivities;
    }

    public ArrayList<CleaningGroupActivity> getCleaningActivities() {
        return cleaningActivities;
    }

    public void setCleaningActivities(ArrayList<CleaningGroupActivity> cleaningActivities) {
        this.cleaningActivities = cleaningActivities;
    }
}

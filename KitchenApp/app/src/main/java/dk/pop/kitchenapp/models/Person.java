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
    private ArrayList<DinnerActivity> dinnerActivities;
    private ArrayList<ExpenseActivity> expenseActivities;
    private ArrayList<CleaningActivity> cleaningActivities;

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

    public ArrayList<DinnerActivity> getDinnerActivities() {
        return dinnerActivities;
    }

    public void setDinnerActivities(ArrayList<DinnerActivity> dinnerActivities) {
        this.dinnerActivities = dinnerActivities;
    }

    public ArrayList<ExpenseActivity> getExpenseActivities() {
        return expenseActivities;
    }

    public void setExpenseActivities(ArrayList<ExpenseActivity> expenseActivities) {
        this.expenseActivities = expenseActivities;
    }

    public ArrayList<CleaningActivity> getCleaningActivities() {
        return cleaningActivities;
    }

    public void setCleaningActivities(ArrayList<CleaningActivity> cleaningActivities) {
        this.cleaningActivities = cleaningActivities;
    }
}

package dk.pop.kitchenapp.models;

import java.util.ArrayList;

/**
 * Created by dickow on 9/21/16.
 */

public class DinnerActivity extends PlannableActivity {
    private float total;
    private ArrayList<ExpenseActivity> expenses;
    private ArrayList<Person> participants;

    public DinnerActivity(ArrayList<ExpenseActivity> expenses, Kitchen kitchen) throws IllegalArgumentException {
        super(kitchen);

        if(expenses == null || expenses.isEmpty()){
            throw new IllegalArgumentException("expenses was empty, you must provide at least one expense");
        }

        this.expenses = expenses;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public ArrayList<ExpenseActivity> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<ExpenseActivity> expenses) {
        this.expenses = expenses;
    }

    public ArrayList<Person> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Person> participants) {
        this.participants = participants;
    }
}

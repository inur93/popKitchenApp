package dk.pop.kitchenapp.models;

import java.util.ArrayList;
import java.util.UUID;

import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;

/**
 * Created by dickow on 9/21/16.
 */

public class DinnerGroupActivity extends PlannableGroupActivity {
    private float total;
    private ArrayList<ExpenseGroupActivity> expenses;
    private ArrayList<Person> participants;

    public DinnerGroupActivity(ArrayList<ExpenseGroupActivity> expenses, Kitchen kitchen, Person createdBy, UUID id) throws IllegalArgumentException {
        super(createdBy, kitchen, ObjectTypeEnum.DINNERACTIVITY, id);

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

    public ArrayList<ExpenseGroupActivity> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<ExpenseGroupActivity> expenses) {
        this.expenses = expenses;
    }

    public ArrayList<Person> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Person> participants) {
        this.participants = participants;
    }
}

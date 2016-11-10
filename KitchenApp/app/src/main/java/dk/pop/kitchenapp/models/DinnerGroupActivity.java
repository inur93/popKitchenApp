package dk.pop.kitchenapp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;

/**
 * Created by dickow on 9/21/16.
 */

public class DinnerGroupActivity extends PlannableGroupActivity {
    private float total;
    private HashMap<String, String> expenses = new HashMap<>();
    private HashMap<String, String> participants = new HashMap<>();

    public DinnerGroupActivity(
            UUID id,
            String title,
            String description,
            Date date,
            Kitchen kitchen,
            Person createdBy,
            boolean isCancellable,
            float total,
            List<ExpenseGroupActivity> expenses,
            List<Person> participants
            ) throws IllegalArgumentException {
        super(id, title, description, date, kitchen, createdBy, ObjectTypeEnum.DINNERACTIVITY, isCancellable);


        /*if(expenses == null || expenses.isEmpty()){
            throw new IllegalArgumentException("expenses was empty, you must provide at least one expense");
        }*/
        this.total = total;
        if(expenses != null) {
            for (ExpenseGroupActivity e : expenses) {
                this.expenses.put(e.getId(), e.getId());
            }
        }
        if(participants != null) {
            for (Person p : participants) {
                this.participants.put(p.getGoogleId(), p.getGoogleId());
            }
        }
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public HashMap<String, String> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<ExpenseGroupActivity> expenses) {
        this.expenses.clear();
        for(ExpenseGroupActivity e : expenses){
            this.expenses.put(e.getId(), e.getId());
        }
    }

    public HashMap<String, String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Person> participants) {
        this.participants.clear();
        for(Person p : participants){
            this.participants.put(p.getGoogleId(), p.getGoogleId());
        }
    }
}

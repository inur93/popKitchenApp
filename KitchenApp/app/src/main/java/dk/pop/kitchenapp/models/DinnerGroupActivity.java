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
    private List<ExpenseGroupActivity> expenses;
    private List<Person> participants;

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
        this.expenses = expenses;
        this.participants = participants;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<ExpenseGroupActivity> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<ExpenseGroupActivity> expenses) {
        this.expenses = expenses;
    }

    public List<Person> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Person> participants) {
        this.participants = participants;
    }

    public HashMap<String, Object> getValues(){
       HashMap<String, Object> vals = super.getValues();
        vals.put("total", total);

        if(this.expenses != null) {
            HashMap<String, Object> expenses = new HashMap<>();

            for (ExpenseGroupActivity e : this.expenses) {
                expenses.put(e.getId().toString(), e.getId().toString());
            }
            vals.put("expenses", expenses);
        }
        if(this.participants != null){
            HashMap<String, Object> participants = new HashMap<>();
            for(Person p : this.participants){
                participants.put(p.getGoogleId(), p.getGoogleId());
            }
            vals.put("participants", participants);
        }
        return vals;
    }
}

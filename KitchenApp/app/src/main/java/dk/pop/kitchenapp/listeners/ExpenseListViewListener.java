package dk.pop.kitchenapp.listeners;

import android.widget.ArrayAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.List;

import dk.pop.kitchenapp.models.ExpenseGroupActivity;

/**
 * Created by Runi on 01-12-2016.
 */

public class ExpenseListViewListener implements ChildEventListener {

    private List<ExpenseGroupActivity> expenses;
    private ArrayAdapter adapter;
    public ExpenseListViewListener(List<ExpenseGroupActivity> expenses, ArrayAdapter adapter){
        this.adapter = adapter;
        this.expenses = expenses;
    }

    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ExpenseGroupActivity expense = dataSnapshot.getValue(ExpenseGroupActivity.class);
        expenses.add(expense);
        adapter.notifyDataSetChanged();
    }

    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        ExpenseGroupActivity expense = dataSnapshot.getValue(ExpenseGroupActivity.class);

        int index = -1;
        for(int i = 0; i < expenses.size(); i++){
            ExpenseGroupActivity e = expenses.get(i);
            if(e.getId().equals(expense.getId())){
                index = i;
                break;
            }
        }
        if(index > -1){
            expenses.set(index, expense);
        }
        adapter.notifyDataSetChanged();

    }


    public void onChildRemoved(DataSnapshot dataSnapshot) {
        ExpenseGroupActivity expense = dataSnapshot.getValue(ExpenseGroupActivity.class);
        int index = -1;
        for(int i = 0; i < expenses.size(); i++){
            ExpenseGroupActivity e = expenses.get(i);
            if(e.getId().equals(expense.getId())){
                index = i;
                break;
            }
        }
        if(index > -1) expenses.remove(index);
        adapter.notifyDataSetChanged();
    }


    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }


    public void onCancelled(DatabaseError databaseError) {

    }
}

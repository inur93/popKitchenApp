package dk.pop.kitchenapp.listeners.groups;

import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import dk.pop.kitchenapp.adapters.KitchenListAdapter;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.listeners.FireBaseListListener;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;
import dk.pop.kitchenapp.utilities.view.SpinnerWrapper;

/**
 * Created by dickow on 12/11/16.
 */

public class MyGroupsListener implements AutoCloseable, FireBaseListListener<Kitchen, KitchenListAdapter> {
    private KitchenListAdapter adapter;
    private ArrayList<Kitchen> kitchens;
    private ChildEventListener listener;
    private SpinnerWrapper spinnerWrapper;
    private Person person;

    public MyGroupsListener(Person person, ProgressBar spinner){
        this.person = person;
        this.spinnerWrapper = new SpinnerWrapper(spinner);
        this.kitchens = new ArrayList<>();
        this.listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                spinnerWrapper.stopSpinner();
                kitchens.add(dataSnapshot.getValue(Kitchen.class));
                adapter.getFilter().filter("");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                kitchens.indexOf(dataSnapshot.getValue(Kitchen.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                kitchens.remove(dataSnapshot.getValue(Kitchen.class));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @Override
    public void start() {
        this.spinnerWrapper.startSpinner();
        DataManager.getInstance().getKitchensForPerson(person, listener);
    }

    @Override
    public void setAdapter(KitchenListAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public ArrayList<Kitchen> getItems() {
        return this.kitchens;
    }

    @Override
    public void close() throws Exception {
        this.kitchens.clear();
        this.spinnerWrapper.stopSpinner();
        DataManager.getInstance().detachKitchensForPerson(person, listener);
    }
}

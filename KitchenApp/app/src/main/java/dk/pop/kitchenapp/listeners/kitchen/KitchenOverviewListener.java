package dk.pop.kitchenapp.listeners.kitchen;

import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.listeners.FireBaseListListener;
import dk.pop.kitchenapp.models.GroupActivity;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.factories.ActivityFactory;
import dk.pop.kitchenapp.utilities.view.SpinnerWrapper;

/**
 * Created by dickow on 12/11/16.
 */

public class KitchenOverviewListener implements AutoCloseable, FireBaseListListener<GroupActivity> {
    private Kitchen kitchen;
    private SpinnerWrapper spinnerWrapper;
    private ChildEventListener listener;
    private ArrayList<GroupActivity> activities;
    private BaseAdapter adapter;

    public KitchenOverviewListener(Kitchen kitchen, ProgressBar spinner){
        this.kitchen = kitchen;
        this.spinnerWrapper = new SpinnerWrapper(spinner);
        this.activities = new ArrayList<>();
        this.listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                spinnerWrapper.stopSpinner();
                activities.add(ActivityFactory.CreateActivity(dataSnapshot));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                GroupActivity act = ActivityFactory.CreateActivity(dataSnapshot);
                for (int i = 0; i < activities.size(); i++){
                    if(activities.get(i).getId().equals(act.getId())){
                        activities.set(i, act);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                GroupActivity act = ActivityFactory.CreateActivity(dataSnapshot);
                for (int i = 0; i < activities.size(); i++){
                    if(activities.get(i).getId().equals(act.getId())){
                        activities.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
    }

    @Override
    public void start() {
        spinnerWrapper.startSpinner();
        DataManager.getInstance().getActivitiesForKitchen(kitchen, listener);
    }

    @Override
    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public ArrayList<GroupActivity> getItems() {
        return this.activities;
    }

    @Override
    public void close() throws Exception {
        DataManager.getInstance().detachActivitiesForKitchen(kitchen, listener);
        spinnerWrapper.stopSpinner();
        activities.clear();
    }
}

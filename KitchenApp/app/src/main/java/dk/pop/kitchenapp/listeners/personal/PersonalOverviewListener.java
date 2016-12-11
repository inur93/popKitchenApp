package dk.pop.kitchenapp.listeners.personal;

import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.listeners.FireBaseListListener;
import dk.pop.kitchenapp.models.GroupActivity;
import dk.pop.kitchenapp.models.Person;
import dk.pop.kitchenapp.models.factories.ActivityFactory;
import dk.pop.kitchenapp.utilities.view.SpinnerWrapper;

/**
 * Created by dickow on 12/11/16.
 */

public class PersonalOverviewListener implements AutoCloseable, FireBaseListListener<GroupActivity, BaseAdapter>{
    private Person person;
    private SpinnerWrapper spinnerWrapper;
    private ChildEventListener listener;
    private ArrayList<GroupActivity> activities;
    private BaseAdapter adapter;

    public PersonalOverviewListener(Person person, ProgressBar spinner){
        this.person = person;
        this.spinnerWrapper = new SpinnerWrapper(spinner);
        this.activities = new ArrayList<>();
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GroupActivity act = ActivityFactory.CreateActivity(dataSnapshot);
                // If the kitchen of the activity does not equal the current kitchen return
                if(!(act.getKitchen().equals(DataManager.getInstance().getCurrentKitchen().getName()))){
                    return;
                }

                for(int i = 0; i < activities.size(); i++){
                    // If activites already contains the activity return
                    if(activities.get(i).getId().equals(act.getId())){
                        return;
                    }
                }
                spinnerWrapper.stopSpinner();
                activities.add(act);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                GroupActivity act = ActivityFactory.CreateActivity(dataSnapshot);
                for(int i = 0; i < activities.size(); i++){
                    if(activities.get(i).getId().equals(act.getId())){
                        activities.set(i, act);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                GroupActivity act = ActivityFactory.CreateActivity(dataSnapshot);
                for(int i = 0; i < activities.size(); i++){
                    if(activities.get(i).getId().equals(act.getId())){
                        activities.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    public void start(){
        spinnerWrapper.startSpinner();
        DataManager.getInstance().getActivitiesForPerson(person, listener);
    }

    @Override
    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public ArrayList<GroupActivity> getItems() {
        return activities;
    }

    @Override
    public void close() throws Exception {
        DataManager.getInstance().detachActivitiesForPerson(person, listener);
        spinnerWrapper.stopSpinner();
        activities.clear();
    }
}

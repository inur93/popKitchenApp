package dk.pop.kitchenapp.listeners.groups;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import dk.pop.kitchenapp.adapters.KitchenListAdapter;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.listeners.FireBaseListListener;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.utilities.view.SpinnerWrapper;

/**
 * Created by dickow on 12/11/16.
 */

public class GroupCreationListener implements AutoCloseable, FireBaseListListener<Kitchen, KitchenListAdapter> {
    private KitchenListAdapter adapter;
    private ArrayList<Kitchen> kitchens;
    private SpinnerWrapper spinnerWrapper;
    private ChildEventListener listener;

    public GroupCreationListener(ProgressBar spinner){
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

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Kitchen removedKitchen = dataSnapshot.getValue(Kitchen.class);
                for(int i = 0; i < kitchens.size(); i++){
                    if(kitchens.get(i).getName().equals(removedKitchen.getName())){
                        kitchens.remove(i);
                    }
                }
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
        DataManager.getInstance().attachKitchenListener(this.listener);
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
        DataManager.getInstance().detachKitchenListener(this.listener);
    }
}

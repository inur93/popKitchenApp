package dk.pop.kitchenapp.firebase.callbacks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import dk.pop.kitchenapp.MyGroupsActivity;
import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.data.interfaces.FireBaseCallback;
import dk.pop.kitchenapp.fragments.GroupCreationFragment;
import dk.pop.kitchenapp.fragments.kitchen.KitchenOverviewFragment;
import dk.pop.kitchenapp.logging.LoggingTag;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;

/**
 * Created by Runi on 01-12-2016.
 * used in MainActivity and determines which view to show depending on callback from firebase.
 */

public class UserLoggedInCallback implements FireBaseCallback<Person> {

    private AppCompatActivity activity;
    public UserLoggedInCallback(AppCompatActivity activity){
        this.activity = activity;
    }
    @Override
    public void onSuccessCreate(Person entity) {
        DataManager.getInstance().setCurrentPerson(entity);
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.drawer_navigation_main_content, new GroupCreationFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFailureCreate() {
        Log.e(LoggingTag.ERROR.name(), "Could not create the person... FATAL ERROR");
    }

    @Override
    public void onExists(Person entity) {
        DataManager.getInstance().setCurrentPerson(entity);
        if (entity.getKitchens().isEmpty()) {
            // Route to first personal_calendar fragment
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.drawer_navigation_main_content, new GroupCreationFragment())
                    .addToBackStack(null)
                    .commit();
        } else if(entity.getKitchens().size() == 1) {
            String kitchenName = entity.getKitchens().values().iterator().next();
            DataManager.getInstance().getKitchen(kitchenName, new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Kitchen kitchen = dataSnapshot.getValue(Kitchen.class);
                    DataManager.getInstance().setCurrentKitchen(kitchen);
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.drawer_navigation_main_content, new KitchenOverviewFragment())
                            .commit();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }else{
            // Route to my groups
            Intent intent = new Intent(activity, MyGroupsActivity.class);
            activity.startActivity(intent);
        }
    }
}

package dk.pop.kitchenapp.data;

import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import dk.pop.kitchenapp.data.interfaces.FireBaseCallback;
import dk.pop.kitchenapp.models.DinnerGroupActivity;
import dk.pop.kitchenapp.models.GroupActivity;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;

/**
 * Created by dickow on 10/2/16.
 */
public class DataManager implements IDataManager {
    private static DataManager ourInstance;

    public final String KITCHENRESOURCE = "kitchens/";
    public final String PERSONRESOURCE = "persons/";
    public final String ACTIVITIESRESOURCE = "activities/";
    private Person currentPerson;
    private Kitchen currentKitchen;

    private DatabaseReference database;

    public static DataManager getInstance() {
        if(ourInstance == null){
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    private DataManager() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void createKitchen(@NonNull final Kitchen kitchen, final FireBaseCallback<Kitchen> callback){
        database.child(KITCHENRESOURCE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(kitchen.getName())){
                    database.child(KITCHENRESOURCE).child(kitchen.getName()).setValue(kitchen);
                    if(callback != null){
                        callback.onSuccessCreate(kitchen);
                    }
                }
                else{
                    callback.onExists(dataSnapshot.child(kitchen.getName()).getValue(Kitchen.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailureCreate();
            }
        });
    }

    @Override
    public void createPerson(@NonNull final Person person, final FireBaseCallback<Person> callback){
        database.child(PERSONRESOURCE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(person.getGoogleId())){
                    database.child(PERSONRESOURCE).child(person.getGoogleId()).setValue(person);
                    if(callback != null){
                        callback.onSuccessCreate(person);
                    }
                }
                else{
                    if(callback != null){
                        callback.onExists(dataSnapshot.child(person.getGoogleId()).getValue(Person.class));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailureCreate();
            }
        });
    }

    @Override
    public void getPerson(String googleId, ValueEventListener listener){
        database.child(PERSONRESOURCE)
                .child(googleId)
                .addListenerForSingleValueEvent(listener);
    }

    @Override
    public void getKitchen(String name, ValueEventListener listener){
        database.child(KITCHENRESOURCE)
                .child(name)
                .addListenerForSingleValueEvent(listener);
    }

    @Override
    public void attachKitchenListener(ChildEventListener listener){
        database.child(KITCHENRESOURCE)
                .addChildEventListener(listener);
    }

    @Override
    public void detachKitchenListener(ChildEventListener listener){
        database.child(KITCHENRESOURCE)
                .removeEventListener(listener);
    }

    @Override
    public void createActivity(GroupActivity activity){
        database.child(ACTIVITIESRESOURCE).child(activity.getId().toString()).setValue(activity.getValues());
       // database.child(ACTIVITIESRESOURCE).child(activity.getId().toString()).setValue(activity);
        database.child(KITCHENRESOURCE).child(activity.getKitchen().getName()).child(ACTIVITIESRESOURCE).child(activity.getId().toString()).setValue(activity.getId().toString());

        switch (activity.getType()) {
            case DINNERACTIVITY:
                if(activity instanceof DinnerGroupActivity){
                    DinnerGroupActivity dinnerActivity = (DinnerGroupActivity) activity;

                }
                break;
            case CLEANINGACTIVITY:
                break;
            case EXPENSEACTIVITY:
                break;
        }
    }

    @Override
    public void associateToKitchen(Kitchen kitchen, Person person){
        database.child(KITCHENRESOURCE)
                .child(kitchen.getName())
                .child(PERSONRESOURCE)
                .child(person.getGoogleId())
                .setValue(person.getGoogleId());

        database.child(PERSONRESOURCE)
                .child(person.getGoogleId())
                .child(KITCHENRESOURCE)
                .child(kitchen.getName())
                .setValue(kitchen.getName());
    }

    @Override
    public void disAssociateFromKitchen(Kitchen kitchen, Person person){
        database.child(KITCHENRESOURCE)
                .child(kitchen.getName())
                .child(PERSONRESOURCE)
                .child(person.getGoogleId())
                .removeValue();

        database
                .child(PERSONRESOURCE)
                .child(person.getGoogleId())
                .child(KITCHENRESOURCE)
                .child(kitchen.getName())
                .removeValue();
    }

    @Override
    public void getKitchensForPerson(Person person, ChildEventListener listener){
        Query query = database.child(KITCHENRESOURCE)
                .orderByChild(String.format("%s/%s",PERSONRESOURCE, person.getGoogleId()))
                .equalTo(person.getGoogleId());
        query.addChildEventListener(listener);
    }

    @Override
    public Person getCurrentPerson() {
        return currentPerson;
    }

    @Override
    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }

    @Override
    public Kitchen getCurrentKitchen() {
        return currentKitchen;
    }

    @Override
    public void setCurrentKitchen(Kitchen currentKitchen) {
        this.currentKitchen = currentKitchen;
    }
}

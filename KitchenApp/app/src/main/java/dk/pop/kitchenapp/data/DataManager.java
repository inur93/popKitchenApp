package dk.pop.kitchenapp.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import dk.pop.kitchenapp.BuildConfig;
import dk.pop.kitchenapp.models.CleaningGroupActivity;
import dk.pop.kitchenapp.models.DinnerGroupActivity;
import dk.pop.kitchenapp.models.ExpenseGroupActivity;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;

/**
 * Created by dickow on 10/2/16.
 */
public class DataManager {
    private static DataManager ourInstance;

    private final String KITCHENRESOURCE = "kitchens/";
    private final String PERSONRESOURCE = "persons/";
    private final String ACTIVITIESRESOURCE = "activities/";

    private Kitchen kitchen;
    private Person person;
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

    public void createKitchen(Kitchen kitchen){
        database.child(KITCHENRESOURCE).push().setValue(kitchen);
    }

    public void createPerson(Person person){
        database.child(PERSONRESOURCE).push().setValue(person);
    }

    public void associateToKitchen(Kitchen kitchen, Person person){
        database.child(KITCHENRESOURCE).child(kitchen.getName()).child(PERSONRESOURCE).push().setValue(person.getGoogleId());
    }

    public void disAssociateFromKitchen(Kitchen kitchen, Person person){

    }
}

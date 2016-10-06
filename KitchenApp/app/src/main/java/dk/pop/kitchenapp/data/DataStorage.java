package dk.pop.kitchenapp.data;

import android.app.Application;

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
public class DataStorage extends Application{
    private static DataStorage ourInstance;

    private Kitchen kitchen;
    private Person person;

    public static DataStorage getInstance() {
        if(ourInstance == null){
            ourInstance = new DataStorage();
        }
        return ourInstance;
    }

    private DataStorage() {
        if(BuildConfig.DEBUG){
            // Setup kitchen
            kitchen = new Kitchen("Test Kitchen");

            // Setup person
            person = new Person();
            person.getKitchens().add(kitchen);
            person.setGoogleId("KlausBruun@gmail.com");
            person.setActive(true);
            person.setRoomNumber(200);


            // Setup persons
            ArrayList<Person> kitchenCleaningPersons = new ArrayList<>();
            kitchenCleaningPersons.add(new Person());
            kitchenCleaningPersons.add(new Person());
            kitchenCleaningPersons.add(new Person());
            kitchenCleaningPersons.add(new Person());
            kitchenCleaningPersons.add(new Person());

            // Setup kitchen activities
            kitchen.getActivities().add(new CleaningGroupActivity(kitchenCleaningPersons, kitchen, this.person));
            kitchen.getActivities().add(new CleaningGroupActivity(kitchenCleaningPersons, kitchen, this.person));
            kitchen.getActivities().add(new CleaningGroupActivity(kitchenCleaningPersons, kitchen, this.person));
            kitchen.getActivities().add(new CleaningGroupActivity(kitchenCleaningPersons, kitchen, this.person));
            kitchen.getActivities().add(new CleaningGroupActivity(kitchenCleaningPersons, kitchen, this.person));

            // This person array
            ArrayList<Person> thisPerson = new ArrayList<>();
            thisPerson.add(person);

            person.getCleaningActivities().add(new CleaningGroupActivity(thisPerson, this.kitchen, this.person));

            ArrayList<ExpenseGroupActivity> expenses = new ArrayList<>();
            expenses.add(new ExpenseGroupActivity(this.person, this.person, 245.05f, this.kitchen));
            expenses.add(new ExpenseGroupActivity(this.person, this.person, 202.95f, this.kitchen));
            expenses.add(new ExpenseGroupActivity(this.person, this.person, 865.95f, this.kitchen));
            person.getDinnerActivities().add(new DinnerGroupActivity(expenses, this.kitchen, this.person));
        }
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DataStorage.getInstance();
    }
}

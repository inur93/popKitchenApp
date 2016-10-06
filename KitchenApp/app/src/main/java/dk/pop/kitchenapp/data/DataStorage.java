package dk.pop.kitchenapp.data;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

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
    private List<Person> participants;

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


            this.person = createPerson(kitchen, "KlausBruun@gmail.com", true, 200);

            this.participants = new ArrayList<>();
            this.participants.add(createPerson(kitchen, "jespergroenkjær@gmail.com", true, 47));
            this.participants.add(createPerson(kitchen, "nicholascage@gmail.com", true, 43));
            this.participants.add(createPerson(kitchen, "willsmith@gmail.com", true, 123));


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

    private Person createPerson(Kitchen kitchen, String id, boolean active, int roomNo){
        // Setup person
        person = new Person();
        person.getKitchens().add(kitchen);
        person.setActive(active);
        person.setGoogleId(id);
        person.setRoomNumber(roomNo);
        return person;
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

    public List<Person> getParticipants(){
        return this.participants;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DataStorage.getInstance();
    }
}

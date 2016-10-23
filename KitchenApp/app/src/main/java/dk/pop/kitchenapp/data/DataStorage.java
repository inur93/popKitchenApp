package dk.pop.kitchenapp.data;

import android.app.Application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dk.pop.kitchenapp.BuildConfig;
import dk.pop.kitchenapp.models.CleaningGroupActivity;
import dk.pop.kitchenapp.models.DinnerGroupActivity;
import dk.pop.kitchenapp.models.ExpenseGroupActivity;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;
import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;
import dk.pop.kitchenapp.viewModels.PersonViewModel;

/**
 * Created by dickow on 10/2/16.
 */
public class DataStorage extends Application{
    private static DataStorage ourInstance;

    private Kitchen kitchen;
    private Person person;
    private List<PersonViewModel> participants;

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
            this.participants.add(createPersonViewModel(kitchen, "jespergroenkjær@gmail.com", true, 47, true));
            this.participants.add(createPersonViewModel(kitchen, "nicholascage@gmail.com", true, 43, true));
            this.participants.add(createPersonViewModel(kitchen, "willsmith@gmail.com", true, 123, true));


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
        PersonViewModel person = createPersonViewModel(kitchen, id, active, roomNo, false);
        return person.getPerson();
    }
    private PersonViewModel createPersonViewModel(Kitchen kitchen, String id, boolean active, int roomNo, boolean isSelected){
        // Setup person
        person = new Person();
        person.getKitchens().add(kitchen);
        person.setActive(active);
        person.setGoogleId(id);
        person.setRoomNumber(roomNo);

        return new PersonViewModel(person, isSelected);
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

    public List<PersonViewModel> getParticipants(boolean doCopy, boolean isSelected){
        if(doCopy){
            List<PersonViewModel> copy = new ArrayList<>();
           for(PersonViewModel p : this.participants){
               PersonViewModel nP = new PersonViewModel(p.getPerson(), isSelected);
               copy.add(nP);
           }
            return copy;
        }
        return this.participants;
    }

    public String createActivity(ObjectTypeEnum type, String title, String description, Date date, List<Person> responsibles, List<Person> participants){
        switch (type){
            case CLEANINGACTIVITY:

                break;
            case DINNERACTIVITY:

                break;
            case EXPENSEACTIVITY:

                break;
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DataStorage.getInstance();
    }
}

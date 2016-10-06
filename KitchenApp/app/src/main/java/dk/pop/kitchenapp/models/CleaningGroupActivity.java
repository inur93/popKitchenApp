package dk.pop.kitchenapp.models;

import java.util.ArrayList;

import dk.pop.kitchenapp.models.enums.CleaningStatusEnum;
import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;

/**
 * Created by dickow on 9/21/16.
 */

public class CleaningGroupActivity extends PlannableGroupActivity {
    private CleaningStatusEnum status;
    private ArrayList<Person> persons;

    public CleaningGroupActivity(ArrayList<Person> persons, Kitchen kitchen, Person createdBy) throws IllegalArgumentException{
        super(createdBy, kitchen, ObjectTypeEnum.CLEANINGACTIVITY);

        if(persons == null || persons.isEmpty()){
            throw new IllegalArgumentException("You must provide atleast one person for the cleaning");
        }

        this.persons = persons;
    }

    public CleaningStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CleaningStatusEnum status) {
        this.status = status;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }
}

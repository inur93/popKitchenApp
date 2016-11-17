package dk.pop.kitchenapp.models;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import dk.pop.kitchenapp.models.enums.CleaningStatusEnum;
import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;

/**
 * Created by dickow on 9/21/16.
 */

public class CleaningGroupActivity extends PlannableGroupActivity {
    private CleaningStatusEnum status;
    private HashMap<String, String> persons = new HashMap<>();

    public CleaningGroupActivity(){
        super(null, null, null, null, null, null, null, false);

    }

    public CleaningGroupActivity(
            UUID id,
            String title,
            String description,
            Date date,
            Kitchen kitchen,
            Person createdBy,
            boolean isCancellable,
            List<Person> participants,
            CleaningStatusEnum status
            ) throws IllegalArgumentException{

        super(id, title, description, date, kitchen, createdBy, ObjectTypeEnum.CLEANINGACTIVITY, isCancellable);

        this.status = status == null ? CleaningStatusEnum.SCHEDULED : status;
        if(participants != null)
        for(Person p : participants) {
            this.persons.put(p.getGoogleId(), p.getGoogleId());
        }

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

    public HashMap<String, String> getPersons() {
        return persons;
    }

    public void setPersons(HashMap<String, String> persons){
        this.persons = persons;
    }

}

package dk.pop.kitchenapp.listeners;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.List;

import dk.pop.kitchenapp.adapters.ActivityPersonNameRoomNoAdapter;
import dk.pop.kitchenapp.models.Person;
import dk.pop.kitchenapp.viewModels.PersonViewModel;

/**
 * Created by Runi on 12-11-2016.
 */

public class ParticipantListViewListener implements ChildEventListener {

    private List<PersonViewModel> persons;
    private ActivityPersonNameRoomNoAdapter adapter;
    public ParticipantListViewListener(List<PersonViewModel> persons, ActivityPersonNameRoomNoAdapter adapter){
        this.adapter = adapter;
        this.persons = persons;
    }

    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Person p = dataSnapshot.getValue(Person.class);
        persons.add(new PersonViewModel(p, false));
        adapter.notifyDataSetChanged();
    }

    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Person p = dataSnapshot.getValue(Person.class);

        int index = -1;
        for(int i = 0; i < persons.size(); i++){
            PersonViewModel person = persons.get(i);
            if(person.getPerson().getGoogleId().equals(p.getGoogleId())){
                persons.get(i).setPerson(p);
                break;
            }
        }
        adapter.notifyDataSetChanged();

    }


    public void onChildRemoved(DataSnapshot dataSnapshot) {
        Person p = dataSnapshot.getValue(Person.class);
        int index = -1;
        for(int i = 0; i < persons.size(); i++){
            PersonViewModel person = persons.get(i);
            if(person.getPerson().getGoogleId().equals(p.getGoogleId())){
                index = i;
                break;
            }
        }
        if(index > -1) persons.remove(index);
        adapter.notifyDataSetChanged();
    }


    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }


    public void onCancelled(DatabaseError databaseError) {

    }
}

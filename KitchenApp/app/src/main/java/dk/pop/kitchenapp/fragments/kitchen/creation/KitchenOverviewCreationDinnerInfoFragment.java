package dk.pop.kitchenapp.fragments.kitchen.creation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.ActivityPersonNameRoomNoAdapter;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.models.Person;
import dk.pop.kitchenapp.navigation.FragmentExtension;
import dk.pop.kitchenapp.viewModels.PersonViewModel;

/**
 * Created by Runi on 27-10-2016.
 */

public class KitchenOverviewCreationDinnerInfoFragment extends FragmentExtension {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_kitchen_overview_activity_creation_dinner_info,
                container,
                false);
        AQuery aq = new AQuery(view);
        final ListView participants = aq.id(R.id.activity_creation_dinner_participant_list).getListView();
        final List<PersonViewModel> persons = new ArrayList<>();
        final ActivityPersonNameRoomNoAdapter adapter = new ActivityPersonNameRoomNoAdapter(
                getContext(), persons);
        participants.setAdapter(adapter);
        DataManager.getInstance()
                .getPersonsFromKitchen(
                        DataManager.getInstance().getCurrentKitchen(),
                        new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                Person p = dataSnapshot.getValue(Person.class);
                                persons.add(new PersonViewModel(p, false));
                            }

                            @Override
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

                            @Override
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
                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
        return view;
    }
}

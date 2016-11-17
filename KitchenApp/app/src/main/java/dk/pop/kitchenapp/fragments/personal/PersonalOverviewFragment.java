package dk.pop.kitchenapp.fragments.personal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.ActivityListAdapter;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.models.GroupActivity;
import dk.pop.kitchenapp.models.factories.ActivityFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalOverviewFragment extends Fragment {

    private ActivityListAdapter adapter;
    private ListView activityList;
    private ArrayList<GroupActivity> activities = new ArrayList<>();
    private ChildEventListener listener;

    public PersonalOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_overview, container, false);

        adapter = new ActivityListAdapter(getContext(), activities);
        AQuery aq = new AQuery(view);
        activityList = aq.id(R.id.personal_overview_listview).getListView();
        activityList.setAdapter(adapter);
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GroupActivity act = ActivityFactory.CreateActivity(dataSnapshot);
                for(int i = 0; i < activities.size(); i++){
                    if(activities.get(i).getId().equals(act.getId()) || !act.getKitchen().equals(DataManager.getInstance().getCurrentKitchen().getName())){
                        return;
                    }
                }
                activities.add(act);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                GroupActivity act = ActivityFactory.CreateActivity(dataSnapshot);
                for(int i = 0; i < activities.size(); i++){
                    if(activities.get(i).getId().equals(act.getId())){
                        activities.set(i, act);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                GroupActivity act = ActivityFactory.CreateActivity(dataSnapshot);
                for(int i = 0; i < activities.size(); i++){
                    if(activities.get(i).getId().equals(act.getId())){
                        activities.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        DataManager.getInstance().getActivitiesForPerson(DataManager.getInstance().getCurrentPerson(), listener);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        DataManager.getInstance().detachActivitiesForPerson(DataManager.getInstance().getCurrentPerson(), listener);
        activities.clear();
    }
}

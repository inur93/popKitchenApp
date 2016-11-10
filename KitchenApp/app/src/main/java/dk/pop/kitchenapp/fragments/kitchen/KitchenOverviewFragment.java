package dk.pop.kitchenapp.fragments.kitchen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.ActivityListAdapter;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.fragments.kitchen.creation.KitchenOverviewActivityCreationFragment;
import dk.pop.kitchenapp.logging.LoggingTag;
import dk.pop.kitchenapp.models.GroupActivity;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;
import dk.pop.kitchenapp.models.factories.ActivityFactory;
import dk.pop.kitchenapp.navigation.FragmentExtension;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenOverviewFragment extends FragmentExtension implements View.OnClickListener{
    private ListView allActivities;
    private ChildEventListener listener;
    private ArrayList<GroupActivity> activities = new ArrayList<>();

    public KitchenOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("inflate kitchen overview");
        View view =  inflater.inflate(R.layout.fragment_kitchen_overview, container, false);

        AQuery aq = new AQuery(view);
        this.allActivities = aq.id(R.id.kitchen_overview_list_view).getListView();
        this.allActivities.setAdapter(new ActivityListAdapter(getContext(), activities));
        aq.id(R.id.kitchenOverviewCreateActivityBtn).clicked(this);
        aq.id(R.id.kitchenOverviewShowCalendarBtn).clicked(this);

        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                activities.add(ActivityFactory.CreateActivity(dataSnapshot));
                ((ActivityListAdapter)allActivities.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                GroupActivity act = ActivityFactory.CreateActivity(dataSnapshot);
                for (int i = 0; i < activities.size(); i++){
                    if(activities.get(i).getId().equals(act.getId())){
                        activities.set(i, act);
                    }
                }
                ((ActivityListAdapter)allActivities.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                GroupActivity act = ActivityFactory.CreateActivity(dataSnapshot);
                for (int i = 0; i < activities.size(); i++){
                    if(activities.get(i).getId().equals(act.getId())){
                        activities.remove(i);
                    }
                }
                ((ActivityListAdapter)allActivities.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        DataManager.getInstance().getActivitiesForKitchen(DataManager.getInstance().getCurrentKitchen(), listener);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.kitchenOverviewCreateActivityBtn:
                Log.d(LoggingTag.INFO.name(), "create activity btn was clicked");
                final KitchenOverviewActivityCreationFragment frag = new KitchenOverviewActivityCreationFragment();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.kitchen_overview_wrapper_fragment_placeholder,
                               frag)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.kitchenOverviewShowCalendarBtn:
                Log.d(LoggingTag.INFO.name(), "show calendar btn was clicked");
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.kitchen_overview_wrapper_fragment_placeholder,
                                new KitchenOverviewCalendarFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        DataManager.getInstance().detachActivitiesForKitchen(DataManager.getInstance().getCurrentKitchen(), listener);
    }
}

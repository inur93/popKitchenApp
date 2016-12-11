package dk.pop.kitchenapp.fragments.personal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.ActivityListAdapter;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.listeners.ActivityOnItemClickListener;
import dk.pop.kitchenapp.listeners.personal.PersonalOverviewListener;
import dk.pop.kitchenapp.models.GroupActivity;
import dk.pop.kitchenapp.models.factories.ActivityFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalOverviewFragment extends Fragment {
    private ListView activityList;
    PersonalOverviewListener overviewListener;

    public PersonalOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_overview, container, false);
        AQuery aq = new AQuery(view);
        overviewListener = new PersonalOverviewListener(
                DataManager.getInstance().getCurrentPerson(),
                aq.id(R.id.personal_overview_spinner).getProgressBar());
        ActivityListAdapter adapter = new ActivityListAdapter(getContext(), overviewListener.getItems());
        overviewListener.setAdapter(adapter);

        // Setup list view
        activityList = aq.id(R.id.personal_overview_listview).getListView();
        activityList.setAdapter(adapter);
        activityList.setOnItemClickListener(new ActivityOnItemClickListener(this, overviewListener.getItems()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getString(R.string.personal_overview_title));
        overviewListener.start();
    }

    @Override
    public void onStop() {
        super.onStop();

        try {
            overviewListener.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

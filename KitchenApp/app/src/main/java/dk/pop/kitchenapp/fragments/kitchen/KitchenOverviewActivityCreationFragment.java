package dk.pop.kitchenapp.fragments.kitchen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.AQuery;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.ActivityPersonNameRoomNoAdapter;
import dk.pop.kitchenapp.data.DataStorage;
import dk.pop.kitchenapp.logging.LoggingTag;
import dk.pop.kitchenapp.navigation.FragmentExtension;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenOverviewActivityCreationFragment extends FragmentExtension implements View.OnClickListener{


    private ListView participants;
    private ListView responsibles;

    public KitchenOverviewActivityCreationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kitchen_overview_activity_creation, container, false);

        AQuery aq = new AQuery(view);

        aq.id(R.id.kitchen_overview_activity_creation_save_btn).clicked(this);


        this.participants = aq.id(R.id.activity_creation_participant_list).getListView();
        this.participants.setAdapter(
            new ActivityPersonNameRoomNoAdapter(view.getContext(), DataStorage.getInstance().getParticipants()));

        this.responsibles = aq.id(R.id.activity_creation_responsible_list).getListView();
        this.responsibles.setAdapter(
                new ActivityPersonNameRoomNoAdapter(view.getContext(), DataStorage.getInstance().getParticipants()));

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.kitchen_overview_activity_creation_save_btn){
            Log.d(LoggingTag.INFO.name(), "You pressed the save button");

            // Save the newly created GroupActivity here here

            // Return to the previous fragment
        }
    }
}

package dk.pop.kitchenapp.fragments.kitchen.creation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.AQuery;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.ActivityPersonNameRoomNoAdapter;
import dk.pop.kitchenapp.data.DataStorage;

/**
 * Created by Runi on 22-10-2016.
 */

public class KitchenOverviewCreationSelectParticipantsResponsibles extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        System.out.println("inflate creation general info");
        View view = inflater.inflate(
                R.layout.fragment_kitchen_overview_activity_creation_select_participants_responsibles,
                container,
                false);
        AQuery aq = new AQuery(view);

        ListView participants = aq.id(R.id.activity_creation_participant_list).getListView();
        ListView responsibles = aq.id(R.id.activity_creation_responsible_list).getListView();

        participants.setAdapter(new ActivityPersonNameRoomNoAdapter(getContext(), DataStorage.getInstance().getParticipants(true, true)));
        responsibles.setAdapter(new ActivityPersonNameRoomNoAdapter(getContext(), DataStorage.getInstance().getParticipants(true, false)));

        return view;
    }

        @Override
    public void onClick(View v) {

    }
}

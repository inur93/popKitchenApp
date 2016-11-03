package dk.pop.kitchenapp.fragments.kitchen.creation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.AQuery;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.ActivityPersonNameRoomNoAdapter;
import dk.pop.kitchenapp.data.DataStorage;
import dk.pop.kitchenapp.navigation.FragmentExtension;

/**
 * Created by Runi on 27-10-2016.
 */

public class KitchenOverviewCreationCleaningInfoFragment extends FragmentExtension {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_kitchen_overview_activity_creation_cleaning_info,
                container,
                false);
        AQuery aq = new AQuery(view);

        ListView participants = aq.id(R.id.activity_creation_cleaning_participant_list).getListView();
        participants.setAdapter(new ActivityPersonNameRoomNoAdapter(getContext(), DataStorage.getInstance().getParticipants(true, false)));
        return view;
    }

}

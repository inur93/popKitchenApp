package dk.pop.kitchenapp.fragments.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.ActivityPersonNameRoomNoAdapter;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.listeners.ParticipantListViewListener;
import dk.pop.kitchenapp.viewModels.PersonViewModel;

/**
 * Created by Runi on 27-10-2016.
 */

public class KitchenOverviewCreationDinnerInfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_activity_creation_dinner_info,
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
                        new ParticipantListViewListener(persons, adapter));
        return view;
    }
}

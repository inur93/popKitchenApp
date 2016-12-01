package dk.pop.kitchenapp.fragments.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.PersonListAdapter;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.listeners.ParticipantListViewListener;
import dk.pop.kitchenapp.models.CleaningGroupActivity;
import dk.pop.kitchenapp.viewModels.PersonViewModel;

/**
 * Created by Runi on 01-12-2016.
 */

public class ActivityInfoCleaningFragment extends Fragment {

    private TextView status;
    private ListView participants;
    private ParticipantListViewListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity_info_cleaning, container, false);

        AQuery aq = new AQuery(view);

        this.status = aq.id(R.id.activity_info_cleaning_status).getTextView();
        this.participants = aq.id(R.id.activity_info_cleaning_participants).getListView();
        Object o = getActivity().getIntent().getSerializableExtra(ActivityInfoFragment.EXTRA_ACTIVITY_INFO_OBJECT);

        if(o == null) return view;
        if(o instanceof CleaningGroupActivity){
            CleaningGroupActivity a = (CleaningGroupActivity) o;
            this.status.setText(a.getStatus().name());
            final List<PersonViewModel> persons = new ArrayList<>();
            final PersonListAdapter adapter = new PersonListAdapter(getContext(), persons);
            participants.setAdapter(adapter);
            listener = new ParticipantListViewListener(persons, adapter);
            DataManager.getInstance()
                    .getPersonsFromKitchen(
                            DataManager.getInstance().getCurrentKitchen(),listener);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        DataManager.getInstance().removePersonsFromKitchenListener(
                DataManager.getInstance().getCurrentKitchen(), listener
        );
    }
}
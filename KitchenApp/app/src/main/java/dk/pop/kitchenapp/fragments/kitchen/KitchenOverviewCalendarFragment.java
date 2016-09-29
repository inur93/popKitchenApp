package dk.pop.kitchenapp.fragments.kitchen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.pop.kitchenapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenOverviewCalendarFragment extends Fragment {


    public KitchenOverviewCalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kitchen_overview_calendar, container, false);
    }

}

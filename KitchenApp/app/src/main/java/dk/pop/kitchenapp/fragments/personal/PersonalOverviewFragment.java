package dk.pop.kitchenapp.fragments.personal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.pop.kitchenapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalOverviewFragment extends Fragment {


    public PersonalOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_overview, container, false);
    }

}

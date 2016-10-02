package dk.pop.kitchenapp.fragments.kitchen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.logging.LoggingTag;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenOverviewFragment extends Fragment implements View.OnClickListener{


    public KitchenOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_kitchen_overview, container, false);

        AQuery aq = new AQuery(view);
        aq.id(R.id.kitchenOverviewCreateActivityBtn).clicked(this);
        aq.id(R.id.kitchenOverviewShowCalendarBtn).clicked(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.kitchenOverviewCreateActivityBtn:
                Log.d(LoggingTag.INFO.name(), "create activity btn was clicked");
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.kitchen_overview_wrapper_fragment_placeholder,
                                new KitchenOverviewActivityCreationFragment())
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
}

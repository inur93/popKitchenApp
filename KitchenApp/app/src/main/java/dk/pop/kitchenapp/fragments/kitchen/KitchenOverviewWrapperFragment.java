package dk.pop.kitchenapp.fragments.kitchen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;

import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.logging.LoggingTag;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenOverviewWrapperFragment extends Fragment{

    public KitchenOverviewWrapperFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        List<Fragment> fragments = getChildFragmentManager().getFragments();

        if(fragments == null || fragments.isEmpty()) {
            getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.kitchen_overview_wrapper_fragment_placeholder,
                            new KitchenOverviewFragment())
                    .addToBackStack(null)
                    .commit();
        }

        return inflater.inflate(R.layout.fragment_kitchen_overview_wrapper, container, false);
    }


}

package dk.pop.kitchenapp.fragments.kitchen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.AQuery;

import dk.pop.kitchenapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenOverviewFragment extends Fragment implements View.OnClickListener{
    private ListView allActivities;

    public KitchenOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("inflate kitchen overview");
        View view =  inflater.inflate(R.layout.fragment_kitchen_overview, container, false);

        AQuery aq = new AQuery(view);
        this.allActivities = aq.id(R.id.kitchen_overview_list_view).getListView();
        return view;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onStop() {
        super.onStop();
        //DataManager.getInstance().detachActivitiesForKitchen(DataManager.getInstance().getCurrentKitchen(), listener);
        //activities.clear();
    }
}

package dk.pop.kitchenapp.fragments.kitchen;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.androidquery.AQuery;

import dk.pop.kitchenapp.GroupsActivity;
import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.ActivityListAdapter;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.data.IDataManager;
import dk.pop.kitchenapp.listeners.ActivityOnItemClickListener;
import dk.pop.kitchenapp.listeners.kitchen.KitchenOverviewListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenOverviewFragment extends Fragment{
    private ListView allActivities;
    private KitchenOverviewListener kitchenListener;

    public KitchenOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_kitchen_overview, container, false);

        AQuery aq = new AQuery(view);

        this.kitchenListener = new KitchenOverviewListener(
                DataManager.getInstance().getCurrentKitchen(),
                aq.id(R.id.kitchen_overview_spinner).getProgressBar());

        BaseAdapter adapter = new ActivityListAdapter(getContext(), this.kitchenListener.getItems());
        this.kitchenListener.setAdapter(adapter);

        this.allActivities = aq.id(R.id.kitchen_overview_list_view).getListView();
        this.allActivities.setAdapter(adapter);
        this.allActivities.setOnItemClickListener(new ActivityOnItemClickListener(this, this.kitchenListener.getItems()));
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        IDataManager dm = DataManager.getInstance();
        if(dm.getCurrentKitchen() == null){
            Intent intent = new Intent(getActivity(), GroupsActivity.class);
            startActivity(intent);
            return;
        }
        getActivity().setTitle(getString(R.string.kitchen_overview_title));
        this.kitchenListener.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            this.kitchenListener.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

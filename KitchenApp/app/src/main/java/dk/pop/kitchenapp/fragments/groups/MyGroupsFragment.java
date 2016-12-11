package dk.pop.kitchenapp.fragments.groups;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import dk.pop.kitchenapp.MainActivity;
import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.KitchenListAdapter;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.data.dataPassing.DataPassingEnum;
import dk.pop.kitchenapp.listeners.groups.MyGroupsListener;
import dk.pop.kitchenapp.models.Kitchen;

/**
 * Created by Runi on 11-12-2016.
 */

public class MyGroupsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView groupsList;
    private MyGroupsListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_groups, container, false);
        AQuery aq = new AQuery(view);

        listener = new MyGroupsListener(
                DataManager.getInstance().getCurrentPerson(),
                aq.id(R.id.my_groups_spinner).getProgressBar());
        KitchenListAdapter adapter = new KitchenListAdapter(listener.getItems(), getContext());
        listener.setAdapter(adapter);

        groupsList = aq.id(R.id.my_groups_list_view).getListView();
        groupsList.setAdapter(adapter);
        groupsList.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Kitchen clickedKitchen = (Kitchen)groupsList.getAdapter().getItem(position);
        DataManager.getInstance().setCurrentKitchen(clickedKitchen);
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra(DataPassingEnum.KITCHEN.name(), clickedKitchen);
        intent.putExtra(DataPassingEnum.PERSON.name(), DataManager.getInstance().getCurrentPerson());
        startActivity(intent);
    }

    @Override
    public void onStart(){
        super.onStart();
        getActivity().setTitle(getString(R.string.my_groups_title));
        this.listener.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            this.listener.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

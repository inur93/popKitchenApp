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
import dk.pop.kitchenapp.models.Kitchen;

/**
 * Created by Runi on 11-12-2016.
 */

public class MyGroupsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView groupsList;
    private ArrayList<Kitchen> kitchens;
    private ProgressBar spinner;
    ChildEventListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_groups, container, false);
        AQuery aq = new AQuery(view);

        spinner = aq.id(R.id.my_groups_spinner).getProgressBar();

        // Inflate the layout for this fragment
        kitchens = new ArrayList<>();
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                spinner.setVisibility(View.GONE);
                kitchens.add(dataSnapshot.getValue(Kitchen.class));
                ((KitchenListAdapter) groupsList.getAdapter()).getFilter().filter("");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                kitchens.indexOf(dataSnapshot.getValue(Kitchen.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                kitchens.remove(dataSnapshot.getValue(Kitchen.class));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        groupsList = aq.id(R.id.my_groups_list_view).getListView();
        groupsList.setAdapter(new KitchenListAdapter(kitchens, getContext()));
        groupsList.setOnItemClickListener(this);
        DataManager.getInstance().getKitchensForPerson(DataManager.getInstance().getCurrentPerson(), listener);
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
        spinner.setVisibility(View.VISIBLE);
        getActivity().setTitle(getString(R.string.my_groups_title));
    }

    @Override
    public void onStop() {
        super.onStop();
        DataManager.getInstance().detachKitchensForPerson(DataManager.getInstance().getCurrentPerson(), listener);
    }
}

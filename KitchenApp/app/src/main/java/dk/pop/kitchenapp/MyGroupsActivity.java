package dk.pop.kitchenapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import dk.pop.kitchenapp.adapters.KitchenListAdapter;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.data.dataPassing.DataPassingEnum;
import dk.pop.kitchenapp.models.Kitchen;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyGroupsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView groupsList;
    private ArrayList<Kitchen> kitchens;
    ChildEventListener listener;

    public MyGroupsActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups);

        // Inflate the layout for this fragment
        kitchens = new ArrayList<>();
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                kitchens.add(dataSnapshot.getValue(Kitchen.class));
                ((KitchenListAdapter)groupsList.getAdapter()).getFilter().filter("");
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

        AQuery aq = new AQuery(this);
        groupsList = aq.id(R.id.my_groups_list_view).getListView();
        groupsList.setAdapter(new KitchenListAdapter(kitchens, this));
        groupsList.setOnItemClickListener(this);
        DataManager.getInstance().getKitchensForPerson(DataManager.getInstance().getCurrentPerson(), listener);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Kitchen clickedKitchen = (Kitchen)groupsList.getAdapter().getItem(position);
        DataManager.getInstance().setCurrentKitchen(clickedKitchen);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(DataPassingEnum.KITCHEN.name(), clickedKitchen);
        intent.putExtra(DataPassingEnum.PERSON.name(), DataManager.getInstance().getCurrentPerson());
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        DataManager.getInstance().detachKitchensForPerson(DataManager.getInstance().getCurrentPerson(), listener);
    }
}

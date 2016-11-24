package dk.pop.kitchenapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import dk.pop.kitchenapp.data.interfaces.FireBaseCallback;
import dk.pop.kitchenapp.models.Kitchen;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupCreationFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{

    private AQuery aq;
    private ListView kitchenList;
    private ChildEventListener listener;
    private ArrayList<Kitchen> kitchens;
    private View view;
    private KitchenListAdapter adapter;
    private ProgressBar spinner;

    public GroupCreationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_group_creation, container, false);
        aq = new AQuery(view);

        spinner = aq.id(R.id.group_creation_spinner).getProgressBar();
        kitchens = new ArrayList<>();

        // Required empty public constructor
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                spinner.setVisibility(View.GONE);
                kitchens.add(dataSnapshot.getValue(Kitchen.class));
                adapter.getFilter().filter("");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Kitchen removedKitchen = dataSnapshot.getValue(Kitchen.class);
                for(int i = 0; i < kitchens.size(); i++){
                    if(kitchens.get(i).getName().equals(removedKitchen.getName())){
                        kitchens.remove(i);
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        aq.id(R.id.group_creation_create_btn).clicked(this);
        kitchenList = aq.id(R.id.group_creation_list_view).getListView();
        kitchenList.setOnItemClickListener(this);

        // Setup list adapter
        adapter = new KitchenListAdapter(kitchens, getContext());

        kitchenList.setAdapter(adapter);
        adapter.getFilter().filter("");

        // Setup Text Watcher
        aq.id(R.id.group_creation_search_field).getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return this.view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.group_creation_create_btn:
                String kitchenName = this.aq.id(R.id.group_creation_search_field).getEditText().getText().toString();
                DataManager.getInstance().createKitchen(new Kitchen(kitchenName), new FireBaseCallback<Kitchen>() {
                    @Override
                    public void onSuccessCreate(Kitchen entity) {
                        DataManager.getInstance().associateToKitchen(entity, DataManager.getInstance().getCurrentPerson());
                    }

                    @Override
                    public void onFailureCreate() {

                    }

                    @Override
                    public void onExists(Kitchen entity) {
                        Toast.makeText(getView().getContext(), String.format("The kitchen %s already existed", entity.getName()), Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        spinner.setVisibility(View.VISIBLE);
        DataManager.getInstance().attachKitchenListener(this.listener);
    }

    @Override
    public void onStop() {
        super.onStop();
        DataManager.getInstance().detachKitchenListener(this.listener);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Kitchen kitchen = (Kitchen)this.kitchenList.getAdapter().getItem(position);
        DataManager.getInstance().associateToKitchen(kitchen, DataManager.getInstance().getCurrentPerson());
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra(DataPassingEnum.KITCHEN.name(), kitchen);
        intent.putExtra(DataPassingEnum.PERSON.name(), DataManager.getInstance().getCurrentPerson());
        startActivity(intent);
    }
}

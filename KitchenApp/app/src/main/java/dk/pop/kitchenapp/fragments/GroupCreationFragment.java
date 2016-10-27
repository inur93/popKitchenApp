package dk.pop.kitchenapp.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.data.interfaces.FireBaseCallback;
import dk.pop.kitchenapp.models.Kitchen;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupCreationFragment extends Fragment implements View.OnClickListener{

    private AQuery aq;
    private ListView kitchenList;
    private ChildEventListener listener;
    private ArrayList<Kitchen> kitchens;
    private ArrayList<Kitchen> filteredKitchens;
    private View view;
    private ArrayAdapter<Kitchen> adapter;

    public GroupCreationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_group_creation, container, false);

        kitchens = new ArrayList<>();
        filteredKitchens = new ArrayList<>();
        // Required empty public constructor
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                kitchens.add(dataSnapshot.getValue(Kitchen.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

        aq = new AQuery(view);
        aq.id(R.id.group_creation_create_btn).clicked(this);
        aq.id(R.id.group_creation_search_btn).clicked(this);
        kitchenList = aq.id(R.id.group_creation_list_view).getListView();

        // Setup list adapter
        adapter = new ArrayAdapter<Kitchen>(view.getContext(), 0, filteredKitchens) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Kitchen kitchen = getItem(position);
                if(convertView == null){
                    convertView = new TextView(this.getContext());
                }
                ((TextView)convertView).setText(kitchen.getName());
                return convertView;
            }

            @NonNull
            @Override
            public Filter getFilter() {
                Filter filter = new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {
                        FilterResults result = new FilterResults();
                        if(constraint.length() <= 0){
                            result.count = kitchens.size();
                            result.values = kitchens;
                        }
                        else {
                            ArrayList<Kitchen> filteredValues = new ArrayList<>();
                            for (Kitchen kitchen :
                                    kitchens) {
                                if (kitchen.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                    filteredValues.add(kitchen);
                                }
                            }

                            result.count = filteredValues.size();
                            result.values = filteredValues;
                        }
                        return result;
                    }

                    @Override
                    protected void publishResults(CharSequence constraint, FilterResults results) {
                        filteredKitchens.clear();
                        filteredKitchens.addAll((ArrayList<Kitchen>) results.values);
                        notifyDataSetChanged();
                    }
                };
                return filter;
            }
        };

        kitchenList.setAdapter(adapter);

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
            case R.id.group_creation_search_btn:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        DataManager.getInstance().attachKitchenListener(this.listener);
    }

    @Override
    public void onStop() {
        super.onStop();
        DataManager.getInstance().detachKitchenListener(this.listener);
    }
}

package dk.pop.kitchenapp.fragments.kitchen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.fragments.kitchen.creation.KitchenOverviewActivityCreationFragment;
import dk.pop.kitchenapp.logging.LoggingTag;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;
import dk.pop.kitchenapp.navigation.FragmentExtension;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenOverviewFragment extends FragmentExtension implements View.OnClickListener{
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
        //this.allActivities.setAdapter(new KitchenActivitiesAdapter(view.getContext(), DataStorage.getInstance().getKitchen()));
        aq.id(R.id.kitchenOverviewCreateActivityBtn).clicked(this);
        aq.id(R.id.kitchenOverviewShowCalendarBtn).clicked(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.kitchenOverviewCreateActivityBtn:
                Log.d(LoggingTag.INFO.name(), "create activity btn was clicked");
                final KitchenOverviewActivityCreationFragment frag = new KitchenOverviewActivityCreationFragment();
                // single read
                DataManager.getInstance().getPerson(
                        FirebaseAuth.getInstance().getCurrentUser().getUid(),
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Object value = dataSnapshot.getValue();
                                Person p = dataSnapshot.getValue(Person.class);
                                frag.setPerson(p);
                                List<String> kitchens = p.getKitchenIds();
                                if(kitchens.size() > 0) {
                                    DataManager.getInstance().getKitchen(
                                            kitchens.get(0),
                                            new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Kitchen k = dataSnapshot.getValue(Kitchen.class);
                                                    frag.setKitchen(k);
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            }
                                    );
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );


                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.kitchen_overview_wrapper_fragment_placeholder,
                               frag)
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

package dk.pop.kitchenapp.fragments.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.models.ExpenseGroupActivity;
import dk.pop.kitchenapp.models.Person;

/**
 * Created by Runi on 01-12-2016.
 */

public class ActivityInfoExpenseFragment extends Fragment {

    private TextView price;
    private TextView responsible;
    private ValueEventListener listener;
    private String responsibleId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("inflate expense info");
        View view = inflater.inflate(R.layout.fragment_activity_info_expense, container, false);

        AQuery aq = new AQuery(view);

        this.price = aq.id(R.id.activity_info_expense_price).getTextView();
        this.responsible = aq.id(R.id.activity_info_expense_responsible).getTextView();

        Object o = getActivity().getIntent().getSerializableExtra(ActivityInfoFragment.EXTRA_ACTIVITY_INFO_OBJECT);
        if(o == null) return view;

        if(o instanceof ExpenseGroupActivity){
            ExpenseGroupActivity a = (ExpenseGroupActivity) o;
            this.price.setText(String.valueOf(a.getPrice()));
            this.responsible.setText(a.getResponsible());
            listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Person p = dataSnapshot.getValue(Person.class);
                    responsible.setText(p.getDisplayName());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
            responsibleId = a.getResponsible();
            DataManager.getInstance().getPerson(a.getResponsible(), listener);
        }



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        DataManager.getInstance().removePersonListener(responsibleId, listener);
    }
}
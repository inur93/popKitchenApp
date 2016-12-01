package dk.pop.kitchenapp.fragments.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.ExpenseListAdapter;
import dk.pop.kitchenapp.adapters.PersonListAdapter;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.listeners.ExpenseListViewListener;
import dk.pop.kitchenapp.listeners.ParticipantListViewListener;
import dk.pop.kitchenapp.models.DinnerGroupActivity;
import dk.pop.kitchenapp.models.ExpenseGroupActivity;
import dk.pop.kitchenapp.viewModels.PersonViewModel;

/**
 * Created by Runi on 01-12-2016.
 */

public class ActivityInfoDinnerFragment extends Fragment{
    private TextView total;
    private ListView expenses;
    private ListView participants;
    private ParticipantListViewListener participantListener;
    private ExpenseListViewListener expenseListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity_info_dinner, container, false);

        AQuery aq = new AQuery(view);

        this.total = aq.id(R.id.activity_info_dinner_total).getTextView();
        this.participants = aq.id(R.id.activity_info_dinner_participants).getListView();
        this.expenses = aq.id(R.id.activity_info_dinner_expenses).getListView();

        Object o = getActivity().getIntent().getSerializableExtra(ActivityInfoFragment.EXTRA_ACTIVITY_INFO_OBJECT);

        if(o == null) return view;
        if(o instanceof DinnerGroupActivity){
            DinnerGroupActivity a = (DinnerGroupActivity) o;
            this.total.setText(String.valueOf(a.getTotal()));

            final List<PersonViewModel> persons = new ArrayList<>();
            final PersonListAdapter adapter = new PersonListAdapter(getContext(), persons);
            this.participants.setAdapter(adapter);
            this.participantListener = new ParticipantListViewListener(persons, adapter);
            //TODO get persons participating in activity
            DataManager.getInstance()
                    .getPersonsFromKitchen(
                            DataManager.getInstance().getCurrentKitchen(), participantListener);

            final List<ExpenseGroupActivity> expenses = new ArrayList<>();
            final ExpenseListAdapter expenseAdapter = new ExpenseListAdapter(getContext(), expenses);
            this.expenses.setAdapter(expenseAdapter);
            this.expenseListener = new ExpenseListViewListener(expenses, expenseAdapter);
            DataManager.getInstance()
                    .getExpensesForActivity(a, expenseListener);
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
        DataManager.getInstance().removePersonsFromKitchenListener(
                DataManager.getInstance().getCurrentKitchen(), participantListener
        );
    }
}

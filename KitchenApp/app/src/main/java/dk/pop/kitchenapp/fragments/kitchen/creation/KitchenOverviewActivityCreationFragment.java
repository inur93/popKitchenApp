package dk.pop.kitchenapp.fragments.kitchen.creation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.logging.LoggingTag;
import dk.pop.kitchenapp.models.ActivityType;
import dk.pop.kitchenapp.models.DinnerGroupActivity;
import dk.pop.kitchenapp.models.ExpenseGroupActivity;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;
import dk.pop.kitchenapp.navigation.FragmentExtension;
import dk.pop.kitchenapp.viewModels.PersonViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenOverviewActivityCreationFragment extends FragmentExtension implements View.OnClickListener{


    private List<Person> participants;
    private List<Person> responsibles;

    private Person person = null;
    private Kitchen kitchen = null;


    public static final String TAG_GENERAL_INFO = "GENERAL_INFO";
    public static final String TAG_SELECT_PARTICIPANTS = "SELECT_PARTICIPANTS";
    public static final String TAG_DINNER_INFO = "DINNER_INFO";
    public static final String TAG_EXPENSE_INFO = "EXPENSE_INFO";
    public static final String TAG_CLEANING_INFO = "CLEANING_INFO";



    public KitchenOverviewActivityCreationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("inflate activity creation");
        View view = inflater.inflate(R.layout.fragment_kitchen_overview_activity_creation, container, false);

        AQuery aq = new AQuery(view);


        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_creation_content,
                        new KitchenOverviewActivityCreationGeneralInfoFragment(),
                        TAG_GENERAL_INFO)
                .commit();


        aq.id(R.id.activity_creation_next_fragment_btn).clicked(this);

/*
        this.participants = aq.id(R.id.activity_creation_participant_list).getListView();
        this.responsibles = aq.id(R.id.activity_creation_responsible_list).getListView();

        aq.id(R.id.activity_creation_type_spinner).getSpinner().setAdapter(new ActivityTypeSpinnerAdapter(getContext()));

        this.participants.setAdapter(
            new ActivityPersonNameRoomNoAdapter(view.getContext(), DataStorage.getInstance().getParticipants(), true));

        this.responsibles.setAdapter(
                new ActivityPersonNameRoomNoAdapter(view.getContext(), DataStorage.getInstance().getParticipants(), false));
*/

        return view;
    }


    @Override
    public void onClick(View v) {
        System.out.println("ids: content=" + R.id.activity_creation_content + "; generalInfo=" + R.layout.fragment_kitchen_overview_activity_creation_general_info);

        //getFragmentManager().findFragmentByTag(TAG_GENERAL_INFO).isVisible();
        //getFragmentManager().findFragmentById(R.id.activity_creation_content);

        if(v.getId() == R.id.activity_creation_next_fragment_btn){
            Log.d(LoggingTag.INFO.name(), "You pressed the next button");

            // get current fragment in fragment placeholder
            Fragment generalInfo = getFragmentManager().findFragmentByTag(TAG_GENERAL_INFO);

            AQuery aq = new AQuery(generalInfo.getView());
            ActivityType type =(ActivityType) aq.id(R.id.activity_creation_type_spinner).getSpinner().getSelectedItem();
            String title  = aq.id(R.id.activity_creation_title_edit).getText().toString();
            String description = aq.id(R.id.activity_creation_description_edit).getText().toString();
            String dateText = aq.id(R.id.activity_creation_date_edit).getText().toString();
            Date date = null;
            if(dateText != null && dateText.length() > 0){
                date = new Date(dateText);
            }

            boolean canBeCancelled = aq.id(R.id.activity_creation_cancellable_switch).isChecked();



            String toast = "";
            if(title == null || title.length() == 0) toast += "title can not be empty. ";
            if(date == null) toast += "you need to select a date. ";


            if(toast.length() > 0) {
                Toast.makeText(getContext(), toast, Toast.LENGTH_LONG).show();
                return;
            }


            DataManager mgm = DataManager.getInstance();
            switch (type.type){
                case DINNERACTIVITY:
                    String priceStr = aq.id(R.id.create_dinner_price_edit).getText().toString();
                    float price = Float.valueOf(priceStr.isEmpty() ? "0" : priceStr);
                    boolean isCancellable = aq.id(R.id.activity_creation_cancellable_switch).isChecked();
                    ExpenseGroupActivity expense = new ExpenseGroupActivity(
                            null,
                            title,
                            description,
                            date,
                            kitchen,
                            person,
                            person,
                            price);
                    List<ExpenseGroupActivity> expenses = new ArrayList<>();
                    expenses.add(expense);
                    mgm.createActivity(new DinnerGroupActivity(
                            null,
                            title,
                            description,
                            date,
                            kitchen,
                            person,
                            isCancellable,
                            price,
                            null,
                            responsibles
                            ));
                    break;
                case CLEANINGACTIVITY:
                    break;
                case EXPENSEACTIVITY:
                    break;
            }






            // Save the newly created GroupActivity here here
            //DataStorage.getInstance().createActivity();
            // Return to the previous fragment


        }
    }

    public void setKitchen(Kitchen kitchen){
        this.kitchen = kitchen;
    }
    public void setPerson(Person person){
        this.person = person;
    }

    public List<Person> getPersonList(ListView list){
        List<Person> selectedParticipants = new ArrayList<>();
        if(list == null) return selectedParticipants;
        for(int i = 0; i < list.getCount(); i++){
            PersonViewModel p = (PersonViewModel) list.getItemAtPosition(i);
            System.out.println("person list: " + p.getPerson().getRoomNumber() + ";" + p.isSelected());
            if(p.isSelected()){
                selectedParticipants.add(p.getPerson());
            }
        }
        return selectedParticipants;
    }
}

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
import dk.pop.kitchenapp.adapters.ActivityPersonNameRoomNoAdapter;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.data.IDataManager;
import dk.pop.kitchenapp.fragments.kitchen.KitchenOverviewFragment;
import dk.pop.kitchenapp.logging.LoggingTag;
import dk.pop.kitchenapp.models.ActivityType;
import dk.pop.kitchenapp.models.CleaningGroupActivity;
import dk.pop.kitchenapp.models.DinnerGroupActivity;
import dk.pop.kitchenapp.models.ExpenseGroupActivity;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;
import dk.pop.kitchenapp.models.enums.CleaningStatusEnum;
import dk.pop.kitchenapp.navigation.FragmentExtension;
import dk.pop.kitchenapp.viewModels.PersonViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenOverviewActivityCreationFragment extends FragmentExtension implements View.OnClickListener{



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


        try {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_creation_content,
                            new KitchenOverviewActivityCreationGeneralInfoFragment(),
                            TAG_GENERAL_INFO)
                    .commit();
        }catch (Throwable e){
            System.out.println("failed to start generalinfofragment in activityCreationFragmentuh");
        }

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
        //System.out.println("ids: content=" + R.id.activity_creation_content + "; generalInfo=" + R.layout.fragment_kitchen_overview_activity_creation_general_info);

        //getFragmentManager().findFragmentByTag(TAG_GENERAL_INFO).isVisible();
        //getFragmentManager().findFragmentById(R.id.activity_creation_content);

        if(v.getId() == R.id.activity_creation_next_fragment_btn){
            Log.d(LoggingTag.INFO.name(), "You pressed the next button");

            // get current fragment in fragment placeholder
            Fragment generalInfo = null;
            try {
                generalInfo = getFragmentManager().findFragmentByTag(TAG_GENERAL_INFO);
            }catch (Exception e){
                return;
            }

            AQuery aq = new AQuery(generalInfo.getView());
            ActivityType type =(ActivityType) aq.id(R.id.activity_creation_type_spinner).getSpinner().getSelectedItem();
            String title  = aq.id(R.id.activity_creation_title_edit).getText().toString();
            String description = aq.id(R.id.activity_creation_description_edit).getText().toString();
            String dateText = aq.id(R.id.activity_creation_date_edit).getText().toString();
            boolean isCancellable = aq.id(R.id.activity_creation_cancellable_switch).isChecked();
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

            person = DataManager.getInstance().getCurrentPerson();
            kitchen = DataManager.getInstance().getCurrentKitchen();




            IDataManager mgm = DataManager.getInstance();
            switch (type.type){
                case DINNERACTIVITY:
                    String priceStr = aq.id(R.id.create_dinner_price_edit).getText().toString();
                    float price = Float.valueOf(priceStr.isEmpty() ? "0" : priceStr);

                    ActivityPersonNameRoomNoAdapter adapterDinner = (ActivityPersonNameRoomNoAdapter) aq.id(R.id.activity_creation_dinner_participant_list).getListView().getAdapter();
                    List<Person> responsiblesDinner = adapterDinner.getSelectedPersons();

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
                            responsiblesDinner
                            ));
                    break;
                case CLEANINGACTIVITY:
                    ActivityPersonNameRoomNoAdapter adapter = (ActivityPersonNameRoomNoAdapter) aq.id(R.id.activity_creation_cleaning_participant_list).getListView().getAdapter();
                    List<Person> responsibles = adapter.getSelectedPersons();
                    mgm.createActivity(new CleaningGroupActivity(null,
                            title,
                            description,
                            date,
                            kitchen,
                            person,
                            isCancellable,
                            responsibles,
                            CleaningStatusEnum.SCHEDULED));
                    break;
                case EXPENSEACTIVITY:
                    float expPrice = Float.valueOf(aq.id(R.id.create_expense_price_edit).getText().toString());
                    mgm.createActivity(new ExpenseGroupActivity(null,
                            title,
                            description,
                            date,
                            kitchen,
                            person,
                            person,
                            expPrice));
                    break;
            }

            try {
                Fragment frag = getFragmentManager().findFragmentByTag(TAG_GENERAL_INFO);
                getFragmentManager().beginTransaction().remove(frag).commit();
            }catch (Exception e){
                System.out.println("error removing general info fragment");
            }
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.kitchen_overview_wrapper_fragment_placeholder,
                            new KitchenOverviewFragment(),
                            null)
                    .commit();



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

package dk.pop.kitchenapp.fragments.kitchen.creation;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidquery.AQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.ActivityPersonNameRoomNoAdapter;
import dk.pop.kitchenapp.adapters.ActivityTypeSpinnerAdapter;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.data.IDataManager;
import dk.pop.kitchenapp.fragments.kitchen.KitchenOverviewFragment;
import dk.pop.kitchenapp.listeners.ActivityTypeSpinnerListener;
import dk.pop.kitchenapp.logging.LoggingTag;
import dk.pop.kitchenapp.models.ActivityType;
import dk.pop.kitchenapp.models.CleaningGroupActivity;
import dk.pop.kitchenapp.models.DinnerGroupActivity;
import dk.pop.kitchenapp.models.ExpenseGroupActivity;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;
import dk.pop.kitchenapp.models.enums.CleaningStatusEnum;
import dk.pop.kitchenapp.viewModels.PersonViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityCreationFragment extends Fragment implements View.OnClickListener{

    private Spinner type;
    private EditText title;
    private EditText description;
    private AQuery canBeCancelled;

    private Calendar calendar;
    private Button datePicker;
    private DatePickerDialog.OnDateSetListener date;

    public static final String TAG_GENERAL_INFO = "GENERAL_INFO";
    public static final String TAG_SELECT_PARTICIPANTS = "SELECT_PARTICIPANTS";
    public static final String TAG_DINNER_INFO = "DINNER_INFO";
    public static final String TAG_EXPENSE_INFO = "EXPENSE_INFO";
    public static final String TAG_CLEANING_INFO = "CLEANING_INFO";



    public ActivityCreationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("inflate activity creation");
        View view = inflater.inflate(R.layout.fragment_activity_creation, container, false);

        AQuery aq = new AQuery(view);

        aq.id(R.id.activity_creation_next_fragment_btn).clicked(this);
        this.type = aq.id(R.id.activity_creation_type_spinner).getSpinner();
        this.title = aq.id(R.id.activity_creation_title_edit).getEditText();
        this.description = aq.id(R.id.activity_creation_description_edit).getEditText();

        // FIX - can't get switch from aquery
        this.canBeCancelled = aq.id(R.id.activity_creation_cancellable_switch);
        this.datePicker = aq.id(R.id.activity_creation_date_edit).getButton();

        this.type.setOnItemSelectedListener(new ActivityTypeSpinnerListener(this));
        aq.id(R.id.activity_creation_type_spinner).getSpinner().setAdapter(new ActivityTypeSpinnerAdapter(getContext()));

        calendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        datePicker.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus) {
                    new DatePickerDialog(getContext(),
                            date,
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });



        return view;
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.activity_creation_next_fragment_btn){
            Log.d(LoggingTag.INFO.name(), "You pressed the next button");

            AQuery aq = new AQuery(getView());
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

            Person person = DataManager.getInstance().getCurrentPerson();
            Kitchen kitchen = DataManager.getInstance().getCurrentKitchen();




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


            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.drawer_navigation_main_content,
                            new KitchenOverviewFragment(),
                            null)
                    .commit();



        }
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

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        datePicker.setText(sdf.format(calendar.getTime()));
    }
}

package dk.pop.kitchenapp.fragments.kitchen.creation;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.androidquery.AQuery;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.ActivityTypeSpinnerAdapter;
import dk.pop.kitchenapp.models.ActivityType;
import dk.pop.kitchenapp.navigation.FragmentExtension;

/**
 * Created by Runi on 22-10-2016.
 */

public class KitchenOverviewActivityCreationGeneralInfoFragment extends FragmentExtension implements View.OnClickListener {

    private Spinner type;
    private EditText title;
    private EditText description;
    private AQuery canBeCancelled;

    private Calendar calendar;
    private Button datePicker;
    private DatePickerDialog.OnDateSetListener date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        System.out.println("inflate creation general info");
        View view = inflater.inflate(R.layout.fragment_kitchen_overview_activity_creation_general_info, container, false);

        AQuery aq = new AQuery(view);

        this.type = aq.id(R.id.activity_creation_type_spinner).getSpinner();
        this.title = aq.id(R.id.activity_creation_title_edit).getEditText();
        this.description = aq.id(R.id.activity_creation_description_edit).getEditText();

        // FIX - can't get switch from aquery
        this.canBeCancelled = aq.id(R.id.activity_creation_cancellable_switch);
        this.datePicker = aq.id(R.id.activity_creation_date_edit).getButton();

        this.type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("item selected: " + parent.getCount() + ";" + position + ";" +  id);
                ActivityType type = (ActivityType) parent.getItemAtPosition(position);
                FragmentExtension fragment = null;
                String tag = null;
                System.out.println("type selected: " + type.name);
                switch (type.type){
                    case CLEANINGACTIVITY:
                        fragment = new KitchenOverviewCreationCleaningInfoFragment();
                        tag = KitchenOverviewActivityCreationFragment.TAG_CLEANING_INFO;
                        break;
                    case DINNERACTIVITY:
                        fragment = new KitchenOverviewCreationDinnerInfoFragment();
                        tag = KitchenOverviewActivityCreationFragment.TAG_DINNER_INFO;
                        break;
                    case EXPENSEACTIVITY:
                        fragment = new KitchenOverviewCreationExpenseInfoFragment();
                        tag = KitchenOverviewActivityCreationFragment.TAG_EXPENSE_INFO;
                        break;
                    case KITCHEN:
                        System.out.println("kitchen selected. it is not valid");
                        break;
                    case PERSON:
                        System.out.println("person selected. it is not valid");
                        default:
                            System.out.println("wrong value selected: " + type.name + ";" + type.type);
                }
                if(tag != null && fragment != null) {
                    getChildFragmentManager()
                            .beginTransaction()
                            .replace(R.id.activity_creation_custom_content,
                                    fragment,
                                    tag)
                            .commit();
                }else{
                    getChildFragmentManager()
                            .beginTransaction()
                            .replace(R.id.activity_creation_custom_content,
                                    null)
                            .commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        aq.id(R.id.activity_creation_type_spinner).getSpinner().setAdapter(new ActivityTypeSpinnerAdapter(getContext()));

        calendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
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

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        datePicker.setText(sdf.format(calendar.getTime()));
    }


    @Override
    public void onClick(View v) {

    }
}

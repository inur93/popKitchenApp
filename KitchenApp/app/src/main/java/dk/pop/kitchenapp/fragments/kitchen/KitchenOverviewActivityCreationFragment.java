package dk.pop.kitchenapp.fragments.kitchen;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.androidquery.AQuery;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.ActivityPersonNameRoomNoAdapter;
import dk.pop.kitchenapp.data.DataStorage;
import dk.pop.kitchenapp.logging.LoggingTag;
import dk.pop.kitchenapp.navigation.FragmentExtension;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenOverviewActivityCreationFragment extends FragmentExtension implements View.OnClickListener{


    private ListView participants;
    private ListView responsibles;
    private EditText datePicker;
    private Calendar calendar;

    public KitchenOverviewActivityCreationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kitchen_overview_activity_creation, container, false);

        AQuery aq = new AQuery(view);

        aq.id(R.id.kitchen_overview_activity_creation_save_btn).clicked(this);


        this.participants = aq.id(R.id.activity_creation_participant_list).getListView();
        this.participants.setAdapter(
            new ActivityPersonNameRoomNoAdapter(view.getContext(), DataStorage.getInstance().getParticipants()));

        this.responsibles = aq.id(R.id.activity_creation_responsible_list).getListView();
        this.responsibles.setAdapter(
                new ActivityPersonNameRoomNoAdapter(view.getContext(), DataStorage.getInstance().getParticipants()));

        datePicker = aq.id(R.id.activity_creation_date_edit).getEditText();
        //datepicker.setOnClickListener();

        calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

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
                    InputMethodManager imm =  (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    System.out.println(v.getWindowToken());
                    new DatePickerDialog(getContext(),
                            date,
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                System.out.println("focus changed: " + hasFocus);
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
        if(v.getId() == R.id.kitchen_overview_activity_creation_save_btn){
            Log.d(LoggingTag.INFO.name(), "You pressed the save button");

            // Save the newly created GroupActivity here here

            // Return to the previous fragment
        }
    }
}

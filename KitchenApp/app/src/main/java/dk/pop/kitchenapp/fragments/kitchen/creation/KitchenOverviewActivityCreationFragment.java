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
import dk.pop.kitchenapp.logging.LoggingTag;
import dk.pop.kitchenapp.models.ActivityType;
import dk.pop.kitchenapp.models.Person;
import dk.pop.kitchenapp.navigation.FragmentExtension;
import dk.pop.kitchenapp.viewModels.PersonViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenOverviewActivityCreationFragment extends FragmentExtension implements View.OnClickListener{


    private List<Person> participants;
    private List<Person> responsibles;

    private ActivityType type = null;
    private String title = null;
    private String description = null;
    private Date date = null;
    private boolean canBeCancelled = false;

    private final String TAG_GENERAL_INFO = "GENERAL_INFO";
    private final String TAG_SELECT_PARTICIPANTS = "SELECT_PARTICIPANTS";
    private FragmentExtension selectParticipants = new KitchenOverviewCreationSelectParticipantsResponsibles();


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
            Fragment activeFrag = getFragmentManager().findFragmentByTag(TAG_GENERAL_INFO);
            //getFragmentManager().findFragmentById(R.id.activity_creation_content);
            if(activeFrag == null || !activeFrag.isVisible()){
                activeFrag = getFragmentManager().findFragmentByTag(TAG_SELECT_PARTICIPANTS);
            }

            AQuery aq = new AQuery(activeFrag.getView());

            if(activeFrag.getTag().equals(TAG_GENERAL_INFO)){

                ActivityType type =(ActivityType) aq.id(R.id.activity_creation_type_spinner).getSpinner().getSelectedItem();
                if(type != null) this.type = type;

                this.title  = aq.id(R.id.activity_creation_title_edit).getText().toString();
                this.description = aq.id(R.id.activity_creation_description_edit).getText().toString();
                String dateText = aq.id(R.id.activity_creation_date_edit).getText().toString();
                if(dateText != null && dateText.length() > 0){
                    this.date = new Date(dateText);
                }

                this.canBeCancelled = aq.id(R.id.activity_creation_cancellable_switch).isChecked();

                String toast = "";
                if(title == null || title.length() == 0) toast += "title can not be empty. ";
                if(date == null) toast += "you need to select a date. ";

                if(toast.length() > 0) {
                    resetValues();
                    Toast.makeText(getContext(), toast, Toast.LENGTH_LONG).show();
                    return;
                }

                getFragmentManager().beginTransaction()
                        .replace(R.id.activity_creation_content,
                        selectParticipants,
                                TAG_SELECT_PARTICIPANTS)
                        .addToBackStack(null)
                        .commit();
            }else if(activeFrag.getTag().equals(TAG_SELECT_PARTICIPANTS)){
                ListView responsiblesList = aq.id(R.id.activity_creation_responsible_list).getListView();
                this.responsibles = getPersonList(responsiblesList);

                String toast = "";
                if(this.responsibles.size() == 0) toast += "You need to select at least one responsible.\n";

                ListView participantsList = aq.id(R.id.activity_creation_participant_list).getListView();
                this.participants = getPersonList(participantsList);

                if(this.participants.size() == 0) toast += "You need to select at least one participant.";

                if(toast.length() > 0){
                    Toast.makeText(getContext(), toast, Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getContext(), "not implemented yet", Toast.LENGTH_LONG).show();
            }

            //ActivityType item = (ActivityType) aq.id(R.id.activity_creation_type_spinner).getSpinner().getSelectedItem();

            //System.out.println("selected item: " + item.name);

/*

            ObjectTypeEnum type = ((ActivityType) this.type.getSelectedItem()).type;
            String title = this.title.getText().toString();
            String description = this.description.getText().toString();
            boolean canBeCancelled = this.canBeCancelled.isChecked();
            System.out.println("is checked: " + canBeCancelled);


            List<Person> selectedParticipants = getPersonList(this.participants);
            List<Person> selectedResponsible = getPersonList(this.responsibles);


            DataStorage.getInstance().createActivity(
                    type,
                    title,
                    description,
                    new Date(datePicker.getText().toString()),
                    selectedResponsible,
                    selectedParticipants);
            // Save the newly created GroupActivity here here
            //DataStorage.getInstance().createActivity();
            // Return to the previous fragment

            */
        }
    }

    public void resetValues(){
        this.canBeCancelled = false;
        this.title = null;
        this.type = null;
        this.description = null;
        this.date = null;
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

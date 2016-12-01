package dk.pop.kitchenapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.models.Person;
import dk.pop.kitchenapp.viewModels.PersonViewModel;

/**
 * Created by Runi on 06-10-2016.
 */

public class ActivityPersonNameRoomNoAdapter extends ArrayAdapter<PersonViewModel> {
    private List<PersonViewModel> persons = null;
    public ActivityPersonNameRoomNoAdapter(Context context, List objects) {
        super(context, 0, objects);
        persons = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PersonViewModel act = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_list_view_person_name_roomno, parent, false);
            //System.out.println("setting view was null");
        }else{
            //System.out.println("setting view was not null");
        }


        AQuery aq = new AQuery(convertView);
        aq.id(R.id.view_list_view_person_name_roomno_name).text(act.getPerson().getDisplayName());
        aq.id(R.id.view_list_view_person_name_roomno_roomno).text(String.valueOf(act.getPerson().getRoomNumber()));
        CheckBox isSelectedCheckBox = aq.id(R.id.view_list_view_person_name_roomno_selected).getCheckBox();
       // System.out.println("setting: get is checked: " + act.getPerson().getGoogleId() + "=>"+ act.isSelected());
        isSelectedCheckBox.setChecked(act.isSelected());

        isSelectedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            int pos = position;
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               // System.out.println("setting is checked: " + pos + "-" + isChecked);
                getItem(pos).setIsSelected(isChecked);
                //notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public List<Person> getSelectedPersons(){
        List<Person> selectedPersons = new ArrayList<>();
        for(PersonViewModel p : persons){
            if(p.isSelected()){
                selectedPersons.add(p.getPerson());
            }
        }
        return selectedPersons;
    }



}

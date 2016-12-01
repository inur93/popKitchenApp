package dk.pop.kitchenapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.androidquery.AQuery;

import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.viewModels.PersonViewModel;

/**
 * Created by Runi on 01-12-2016.
 */

public class PersonListAdapter extends ArrayAdapter<PersonViewModel> {
    private List<PersonViewModel> persons = null;
    public PersonListAdapter(Context context, List objects) {
        super(context, 0, objects);
        persons = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PersonViewModel act = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.person_list_item, parent, false);
        }

        AQuery aq = new AQuery(convertView);
        aq.id(R.id.person_list_item_name).text(act.getPerson().getDisplayName());
        aq.id(R.id.person_list_item_room_no).text(String.valueOf(act.getPerson().getRoomNumber()));

        return convertView;
    }

}

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
import dk.pop.kitchenapp.models.Person;

/**
 * Created by Runi on 06-10-2016.
 */

public class ActivityPersonNameRoomNoAdapter extends ArrayAdapter<Person> {
    public ActivityPersonNameRoomNoAdapter(Context context, List objects) {
        super(context, 0, objects);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person act = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_list_view_person_name_roomno, parent, false);
        }
        AQuery aq = new AQuery(convertView);
        aq.id(R.id.view_list_view_person_name_roomno_name).text(act.getGoogleId());
        aq.id(R.id.view_list_view_person_name_roomno_roomno).text(String.valueOf(act.getRoomNumber()));

        return convertView;
    }
}

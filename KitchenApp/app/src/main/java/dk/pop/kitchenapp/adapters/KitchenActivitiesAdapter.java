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
import dk.pop.kitchenapp.models.GroupActivity;

/**
 * Created by dickow on 10/6/16.
 */

public class KitchenActivitiesAdapter extends ArrayAdapter<GroupActivity> {
    public KitchenActivitiesAdapter(Context context, List<GroupActivity> objects) {
        super(context, 0 ,objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupActivity act = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_kitchen_overview_list_view_activities, parent, false);
        }
        AQuery aq = new AQuery(convertView);
        aq.id(R.id.kitchen_overview_list_view_person).text(act.getCreatedBy().getGoogleId());
        aq.id(R.id.kitchen_overview_list_view_title).text(act.getTitle());
        aq.id(R.id.kitchen_overview_list_view_date).text(act.getDate().toString());

        return convertView;
    }
}

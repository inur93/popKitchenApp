package dk.pop.kitchenapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.models.ActivityType;
import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;

/**
 * Created by Runi on 22-10-2016.
 */

public class ActivityTypeSpinnerAdapter extends ArrayAdapter<ActivityType> {

    private List<ActivityType> activityTypes;


    public ActivityTypeSpinnerAdapter(Context context, List<ActivityType> activityTypes) {
        super(context, 0, activityTypes);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ActivityType type = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_list_view_activity_type, parent, false);
        }
        AQuery aq = new AQuery(convertView);
        aq.id(R.id.view_list_view_activity_type_name).text(type.name);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}

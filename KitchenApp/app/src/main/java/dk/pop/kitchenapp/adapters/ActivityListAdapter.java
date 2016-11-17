package dk.pop.kitchenapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.models.GroupActivity;


/**
 * Created by dickow on 11/10/16.
 */

public class ActivityListAdapter extends ArrayAdapter<GroupActivity> {

    public ActivityListAdapter(Context context, List<GroupActivity> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupActivity act = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_activity_list, parent, false);
        }

        // Set the image for the activity
        ImageView icon = (ImageView)convertView.findViewById(R.id.activity_list_image);
        switch (act.getType()){

            case DINNERACTIVITY:
                icon.setImageResource(R.drawable.dinner);
                break;
            case CLEANINGACTIVITY:
                icon.setImageResource(R.drawable.vacuum);
                break;
            case EXPENSEACTIVITY:
                icon.setImageResource(R.drawable.money_bag_with_dollar_symbol);
                break;
        }

        // Set the textual information
        ((TextView)convertView.findViewById(R.id.activity_list_title_text)).setText(act.getTitle());
        ((TextView)convertView.findViewById(R.id.activity_list_description)).setText(act.getDescription());
        String formattedDate;
        Date date = new Date(act.getDate());
        formattedDate = new SimpleDateFormat("EEE - dd - MMM - yyyy").format(date);

        ((TextView)convertView.findViewById(R.id.activity_list_date)).setText(formattedDate);

        return convertView;
    }
}

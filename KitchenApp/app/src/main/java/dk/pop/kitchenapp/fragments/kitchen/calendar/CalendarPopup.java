package dk.pop.kitchenapp.fragments.kitchen.calendar;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.ActivityListAdapter;
import dk.pop.kitchenapp.models.GroupActivity;

/**
 * Created by dickow on 11/10/16.
 */

public class CalendarPopup {
    private String headerText;

    public CalendarPopup(String header){
        this.headerText = header;
    }

    public CalendarPopup(Date date){
        this.headerText = new SimpleDateFormat("EEE - dd - MMM - yyyy").format(date);
    }

    public View getView(Context ctx, List<Event> events){
        View view = View.inflate(ctx, R.layout.popup_event_list, null);
        ListView list = (ListView)view.findViewById(R.id.popup_list_view);
        TextView header = (TextView)view.findViewById(R.id.popup_list_header);
        header.setText(headerText);

        ArrayList<GroupActivity> activities = new ArrayList<>();
        for (Event event : events) {
            activities.add((GroupActivity)event.getData());
        }

        ActivityListAdapter adapter = new ActivityListAdapter(ctx, activities);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }
}

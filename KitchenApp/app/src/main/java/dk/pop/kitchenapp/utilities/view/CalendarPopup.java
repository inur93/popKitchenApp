package dk.pop.kitchenapp.utilities.view;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.adapters.ActivityListAdapter;
import dk.pop.kitchenapp.fragments.activity.ActivityInfoFragment;
import dk.pop.kitchenapp.models.GroupActivity;

/**
 * Created by dickow on 11/10/16.
 */

public class CalendarPopup {
    private String headerText;
    private ListView list;
    public CalendarPopup(String header){
        this.headerText = header;
    }

    public CalendarPopup(Date date){
        this.headerText = new SimpleDateFormat("EEE - dd - MMM - yyyy").format(date);
    }

    public View getView(final Fragment parentFragment, List<Event> events, final PopupWindow popupWindow){
        View view = View.inflate(parentFragment.getContext(), R.layout.popup_event_list, null);
        list = (ListView)view.findViewById(R.id.popup_list_view);
        TextView header = (TextView)view.findViewById(R.id.popup_list_header);
        header.setText(headerText);

        ArrayList<GroupActivity> activities = new ArrayList<>();
        for (Event event : events) {
            activities.add((GroupActivity)event.getData());
        }

        ActivityListAdapter adapter = new ActivityListAdapter(parentFragment.getContext(), activities);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupActivity activity = (GroupActivity) list.getItemAtPosition(position);
                //DataManager.getInstance().setExpenseToShow(activity);
                parentFragment.getActivity().getIntent().putExtra(ActivityInfoFragment.EXTRA_ACTIVITY_INFO_OBJECT, activity);
                parentFragment.getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.drawer_navigation_main_content, new ActivityInfoFragment(), null)
                        .addToBackStack(null)
                        .commit();
                popupWindow.dismiss();
            }
        });

        adapter.notifyDataSetChanged();

        return view;
    }
}

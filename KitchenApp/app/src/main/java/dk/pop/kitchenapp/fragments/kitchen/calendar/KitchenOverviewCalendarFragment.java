package dk.pop.kitchenapp.fragments.kitchen.calendar;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.models.GroupActivity;
import dk.pop.kitchenapp.models.factories.ActivityFactory;
import dk.pop.kitchenapp.navigation.FragmentExtension;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenOverviewCalendarFragment extends FragmentExtension {
    private ChildEventListener listener;
    private TextView monthHeader;

    public KitchenOverviewCalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kitchen_overview_calendar, container, false);

        final CompactCalendarView calendarView = (CompactCalendarView)view.findViewById(R.id.kitchen_overview_calendar_view);
        monthHeader = (TextView)view.findViewById(R.id.kitchen_overview_calendar_header);
        monthHeader.setText(new SimpleDateFormat("MMM yyyy").format(new Date()));

        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GroupActivity act = ActivityFactory.CreateActivity(dataSnapshot);
                Event event = new Event(Color.GREEN, new Date(act.getDate()).getTime(), act);
                calendarView.addEvent(event, true);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                GroupActivity act = ActivityFactory.CreateActivity(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                GroupActivity act = ActivityFactory.CreateActivity(dataSnapshot);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        DataManager.getInstance().getActivitiesForKitchen(DataManager.getInstance().getCurrentKitchen(), listener);

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date date) {
                List<Event> events = calendarView.getEvents(date);
                if(!events.isEmpty()){
                    PopupWindow popup = new PopupWindow(getContext());
                    popup.setFocusable(true);
                    popup.setContentView(new CalendarPopup(date).getView(getContext(), events));
                    popup.showAtLocation(getView(), Gravity.CENTER, 10, 10);
                }
            }

            @Override
            public void onMonthScroll(Date date) {
                monthHeader.setText(new SimpleDateFormat("MMM - yyyy").format(date));
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        DataManager.getInstance().detachActivitiesForKitchen(DataManager.getInstance().getCurrentKitchen(), listener);
    }
}

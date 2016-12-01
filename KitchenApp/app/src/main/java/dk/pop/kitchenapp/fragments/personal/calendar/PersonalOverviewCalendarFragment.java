package dk.pop.kitchenapp.fragments.personal.calendar;


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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.models.GroupActivity;
import dk.pop.kitchenapp.models.factories.ActivityFactory;
import dk.pop.kitchenapp.utilities.view.CalendarPopup;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalOverviewCalendarFragment extends Fragment {
    private ChildEventListener listener;
    private TextView monthHeader;
    private ArrayList<GroupActivity> activities;

    public PersonalOverviewCalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_overview_calendar, container, false);
        final CompactCalendarView calendarView = (CompactCalendarView)view.findViewById(R.id.personal_overview_calendar_view);
        monthHeader = (TextView)view.findViewById(R.id.personal_overview_calendar_header);
        monthHeader.setText(new SimpleDateFormat("MMM yyyy").format(new Date()));

        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GroupActivity act = ActivityFactory.CreateActivity(dataSnapshot);
                // If the kitchen of the activity does not equal the current kitchen return
                if(!(act.getKitchen().equals(DataManager.getInstance().getCurrentKitchen().getName()))){
                    return;
                }

                for(int i = 0; i < activities.size(); i++){
                    // If activites already contains the activity return
                    if(activities.get(i).getId().equals(act.getId())){
                        return;
                    }
                }

                activities.add(act);
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

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date date) {
                List<Event> events = calendarView.getEvents(date);
                if(!events.isEmpty()){
                    PopupWindow popup = new PopupWindow(getContext());
                    popup.setFocusable(true);
                    popup.setContentView(new CalendarPopup(date).getView(PersonalOverviewCalendarFragment.this, events, popup));
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
    public void onStart(){
        super.onStart();
        DataManager.getInstance().getActivitiesForPerson(DataManager.getInstance().getCurrentPerson(), listener);
        activities = new ArrayList<>();
    }

    @Override
    public void onStop() {
        super.onStop();
        DataManager.getInstance().detachActivitiesForPerson(DataManager.getInstance().getCurrentPerson(), listener);
        activities = null;
    }
}

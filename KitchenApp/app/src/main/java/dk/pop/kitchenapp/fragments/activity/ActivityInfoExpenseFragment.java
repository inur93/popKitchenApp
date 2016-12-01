package dk.pop.kitchenapp.fragments.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.models.CleaningGroupActivity;
import dk.pop.kitchenapp.models.GroupActivity;

/**
 * Created by Runi on 01-12-2016.
 */

public class ActivityInfoExpenseFragment extends Fragment {

    private ImageView image;
    private TextView title;
    private TextView description;
    private TextView date;
    private CheckBox canBeCancelled;

    public static final String EXTRA_ACTIVITY_INFO_OBJECT = "EXTRA_ACTIVITY_INFO_OBJECT";

    public static final String TAG_GENERAL_INFO = "GENERAL_INFO";
    public static final String TAG_SELECT_PARTICIPANTS = "SELECT_PARTICIPANTS";
    public static final String TAG_DINNER_INFO = "DINNER_INFO";
    public static final String TAG_EXPENSE_INFO = "EXPENSE_INFO";
    public static final String TAG_CLEANING_INFO = "CLEANING_INFO";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("inflate expense info");
        View view = inflater.inflate(R.layout.fragment_activity_info, container, false);

        AQuery aq = new AQuery(view);

        this.image = aq.id(R.id.activity_info_image).getImageView();
        this.title = aq.id(R.id.activity_info_title).getTextView();
        this.date = aq.id(R.id.activity_info_date).getTextView();
        this.canBeCancelled = aq.id(R.id.activity_info_can_be_cancelled).getCheckBox();
        this.description = aq.id(R.id.activity_info_description).getTextView();
        Object o = getActivity().getIntent().getSerializableExtra(EXTRA_ACTIVITY_INFO_OBJECT);
        if(o == null) return view;

        if(o instanceof GroupActivity){
            GroupActivity a = (GroupActivity) o;
            this.title.setText(a.getTitle());
            this.date.setText(a.getDate());
            this.description.setText(a.getDescription());
        }

        Fragment childFragment = null;
        if(o instanceof CleaningGroupActivity){

        }



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
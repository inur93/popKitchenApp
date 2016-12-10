package dk.pop.kitchenapp.listeners;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.fragments.activity.ActivityInfoFragment;
import dk.pop.kitchenapp.models.GroupActivity;

/**
 * Created by Runi on 03-12-2016.
 */

public class ActivityOnItemClickListener implements AdapterView.OnItemClickListener {
    private Fragment fragment;
    private List<GroupActivity> items;
    public ActivityOnItemClickListener(Fragment fragment, List<GroupActivity> items){
        this.fragment = fragment;
        this.items = items;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GroupActivity activity = items.get(position);
        //DataManager.getInstance().setExpenseToShow(activity);
        fragment.getActivity().getIntent().putExtra(ActivityInfoFragment.EXTRA_ACTIVITY_INFO_OBJECT, activity);
        fragment.getFragmentManager()
                .beginTransaction()
                .replace(R.id.drawer_navigation_main_content, new ActivityInfoFragment(), null)
                .addToBackStack(null)
                .commit();
    }
}

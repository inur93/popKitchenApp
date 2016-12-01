package dk.pop.kitchenapp.listeners;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.fragments.activity.ActivityCreationFragment;
import dk.pop.kitchenapp.fragments.activity.KitchenOverviewCreationCleaningInfoFragment;
import dk.pop.kitchenapp.fragments.activity.KitchenOverviewCreationDinnerInfoFragment;
import dk.pop.kitchenapp.fragments.activity.KitchenOverviewCreationExpenseInfoFragment;
import dk.pop.kitchenapp.models.ActivityType;

/**
 * Created by Runi on 10-11-2016.
 */
public class ActivityTypeSpinnerListener implements AdapterView.OnItemSelectedListener {

    private Fragment parentFragment;
    public ActivityTypeSpinnerListener(Fragment fragment){
        this.parentFragment = fragment;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ActivityType type = (ActivityType) parent.getItemAtPosition(position);
        String tag = null;
        Fragment fragment = null;
        System.out.println("type selected: " + type.name);
        switch (type.type){
            case CLEANINGACTIVITY:
                fragment = new KitchenOverviewCreationCleaningInfoFragment();
                tag = ActivityCreationFragment.TAG_CLEANING_INFO;
                break;
            case DINNERACTIVITY:
                fragment = new KitchenOverviewCreationDinnerInfoFragment();
                tag = ActivityCreationFragment.TAG_DINNER_INFO;
                break;
            case EXPENSEACTIVITY:
                fragment = new KitchenOverviewCreationExpenseInfoFragment();
                tag = ActivityCreationFragment.TAG_EXPENSE_INFO;
                break;
            case KITCHEN:
                System.out.println("kitchen selected. it is not valid");
                break;
            case PERSON:
                System.out.println("person selected. it is not valid");
                break;
            default:
                System.out.println("wrong value selected: " + type.name + ";" + type.type);
        }
        if(tag != null && fragment != null) {
            this.parentFragment.getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_creation_custom_content,
                            fragment,
                            tag)
                    .commit();
        }else{
            this.parentFragment.getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_creation_custom_content,
                            null)
                    .commit();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

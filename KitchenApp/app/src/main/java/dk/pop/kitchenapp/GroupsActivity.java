package dk.pop.kitchenapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.fragments.groups.GroupCreationFragment;
import dk.pop.kitchenapp.fragments.groups.MyGroupsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsActivity extends AppCompatActivity{

    public static final String BUNDLE_KEY_FRAGMENT_TO_SHOW = "FRAGMENT_TO_SHOW";
    public GroupsActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Fragment fragment = null;
        String fragmentToShow = getIntent().getStringExtra(BUNDLE_KEY_FRAGMENT_TO_SHOW);

        if(DataManager.getInstance().getCurrentPerson().getKitchens().isEmpty()
                || GroupCreationFragment.class.getName().equals(fragmentToShow)){
                // Route to first personal_calendar fragment
            fragment = new GroupCreationFragment();
        }else{
            fragment = new MyGroupsFragment();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.groups_activity_content, fragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

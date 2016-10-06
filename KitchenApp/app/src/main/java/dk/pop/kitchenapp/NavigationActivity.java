package dk.pop.kitchenapp;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidquery.AQuery;

import dk.pop.kitchenapp.fragments.kitchen.KitchenOverviewWrapperFragment;
import dk.pop.kitchenapp.fragments.personal.PersonalOverviewFragment;
import dk.pop.kitchenapp.navigation.ActivityNavigation;

public class NavigationActivity extends ActivityNavigation{

    FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        AQuery aq = new AQuery(this);

        // Setup the tab bar
        tabHost = (FragmentTabHost) findViewById(R.id.navigationActivityTabHost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.tabContent);

        tabHost.addTab(tabHost.newTabSpec(getString(R.string.kitchenTabText)).setIndicator(getString(R.string.kitchenTabText)), KitchenOverviewWrapperFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.personalTabText)).setIndicator(getString(R.string.personalTabText)), PersonalOverviewFragment.class, null);
    }
}

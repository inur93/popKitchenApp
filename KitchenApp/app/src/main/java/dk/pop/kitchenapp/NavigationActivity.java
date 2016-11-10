package dk.pop.kitchenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuInflater;

import com.androidquery.AQuery;

import dk.pop.kitchenapp.data.AuthenticationManager;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.data.dataPassing.DataPassingEnum;
import dk.pop.kitchenapp.data.dataPassing.PrimaryActivityStateEnum;
import dk.pop.kitchenapp.fragments.kitchen.KitchenOverviewWrapperFragment;
import dk.pop.kitchenapp.fragments.personal.PersonalOverviewFragment;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;
import dk.pop.kitchenapp.navigation.ActivityNavigation;

public class NavigationActivity extends ActivityNavigation{

    FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            Kitchen kitchen = (Kitchen)savedInstanceState.getSerializable(DataPassingEnum.KITCHEN.name());
            Person person = (Person)savedInstanceState.getSerializable(DataPassingEnum.PERSON.name());

            DataManager.getInstance().setCurrentPerson(person);
            DataManager.getInstance().setCurrentKitchen(kitchen);
        }
        else{
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            if(extras != null) {
                Kitchen kitchen = (Kitchen) extras.getSerializable(DataPassingEnum.KITCHEN.name());
                Person person = (Person) extras.getSerializable(DataPassingEnum.PERSON.name());

                DataManager.getInstance().setCurrentPerson(person);
                DataManager.getInstance().setCurrentKitchen(kitchen);
            }else{
                System.out.println("error extras is null");
            }
        }

        setContentView(R.layout.activity_navigation);

        // Setup the tab bar
        tabHost = (FragmentTabHost) findViewById(R.id.navigationActivityTabHost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.tabContent);

        tabHost.addTab(tabHost.newTabSpec(getString(R.string.kitchenTabText)).setIndicator(getString(R.string.kitchenTabText)), KitchenOverviewWrapperFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.personalTabText)).setIndicator(getString(R.string.personalTabText)), PersonalOverviewFragment.class, null);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.general_options_menu_group_creation:
                intent = new Intent(this, PrimaryActivity.class);
                intent.putExtra(DataPassingEnum.STATE.name(), PrimaryActivityStateEnum.GROUPCREATION.name());
                startActivity(intent);
                break;
            case R.id.general_options_menu_my_groups:
                intent = new Intent(this, PrimaryActivity.class);
                intent.putExtra(DataPassingEnum.STATE.name(), PrimaryActivityStateEnum.MYGROUPS.name());
                startActivity(intent);
                break;
            case R.id.general_options_menu_logout:
                AuthenticationManager.getInstance().getFirebaseAuth().signOut();
                Intent signInIntent = new Intent(this, SignInActivity.class);
                startActivity(signInIntent);
                break;
            case R.id.general_options_menu_personal_info:
                Intent personalPageInten = new Intent(this, PersonalPageActivity.class);
                startActivity(personalPageInten);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(DataPassingEnum.KITCHEN.name(), DataManager.getInstance().getCurrentKitchen());
        outState.putSerializable(DataPassingEnum.PERSON.name(), DataManager.getInstance().getCurrentPerson());
    }
}

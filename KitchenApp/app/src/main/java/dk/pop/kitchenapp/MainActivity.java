package dk.pop.kitchenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dk.pop.kitchenapp.adapters.DrawerListAdapter;
import dk.pop.kitchenapp.data.AuthenticationManager;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.data.dataPassing.DataPassingEnum;
import dk.pop.kitchenapp.data.dataPassing.PrimaryActivityStateEnum;
import dk.pop.kitchenapp.data.interfaces.FireBaseCallback;
import dk.pop.kitchenapp.fragments.GroupCreationFragment;
import dk.pop.kitchenapp.fragments.kitchen.KitchenOverviewFragment;
import dk.pop.kitchenapp.fragments.kitchen.calendar.KitchenOverviewCalendarFragment;
import dk.pop.kitchenapp.fragments.kitchen.creation.ActivityCreationFragment;
import dk.pop.kitchenapp.fragments.personal.PersonalOverviewFragment;
import dk.pop.kitchenapp.fragments.personal.PersonalPageFragment;
import dk.pop.kitchenapp.logging.LoggingTag;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;
import dk.pop.kitchenapp.viewModels.NavItem;

/**
 * Created by Runi on 10-11-2016.
 */

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    ListView drawerNavigationList;
    RelativeLayout drawerNavigationPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout drawerLayout;
    private PrimaryActivityStateEnum state = null;


    ArrayList<NavItem> navItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navItems.add(new NavItem(NavItem.NAV_ITEM_TYPE.KITCHEN_OVERVIEW,"Kitchen Overview", R.drawable.ic_exit_to_app_black_24dp));
        navItems.add(new NavItem(NavItem.NAV_ITEM_TYPE.PERSONAL_OVERVIEW, "Personal Overview", R.drawable.ic_exit_to_app_black_24dp));
        navItems.add(new NavItem(NavItem.NAV_ITEM_TYPE.CREATE_ACTIVITY, "Create Activity", R.drawable.ic_exit_to_app_black_24dp));
        navItems.add(new NavItem(NavItem.NAV_ITEM_TYPE.EDIT_PERSONAL_INFO, "Edit Personal Information", R.drawable.ic_exit_to_app_black_24dp));
        navItems.add(new NavItem(NavItem.NAV_ITEM_TYPE.PERSONAL_CALENDAR, "Personal Calendar", R.drawable.ic_exit_to_app_black_24dp));
        navItems.add(new NavItem(NavItem.NAV_ITEM_TYPE.KITCHEN_CALENDAR, "Kitchen Calendar", R.drawable.ic_exit_to_app_black_24dp));

        // DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.main_activity);

        // Populate the Navigtion Drawer with options
        drawerNavigationPane = (RelativeLayout) findViewById(R.id.drawer_navigation_pane);
        drawerNavigationList = (ListView) findViewById(R.id.drawer_navigation_list);
        DrawerListAdapter adapter = new DrawerListAdapter(this, navItems);
        drawerNavigationList.setAdapter(adapter);

        // Drawer Item click listeners
        drawerNavigationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

        String strState;
        if(savedInstanceState != null){
            strState = savedInstanceState.getString(DataPassingEnum.STATE.name());
        }
        else{
            if(getIntent().hasExtra(DataPassingEnum.STATE.name())){
                strState = getIntent().getExtras().getString(DataPassingEnum.STATE.name());
            }
            else{
                strState = null;
            }
        }

        if(strState != null){
            this.state = PrimaryActivityStateEnum.valueOf(strState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (AuthenticationManager.getInstance().getFirebaseUser() == null) {
            Intent signInIntent = new Intent(this, SignInActivity.class);
            startActivity(signInIntent);
            return;
        }
        if(DataManager.getInstance().getCurrentPerson() != null
                && DataManager.getInstance().getCurrentKitchen() != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.drawer_navigation_main_content, new KitchenOverviewFragment())
                    .commit();
            setTitle(navItems.get(0).title); //TODO do something prettier
            return;
        }
        final FirebaseUser user = AuthenticationManager.getInstance().getFirebaseUser();
        Toast.makeText(this, AuthenticationManager.getInstance().getFirebaseUser().getDisplayName(), Toast.LENGTH_SHORT).show();
        DataManager.getInstance()
                .createPerson(
                        new Person(user.getUid(), user.getDisplayName(), true), new FireBaseCallback<Person>() {
                            @Override
                            public void onSuccessCreate(Person entity) {
                                DataManager.getInstance().setCurrentPerson(entity);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .add(R.id.drawer_navigation_main_content, new GroupCreationFragment())
                                        .addToBackStack(null)
                                        .commit();
                            }

                            @Override
                            public void onFailureCreate() {
                                Log.e(LoggingTag.ERROR.name(), "Could not create the person... FATAL ERROR");
                            }

                            @Override
                            public void onExists(Person entity) {
                                DataManager.getInstance().setCurrentPerson(entity);
                                if (entity.getKitchens().isEmpty()) {
                                    // Route to first time fragment
                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .add(R.id.drawer_navigation_main_content, new GroupCreationFragment())
                                            .addToBackStack(null)
                                            .commit();
                                } else if(entity.getKitchens().size() == 1) {
                                    String kitchenName = entity.getKitchens().values().iterator().next();
                                    DataManager.getInstance().getKitchen(kitchenName, new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Kitchen kitchen = dataSnapshot.getValue(Kitchen.class);
                                            DataManager.getInstance().setCurrentKitchen(kitchen);
                                            getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.drawer_navigation_main_content, new KitchenOverviewFragment())
                                                    .commit();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }else{
                                    // Route to my groups
                                    Intent intent = new Intent(MainActivity.this, MyGroupsActivity.class);
                                    startActivity(intent);

                                }

                            }
                        });


    }
    /*
* Called when a particular item from the navigation drawer
* is selected.
* */
    private void selectItemFromDrawer(int position) {
        NavItem selectedItem = navItems.get(position);
        Fragment fragment = null;
        switch (selectedItem.type){
            case CREATE_ACTIVITY:
                fragment = new ActivityCreationFragment();
                break;
            case EDIT_PERSONAL_INFO:
                fragment = new PersonalPageFragment();
                break;
            case KITCHEN_CALENDAR:
                fragment = new KitchenOverviewCalendarFragment();
                break;

            case KITCHEN_OVERVIEW:
                fragment = new KitchenOverviewFragment();
                break;
            case PERSONAL_CALENDAR:
                //TODO make personal calendar fragment
                fragment = new KitchenOverviewCalendarFragment();
                break;
            case PERSONAL_OVERVIEW:
                fragment = new PersonalOverviewFragment();
                break;
            default:
                //we do not know what to do so we just return.
                return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.drawer_navigation_main_content, fragment)
                .addToBackStack(null)
                .commit();

        drawerNavigationList.setItemChecked(position, true);
        setTitle(selectedItem.title);

        // Close the drawer
        drawerLayout.closeDrawer(drawerNavigationPane);
    }




    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item){
        switch (item.getItemId()){
            case R.id.general_options_menu_group_creation:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.drawer_navigation_main_content, new GroupCreationFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.general_options_menu_my_groups:
                Intent myGroupsIntent = new Intent(this, MyGroupsActivity.class);
                startActivity(myGroupsIntent);
                break;
            case R.id.general_options_menu_logout:
                AuthenticationManager.getInstance().getFirebaseAuth().signOut();
                Intent signInIntent = new Intent(this, SignInActivity.class);
                startActivity(signInIntent);
                break;
            case R.id.general_options_menu_personal_info:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.drawer_navigation_main_content, new PersonalPageFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

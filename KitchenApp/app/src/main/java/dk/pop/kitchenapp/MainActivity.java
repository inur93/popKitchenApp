package dk.pop.kitchenapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dk.pop.kitchenapp.adapters.DrawerListAdapter;
import dk.pop.kitchenapp.data.AuthenticationManager;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.extensions.BitmapHelper;
import dk.pop.kitchenapp.firebase.callbacks.UserLoggedInCallback;
import dk.pop.kitchenapp.fragments.GroupCreationFragment;
import dk.pop.kitchenapp.fragments.kitchen.KitchenOverviewFragment;
import dk.pop.kitchenapp.fragments.kitchen.calendar.KitchenOverviewCalendarFragment;
import dk.pop.kitchenapp.fragments.activity.ActivityCreationFragment;
import dk.pop.kitchenapp.fragments.personal.PersonalOverviewFragment;
import dk.pop.kitchenapp.fragments.personal.PersonalPageFragment;
import dk.pop.kitchenapp.fragments.personal.UserProfileFragment;
import dk.pop.kitchenapp.models.Person;
import dk.pop.kitchenapp.tasks.DownloadImageTask;
import dk.pop.kitchenapp.viewModels.NavItem;

/**
 * Created by Runi on 10-11-2016.
 */

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    private TextView viewProfileText;
    private ListView drawerNavigationList;
    private RelativeLayout drawerNavigationPane;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private ValueEventListener personListener;

    private ProgressBar spinner;

    private ArrayList<NavItem> navItems = new ArrayList<NavItem>();

    public MainActivity(){
       this.personListener =  new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView usernameText = (TextView) findViewById(R.id.drawer_navigation_username);
                usernameText.setText(dataSnapshot.getValue(Person.class).getDisplayName());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navItems.add(new NavItem(NavItem.NAV_ITEM_TYPE.KITCHEN_OVERVIEW,"Kitchen Overview", R.drawable.users));
        navItems.add(new NavItem(NavItem.NAV_ITEM_TYPE.PERSONAL_OVERVIEW, "Personal Overview", R.drawable.social));
        navItems.add(new NavItem(NavItem.NAV_ITEM_TYPE.CREATE_ACTIVITY, "Create Activity", R.drawable.create_activity));
        navItems.add(new NavItem(NavItem.NAV_ITEM_TYPE.EDIT_PERSONAL_INFO, "Edit Personal Information", R.drawable.user));
        navItems.add(new NavItem(NavItem.NAV_ITEM_TYPE.PERSONAL_CALENDAR, "Personal Calendar", R.drawable.personal_calendar));
        navItems.add(new NavItem(NavItem.NAV_ITEM_TYPE.KITCHEN_CALENDAR, "Kitchen Calendar", R.drawable.kitchen_calendar));
        spinner = (ProgressBar)findViewById(R.id.main_activity_spinner);
        String url = getIntent().getStringExtra(getString(R.string.user_profile_picture_url));
        if(url != null){
            PreferenceManager
                    .getDefaultSharedPreferences(this)
                    .edit()
                    .putString(getString(R.string.user_profile_picture_url), url).commit();
        }else{
            url = PreferenceManager.getDefaultSharedPreferences(this)
                    .getString(getString(R.string.user_profile_picture_url), null);
        }
        Bitmap picture = DataManager.getInstance().getProfilePicture();

        //first try
        if(picture != null) { //true if image is in memory
            ((ImageView) findViewById(R.id.user_profile_image)).setImageBitmap(picture);
        }//second try. true if image is saved in file
        else if((picture = BitmapHelper.loadBitmap(this, "profilepicture.jpg")) != null){
            ((ImageView) findViewById(R.id.user_profile_image)).setImageBitmap(picture);
        }else{
            // last resort is to try download image
            new DownloadImageTask((ImageView) findViewById(R.id.user_profile_image), this).execute(url);
        }

        System.out.println("profile picture: " + url);

        viewProfileText = (TextView) findViewById(R.id.drawer_navigation_view_profile);
        viewProfileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.drawer_navigation_main_content, new UserProfileFragment())
                        .addToBackStack(null)
                        .commit();
                drawerLayout.closeDrawer(drawerNavigationPane);
            }
        });

        setupDrawer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        spinner.setVisibility(View.VISIBLE);

        // Check if a user is already present via FireBase
        if (AuthenticationManager.getInstance().getFirebaseUser() != null) {
            DataManager.getInstance()
                    .addPersonListener(AuthenticationManager
                            .getInstance()
                            .getFirebaseUser()
                            .getUid(), personListener);

        }else {
            //if not we go to sign in activity and return.
            Intent signInIntent = new Intent(this, SignInActivity.class);
            startActivity(signInIntent);
            spinner.setVisibility(View.GONE);
            return;
        }

        // if user not logged in properly, we'll do it now
        if(DataManager.getInstance().getCurrentPerson() == null) {
            final FirebaseUser user = AuthenticationManager.getInstance().getFirebaseUser();
            Toast.makeText(this, AuthenticationManager.getInstance().getFirebaseUser().getDisplayName(), Toast.LENGTH_SHORT).show();
            //listener determines which view to show.
            DataManager.getInstance()
                    .createPerson(
                            new Person(user.getUid(), user.getDisplayName(), true), new UserLoggedInCallback(this));

        }
        // if user has selected a kitchen but no fragment is shown we'll give him the kitchen overview
        else if(DataManager.getInstance().getCurrentKitchen() != null
                && getSupportFragmentManager().getFragments() == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.drawer_navigation_main_content, new KitchenOverviewFragment())
                    .commit();
        }

        spinner.setVisibility(View.GONE);
        // Activate the home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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


        // Close the drawer
        drawerLayout.closeDrawer(drawerNavigationPane);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item){
        // Handle actionbar button
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        // Handle options menu button clicks
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    /**
     *Sets up the navigation drawer
     */
    private void setupDrawer(){
        // DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.main_activity);

        // Setup the drawer toggler
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);

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
    }

    @Override
    protected void onStop() {
        super.onStop();
            DataManager
                    .getInstance()
                    .removeCurrentPersonListener(personListener);

    }
}

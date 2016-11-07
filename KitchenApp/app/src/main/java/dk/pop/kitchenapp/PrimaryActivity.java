package dk.pop.kitchenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import dk.pop.kitchenapp.data.AuthenticationManager;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.data.dataPassing.DataPassingEnum;
import dk.pop.kitchenapp.data.dataPassing.PrimaryActivityStateEnum;
import dk.pop.kitchenapp.data.interfaces.FireBaseCallback;
import dk.pop.kitchenapp.fragments.GroupCreationFragment;
import dk.pop.kitchenapp.fragments.MyGroupsFragment;
import dk.pop.kitchenapp.logging.LoggingTag;
import dk.pop.kitchenapp.models.Person;

public class PrimaryActivity extends AppCompatActivity{

    private PrimaryActivityStateEnum state = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);

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
        if(state != null){
            switch (state){
                case GROUPCREATION:
                    getSupportFragmentManager().beginTransaction().add(R.id.primary_activity_frame_layout, new GroupCreationFragment()).addToBackStack(null).commit();
                    break;
                case MYGROUPS:
                    getSupportFragmentManager().beginTransaction().add(R.id.primary_activity_frame_layout, new MyGroupsFragment()).addToBackStack(null).commit();
                    break;
            }
        }
        else {
            if (AuthenticationManager.getInstance().getFirebaseUser() == null) {
                Intent signInIntent = new Intent(this, SignInActivity.class);
                startActivity(signInIntent);
            } else {
                final FirebaseUser user = AuthenticationManager.getInstance().getFirebaseUser();
                Toast.makeText(this, AuthenticationManager.getInstance().getFirebaseUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                DataManager.getInstance().createPerson(new Person(user.getUid(), user.getDisplayName(), true), new FireBaseCallback<Person>() {
                    @Override
                    public void onSuccessCreate(Person entity) {
                        DataManager.getInstance().setCurrentPerson(entity);
                        getSupportFragmentManager().beginTransaction().add(R.id.primary_activity_frame_layout, new GroupCreationFragment()).addToBackStack(null).commit();
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
                            getSupportFragmentManager().beginTransaction().add(R.id.primary_activity_frame_layout, new GroupCreationFragment()).addToBackStack(null).commit();
                        } else {
                            // Route to my groups
                            getSupportFragmentManager().beginTransaction().add(R.id.primary_activity_frame_layout, new MyGroupsFragment()).addToBackStack(null).commit();
                        }
                    }
                });
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_options_menu, menu);
        return true;
    }

     public boolean onOptionsItemSelected(android.view.MenuItem item){
         switch (item.getItemId()){
             case R.id.general_options_menu_group_creation:
                 getSupportFragmentManager().beginTransaction().replace(R.id.primary_activity_frame_layout, new GroupCreationFragment()).addToBackStack(null).commit();
                 break;
             case R.id.general_options_menu_my_groups:
                 getSupportFragmentManager().beginTransaction().replace(R.id.primary_activity_frame_layout, new MyGroupsFragment()).addToBackStack(null).commit();
                 break;
             case R.id.general_options_menu_logout:
                 AuthenticationManager.getInstance().getFirebaseAuth().signOut();
                 Intent signInIntent = new Intent(this, SignInActivity.class);
                 startActivity(signInIntent);
                 break;
         }
        return super.onOptionsItemSelected(item);
     }
}

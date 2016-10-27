package dk.pop.kitchenapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import dk.pop.kitchenapp.data.AuthenticationManager;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.data.interfaces.FireBaseCallback;
import dk.pop.kitchenapp.fragments.GroupCreationFragment;
import dk.pop.kitchenapp.fragments.MyGroupsFragment;
import dk.pop.kitchenapp.logging.LoggingTag;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;

public class PrimaryActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(AuthenticationManager.getInstance().getFirebaseUser() == null) {
            Intent signInIntent = new Intent(this, SignInActivity.class);
            startActivity(signInIntent);
        }
        else{
            final FirebaseUser user = AuthenticationManager.getInstance().getFirebaseUser();
            Toast.makeText(this, AuthenticationManager.getInstance().getFirebaseUser().getDisplayName(), Toast.LENGTH_SHORT).show();
            DataManager.getInstance().createPerson(new Person(user.getUid(), user.getDisplayName(), true), new FireBaseCallback<Person>() {
                @Override
                public void onSuccessCreate(Person entity) {
                    getSupportFragmentManager().beginTransaction().add(R.id.primary_activity_frame_layout, new GroupCreationFragment()).addToBackStack(null).commit();
                }

                @Override
                public void onFailureCreate() {
                    Log.e(LoggingTag.ERROR.name(), "Could not create the person... FATAL ERROR");
                }

                @Override
                public void onExists(Person entity) {
                    if(entity.getKitchens().isEmpty()){
                        // Route to first time fragment
                        getSupportFragmentManager().beginTransaction().add(R.id.primary_activity_frame_layout, new GroupCreationFragment()).addToBackStack(null).commit();
                    }
                    else{
                        // Route to my groups
                        getSupportFragmentManager().beginTransaction().add(R.id.primary_activity_frame_layout, new MyGroupsFragment()).addToBackStack(null).commit();
                    }
                }
            });
        }
    }
}

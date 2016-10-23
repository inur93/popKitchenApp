package dk.pop.kitchenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import dk.pop.kitchenapp.data.AuthenticationManager;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.logging.LoggingTag;
import dk.pop.kitchenapp.models.Kitchen;

public class PrimaryActivity extends AppCompatActivity implements View.OnClickListener{

    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);

        aq = new AQuery(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.groupCreationSearchBtn:
                break;
            case R.id.groupCreationCreateBtn:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(AuthenticationManager.getInstance().getFirebaseUser() == null) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(AuthenticationManager.getInstance().getSignClient(this, this, getString(R.string.default_web_client_id)));
            startActivityForResult(signInIntent, 9001);
        }
        else{
            Toast.makeText(this, AuthenticationManager.getInstance().getFirebaseUser().getDisplayName(), Toast.LENGTH_SHORT).show();
            DataManager.getInstance().createKitchen(new Kitchen("First Kitchen"));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 9001) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                AuthenticationManager.getInstance().authorizeWithFirebase(this, account);
            } else {
                // Google Sign In failed
                Log.e(LoggingTag.LOGIN.name(), "Google Sign In failed.");
            }
        }
    }
}

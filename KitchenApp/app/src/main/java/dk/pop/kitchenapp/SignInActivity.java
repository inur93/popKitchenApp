package dk.pop.kitchenapp;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import dk.pop.kitchenapp.data.AuthenticationManager;
import dk.pop.kitchenapp.data.interfaces.Callback;
import dk.pop.kitchenapp.logging.LoggingTag;

public class SignInActivity extends AppCompatActivity{

    public static final int REQUESTCODE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQUESTCODE) {
            final GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                AuthenticationManager.getInstance().authorizeWithFirebase(this, account, new Callback() {
                    @Override
                    public void onSuccess() {

                        Log.d(LoggingTag.LOGIN.name(), "Successfully authenticated");

                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        String pictureUrl = result.getSignInAccount().getPhotoUrl().toString();
                        System.out.println("image url: " + pictureUrl);
                        intent.putExtra(getString(R.string.user_profile_picture_url), pictureUrl);
                        PreferenceManager
                                .getDefaultSharedPreferences(SignInActivity.this)
                                .edit()
                                .putString(getString(R.string.user_profile_picture_url), pictureUrl)
                                .commit();
                        startActivity(intent);
                    }

                    @Override
                    public void onFailue() {
                        Log.e(LoggingTag.LOGIN.name(), "Could not authenticate the user");
                        finish();
                    }
                });
            } else {
                // Google Sign In failed
                Log.e(LoggingTag.LOGIN.name(), "Google Sign In failed.");
            }
        }else{

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(AuthenticationManager.getInstance().getSignClient(this, this, getString(R.string.default_web_client_id)));
        startActivityForResult(signInIntent, REQUESTCODE);
    }
}

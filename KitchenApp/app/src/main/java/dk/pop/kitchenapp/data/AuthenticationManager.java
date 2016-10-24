package dk.pop.kitchenapp.data;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;

import dk.pop.kitchenapp.BuildConfig;
import dk.pop.kitchenapp.data.interfaces.Callback;
import dk.pop.kitchenapp.logging.LoggingTag;
import dk.pop.kitchenapp.models.CleaningGroupActivity;
import dk.pop.kitchenapp.models.DinnerGroupActivity;
import dk.pop.kitchenapp.models.ExpenseGroupActivity;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;

/**
 * Created by dickow on 10/2/16.
 */
public class AuthenticationManager extends Application implements GoogleApiClient.OnConnectionFailedListener{
    private static AuthenticationManager ourInstance;

    private FirebaseAuth firebaseAuth;
    private GoogleApiClient apiClient;


    public static AuthenticationManager getInstance() {
        if(ourInstance == null){
            ourInstance = new AuthenticationManager();
        }
        return ourInstance;
    }

    private AuthenticationManager() {
        // Initialize the firebase instance
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public GoogleApiClient getSignClient(Context ctx, FragmentActivity act, String clientId){
        if(apiClient == null) {
            apiClient = new GoogleApiClient.Builder(ctx)
                    .enableAutoManage(act, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions
                            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(clientId)
                            .requestEmail()
                            .build())
                    .build();
        }
        return apiClient;
    }

    public FirebaseAuth getFirebaseAuth(){return this.firebaseAuth;}

    public FirebaseUser getFirebaseUser(){return this.firebaseAuth.getCurrentUser();}

    public void authorizeWithFirebase(final Context ctx, GoogleSignInAccount acct, final Callback callback){
        Log.d(LoggingTag.LOGIN.name(), "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(LoggingTag.LOGIN.name(), "signInWithCredential:onComplete:" + task.isSuccessful());

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.w(LoggingTag.LOGIN.name(), "signInWithCredential", task.getException());
                    Toast.makeText(ctx, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    callback.onFailue();
                }
                else{
                    callback.onSuccess();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(LoggingTag.LOGIN.name(), "Connection failed what to do now");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = new AuthenticationManager();
    }
}

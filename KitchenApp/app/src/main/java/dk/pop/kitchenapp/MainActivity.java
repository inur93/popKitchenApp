package dk.pop.kitchenapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private TextView txtReadKey;
    private TextView txtReadValue;

    private Button btnWrite;
    private Button btnRead;

    private EditText writeKey;
    private EditText writeValue;

    private Firebase fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initializing firebase
        // needs to be done before calling any firebase methods
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        // example of creating a reference:
        fb = new Firebase("https://kitchenapp-3d380.firebaseio.com/");

        /*fb.authWithPassword("runivormadal@gmail.com", "gooInuR-2801", new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("success: " + authData.getUid());
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                System.out.println("firebase error: " + firebaseError.getMessage());
            }
        });*/

        this.txtReadKey = (TextView) findViewById(R.id.dataKey);
        this.txtReadValue = (TextView) findViewById(R.id.dataValue);
        this.btnWrite = (Button) findViewById(R.id.btnInsert);
        this.btnRead = (Button) findViewById(R.id.btnReadKey);
        this.writeKey = (EditText) findViewById(R.id.inputKey);
        this.writeValue = (EditText) findViewById(R.id.inputValue);

        this.btnRead.setOnClickListener(this);
        this.btnWrite.setOnClickListener(this);

        //String token = fb.getAuth().getToken();
        //System.out.println("token: " + token);
        /*fb.authWithCustomToken(token, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("success: " + authData.getProvider());
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                System.out.println("firebase error: " + firebaseError.getMessage());
            }
        });*/
        //example writing data:
      /*  myFirebaseRef.child("message").setValue("Do you have data? You'll love Firebase.");

        //example of reading data:
        myFirebaseRef.child("message").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ((TextView) findViewById(R.id.txtHello)).setText(snapshot.getValue().toString());
                System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }

        });*/

        // quickstart https://www.firebase.com/docs/android/quickstart.html
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://dk.pop.kitchenapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://dk.pop.kitchenapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnInsert:
                String key = this.writeKey.getText().toString();
                String value = this.writeValue.getText().toString();
               // fb.child(key).setValue(value);
                System.out.println("writing: " + key + ":" + value);
                break;
            case R.id.btnReadKey:
                String key1 = this.writeKey.getText().toString();
                String value1 = this.writeValue.getText().toString();
               /* fb.child(key1).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        txtReadValue.setText(snapshot.getValue().toString());
                        txtReadKey.setText(snapshot.getKey());
                        System.out.println("read value: " + snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
                    }

                    @Override
                    public void onCancelled(FirebaseError error) {
                    }

                });*/
                break;
        }
    }
}

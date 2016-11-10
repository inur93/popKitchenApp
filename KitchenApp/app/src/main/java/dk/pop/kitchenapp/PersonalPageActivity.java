package dk.pop.kitchenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.extensions.StringExtensions;
import dk.pop.kitchenapp.models.Person;

public class PersonalPageActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText nameField, roomField;
    private Person currentPerson;
    private ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);

        AQuery aq = new AQuery(this);
        aq.id(R.id.personal_page_save_btn).clicked(this);
        nameField = aq.id(R.id.personal_page_name_field).getEditText();
        roomField = aq.id(R.id.personal_page_room_text).getEditText();
        currentPerson = DataManager.getInstance().getCurrentPerson();

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentPerson = dataSnapshot.getValue(Person.class);
                nameField.setText(currentPerson.getDisplayName());
                roomField.setText(currentPerson.getRoomNumber());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        DataManager.getInstance().attachPersonEventListener(currentPerson, listener);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.personal_page_save_btn){
            // Get the text from the fields
            String newName = nameField.getText().toString();
            String newRoom = roomField.getText().toString();

            // Do not let the user save empty values
            newName = StringExtensions.isNullOrEmpty(newName) ? null : newName;
            newRoom = StringExtensions.isNullOrEmpty(newRoom) ? null : newRoom;

            // Save the update to the database
            DataManager.getInstance().updatePerson(currentPerson.getGoogleId(), newName, newRoom);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        DataManager.getInstance().detachPersonEventListener(currentPerson, listener);
    }
}

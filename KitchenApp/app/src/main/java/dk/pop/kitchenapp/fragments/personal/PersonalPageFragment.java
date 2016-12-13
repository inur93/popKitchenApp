package dk.pop.kitchenapp.fragments.personal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.extensions.StringExtensions;
import dk.pop.kitchenapp.models.Person;

public class PersonalPageFragment extends Fragment implements View.OnClickListener{

    private AQuery aq;
    private EditText nameField, roomField;
    private Person currentPerson;
    private ValueEventListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);


        aq = new AQuery(view);
        aq.id(R.id.personal_page_save_btn).clicked(this);
        nameField = aq.id(R.id.personal_page_name_field).getEditText();
        roomField = aq.id(R.id.personal_page_room_text).getEditText();
        currentPerson = DataManager.getInstance().getCurrentPerson();

        nameField.setText(currentPerson.getDisplayName());
        roomField.setText(currentPerson.getRoomNumber());

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

        return view;

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.personal_page_save_btn){
            // Get the text from the fields
            String newName = nameField.getText().toString();
            String newRoom = roomField.getText().toString();

            // Do not let the user save empty values
            newName = StringExtensions.isNullOrEmpty(newName) ? null : newName.trim();
            newRoom = StringExtensions.isNullOrEmpty(newRoom) ? null : newRoom.trim();

            // Save the update to the database
            DataManager.getInstance().updatePerson(currentPerson.getGoogleId(), newName, newRoom);

            if(!currentPerson.getDisplayName().equals(newName))
                Toast.makeText(getContext(), getString(R.string.personal_update_name_changed) + newName, Toast.LENGTH_LONG).show();
            if(!currentPerson.getRoomNumber().equals(newRoom))
                Toast.makeText(getContext(), getString(R.string.personal_update_room_update) + newRoom, Toast.LENGTH_LONG).show();

            getFragmentManager()
                   .popBackStack();
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        DataManager.getInstance().attachPersonEventListener(currentPerson, listener);
        getActivity().setTitle(R.string.personal_page_title);
    }

    @Override
    public void onStop() {
        super.onStop();
        DataManager.getInstance().detachPersonEventListener(currentPerson, listener);
    }

}

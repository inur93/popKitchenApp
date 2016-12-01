package dk.pop.kitchenapp.fragments.personal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.models.Person;

/**
 * Created by Runi on 01-12-2016.
 */

public class UserProfileFragment extends Fragment implements View.OnClickListener {

    private AQuery aq;
    private TextView nameField, roomField;
    private Person currentPerson;
    private ValueEventListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);


        aq = new AQuery(view);
        aq.id(R.id.user_profile_edit_profile).clicked(this);
        nameField = aq.id(R.id.user_profile_name).getTextView();
        roomField = aq.id(R.id.user_profile_room_no).getTextView();
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
        if (v.getId() == R.id.user_profile_edit_profile) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.drawer_navigation_main_content, new PersonalPageFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        DataManager.getInstance().attachPersonEventListener(currentPerson, listener);
        getActivity().setTitle(R.string.user_profile_title);
    }

    @Override
    public void onStop() {
        super.onStop();
        DataManager.getInstance().detachPersonEventListener(currentPerson, listener);
    }
}

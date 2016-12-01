package dk.pop.kitchenapp.data;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;

import dk.pop.kitchenapp.data.interfaces.FireBaseCallback;
import dk.pop.kitchenapp.models.GroupActivity;
import dk.pop.kitchenapp.models.Kitchen;
import dk.pop.kitchenapp.models.Person;

/**
 * Created by Runi on 10-11-2016.
 */
public interface IDataManager {
    void createKitchen(@NonNull Kitchen kitchen, FireBaseCallback<Kitchen> callback);

    void createPerson(@NonNull Person person, FireBaseCallback<Person> callback);

    void addPersonListener(String googleId, ValueEventListener listener);

    void removePersonListener(String googleId, ValueEventListener listener);

    void removeCurrentPersonListener(ValueEventListener listener);

    void updatePerson(String googleId, String name, String roomNumber);

    void getPerson(String googleId, ValueEventListener listener);

    void getKitchen(String name, ValueEventListener listener);

    void attachKitchenListener(ChildEventListener listener);

    void detachKitchenListener(ChildEventListener listener);

    void createActivity(GroupActivity activity);

    void associateToKitchen(Kitchen kitchen, Person person);

    void disAssociateFromKitchen(Kitchen kitchen, Person person);

    void getKitchensForPerson(@NonNull Person person, ChildEventListener listener);

    void detachKitchensForPerson(@NonNull Person person, ChildEventListener listener);

    void getActivitiesForPerson(@NonNull Person person, ChildEventListener listener);

    void detachActivitiesForPerson(@NonNull Person person, ChildEventListener listener);

    void getActivitiesForKitchen(@NonNull Kitchen kitchen, ChildEventListener listener);

    void detachActivitiesForKitchen(@NonNull Kitchen kitchen, ChildEventListener listener);

    Person getCurrentPerson();

    void setCurrentPerson(Person currentPerson);

    Kitchen getCurrentKitchen();

    void setCurrentKitchen(Kitchen currentKitchen);

    void getPersonsFromKitchen(@NonNull Kitchen kitchen, ChildEventListener listener);

    void getExpensesForActivity(GroupActivity activity, ChildEventListener listener);

    void removeExpenseForActivityListener(GroupActivity activity, ChildEventListener listener);

    void removePersonsFromKitchenListener(Kitchen kitchen, ChildEventListener listener);

    void attachPersonEventListener(@NonNull Person person, ValueEventListener listener);

    void detachPersonEventListener(@NonNull Person person, ValueEventListener listener);

    void setProfilePicture(Bitmap picture);

    Bitmap getProfilePicture();

    //void setExpenseToShow(ExpenseGroupActivity activity);
}

package dk.pop.kitchenapp.models.factories;

import com.google.firebase.database.DataSnapshot;

import dk.pop.kitchenapp.models.CleaningGroupActivity;
import dk.pop.kitchenapp.models.DinnerGroupActivity;
import dk.pop.kitchenapp.models.ExpenseGroupActivity;
import dk.pop.kitchenapp.models.GroupActivity;
import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;

/**
 * Created by dickow on 11/10/16.
 */

public final class ActivityFactory {
    public static GroupActivity CreateActivity(DataSnapshot snapshot) throws IllegalArgumentException{
        if(!snapshot.hasChild("type")){
            throw new IllegalArgumentException("Could not find the child 'type' on the snapshot, please provide a valid snapshot");
        }

        ObjectTypeEnum type = ObjectTypeEnum.valueOf((String)snapshot.child("type").getValue());
        switch (type){

            case DINNERACTIVITY:
                return snapshot.getValue(DinnerGroupActivity.class);
            case CLEANINGACTIVITY:
                return snapshot.getValue(CleaningGroupActivity.class);
            case EXPENSEACTIVITY:
                return snapshot.getValue(ExpenseGroupActivity.class);
        }

        return null;
    }
}

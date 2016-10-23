package dk.pop.kitchenapp.models;

import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;

/**
 * Created by Runi on 22-10-2016.
 */

public class ActivityType{
    public ObjectTypeEnum type;
    public String name;

    public ActivityType(ObjectTypeEnum type, String name){
        this.type = type;
        this.name = name;
    }
}
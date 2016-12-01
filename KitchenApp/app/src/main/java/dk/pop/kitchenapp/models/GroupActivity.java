package dk.pop.kitchenapp.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;

/**
 * Created by dickow on 9/21/16.
 */

public abstract class GroupActivity implements Serializable{
    private String title;
    private String description;
    private String date;
    private String kitchen;
    private String createdBy;
    private final ObjectTypeEnum type;
    private final String id;

    public GroupActivity(){
        // Required Empty constructor
        type = null;
        id = null;
    }

    public GroupActivity(
            UUID id,
            String title,
            String description,
            Date date,
            Kitchen kitchen,
            Person createdBy,
            ObjectTypeEnum type
            ){
        this.id = id == null ? UUID.randomUUID().toString() : id.toString();
        this.title = title;
        this.description = description;
        this.date = date == null ? null : date.toString();
        this.kitchen = kitchen == null ? null : kitchen.getName();
        this.createdBy = createdBy == null ? null : createdBy.getGoogleId();
        this.type = type;

    }

    public String getTitle() {
        return title;
    }

    public String getId(){return this.id;}

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKitchen() {
        return kitchen;
    }

    public void setKitchen(String kitchenId){this.kitchen = kitchenId;}

    public ObjectTypeEnum getType() {
        return type;
    }

    public String getCreatedBy() {return createdBy;}

    public void setCreatedBy(String createdBy){this.createdBy = createdBy;}

}

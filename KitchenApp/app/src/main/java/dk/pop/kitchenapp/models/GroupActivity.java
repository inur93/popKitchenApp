package dk.pop.kitchenapp.models;

import java.util.Date;
import java.util.UUID;

import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;

/**
 * Created by dickow on 9/21/16.
 */

public abstract class GroupActivity {
    private String title;
    private String description;
    private String date;
    private String kitchen;
    private String createdBy;
    private final ObjectTypeEnum type;
    private final String id;

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
        this.date = date.toString();
        this.kitchen = kitchen.getName();
        this.createdBy = createdBy.getGoogleId();
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

    public void setDate(Date date) {
        this.date = date.toString();
    }
    public void setDate(String date) {this.date = date; }

    public String getKitchen() {
        return kitchen;
    }

    public ObjectTypeEnum getType() {
        return type;
    }

    public String getCreatedBy() {return createdBy;}

}

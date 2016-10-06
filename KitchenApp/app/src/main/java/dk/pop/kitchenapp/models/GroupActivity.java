package dk.pop.kitchenapp.models;

import java.util.Date;

import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;

/**
 * Created by dickow on 9/21/16.
 */

public abstract class GroupActivity {
    private String title;
    private String description;
    private Date date;
    private Kitchen kitchen;
    private Person createdBy;
    private final ObjectTypeEnum type;

    public GroupActivity(Kitchen kitchen, Person createdBy, ObjectTypeEnum type){
        this.kitchen = kitchen;
        this.createdBy = createdBy;
        this.type = type;
        this.date = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public ObjectTypeEnum getType() {
        return type;
    }

    public Person getCreatedBy() {return createdBy;}
}

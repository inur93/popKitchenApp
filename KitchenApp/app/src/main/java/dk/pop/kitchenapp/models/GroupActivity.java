package dk.pop.kitchenapp.models;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

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
    private final UUID id;

    public GroupActivity(
            UUID id,
            String title,
            String description,
            Date date,
            Kitchen kitchen,
            Person createdBy,
            ObjectTypeEnum type
            ){
        this.id = id == null ? UUID.randomUUID() : id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.kitchen = kitchen;
        this.createdBy = createdBy;
        this.type = type;

    }

    public String getTitle() {
        return title;
    }

    public UUID getId(){return this.id;}

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

    // easy access to get data that we want to save.
    public HashMap<String, Object> getValues(){
        HashMap<String, Object> vals = new HashMap<>();
        vals.put("title", title);
        vals.put("description", description);
        vals.put("date", date == null ? "" : date.toString());
        vals.put("id", id);
        vals.put("kitchen", kitchen == null ? null : kitchen.getName());
        vals.put("type", type);
        vals.put("createdBy", createdBy.getGoogleId());
        return vals;
    }

    public void mapValues(HashMap<String, Object> values){
        //TODO
    }
}

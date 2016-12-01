package dk.pop.kitchenapp.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;

/**
 * Created by dickow on 9/21/16.
 */

public class ExpenseGroupActivity extends GroupActivity implements Serializable{
    private float price;
    private String responsible;

    public ExpenseGroupActivity(){

    }

    public ExpenseGroupActivity(
            UUID id,
            String title,
            String description,
            Date date,
            Kitchen kitchen,
            Person createdBy,
            Person responsible,
            float price) {

        super(id, title, description, date, kitchen, createdBy, ObjectTypeEnum.EXPENSEACTIVITY);

        this.responsible = responsible.getGoogleId();
        this.price = price;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(Person responsible) {
        this.responsible = responsible.getGoogleId();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


}

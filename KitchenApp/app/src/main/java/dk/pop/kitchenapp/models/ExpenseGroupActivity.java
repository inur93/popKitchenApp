package dk.pop.kitchenapp.models;

import java.util.Date;
import java.util.UUID;

import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;

/**
 * Created by dickow on 9/21/16.
 */

public class ExpenseGroupActivity extends GroupActivity {
    private float price;
    private Person responsible;

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

        this.responsible = responsible;
        this.price = price;
    }

    public Person getResponsible() {
        return responsible;
    }

    public void setResponsible(Person responsible) {
        this.responsible = responsible;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

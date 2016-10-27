package dk.pop.kitchenapp.models;

import java.util.UUID;

import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;

/**
 * Created by dickow on 9/21/16.
 */

public class ExpenseGroupActivity extends GroupActivity {
    private float price;
    private Person responsible;

    public ExpenseGroupActivity(Person responsible, Person createdBy, float price, Kitchen kitchen, UUID id) {
        super(kitchen, createdBy, ObjectTypeEnum.EXPENSEACTIVITY, id);
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

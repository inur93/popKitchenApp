package dk.pop.kitchenapp.models;

import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;

/**
 * Created by dickow on 9/21/16.
 */

public class ExpenseActivity extends Activity {
    private float price;
    private Person responsible;

    public ExpenseActivity(Person responsible, Kitchen kitchen) {
        super(kitchen, ObjectTypeEnum.EXPENSEACTIVITY);
        this.responsible = responsible;
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

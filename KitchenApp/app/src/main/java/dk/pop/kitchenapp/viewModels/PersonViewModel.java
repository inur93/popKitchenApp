package dk.pop.kitchenapp.viewModels;

import dk.pop.kitchenapp.models.Person;

/**
 * Created by Runi on 22-10-2016.
 */

public class PersonViewModel {
    private Person person;
    private boolean isSelected;

    public PersonViewModel(Person person, boolean isSelected){
        this.person = person;
        this.isSelected = isSelected;
    }

    public Person getPerson(){
        return this.person;
    }

    public boolean isSelected(){
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected){
        this.isSelected = isSelected;
    }
}

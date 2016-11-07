package dk.pop.kitchenapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dickow on 9/21/16.
 */

public class Kitchen implements Serializable{
    private String name;
    private HashMap<String, String> activities;
    private HashMap<String, String> persons;


    public Kitchen(){
        this.activities = new HashMap<>();
    }

    public Kitchen(String name){
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }


}

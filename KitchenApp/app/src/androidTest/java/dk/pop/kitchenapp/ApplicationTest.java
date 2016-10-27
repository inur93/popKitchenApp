package dk.pop.kitchenapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.models.Kitchen;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

        Kitchen kitchen1 = new Kitchen("Kitchen1");

        Kitchen kitchen2 = new Kitchen("Kitchen2");

        DataManager.getInstance().createKitchen(kitchen1);
        DataManager.getInstance().createKitchen(kitchen2);
    }
}
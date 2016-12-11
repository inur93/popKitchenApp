package dk.pop.kitchenapp.listeners;

import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by dickow on 12/11/16.
 */

public interface FireBaseListListener<T> {
    void start();

    void setAdapter(BaseAdapter adapter);

    ArrayList<T> getItems();
}

package dk.pop.kitchenapp.listeners;

import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by dickow on 12/11/16.
 */

public interface FireBaseListListener<TItem, TAdapter extends BaseAdapter> {
    void start();

    void setAdapter(TAdapter adapter);

    ArrayList<TItem> getItems();
}

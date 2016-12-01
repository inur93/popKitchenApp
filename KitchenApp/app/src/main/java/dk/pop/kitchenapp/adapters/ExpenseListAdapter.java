package dk.pop.kitchenapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.androidquery.AQuery;

import java.util.List;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.models.ExpenseGroupActivity;

/**
 * Created by Runi on 01-12-2016.
 */

public class ExpenseListAdapter extends ArrayAdapter<ExpenseGroupActivity> {
    private List<ExpenseGroupActivity> persons = null;
    public ExpenseListAdapter(Context context, List objects) {
        super(context, 0, objects);
        persons = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ExpenseGroupActivity act = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.expenses_list_item, parent, false);
        }

        AQuery aq = new AQuery(convertView);
        aq.id(R.id.expenses_list_item_title).text(act.getTitle());
        aq.id(R.id.expenses_list_item_price).text(String.valueOf(act.getPrice()));

        return convertView;
    }
}

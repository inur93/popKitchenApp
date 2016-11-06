package dk.pop.kitchenapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dk.pop.kitchenapp.R;
import dk.pop.kitchenapp.models.Kitchen;

/**
 * Created by dickow on 11/3/16.
 */

public class KitchenListAdapter extends BaseAdapter implements Filterable{

    private ArrayList<Kitchen> kitchens;
    private ArrayList<Kitchen> filteredKitchens;
    private Context context;
    private Filter filter;

    public KitchenListAdapter(ArrayList<Kitchen> kitchens, Context context){
        this.kitchens = kitchens;
        this.context = context;
        this.filteredKitchens = kitchens;
    }

    @Override
    public int getCount() {
        return this.filteredKitchens.size();
    }

    @Override
    public Object getItem(int position) {
        return this.filteredKitchens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.filteredKitchens.get(position).getName().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Kitchen kitchen = (Kitchen) getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.view_kitchen_list, null);
            ((ImageView)convertView.findViewById(R.id.view_kitchen_list_image_view)).setImageResource(R.drawable.users);
        }

        ((TextView)convertView.findViewById(R.id.view_kitchen_list_text_view)).setText(kitchen.getName());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults result = new FilterResults();
                    if(constraint.length() <= 0){
                        result.count = kitchens.size();
                        result.values = kitchens;
                    }
                    else {
                        ArrayList<Kitchen> filteredValues = new ArrayList<>();
                        for (Kitchen kitchen :
                                kitchens) {
                            if (kitchen.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                filteredValues.add(kitchen);
                            }
                        }

                        result.count = filteredValues.size();
                        result.values = filteredValues;
                    }
                    return result;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    filteredKitchens = (ArrayList<Kitchen>)results.values;
                    notifyDataSetChanged();
                }
            };
        }
        return filter;
    }
}

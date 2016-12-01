package dk.pop.kitchenapp.fragments.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.pop.kitchenapp.R;

/**
 * Created by Runi on 23-10-2016.
 */

public class KitchenOverviewCreationExpenseInfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_activity_expense_info,
                container,
                false);
        return view;
    }
}

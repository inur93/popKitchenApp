package dk.pop.kitchenapp.navigation;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by dickow on 10/2/16.
 */

public class ActivityNavigation extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        if(!FragmentExtension.handleBackPressed(getSupportFragmentManager())){
            super.onBackPressed();
        }
    }
}

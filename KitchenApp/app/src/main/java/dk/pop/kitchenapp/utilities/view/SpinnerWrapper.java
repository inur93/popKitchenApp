package dk.pop.kitchenapp.utilities.view;

import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dickow on 12/11/16.
 */

public class SpinnerWrapper {
    private ProgressBar spinner;
    private Handler handler;

    public SpinnerWrapper(ProgressBar spinner){
        this.spinner = spinner;
        this.handler = new Handler();
    }

    public void startSpinner(){
        this.spinner.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                spinner.setVisibility(View.INVISIBLE);
            }
        }, 10*1000);
    }

    public void stopSpinner(){
        this.spinner.setVisibility(View.INVISIBLE);
    }
}

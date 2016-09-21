package dk.pop.kitchenapp.models;

/**
 * Created by dickow on 9/21/16.
 */

public abstract class PlannableActivity extends Activity {
    private boolean cancellable;
    private boolean cancelled;

    public PlannableActivity(Kitchen kitchen) {
        super(kitchen);
    }

    public boolean isCancellable() {
        return cancellable;
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

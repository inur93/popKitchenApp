package dk.pop.kitchenapp.models;

import java.util.Date;
import java.util.UUID;

import dk.pop.kitchenapp.models.enums.ObjectTypeEnum;

/**
 * Created by dickow on 9/21/16.
 */

public abstract class PlannableGroupActivity extends GroupActivity {
    private boolean cancellable;
    private boolean cancelled;

    public PlannableGroupActivity(
            UUID id,
            String title,
            String description,
            Date date,
            Kitchen kitchen,
            Person createdBy,
            ObjectTypeEnum type,
            boolean cancellable) {
        super(id, title, description, date, kitchen, createdBy, type);
        this.cancellable = cancellable;
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

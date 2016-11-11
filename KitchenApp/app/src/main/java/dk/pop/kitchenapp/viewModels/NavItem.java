package dk.pop.kitchenapp.viewModels;

/**
 * Created by Runi on 10-11-2016.
 */

public class NavItem {
    public enum NAV_ITEM_TYPE{
        KITCHEN_OVERVIEW,
        PERSONAL_OVERVIEW,
        CREATE_ACTIVITY,
        EDIT_PERSONAL_INFO,
        PERSONAL_CALENDAR,
        KITCHEN_CALENDAR
    }
    public String title;
    public NAV_ITEM_TYPE type;
    public int icon;

    public NavItem(NAV_ITEM_TYPE type, String title, int icon) {
        this.title = title;
        this.type = type;
        this.icon = icon;
    }
}
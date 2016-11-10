package dk.pop.kitchenapp.extensions;

/**
 * Created by dickow on 11/10/16.
 */

public final class StringExtensions{
    public static boolean isNullOrEmpty(String str){
        return str == null || str.length() <= 0;
    }
}

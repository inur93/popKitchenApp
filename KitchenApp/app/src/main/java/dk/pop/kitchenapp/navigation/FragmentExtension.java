package dk.pop.kitchenapp.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by dickow on 10/2/16.
 */

public class FragmentExtension extends Fragment {

    public static boolean handleBackPressed(FragmentManager fm){
        if(fm.getFragments() != null){
            for(Fragment frag : fm.getFragments()){
                if(frag != null && frag.isVisible() && frag instanceof FragmentExtension){
                    if(((FragmentExtension)frag).onBackPressed()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected boolean onBackPressed(){
        FragmentManager fm = getChildFragmentManager();
        if(handleBackPressed(fm)){
            return true;
        }
        else if(getUserVisibleHint() && fm.getBackStackEntryCount() > 0){
            fm.popBackStack();
            return true;
        }
        return false;
    }
}

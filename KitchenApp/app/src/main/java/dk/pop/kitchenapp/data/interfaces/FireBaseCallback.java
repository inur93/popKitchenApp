package dk.pop.kitchenapp.data.interfaces;

/**
 * Created by dickow on 10/27/16.
 */

public interface FireBaseCallback<T> {
    void onSuccessCreate(T entity);
    void onFailureCreate();
    void onExists(T entity);
}

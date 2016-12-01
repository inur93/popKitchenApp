package dk.pop.kitchenapp.extensions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Runi on 01-12-2016.
 */

public class BitmapHelper {

    public static void saveBitmap(Context context, Bitmap bitmapImage, String filename){

        if(bitmapImage == null) return;
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(fos != null)fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap loadBitmap(Context context, String filename){
        FileInputStream fis = null;
        Bitmap picture = null;
        try {
            fis = context.openFileInput(filename);
            picture = BitmapFactory.decodeStream(fis);

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }finally {
            try {
                if(fis != null)fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return picture;
    }
}

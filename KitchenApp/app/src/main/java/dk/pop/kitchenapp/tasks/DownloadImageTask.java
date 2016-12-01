package dk.pop.kitchenapp.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

import dk.pop.kitchenapp.data.DataManager;
import dk.pop.kitchenapp.extensions.BitmapHelper;

/**
 * Created by Runi on 01-12-2016.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private Context context;

    public DownloadImageTask(ImageView bmImage, Context context) {
        this.bmImage = bmImage;
        this.context = context;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataManager.getInstance().setProfilePicture(mIcon11);

        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        BitmapHelper.saveBitmap(context, result, "profilepicture.jpg");
        bmImage.setImageBitmap(result);
    }
}
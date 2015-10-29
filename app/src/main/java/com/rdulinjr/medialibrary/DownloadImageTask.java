package com.rdulinjr.medialibrary;

/**
 * Created by Rj on 10/26/2015.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.os.AsyncTask;
import java.io.*;


public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap myImage = null;
        try {
            // put inmage in inputstream and convert it to bitmap
            InputStream in = new java.net.URL(url).openStream();
            myImage = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return myImage;
    }

    protected void onPostExecute(Bitmap result) {
        // set the imageview to the bitmap
        bmImage.setImageBitmap(result);
    }
}

package com.rdulinjr.medialibrary;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Rj on 10/28/2015.
 */
public class DownloadMediaInfoTask extends AsyncTask<String, String, String>{

    @Override
    protected String doInBackground(String... uri) {
        URL url;
        HttpURLConnection urlConnection = null;
        String response = "";
        try {
            url = new URL(uri[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = readStream(in);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(urlConnection != null)
                urlConnection.disconnect();
            }
        return response;
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}

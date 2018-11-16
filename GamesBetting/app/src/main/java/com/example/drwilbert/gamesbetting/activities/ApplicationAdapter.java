package com.example.drwilbert.gamesbetting.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drwilbert.gamesbetting.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/

public class ApplicationAdapter extends ArrayAdapter<Application>{
    private List<Application> items;
    // ImageView imageView;

    public ApplicationAdapter(Context context,  List<Application> items)  {
        super(context,R.layout.app_custom_list, items);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public Application getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.app_custom_list, null);
        }

        Application app = items.get(position);

        if (app != null) {
            TextView teamOne = (TextView) v.findViewById(R.id.teamone);
            TextView teamTwo = (TextView) v.findViewById(R.id.teamtwo);

           ImageView imageViewone = (ImageView)v.findViewById(R.id.teamOneLogo);
           ImageView imageViewtwo = (ImageView)v.findViewById(R.id.teamTwoLogo);

            if(teamOne != null) teamOne.setText(app.getTeamone());
            if(teamTwo != null) teamTwo.setText(app.getTeamtwo());

            if(imageViewone != null){
                // Create an object for subclass of AsyncTask
                GetXMLTask task = new GetXMLTask(imageViewone);
                // Execute the task
                Log.e("URLS", app.getTeamOneLogo());
                task.execute(app.getTeamOneLogo());
            }
            if(imageViewtwo != null){
                // Create an object for subclass of AsyncTask
                GetXMLTask task = new GetXMLTask(imageViewtwo);
                // Execute the task
                Log.e("URLS", app.getTeamTwoLogo());

                task.execute(app.getTeamTwoLogo());
            }
                //new DownLoadImageTask(imageViewone).execute(app.getTeamOneLogo());

            //if(imageViewtwo != null) imageViewtwo.setImageURI(Uri.parse(app.getTeamTwoLogo()));

        }


        return v;
    }


    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public GetXMLTask(ImageView mv){
            imageView = mv;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }
}




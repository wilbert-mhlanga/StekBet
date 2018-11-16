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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.example.drwilbert.gamesbetting.R;



// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/


public class ListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> ID;
    ArrayList<String> Teamone;
    ArrayList<String> Teamtwo;
    ArrayList<String> HomeOdd;
    ArrayList<String> DrawOdd;
    ArrayList<String> AwayOdd;

//    ArrayList<String> Teamonelogo;
//    ArrayList<String> Teamtwologo;


    public ListAdapter(
            Context context2,
            ArrayList<String> id,
            ArrayList<String> sub_teamone,
            ArrayList<String> sub_teamtwo,
            ArrayList<String> sub_homeodd,
            ArrayList<String> sub_drawodd,
            ArrayList<String> sub_awayodd
//            ArrayList<String> sub_teamonelogo,
//            ArrayList<String> sub_teamtwologo
    )

    {

        this.context = context2;
        this.ID = id;
        this.Teamone = sub_teamone;
        this.Teamtwo = sub_teamtwo;
        this.HomeOdd = sub_homeodd;
        this.DrawOdd = sub_drawodd;
        this.AwayOdd = sub_awayodd;
//        this.Teamonelogo = sub_teamonelogo;
//        this.Teamtwologo = sub_teamtwologo;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return ID.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;


        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            child = layoutInflater.inflate(R.layout.app_custom_list, null);

            holder = new Holder();

            holder.Teamone_TextView = (TextView) child.findViewById(R.id.teamone);
            holder.Teamtwo_TextView = (TextView) child.findViewById(R.id.teamtwo);
            holder.Homeodd_TextView = (TextView) child.findViewById(R.id.homeODD);
            holder.Drawodd_TextView = (TextView) child.findViewById(R.id.drawODD);
            holder.Awayodd_TextView = (TextView) child.findViewById(R.id.awayODD);

//            holder.imageViewone = (ImageView) child.findViewById(R.id.teamOneLogo);
//            holder.imageViewtwo = (ImageView) child.findViewById(R.id.teamTwoLogo);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }

//
//            if(holder.imageViewone != null){
//                // Create an object for subclass of AsyncTask
//                GetXMLTask task = new GetXMLTask(holder.imageViewone);
//                // Log.e("URLS", this.Teamonelogo.get(position));
//                // Execute the task
//                task.execute(this.Teamonelogo.get(position));
//            }
//            if( holder.imageViewtwo != null){
//                // Create an object for subclass of AsyncTask
//                GetXMLTask task = new GetXMLTask( holder.imageViewtwo);
//                // Log.e("URLS", this.Teamtwologo.get(position));
//                // Execute the task
//                task.execute(this.Teamtwologo.get(position));
//            }
//
//
//
        holder.Teamone_TextView.setText(Teamone.get(position));
        holder.Teamtwo_TextView.setText(Teamtwo.get(position));
        holder.Homeodd_TextView.setText(HomeOdd.get(position));
        holder.Drawodd_TextView.setText(DrawOdd.get(position));
        holder.Awayodd_TextView.setText(AwayOdd.get(position));




        return child;
    }

    public class Holder {

        TextView Teamone_TextView;
        TextView Teamtwo_TextView;
        TextView Homeodd_TextView;
        TextView Drawodd_TextView;
        TextView Awayodd_TextView;
//        ImageView imageViewone;
//        ImageView imageViewtwo;
    }

//    public class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView imageView;
//
//        public GetXMLTask(ImageView mv){
//            imageView = mv;
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... urls) {
//            Bitmap map = null;
//            for (String url : urls) {
//                map = downloadImage(url);
//            }
//            return map;
//        }
//
//        // Sets the Bitmap returned by doInBackground
//        @Override
//        protected void onPostExecute(Bitmap result) {
//
//            imageView.setImageBitmap(result);
////            ByteArrayOutputStream stream = new ByteArrayOutputStream();
////            result.compress(Bitmap.CompressFormat.PNG, 0, stream);
////            stream.toByteArray();
//
//        }
//
//
//        // Creates Bitmap from InputStream and returns it
//        private Bitmap downloadImage(String url) {
//            Bitmap bitmap = null;
//            InputStream stream = null;
//            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//            bmOptions.inSampleSize = 1;
//
//            try {
//                stream = getHttpConnection(url);
//                bitmap = BitmapFactory.
//                        decodeStream(stream, null, bmOptions);
//                stream.close();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//            return bitmap;
//        }
//
//        // Makes HttpURLConnection and returns InputStream
//        private InputStream getHttpConnection(String urlString)
//                throws IOException {
//            InputStream stream = null;
//            URL url = new URL(urlString);
//            URLConnection connection = url.openConnection();
//
//            try {
//                HttpURLConnection httpConnection = (HttpURLConnection) connection;
//                httpConnection.setRequestMethod("GET");
//                httpConnection.connect();
//
//                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                    stream = httpConnection.getInputStream();
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            return stream;
//        }
//    }
}

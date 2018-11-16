package com.example.drwilbert.gamesbetting.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drwilbert.gamesbetting.R;
import com.example.drwilbert.gamesbetting.activities.leagues.Bundesliga;
import com.example.drwilbert.gamesbetting.activities.leagues.LaLiga;
import com.example.drwilbert.gamesbetting.activities.leagues.PremierLeague;
import com.example.drwilbert.gamesbetting.activities.leagues.SerieA;
import com.example.drwilbert.gamesbetting.activities.leagues.World_Cup_Matches;


// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/

public class LeaguesListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;

    public LeaguesListAdapter (Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.my_leagues_list, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.my_leagues_list, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);

        switch (position) {
            case 0:
                extratxt.setText("Man U, Man city, Liverpool and more ");
                break;
            case 1:
                extratxt.setText("Real Madrid, Barcelona, Atletico and more ");
                break;
            case 2:
                extratxt.setText("FC Bayen Munich, Dotmand, FC Augsburg and more ");
                break;
            case 3:
                extratxt.setText("Juventus, Roma, Inter Milan and more ");
                break;
            case 4:
                extratxt.setText("German, Brazil, Spain, Agentina and more ");
        }

        return rowView;

    };
}
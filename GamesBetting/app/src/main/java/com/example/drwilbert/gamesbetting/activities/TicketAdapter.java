package com.example.drwilbert.gamesbetting.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import com.example.drwilbert.gamesbetting.R;



// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/

public class TicketAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> ID;
    ArrayList<String> Teamone;
    ArrayList<String> Teamtwo;
    ArrayList<String> Winningteam;
    ArrayList<String> Possiblewin;


    public TicketAdapter(

            Context context2,
            ArrayList<String> id,
            ArrayList<String> sub_teamone,
            ArrayList<String> sub_teamtwo,
            ArrayList<String> sub_winningteam,
            ArrayList<String> sub_possiblewin

    )

    {

        this.context = context2;
        this.ID = id;
        this.Teamone = sub_teamone;
        this.Teamtwo = sub_teamtwo;
        this.Winningteam = sub_winningteam;
        this.Possiblewin = sub_possiblewin;

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

            child = layoutInflater.inflate(R.layout.activity_ticket_adapter, null);

            holder = new Holder();

            holder.Teamone_TextView = (TextView) child.findViewById(R.id.teamone);
            holder.Teamtwo_TextView = (TextView) child.findViewById(R.id.teamtwo);
            holder.Winningteam_TextView = (TextView) child.findViewById(R.id.winning_team);
            holder.Possiblewin_TextView = (TextView) child.findViewById(R.id.win_amount);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }


        holder.Teamone_TextView.setText(Teamone.get(position));
        holder.Teamtwo_TextView.setText(Teamtwo.get(position));
        holder.Winningteam_TextView.setText(Winningteam.get(position));
        holder.Possiblewin_TextView.setText(Possiblewin.get(position));




        return child;
    }

    public class Holder {

        TextView Teamone_TextView;
        TextView Teamtwo_TextView;
        TextView Winningteam_TextView;
        TextView Possiblewin_TextView;

    }


    public void remove(int position){

        Teamone.remove(Teamone.get(position));
        Teamtwo.remove(Teamtwo.get(position));
        Winningteam.remove(Winningteam.get(position));
        Possiblewin.remove(Possiblewin.get(position));

    }

}

package com.example.drwilbert.gamesbetting.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.drwilbert.gamesbetting.R;
import com.example.drwilbert.gamesbetting.activities.leagues.PremierLeague;

import java.util.ArrayList;
import java.util.HashMap;


// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/

public class MyTickets extends AppCompatActivity implements View.OnClickListener {

    Double final_odd_product_holder = 1.00;

    SqliteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    private ListView listview;
    Button go_to_bet_amount, add_match;
    TextView total_matches;
    TicketAdapter ticketAdapter ;

    String email, league, DAY, client_id, device_id ,final_odd_product;

    ArrayList<String> ID_Array;
    ArrayList<String> Teamone_Array;
    ArrayList<String> Teamtwo_Array;
    ArrayList<String> Winningteam_Array;
    ArrayList<String> ChosenOdd_Array;

    ArrayList<String> ListViewClickItemArray = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_match_list);

        go_to_bet_amount = (Button) findViewById(R.id.place_a_bet);
        add_match = (Button) findViewById(R.id.add_match);
        total_matches = (TextView) findViewById(R.id.total_matches);

        sqLiteHelper = new SqliteHelper(this);

        listview = (ListView) findViewById(R.id.listView);

        sqLiteDatabase = sqLiteHelper.getReadableDatabase();


        Intent get_device_id = getIntent();
        device_id = get_device_id.getStringExtra("device_id");

        add_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyTickets.this, PremierLeague.class);
                startActivity(intent);
            }
        });

        // Click event for single list row
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                // ShowPlacePref(places, position);
                AlertDialog.Builder showPlace = new AlertDialog.Builder(
                        MyTickets.this);
                showPlace.setMessage("Remove Match from the list?");
                showPlace.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //REMOVE ITEM FROM LISTVIEW

                        ID_Array.remove(position);
                        Teamone_Array.remove(position);
                        Teamtwo_Array.remove(position);
                        Winningteam_Array.remove(position);
                        ChosenOdd_Array.remove(position);


                        cursor.moveToPosition(position);
                        sqLiteHelper.delete(cursor.getInt(cursor.getColumnIndex(SqliteHelper.Primary_Key)));
                        cursor=sqLiteHelper.getAll();
                        listview.setAdapter(ticketAdapter);
                        String count = "You have:  "  +listview.getAdapter().getCount()+ "  matche(s) on your ticket";
                        total_matches.setText(count);


                    }

                });
                showPlace.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                showPlace.show();
            }

        });


        cursor = sqLiteDatabase.rawQuery(" SELECT * FROM " + SqliteHelper.MyTicketsTable + "", null);


        ID_Array = new ArrayList<String>();
        Teamone_Array = new ArrayList<String>();
        Teamtwo_Array = new ArrayList<String>();
        Winningteam_Array = new ArrayList<String>();
        ChosenOdd_Array = new ArrayList<String>();

        if (cursor.moveToFirst()) {

            do {

                ID_Array.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.Primary_Key)));

                ListViewClickItemArray.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.Column_TeamOne)));

                Teamone_Array.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.Column_TeamOne)));
                Teamtwo_Array.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.Column_TeamTwo)));
                Winningteam_Array.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.Column_WinningTeam)));
                ChosenOdd_Array.add("with: " +cursor.getString(cursor.getColumnIndex(SqliteHelper.Column_ChosenODD)));

                // calculating product of odds on ticket
                String temp = cursor.getString(cursor.getColumnIndex(SqliteHelper.Column_ChosenODD));
                Double temp_value = (Double.parseDouble(temp) + 1.00) ;
                final_odd_product_holder = (temp_value*final_odd_product_holder);

                final_odd_product = String.valueOf(final_odd_product_holder);
            } while (cursor.moveToNext());

            Log.e("final_odd_product", String.valueOf(final_odd_product_holder));
        }


        ticketAdapter = new TicketAdapter(this,

                ID_Array,
                Teamone_Array,
                Teamtwo_Array,
                Winningteam_Array,
                ChosenOdd_Array
        );


        listview.setAdapter(ticketAdapter);
        String count = "You have:  "  +listview.getAdapter().getCount()+ "  match(s) on your ticket";
        total_matches.setText(count);

        //Adding Click Listener on button.

        go_to_bet_amount.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(MyTickets.this, Submit_Bet.class);
        Intent get_email = getIntent();

        email = get_email.getStringExtra("email");
        league = get_email.getStringExtra("league");
        DAY = get_email.getStringExtra("DAY");
        client_id = get_email.getStringExtra("client_id");

        intent.putExtra("email", email);
        intent.putExtra("league", league);
        intent.putExtra("DAY", DAY);
        intent.putExtra("client_id", client_id);
        intent.putExtra("device_id", device_id);
        intent.putExtra("final_odd_product", final_odd_product);

        Log.e("your email is", email);
        Log.e("your league is", league);
        Log.e("your day is", DAY);
        startActivity(intent);
    }



}

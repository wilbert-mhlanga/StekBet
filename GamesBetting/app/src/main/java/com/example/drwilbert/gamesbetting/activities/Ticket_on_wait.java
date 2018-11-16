package com.example.drwilbert.gamesbetting.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.drwilbert.gamesbetting.R;

import java.util.ArrayList;


// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/

public class Ticket_on_wait extends AppCompatActivity {

    TextView bet_amount_possible_win;
    SqliteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    private ListView listview;
    TicketAdapter ticketAdapter ;
    String my_bet_amount, my_possible_win;


    ArrayList<String> ID_Array;
    ArrayList<String> Teamone_Array;
    ArrayList<String> Teamtwo_Array;
    ArrayList<String> Winningteam_Array;
    ArrayList<String> ChosenOdd_Array;

    ArrayList<String> ListViewClickItemArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_on_wait);




        sqLiteHelper = new SqliteHelper(this);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();

        bet_amount_possible_win = (TextView) findViewById(R.id.betting_amount_possiblewin);
        listview = (ListView) findViewById(R.id.listView);


        Intent get_device_id = getIntent();
        my_bet_amount = get_device_id.getStringExtra("bet_amount");
        my_possible_win = get_device_id.getStringExtra("possible_win");


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

            } while (cursor.moveToNext());

        }


        ticketAdapter = new TicketAdapter(this,

                ID_Array,
                Teamone_Array,
                Teamtwo_Array,
                Winningteam_Array,
                ChosenOdd_Array
        );


        listview.setAdapter(ticketAdapter);
        String output = "bet amount:"  + "$"+my_bet_amount        +             "           possible win:" +"$"    +my_possible_win ;
        bet_amount_possible_win.setText(output);
    }

}

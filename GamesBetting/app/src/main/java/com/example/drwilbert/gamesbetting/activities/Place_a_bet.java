package com.example.drwilbert.gamesbetting.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drwilbert.gamesbetting.R;
import com.example.drwilbert.gamesbetting.firebase.Config;
import com.example.drwilbert.gamesbetting.firebase.NotificationUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/

public class Place_a_bet extends AppCompatActivity {

    TextView teams;
    SQLiteDatabase sqLiteDatabase;
    Button add_to_ticket, viewticket;
    RadioButton homeOdd, drawOdd, awayOdd;
    String finalodd, winning_team, winning_team_Holder;
    EditText amountbalance, betamount, possiblewin ;
    String  dayHolder, oddHolder, Possible_winHolder;
    Boolean CheckEditText ;
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_a_bet);

        //Assign Id'S
        teams = (TextView) findViewById(R.id.teams);

        homeOdd  = (RadioButton) findViewById(R.id.homeODD);
        drawOdd  = (RadioButton) findViewById(R.id.drawODD);
        awayOdd  = (RadioButton) findViewById(R.id.awayODD);

        amountbalance = (EditText)findViewById(R.id.amountbalance);
        betamount = (EditText)findViewById(R.id.betamount);
        possiblewin = (EditText)findViewById(R.id.possiblewin);

        add_to_ticket = (Button)findViewById(R.id.add_to_ticket);
        viewticket = (Button)findViewById(R.id.viewticket);


        Intent getData = getIntent();
        final String team_one = getData.getStringExtra("teamOne");
        final String team_two = getData.getStringExtra("teamTwo");
        homeOdd.setText(getData.getStringExtra("homeOdd"));
        drawOdd.setText(getData.getStringExtra("drawOdd"));
        awayOdd.setText(getData.getStringExtra("awayOdd"));

        teams.setText(team_one+    "   Vs   "    +team_two);



        betamount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Intent bet_on = getIntent();

                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.oddsgroup);
                int radioButtonID = radioGroup.getCheckedRadioButtonId();

                switch (radioButtonID) {
                    case R.id.homeODD:
                        finalodd = bet_on.getStringExtra("homeOdd");
                        break;
                    case R.id.drawODD:
                        finalodd = bet_on.getStringExtra("drawOdd");
                        break;
                    case R.id.awayODD:
                        finalodd = bet_on.getStringExtra("awayOdd");
                        break;

                }


                Double BetAmount = Double.parseDouble(betamount.getText().toString());
                Double final_finalOdd = Double.parseDouble(finalodd);
                double resultPossibleWin = (BetAmount * final_finalOdd);

                if (TextUtils.isEmpty(betamount.getText())) {

                    Toast.makeText(Place_a_bet.this, "Enter your betting amount", Toast.LENGTH_LONG).show();
                } else {

                    possiblewin.setText(String.valueOf("$" + resultPossibleWin + "0"));
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

//
            }
        });


                viewticket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Place_a_bet.this, MyTickets.class);
                        startActivity(intent);
                    }
                });



        //Adding Click Listener on button.
        add_to_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {

                    Intent Another_bet_on = getIntent();
                    RadioGroup radioGroup = (RadioGroup) findViewById(R.id.oddsgroup);
                    int radioButtonID = radioGroup.getCheckedRadioButtonId();

                    switch (radioButtonID) {

                        case R.id.homeODD:
                            finalodd = Another_bet_on.getStringExtra("homeOdd");
                            winning_team_Holder = Another_bet_on.getStringExtra("teamOne");
                            winning_team = winning_team_Holder + " is winning ";
                            break;
                        case R.id.drawODD:
                            finalodd = Another_bet_on.getStringExtra("drawOdd");
                            winning_team = "its a Draw";
                            break;
                        case R.id.awayODD:
                            finalodd = Another_bet_on.getStringExtra("awayOdd");
                            winning_team_Holder = Another_bet_on.getStringExtra("teamTwo");
                            winning_team = winning_team_Holder + " is winning ";
                            break;

                    }

                    SQLiteDataBaseBuild();

                    Double BetAmount = Double.parseDouble(betamount.getText().toString());
                    Double final_finalOdd = Double.parseDouble(finalodd);
                    double resultPossibleWin = (BetAmount * final_finalOdd);
                    Possible_winHolder = String.valueOf("possible win: " + "$" + resultPossibleWin + "0");

                    Intent intent = new Intent(Place_a_bet.this, MyTickets.class);

                    SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                    String regId = pref.getString("regId", null);
                    if (regId == null)
                        regId = FirebaseInstanceId.getInstance().getToken();

                    intent.putExtra("device_id", regId);

                    String SQLiteDataBaseQueryHolder = "INSERT INTO " + SqliteHelper.MyTicketsTable + " (firstteam,secondteam,winningteam,winning_amount) VALUES('" + team_one + "','" + team_two + "', '" + winning_team + "', '" + Possible_winHolder + "');";
                    sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);
                    startActivity(intent);

                } else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(Place_a_bet.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }


            }
        });



        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                }
            }
        };




    }



    public void CheckEditTextIsEmptyOrNot(){


        dayHolder = getIntent().getStringExtra("DAY");
        oddHolder = finalodd;
        Possible_winHolder = possiblewin.getText().toString();

        if(TextUtils.isEmpty(oddHolder) || TextUtils.isEmpty(Possible_winHolder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }

    public void SQLiteDataBaseBuild(){

        sqLiteDatabase = openOrCreateDatabase(SqliteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }


    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

}

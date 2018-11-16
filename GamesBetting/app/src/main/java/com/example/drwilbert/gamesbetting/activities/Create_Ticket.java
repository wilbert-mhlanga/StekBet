package com.example.drwilbert.gamesbetting.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drwilbert.gamesbetting.R;
import com.example.drwilbert.gamesbetting.firebase.Config;
import com.example.drwilbert.gamesbetting.firebase.NotificationUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;


// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/


public class Create_Ticket extends AppCompatActivity {

    TextView teams;
    SQLiteDatabase sqLiteDatabase;
    Button add_to_ticket, viewticket;
    RadioButton homeOdd, drawOdd, awayOdd;
    String email, league, DAY, client_id, chosen_odd, winning_team, winning_team_Holder;
    EditText amountbalance, betamount, possiblewin ;
    String  dayHolder, oddHolder, Possible_winHolder;
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
       // String email = getData.getStringExtra("email");
        final String team_one = getData.getStringExtra("teamOne");
        final String team_two = getData.getStringExtra("teamTwo");
        homeOdd.setText(getData.getStringExtra("homeOdd"));
        drawOdd.setText(getData.getStringExtra("drawOdd"));
        awayOdd.setText(getData.getStringExtra("awayOdd"));

        teams.setText(team_one +     "   Vs   "     + team_two);



                viewticket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Create_Ticket.this, MyTickets.class);
                        startActivity(intent);
                    }
                });



        //Adding Click Listener on button.
        add_to_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent Another_bet_on = getIntent();
                    RadioGroup radioGroup = (RadioGroup) findViewById(R.id.oddsgroup);
                    int radioButtonID = radioGroup.getCheckedRadioButtonId();

                    switch (radioButtonID) {

                        case R.id.homeODD:
                            chosen_odd = Another_bet_on.getStringExtra("homeOdd");

                            winning_team_Holder = Another_bet_on.getStringExtra("teamOne");
                            winning_team = winning_team_Holder + " is winning ";
                            break;
                        case R.id.drawODD:
                            chosen_odd = Another_bet_on.getStringExtra("drawOdd");
                            winning_team = "a Draw";
                            break;
                        case R.id.awayODD:
                            chosen_odd = Another_bet_on.getStringExtra("awayOdd");
                            winning_team_Holder = Another_bet_on.getStringExtra("teamTwo");
                            winning_team = winning_team_Holder + " is winning ";
                            break;

                    }


                if(TextUtils.isEmpty(chosen_odd))
                {

                    Toast.makeText(Create_Ticket.this, "Please Select your winning team (ODD)", Toast.LENGTH_LONG).show();

                }else {
                    SQLiteDataBaseBuild();

                    Intent intent = new Intent(Create_Ticket.this, MyTickets.class);
                    Intent get_email = getIntent();

                    email = get_email.getStringExtra("email");
                    league = get_email.getStringExtra("league");
                    client_id = get_email.getStringExtra("client_id");

//                    Bundle bundle = getArguments();
//                    email = bundle.getString("email");
//                    league = bundle.getString("league");

                    DAY = get_email.getStringExtra("DAY");

                    SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                    String regId = pref.getString("regId", null);
                    if (regId == null)
                        regId = FirebaseInstanceId.getInstance().getToken();


                    intent.putExtra("email", email);
                    intent.putExtra("league", league);
                    intent.putExtra("DAY", DAY);
                    intent.putExtra("client_id", client_id);
                    intent.putExtra("device_id", regId);

                    String SQLiteDataBaseQueryHolder = "INSERT INTO " + SqliteHelper.MyTicketsTable + " (firstteam,secondteam,winningteam,chosenodd) VALUES('" + team_one + "','" + team_two + "', '" + winning_team + "', '" + chosen_odd + "');";
                    Log.e("query: ", SQLiteDataBaseQueryHolder);
                    sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);
//                    Log.e("your pass league is", league);
 //                   Log.e("your pass email is", email);
                    startActivity(intent);
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

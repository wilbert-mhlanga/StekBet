package com.example.drwilbert.gamesbetting.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.drwilbert.gamesbetting.R;
import com.example.drwilbert.gamesbetting.firebase.Config;
import com.example.drwilbert.gamesbetting.firebase.NotificationUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/

public class Submit_Bet extends AppCompatActivity implements View.OnClickListener {


    SqliteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    Button place_a_bet;
    EditText betting_amount;
    JOSONparser JosonParser = new JOSONparser();
    private static final String URL = "https://smartbet.000webhostapp.com/bets/my_sending_ticket.php";
    //private static final String URL = "http://10.0.2.2/bets/my_sending_ticket.php";

    String email, league, match_day,client_id, device_id, final_odd_product ;
    Double possible_win, odd_product,BettingAmount ;

    //String HttpURL = "https://smartbet.000webhostapp.com/bets/bets.php";
    ProgressDialog progressDialog;
    private BroadcastReceiver mRegistrationBroadcastReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betting_amount);

        place_a_bet = (Button) findViewById(R.id.submit);
        betting_amount = (EditText)findViewById(R.id.editText_betting_amount);
        place_a_bet.setOnClickListener(this);

        sqLiteHelper = new SqliteHelper(this);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();




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

    @Override
    public void onClick(View v) {

        new SubmitBet().execute();

    }

    private class SubmitBet extends AsyncTask<Void, Void, Void> {


        public SubmitBet() {

        }


        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progressDialog = new ProgressDialog(Submit_Bet.this);
            progressDialog.setTitle("LOADING DATA");
            progressDialog.setMessage("Please Wait");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            cursor = sqLiteDatabase.rawQuery(" SELECT * FROM " + SqliteHelper.MyTicketsTable + "", null);
            List<String> params = new ArrayList<>();

            Calculate_Possible_win();

            if (cursor.moveToFirst()) {

                Intent get_email = getIntent();

                email = get_email.getStringExtra("email");
                league = get_email.getStringExtra("league");
                match_day = get_email.getStringExtra("DAY");
                client_id = get_email.getStringExtra("client_id");

                SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                String regId = pref.getString("regId", null);
                if (regId == null)
                    regId = FirebaseInstanceId.getInstance().getToken();
                device_id = regId;

                do {
                    // Building Parameters

                    params.add("{'id': '"+cursor.getString(cursor.getColumnIndex(SqliteHelper.Primary_Key)).toString()+"' ");
                    params.add("'match_day': '"+match_day+"'");
                    params.add("'team_one': '"+cursor.getString(cursor.getColumnIndex(SqliteHelper.Column_TeamOne)).toString()+"' ");
                    params.add("'team_two': '"+cursor.getString(cursor.getColumnIndex(SqliteHelper.Column_TeamTwo)).toString()+"' ");
                    params.add("'winning_team': '"+cursor.getString(cursor.getColumnIndex(SqliteHelper.Column_WinningTeam)).toString()+"'");

                    params.add("'betting_amount': '"+String.valueOf(BettingAmount)+"'");
                    params.add("'possible_win': '"+String.valueOf(possible_win)+"'");
                    params.add("'email': '"+email+"'");
                    params.add("'league': '"+league+"'");
                    params.add("'client_id': '"+client_id+"'");
                    params.add("'device_id': '"+device_id+"' }");

                } while (cursor.moveToNext());
            }


            List<NameValuePair> final_params = new ArrayList<NameValuePair>();

            //Log.e("your email is", email);
            Log.e("ticket_json:", params.toString());
            final_params.add(new BasicNameValuePair("ticket", params.toString()));

            Log.e(" final_ticket_json:", final_params.toString());

            JSONObject json = JosonParser.makeHttpRequest(URL, "POST",final_params);

            if (json==null) {

                Log.d("Ticket:  ", "Null");

            }else{

                Log.e("Post was Sent", json.toString());
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result)

        {
            sqLiteDatabase.close();
            progressDialog.dismiss();
            Toast.makeText(Submit_Bet.this, "Bet was send Successifully....Thank you", Toast.LENGTH_LONG).show();

          Intent intent = new Intent(Submit_Bet.this, Ticket_on_wait.class);
          intent.putExtra("bet_amount", String.valueOf(BettingAmount));
          intent.putExtra("possible_win", String.valueOf(possible_win));
          startActivity(intent);
        }
    }

    public void Calculate_Possible_win(){

        Intent get_device_id = getIntent();
        device_id = get_device_id.getStringExtra("device_id");
        final_odd_product = get_device_id.getStringExtra("final_odd_product");

        odd_product = Double.parseDouble(final_odd_product);
        BettingAmount = Double.parseDouble(betting_amount.getText().toString());

        possible_win = (odd_product * BettingAmount);

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

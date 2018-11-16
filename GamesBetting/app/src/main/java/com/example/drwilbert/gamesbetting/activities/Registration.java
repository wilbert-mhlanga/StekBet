package com.example.drwilbert.gamesbetting.activities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.drwilbert.gamesbetting.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/

public class Registration extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    Button register, log_in;
    EditText First_Name, Last_Name, Email, Password ;
    String F_Name_Holder, L_Name_Holder, EmailHolder, PasswordHolder;
    String finalResult ;
    String HttpURL = "https://smartbet.000webhostapp.com/register/UserRegistration.php";
    //String HttpURL = "http://10.0.2.2/register-login/UserRegistration.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

        String[] urls = {
            "https://smartbet.000webhostapp.com/betting-app/monday_matches.php",
            "https://smartbet.000webhostapp.com/betting-app/tuesday_matches.php",
            "https://smartbet.000webhostapp.com/betting-app/wednesday_matches.php",
            "https://smartbet.000webhostapp.com/betting-app/thursday_matches.php",
            "https://smartbet.000webhostapp.com/betting-app/friday_matches.php",
            "https://smartbet.000webhostapp.com/betting-app/saturday_matches.php",
            "https://smartbet.000webhostapp.com/betting-app/sunday_matches.php"
    };

//    String[] urls = {
//            "http://10.0.2.2/betting-app-test/monday_matches.php",
//            "http://10.0.2.2/betting-app-test/tuesday_matches.php",
//            "http://10.0.2.2/betting-app-test/wednesday_matches.php",
//            "http://10.0.2.2/betting-app-test/thursday_matches.php",
//            "http://10.0.2.2/betting-app-test/friday_matches.php",
//            "http://10.0.2.2/betting-app-test/saturday_matches.php",
//            "http://10.0.2.2/betting-app-test/sunday_matches.php",
//
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Assign Id'S
        First_Name = (EditText)findViewById(R.id.editTextF_Name);
        Last_Name = (EditText)findViewById(R.id.editTextL_Name);
        Email = (EditText)findViewById(R.id.editTextEmail);
        Password = (EditText)findViewById(R.id.editTextPassword);

        register = (Button)findViewById(R.id.Submit);
        log_in = (Button)findViewById(R.id.Login);

        //Adding Click Listener on button.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    // If EditText is not empty and CheckEditText = True then this block will execute.

                    UserRegisterFunction(F_Name_Holder,L_Name_Holder, EmailHolder, PasswordHolder);

                }
                else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(Registration.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }


            }
        });

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Registration.this, UserLogin.class);
                startActivity(intent);

            }
        });

    }

    public void CheckEditTextIsEmptyOrNot(){

        F_Name_Holder = First_Name.getText().toString();
        L_Name_Holder = Last_Name.getText().toString();
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();


        if(TextUtils.isEmpty(F_Name_Holder) || TextUtils.isEmpty(L_Name_Holder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }

    public void UserRegisterFunction(final String F_Name, final String L_Name, final String email, final String password){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Registration.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("Registration Successfully")){

                    SQLiteDataBaseBuild();
                    SQLiteTableBuild();
                    DeletePreviousData();
                    new StoreJSonDataInToSQLiteClass(Registration.this).execute();

                }else{
                    Toast.makeText(Registration.this,"Internet Connetion error, Reconnect and Again",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("f_name",params[0]);

                hashMap.put("L_name",params[1]);

                hashMap.put("email",params[2]);

                hashMap.put("password",params[3]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(F_Name,L_Name,email,password);
    }


    private class StoreJSonDataInToSQLiteClass extends AsyncTask<Void, Void, Void> {

        public Context context;

        String FinalJSonResult;

        public StoreJSonDataInToSQLiteClass(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progressDialog = new ProgressDialog(Registration.this);
            progressDialog.setTitle("LOADING DATA");
            progressDialog.setMessage("Please Wait");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            int x = 0;
            for (String url: urls) {
                HttpServiceClass httpServiceClass = new HttpServiceClass(url);

                try {
                    httpServiceClass.ExecutePostRequest();

                    if (httpServiceClass.getResponseCode() == 200) {

                        FinalJSonResult = httpServiceClass.getResponse();

                        if (FinalJSonResult != null) {

                            JSONArray jsonArray = null;
                            try {

                                jsonArray = new JSONArray(FinalJSonResult);
                                JSONObject jsonObject;
                                Log.e(SqliteHelper.TableNames[x] + ": Table", jsonArray.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    jsonObject = jsonArray.getJSONObject(i);

                                    String tempTeamone = jsonObject.getString("teamOne");
                                    //String tempTeamonelogo = jsonObject.getString("teamOneLogo");
                                    String tempTeamtwo = jsonObject.getString("teamTwo");
                                    //String tempTeamtwologo = jsonObject.getString("teamTwoLogo");
                                    String temphomeOdd = jsonObject.getString("homeOdd");
                                    String tempdrawOdd = jsonObject.getString("drawOdd");
                                    String tempawayOdd = jsonObject.getString("awayOdd");


                                    String SQLiteDataBaseQueryHolder = "INSERT INTO "+SqliteHelper.TableNames[x]+" (teamone,teamtwo,homeOdd,drawOdd,awayOdd) VALUES('"+tempTeamone+"','"+tempTeamtwo+"', '"+temphomeOdd+"', '"+tempdrawOdd+"', '"+tempawayOdd+"');";
                                    //Log.e("result", SQLiteDataBaseQueryHolder);
                                    sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);


                                }
                                x++;

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    } else {

                        Toast.makeText(context, httpServiceClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            sqLiteDatabase.close();
            Toast.makeText(Registration.this,"Registration Successiful, You can Login", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();

            Intent intent = new Intent(Registration.this, UserLogin.class);
            intent.putExtra("Email", Email.getText());
            startActivity(intent);
        }
    }


    public void SQLiteDataBaseBuild(){

        sqLiteDatabase = openOrCreateDatabase(SqliteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild(){

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+SqliteHelper.TableNames[0]+"("+SqliteHelper.Table_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  "+SqliteHelper.Table_Column_2_Teamone+" VARCHAR, "+SqliteHelper.Table_Column_3_Teamtwo+" VARCHAR,  "+SqliteHelper.Table_Column_5_homeOdd+" VARCHAR, "+SqliteHelper.Table_Column_6_drawOdd+" VARCHAR, "+SqliteHelper.Table_Column_7_awayOdd+" VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+SqliteHelper.TableNames[1]+"("+SqliteHelper.Table_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  "+SqliteHelper.Table_Column_2_Teamone+" VARCHAR, "+SqliteHelper.Table_Column_3_Teamtwo+" VARCHAR,  "+SqliteHelper.Table_Column_5_homeOdd+" VARCHAR, "+SqliteHelper.Table_Column_6_drawOdd+" VARCHAR, "+SqliteHelper.Table_Column_7_awayOdd+" VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+SqliteHelper.TableNames[2]+"("+SqliteHelper.Table_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  "+SqliteHelper.Table_Column_2_Teamone+" VARCHAR, "+SqliteHelper.Table_Column_3_Teamtwo+" VARCHAR,  "+SqliteHelper.Table_Column_5_homeOdd+" VARCHAR, "+SqliteHelper.Table_Column_6_drawOdd+" VARCHAR, "+SqliteHelper.Table_Column_7_awayOdd+" VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+SqliteHelper.TableNames[3]+"("+SqliteHelper.Table_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  "+SqliteHelper.Table_Column_2_Teamone+" VARCHAR, "+SqliteHelper.Table_Column_3_Teamtwo+" VARCHAR,  "+SqliteHelper.Table_Column_5_homeOdd+" VARCHAR, "+SqliteHelper.Table_Column_6_drawOdd+" VARCHAR, "+SqliteHelper.Table_Column_7_awayOdd+" VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+SqliteHelper.TableNames[4]+"("+SqliteHelper.Table_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  "+SqliteHelper.Table_Column_2_Teamone+" VARCHAR, "+SqliteHelper.Table_Column_3_Teamtwo+" VARCHAR,  "+SqliteHelper.Table_Column_5_homeOdd+" VARCHAR, "+SqliteHelper.Table_Column_6_drawOdd+" VARCHAR, "+SqliteHelper.Table_Column_7_awayOdd+" VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+SqliteHelper.TableNames[5]+"("+SqliteHelper.Table_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  "+SqliteHelper.Table_Column_2_Teamone+" VARCHAR, "+SqliteHelper.Table_Column_3_Teamtwo+" VARCHAR,  "+SqliteHelper.Table_Column_5_homeOdd+" VARCHAR, "+SqliteHelper.Table_Column_6_drawOdd+" VARCHAR, "+SqliteHelper.Table_Column_7_awayOdd+" VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+SqliteHelper.TableNames[6]+"("+SqliteHelper.Table_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  "+SqliteHelper.Table_Column_2_Teamone+" VARCHAR, "+SqliteHelper.Table_Column_3_Teamtwo+" VARCHAR,  "+SqliteHelper.Table_Column_5_homeOdd+" VARCHAR, "+SqliteHelper.Table_Column_6_drawOdd+" VARCHAR, "+SqliteHelper.Table_Column_7_awayOdd+" VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+SqliteHelper.TableNames[7]+"("+SqliteHelper.Table_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  "+SqliteHelper.Table_Column_2_Teamone+" VARCHAR, "+SqliteHelper.Table_Column_3_Teamtwo+" VARCHAR,  "+SqliteHelper.Table_Column_5_homeOdd+" VARCHAR, "+SqliteHelper.Table_Column_6_drawOdd+" VARCHAR, "+SqliteHelper.Table_Column_7_awayOdd+" VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+SqliteHelper.TableNames[8]+"("+SqliteHelper.Table_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  "+SqliteHelper.Table_Column_2_Teamone+" VARCHAR, "+SqliteHelper.Table_Column_3_Teamtwo+" VARCHAR,  "+SqliteHelper.Table_Column_5_homeOdd+" VARCHAR, "+SqliteHelper.Table_Column_6_drawOdd+" VARCHAR, "+SqliteHelper.Table_Column_7_awayOdd+" VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+SqliteHelper.TableNames[9]+"("+SqliteHelper.Table_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  "+SqliteHelper.Table_Column_2_Teamone+" VARCHAR, "+SqliteHelper.Table_Column_3_Teamtwo+" VARCHAR,  "+SqliteHelper.Table_Column_5_homeOdd+" VARCHAR, "+SqliteHelper.Table_Column_6_drawOdd+" VARCHAR, "+SqliteHelper.Table_Column_7_awayOdd+" VARCHAR)");

        // table for tickets
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+SqliteHelper.MyTicketsTable+"("+SqliteHelper.Primary_Key+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  "+SqliteHelper.Column_TeamOne+" VARCHAR, "+SqliteHelper.Column_TeamTwo+" VARCHAR,  "+SqliteHelper.Column_WinningTeam+" VARCHAR, "+SqliteHelper.Column_ChosenODD +" VARCHAR)");

    }

    public void DeletePreviousData(){

        sqLiteDatabase.execSQL("DELETE FROM " + SqliteHelper.TableNames[0] + "");
        sqLiteDatabase.execSQL("DELETE FROM " + SqliteHelper.TableNames[1] + "");
        sqLiteDatabase.execSQL("DELETE FROM " + SqliteHelper.TableNames[2] + "");
        sqLiteDatabase.execSQL("DELETE FROM " + SqliteHelper.TableNames[3] + "");
        sqLiteDatabase.execSQL("DELETE FROM " + SqliteHelper.TableNames[4] + "");
        sqLiteDatabase.execSQL("DELETE FROM " + SqliteHelper.TableNames[5] + "");
        sqLiteDatabase.execSQL("DELETE FROM " + SqliteHelper.TableNames[6] + "");
        sqLiteDatabase.execSQL("DELETE FROM " + SqliteHelper.TableNames[7] + "");
        sqLiteDatabase.execSQL("DELETE FROM " + SqliteHelper.TableNames[8] + "");
        sqLiteDatabase.execSQL("DELETE FROM " + SqliteHelper.TableNames[9] + "");

        // for tickets
        sqLiteDatabase.execSQL("DELETE FROM " + SqliteHelper.MyTicketsTable + "");

    }


}

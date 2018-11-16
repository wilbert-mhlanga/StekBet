package com.example.drwilbert.gamesbetting.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drwilbert.gamesbetting.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;



// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/

public class UserLogin extends AppCompatActivity {


    EditText Email, Password;
    Button LogIn ;
    String PasswordHolder, EmailHolder;
    String finalResult, client_id ;
    //String HttpURL = "http://10.0.2.2/register-login/UserLogin.php";
    String HttpURL = "https://smartbet.000webhostapp.com/register/UserLogin.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    public static final String UserEmail = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);
        LogIn = (Button)findViewById(R.id.Login);

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    UserLoginFunction(EmailHolder, PasswordHolder);

                }
                else {

                    Toast.makeText(UserLogin.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    public void CheckEditTextIsEmptyOrNot(){

        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {
            CheckEditText = false;
        }
        else {

            CheckEditText = true ;
        }
    }

    public void UserLoginFunction(final String email, final String password){

        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(UserLogin.this,"Checking Login details ",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("Data Matched")){

                    Intent intent = new Intent(UserLogin.this, MainMenu.class);
                    Intent get_clientID = getIntent();

                    client_id = get_clientID.getStringExtra("client_id");

                    intent.putExtra("email", EmailHolder);
                    intent.putExtra("client_id", client_id);
                    Log.e("your email is", EmailHolder);
                    startActivity(intent);

                     }else if(httpResponseMsg.equalsIgnoreCase("Invalid Username or Password Please Try Again")){

                                Toast.makeText(UserLogin.this,"Username OR password incorrect",Toast.LENGTH_LONG).show();
                         }else{

                    Toast.makeText(UserLogin.this,"Internet Connetion error, Reconnect and Again",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("email",params[0]);

                hashMap.put("password",params[1]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(email,password);
    }


}

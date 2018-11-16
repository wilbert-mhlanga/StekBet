package com.example.drwilbert.gamesbetting.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.drwilbert.gamesbetting.R;
import com.example.drwilbert.gamesbetting.activities.leagues.Bundesliga;
import com.example.drwilbert.gamesbetting.activities.leagues.LaLiga;
import com.example.drwilbert.gamesbetting.activities.leagues.PremierLeague;
import com.example.drwilbert.gamesbetting.activities.leagues.SerieA;
import com.example.drwilbert.gamesbetting.activities.leagues.World_Cup_Matches;


import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/

public class MainMenu extends Activity{

    ListView list;
    String email;
    String client_id;

    String[] leagues ={

            "Premeir League",
            "La Liga",
            "Bundesliga",
            "Serie A",
            "World Cup Games"
    };

    Integer[] imgid={
            R.drawable.premier_league,
            R.drawable.laliga,
            R.drawable.bundesliga,
            R.drawable.serie_league,
            R.drawable.worldcup,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_leagues);

        LeaguesListAdapter adapter = new LeaguesListAdapter(this, leagues, imgid);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                Intent get_email = getIntent();


                switch (position) {
                    case 0:
                        //startActivity(new Intent(MainMenu.this, PremierLeague.class));
                        Intent intentPL = new Intent(MainMenu.this, PremierLeague.class);
                        email = get_email.getStringExtra("email");
                        client_id = get_email.getStringExtra("client_id");
                        intentPL.putExtra("email", email);
                        intentPL.putExtra("league", "PremierLeague");
                        intentPL.putExtra("client_id", client_id);
                        Log.e("your email is", email);
                        startActivity(intentPL);
                        break;
                    case 1:
                        //startActivity(new Intent(MainMenu.this, LaLiga.class));
                        Intent intentLL = new Intent(MainMenu.this, LaLiga.class);
                        email = get_email.getStringExtra("email");
                        client_id = get_email.getStringExtra("client_id");
                        intentLL.putExtra("email", email);
                        intentLL.putExtra("league","LaLiga");
                        intentLL.putExtra("client_id", client_id);
                        startActivity(intentLL);
                        break;
                    case 2:
                        //startActivity(new Intent(MainMenu.this, Bundesliga.class));
                        Intent intentBL = new Intent(MainMenu.this, Bundesliga.class);
                        email = get_email.getStringExtra("email");
                        client_id = get_email.getStringExtra("client_id");
                        intentBL.putExtra("email", email);
                        intentBL.putExtra("league","Bundesliga");
                        intentBL.putExtra("client_id", client_id);
                        startActivity(intentBL);
                        break;
                    case 3:
                        //startActivity(new Intent(MainMenu.this, SerieA.class));
                        Intent intentSA = new Intent(MainMenu.this, SerieA.class);
                        email = get_email.getStringExtra("email");
                        client_id = get_email.getStringExtra("client_id");
                        intentSA.putExtra("email", email);
                        intentSA.putExtra("league","SerieA");
                        intentSA.putExtra("client_id", client_id);
                        startActivity(intentSA);
                        break;
                    case 4:

                        Toast.makeText(MainMenu.this, "World cup games not yet available", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(MainMenu.this, World_Cup_Matches.class));
                }

            }
        });
    }
}
package com.example.drwilbert.gamesbetting.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.drwilbert.gamesbetting.R;
import com.example.drwilbert.gamesbetting.activities.ListAdapter;
import com.example.drwilbert.gamesbetting.activities.Place_a_bet;
import com.example.drwilbert.gamesbetting.activities.SqliteHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.ArrayList;


public class SundayFragment extends Fragment{

    SqliteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListAdapter listAdapter ;
    ArrayList<String> ID_Array;
    ArrayList<String> Teamone_Array;
    ArrayList<String> Teamtwo_Array;
    //    ArrayList<String> Teamonelogo_Array;
//    ArrayList<String> Teamtwologo_Array;
    ArrayList<String> homeOdd_Array;
    ArrayList<String> drawOdd_Array;
    ArrayList<String> awayOdd_Array;

    ArrayList<String> ListViewClickItemArray = new ArrayList<String>();

    private ListView listview;
    Activity mActivity;
    View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        rootView = inflater.inflate(R.layout.fragment_sunday, container, false);

        listview = (ListView) rootView.findViewById(R.id.sunday);

        mActivity = getActivity();

        ID_Array = new ArrayList<String>();

        Teamone_Array = new ArrayList<String>();
        //Teamonelogo_Array = new ArrayList<String>();
        Teamtwo_Array = new ArrayList<String>();
        //Teamtwologo_Array = new ArrayList<String>();
        homeOdd_Array = new ArrayList<String>();
        drawOdd_Array = new ArrayList<String>();
        awayOdd_Array = new ArrayList<String>();

        sqLiteHelper = new SqliteHelper(mActivity);

        initView();

        return rootView;
    }

    @Override
    public void onResume() {

        initView(); ;

        super.onResume();
    }

    private void initView() {

        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SqliteHelper.TableNames[6] + "", null);

        ID_Array.clear();
        Teamone_Array.clear();
        //Teamonelogo_Array.clear();
        Teamtwo_Array.clear();
        //Teamtwologo_Array.clear();

        if (cursor.moveToFirst()) {
            do {

                ID_Array.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.Table_Column_ID)));

                //Inserting Column Name into Array to Use at ListView Click Listener Method.
                ListViewClickItemArray.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.Table_Column_2_Teamone)));

                //Teamonelogo_Array.add("https://smartbet.000webhostapp.com/betting-app/"+cursor.getString(cursor.getColumnIndex(SqliteHelper.Table_Column_1_Teamonelogo)));
                Teamone_Array.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.Table_Column_2_Teamone)));
                Teamtwo_Array.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.Table_Column_3_Teamtwo)));
                //Teamtwologo_Array.add("https://smartbet.000webhostapp.com/betting-app/"+cursor.getString(cursor.getColumnIndex(SqliteHelper.Table_Column_4_Teamtwologo)));

                homeOdd_Array.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.Table_Column_5_homeOdd)));
                drawOdd_Array.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.Table_Column_6_drawOdd)));
                awayOdd_Array.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.Table_Column_7_awayOdd)));

            } while (cursor.moveToNext());
        }

        listAdapter = new ListAdapter(mActivity,

                ID_Array,
                Teamone_Array,
                Teamtwo_Array,
                homeOdd_Array,
                drawOdd_Array,
                awayOdd_Array
//                Teamonelogo_Array,
//                Teamtwologo_Array
        );

        listview.setAdapter(listAdapter);
        listview.setOnItemClickListener(myOnItemClickListener);

        cursor.close();
    }

    AdapterView.OnItemClickListener myOnItemClickListener  = new AdapterView.OnItemClickListener(){

        @SuppressLint("NewApi") @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {



            Intent intent = new Intent(getActivity(), Place_a_bet.class);

            intent.putExtra("DAY", "Tuesday");
            //intent.putExtra("teamOneLogo", Teamonelogo_Array.get(position));
            intent.putExtra("teamOne", Teamone_Array.get(position));
            intent.putExtra("teamTwo", Teamtwo_Array.get(position));
            //intent.putExtra("teamTwoLogo", Teamtwologo_Array.get(position));

            intent.putExtra("homeOdd", homeOdd_Array.get(position));
            intent.putExtra("drawOdd", drawOdd_Array.get(position));
            intent.putExtra("awayOdd", awayOdd_Array.get(position));


            getActivity().startActivity(intent);
        }
    };
}
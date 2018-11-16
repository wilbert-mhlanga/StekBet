package com.example.drwilbert.gamesbetting.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/

public class SqliteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static String DATABASE_NAME="MatchesDatabase";

    public static final String MyTicketsTable = "TicketsTable";

    public static final String[] TableNames = {
            "MondayTable",
            "TuesdayTable",
            "WednesdayTable",
            "ThursdayTable",
            "FridayTable",
            "SaturdayTable",
            "SundayTable",
            "AfricanTable",
            "ZimbabweanTable",
            "InternationalTable"
    };


    public static final String Table_Column_ID="id";
   // public static final String Table_Column_1_Teamonelogo="teamonelogo";
    public static final String Table_Column_2_Teamone="teamone";
    public static final String Table_Column_3_Teamtwo="teamtwo";
    //public static final String Table_Column_4_Teamtwologo="teamtwologo";
    public static final String Table_Column_5_homeOdd="homeOdd";
    public static final String Table_Column_6_drawOdd="drawOdd";
    public static final String Table_Column_7_awayOdd="awayOdd";

    // Columns for TicketsTable
    public static final String Primary_Key = "id";
    public static final String Column_TeamOne= "firstteam";
    public static final String Column_TeamTwo= "secondteam";
    public static final String Column_WinningTeam= "winningteam";
    public static final String Column_ChosenODD = "chosenodd";

    public SqliteHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        for(String table : TableNames) {
            String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+table+" ("+Table_Column_ID+" INTEGER PRIMARY KEY, "+Table_Column_2_Teamone+" VARCHAR, "+Table_Column_3_Teamtwo+" VARCHAR,"+Table_Column_5_homeOdd+" VARCHAR, "+Table_Column_6_drawOdd+" VARCHAR,"+Table_Column_7_awayOdd+" VARCHAR)";
            database.execSQL(CREATE_TABLE);
        }

        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+MyTicketsTable+" ("+Primary_Key+" INTEGER PRIMARY KEY, "+Column_TeamOne+" VARCHAR, "+Column_TeamTwo+" VARCHAR,"+Column_WinningTeam+" VARCHAR,"+ Column_ChosenODD +" VARCHAR )";
        database.execSQL(CREATE_TABLE);

    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String table : TableNames){

            db.execSQL("DROP TABLE IF EXISTS " +table);
            onCreate(db);
        }

        db.execSQL("DROP TABLE IF EXISTS " +MyTicketsTable);
        onCreate(db);
    }


    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MyTicketsTable,Primary_Key+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT  * FROM "+MyTicketsTable, null);
        return c;
    }



}
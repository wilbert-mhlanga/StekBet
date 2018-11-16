package com.example.drwilbert.gamesbetting.activities;

// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/


import java.util.List;

public interface FetchDataListener {
    public void onFetchComplete(List<Application> data);
    public void onFetchFailure(String msg);
}
package com.example.drwilbert.gamesbetting.activities;


// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/

public class Application {
    private String teamone;
    private String teamtwo;
    private String teamOneLogo;
    private String teamTwoLogo;

    public String getTeamOneLogo() {

        return teamOneLogo;
    }

    public void setTeamOneLogo(String teamOneLogo) {

        String URIteamonelogo = "http://10.0.2.2/betting-app/"+teamOneLogo;
        this.teamOneLogo = URIteamonelogo;
    }

    public String getTeamTwoLogo() {
        return teamTwoLogo;
    }
    public void setTeamTwoLogo(String teamTwoLogo) {
        String URIteamtwologo = "http://10.0.2.2/betting-app/"+teamTwoLogo;
        this.teamTwoLogo = URIteamtwologo;
    }

    public String getTeamone() {
        return teamone;
    }
    public void setTeamone(String teamone) {
        this.teamone = teamone;
    }


    public String getTeamtwo() {
        return teamtwo;
    }
    public void setTeamtwo(String teamtwo) {
        this.teamtwo = teamtwo;
    }


}

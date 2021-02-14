package com.example.c3a_game;

import java.io.Serializable;

public class Player implements Serializable {
    private String userName;
    private String linkAva;
    private int score;
    private int status;
    public CustomListAdapter.ViewHolder holder;

    public Player(String userName){
        this.userName = userName;
        this.linkAva = "";
        this.score = 0;
        this.status = 0;
    }

    public String getName(){
        return userName;
    }

    public void setName(String name){
        this.userName = name;
    }

    public void setLinkAva(String link){
        this.linkAva = link;
    }

    public int getScore(){
        return score;
    }

    public void updateScore(int score){
        this.score += score;
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public CustomListAdapter.ViewHolder getHolder(){
        return holder;
    }

    public void setHolder(CustomListAdapter.ViewHolder holder){
        this.holder = holder;
    }
}

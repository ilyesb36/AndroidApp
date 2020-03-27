package com.example.myapplication.classes;

public class Player {

    private Integer gold;

    public Player() {

    }

    public Player(Integer gold) {
        this.gold = gold;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    @Override
    public String toString() {
        return "Player{" +
                "gold=" + gold +
                '}';
    }
}

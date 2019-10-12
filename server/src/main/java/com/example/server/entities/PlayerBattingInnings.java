package com.example.server.entities;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "player_batting_innings")
public class PlayerBattingInnings {

    @Id
    private int id;

    @Column
    private int inningsId;

    @Column
    private int playerId;

    @Column
    private int position;

    @Column
    private int deliveries;

    @Column
    private int runs;

    @Column
    private int fours;

    @Column
    private int sixes;

    @Column
    private String wicket;

    @Column
    private String wicketFielder;

    @Column
    private String wicketBowler;

    public int getId() {
        return id;
    }

    public int getInningsId() {
        return inningsId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getPosition() {
        return position;
    }

    public int getDeliveries() {
        return deliveries;
    }

    public int getRuns() {
        return runs;
    }

    public int getFours() {
        return fours;
    }

    public int getSixes() {
        return sixes;
    }

    public String getWicket() {
        return wicket;
    }

    public String getWicketFielder() {
        return wicketFielder;
    }

    public String getWicketBowler() {
        return wicketBowler;
    }
}

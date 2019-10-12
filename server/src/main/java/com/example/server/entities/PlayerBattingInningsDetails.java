package com.example.server.entities;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "player_batting_innings_details")
public class PlayerBattingInningsDetails {

    @Id
    private Integer id;

    @Column
    private String playerName;

    @Column
    private String opposition;

    @Column
    private Integer position;

    @Column
    private Integer deliveries;

    @Column
    private Integer runs;

    @Column
    private Integer fours;

    @Column
    private Integer sixes;

    @Column
    private String wicketType;

    @Column
    private Float strikeRate;

    @Column
    private Float percentOfTotal;

    public Integer getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getOpposition() {
        return opposition;
    }

    public Integer getPosition() {
        return position;
    }

    public Integer getDeliveries() {
        return deliveries;
    }

    public Integer getRuns() {
        return runs;
    }

    public Integer getFours() {
        return fours;
    }

    public Integer getSixes() {
        return sixes;
    }

    public String getWicketType() {
        return wicketType;
    }

    public Float getStrikeRate() {
        return strikeRate;
    }

    public Float getPercentOfTotal() {
        return percentOfTotal;
    }
}

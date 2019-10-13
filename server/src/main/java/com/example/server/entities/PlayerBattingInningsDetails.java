package com.example.server.entities;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Immutable
@Table(name = "player_batting_innings_details")
public class PlayerBattingInningsDetails {

    @Id
    private Integer id;

    @Column(name = "scorecard_name")
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
}

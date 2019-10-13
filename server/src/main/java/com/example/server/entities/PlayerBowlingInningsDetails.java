package com.example.server.entities;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Data
@Table(name = "player_bowling_innings_details")
public class PlayerBowlingInningsDetails {

    @Id
    private Integer id;

    @Column(name = "scorecard_name")
    private String playerName;

    @Column
    private String opposition;

    @Column
    private Integer bowlerNumber;

    @Column
    private Integer deliveries;

    @Column
    private Integer maidens;

    @Column
    private Integer runs;

    @Column
    private Integer wickets;

    @Column
    private Integer wides;

    @Column
    private Integer noBalls;

    @Column
    private Integer hatTricks;

    @Column
    private Float economy;

    @Column
    private Float strikeRate;
}

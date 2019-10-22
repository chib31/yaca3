package com.example.server.entities;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Immutable
@Data
@Table(name = "player_bowling_innings_details")
public class PlayerBowlingDetail {

    @Id
    private Integer id;

    @Column(name = "scorecard_name")
    private String playerName;

    @Column
    private String opposition;

    @Column
    private Date date;

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
    private String result;

    @Column
    private String economy;

    @Column
    private Integer overLength;
}

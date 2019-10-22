package com.example.server.entities;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Data
@Immutable
@Table(name = "player_batting_innings_details")
public class PlayerBattingDetail {

    @Id
    private Integer id;

    @Column(name = "scorecard_name")
    private String playerName;

    @Column
    private String opposition;

    @Column
    private Date date;

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
    private Integer teamTotal;

    @Column
    private String result;
}
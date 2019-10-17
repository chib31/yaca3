package com.example.server.JsonObjects;

import lombok.Data;
import java.sql.Date;

@Data
public class PlayerBattingDetailsJson {
    private Integer id;
    private String playerName;
    private String opposition;
    private Date date;
    private Integer position;
    private Integer deliveries;
    private Integer runs;
    private Integer fours;
    private Integer sixes;
    private String wicketType;
    private String fixture;
    private Double strikeRate;
    private Double percentOfTotal;
}

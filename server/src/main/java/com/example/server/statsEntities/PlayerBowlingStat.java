package com.example.server.statsEntities;

import com.example.server.annotations.DisplayName;
import com.example.server.annotations.Hidden;
import com.example.server.annotations.Sortable;
import lombok.Data;

import java.sql.Date;

@Data
public class PlayerBowlingStat {

  @Hidden
  private Integer id;
  
  @Sortable
  private String playerName;

  @Hidden
  private Integer bowlerNumber;

  @Hidden
  private Integer deliveries;

  @Sortable
  private String overs;

  @Sortable
  private Integer maidens;

  @Sortable
  private Integer runs;

  @Sortable
  private Integer wickets;

  @Sortable
  private Integer wides;

  @Sortable
  private Integer noBalls;

  @Sortable
  @DisplayName("S/R")
  private Double strikeRate;

  @Sortable
  private Double average;

  @Sortable
  private Double economy;

  @Hidden
  private Date date;

  @Hidden
  private String opposition;

  @Sortable
  private String fixture;
}

package com.example.server.statsEntities;

import com.example.server.annotations.DisplayName;
import com.example.server.annotations.Display;
import com.example.server.annotations.Sortable;
import lombok.Data;

import java.sql.Date;

import static com.example.server.enumerations.DisplayType.ALWAYS_HIDE;

@Data
public class PlayerBowlingStat {

  @Display(ALWAYS_HIDE)
  private Integer id;
  
  @Sortable
  private String playerName;

  @Display(ALWAYS_HIDE)
  private Integer bowlerNumber;

  @Display(ALWAYS_HIDE)
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

  @Display(ALWAYS_HIDE)
  private Date date;

  @Display(ALWAYS_HIDE)
  private String opposition;

  @Sortable
  private String fixture;
}

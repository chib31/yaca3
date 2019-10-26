package com.example.server.statsEntities;

import com.example.server.annotations.DisplayName;
import com.example.server.annotations.Filter;
import com.example.server.annotations.Hidden;
import com.example.server.annotations.Sortable;
import lombok.Data;

import java.sql.Date;

@Data
public class PlayerBattingStat {

  @Hidden
  private Integer id;

  // Filter - multi select dropdown
  @Filter("text")
  @Sortable
  private String playerName;

  @Filter("number")
  @Sortable
  private Integer deliveries;

  // Filter min / max
  @Sortable
  private Integer runs;

  // Filter min / max
  @Sortable
  private Integer fours;

  // Filter min / max
  @Sortable
  private Integer sixes;

  // Filter - multi select dropdown
  @Filter("text")
  @Sortable
  @DisplayName("Wicket")
  private String wicketType;

  // Filter min / max
  @Sortable
  @DisplayName("S/R")
  private Double strikeRate;

  // Filter min / max
  @Sortable
  @DisplayName("% of Total")
  private Double percentOfTotal;

  // Filter - multi select dropdown
  @Sortable
  private Integer position;

  // Filter - multi select dropdown for year
  @Hidden
  private Date date;

  // Filter - multi select dropdown
  @Hidden
  private String opposition;

  @Sortable
  private String fixture;
}

package com.example.server.statsEntities;

import com.example.server.annotations.DisplayName;
import com.example.server.annotations.Formatter;
import com.example.server.annotations.Hidden;
import com.example.server.annotations.Sortable;
import lombok.Data;

import java.sql.Date;

@Data
public class PlayerBattingStat {

  @Hidden
  private Integer id;

  @Sortable
  private String playerName;

  @Sortable
  private Integer deliveries;

  @Sortable
  private Integer runs;

  @Sortable
  private Integer fours;

  @Sortable
  private Integer sixes;

  @Sortable
  @DisplayName("Wicket")
  private String wicketType;

  @Sortable
  @DisplayName("S/R")
  private Double strikeRate;

  @Sortable
  @DisplayName("% of Total")
  @Formatter("() => { return 'hello' }")
  private Double percentOfTotal;

  @Sortable
  private Integer position;

  @Hidden
  private Date date;

  @Hidden
  private String opposition;

  @Sortable
  private String fixture;
}

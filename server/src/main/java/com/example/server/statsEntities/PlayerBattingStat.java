package com.example.server.statsEntities;

import com.example.server.annotations.*;
import com.example.server.enumerations.FilterType;
import lombok.Data;

import java.sql.Date;

import static com.example.server.enumerations.DisplayType.*;

@Data
public class PlayerBattingStat {

  @Display(ALWAYS_HIDE)
  private Integer id;

  @Display(ALWAYS_SHOW)
  @Filter(FilterType.TEXT)
  @Sortable
  @DefaultSortAsc
  private String playerName;

  @Filter(FilterType.NUMBER)
  @Sortable
  private Integer deliveries;

  @Filter(FilterType.NUMBER)
  @Sortable(1)
  private Integer runs;

  @Filter(FilterType.NUMBER)
  @Sortable
  private Integer fours;

  @Filter(FilterType.NUMBER)
  @Sortable
  private Integer sixes;

  @Filter(FilterType.TEXT)
  @DisplayName("Wicket")
  private String wicketType;

  @Filter(FilterType.NUMBER)
  @Sortable(2)
  @DisplayName("S/R")
  private Double strikeRate;

  @Filter(FilterType.NUMBER)
  @Sortable
  @DisplayName("% of Total")
  @Display(OPTIONAL_HIDE)
  private Double percentOfTotal;

  @Filter(FilterType.NUMBER)
  @Sortable
  @Display(OPTIONAL_HIDE)
  @DefaultSortAsc
  private Integer position;

  @Display(ALWAYS_HIDE)
  private Date date;

  @Filter(FilterType.TEXT)
  @Display(ALWAYS_HIDE)
  private String opposition;

  @Display(OPTIONAL_HIDE)
  private String fixture;
}

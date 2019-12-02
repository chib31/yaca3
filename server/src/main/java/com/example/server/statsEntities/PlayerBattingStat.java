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
  @Filter(FilterType.TEXT_FILTER)
  @Sortable
  @DefaultSortAsc 
  private String playerName;

  @Filter(FilterType.NUMBER_FILTER)
  @Sortable
  private Integer deliveries;

  @Filter(FilterType.NUMBER_FILTER)
  @Sortable(1)
  private Integer runs;

  @Filter(FilterType.NUMBER_FILTER)
  @Sortable
  private Integer fours;

  @Filter(FilterType.NUMBER_FILTER)
  @Sortable
  private Integer sixes;

  @Filter(FilterType.TEXT_FILTER)
  @DisplayName("Wicket")
  private String wicketType;

  @Filter(FilterType.NUMBER_FILTER)
  @Sortable(2)
  @DisplayName("S/R")
  private Double strikeRate;

  @Filter(FilterType.NUMBER_FILTER)
  @Sortable
  @DisplayName("% of Total")
  @Display(OPTIONAL_HIDE)
  private Double percentOfTotal;

  @Filter(FilterType.NUMBER_FILTER)
  @Sortable
  @Display(OPTIONAL_HIDE)
  @DefaultSortAsc
  private Integer position;

  @Display(ALWAYS_HIDE)
  private Date date;

  @Filter(FilterType.TEXT_FILTER)
  @Display(ALWAYS_HIDE)
  private String opposition;

  @Display(OPTIONAL_HIDE)
  private String fixture;
}

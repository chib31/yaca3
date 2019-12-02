package com.example.server.statsEntities;

import com.example.server.annotations.*;
import com.example.server.enumerations.FilterType;
import lombok.Data;

import java.sql.Date;

import static com.example.server.enumerations.DisplayType.*;

@Data
public class PlayerBowlingStat {

  @Display(ALWAYS_HIDE)
  private Integer id;
  
  @Sortable
  @Filter(FilterType.TEXT_FILTER)
  @Display(ALWAYS_SHOW)
  private String playerName;

  @Display(ALWAYS_HIDE)
  private Integer bowlerNumber;

  @Display(ALWAYS_HIDE)
  @Filter(FilterType.NUMBER_FILTER)
  private Integer deliveries;

  @Sortable
  private String overs;

  @Sortable
  @Filter(FilterType.NUMBER_FILTER)
  private Integer maidens;

  @Sortable
  @Filter(FilterType.NUMBER_FILTER)
  private Integer runs;

  @Sortable(1)
  @Filter(FilterType.NUMBER_FILTER)
  private Integer wickets;

  @Sortable
  @Display(OPTIONAL_HIDE)
  @Filter(FilterType.NUMBER_FILTER)
  private Integer wides;

  @Sortable
  @Display(OPTIONAL_HIDE)
  @Filter(FilterType.NUMBER_FILTER)
  private Integer noBalls;

  @Sortable
  @Filter(FilterType.NUMBER_FILTER)
  @DisplayName("S/R")
  private Double strikeRate;

  @Sortable
  @Filter(FilterType.NUMBER_FILTER)
  private Double average;

  @Sortable(2)
  @DefaultSortAsc
  @Filter(FilterType.NUMBER_FILTER)
  private Double economy;

  @Display(ALWAYS_HIDE)
  private Date date;

  @Display(ALWAYS_HIDE)
  @Filter(FilterType.TEXT_FILTER)
  private String opposition;

  @Display(OPTIONAL_HIDE)
  private String fixture;
}

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
  @Filter(FilterType.TEXT)
  @Display(ALWAYS_SHOW)
  private String playerName;

  @Display(ALWAYS_HIDE)
  private Integer bowlerNumber;

  @Display(ALWAYS_HIDE)
  @Filter(FilterType.NUMBER)
  private Integer deliveries;

  @Sortable
  private String overs;

  @Sortable
  @Filter(FilterType.NUMBER)
  private Integer maidens;

  @Sortable
  @Filter(FilterType.NUMBER)
  private Integer runs;

  @Sortable(1)
  @Filter(FilterType.NUMBER)
  private Integer wickets;

  @Sortable
  @Display(OPTIONAL_HIDE)
  @Filter(FilterType.NUMBER)
  private Integer wides;

  @Sortable
  @Display(OPTIONAL_HIDE)
  @Filter(FilterType.NUMBER)
  private Integer noBalls;

  @Sortable
  @Filter(FilterType.NUMBER)
  @DisplayName("S/R")
  private Double strikeRate;

  @Sortable
  @Filter(FilterType.NUMBER)
  private Double average;

  @Sortable(2)
  @DefaultSortAsc
  @Filter(FilterType.NUMBER)
  private Double economy;

  @Display(ALWAYS_HIDE)
  private Date date;

  @Display(ALWAYS_HIDE)
  @Filter(FilterType.TEXT)
  private String opposition;

  @Display(OPTIONAL_HIDE)
  private String fixture;
}

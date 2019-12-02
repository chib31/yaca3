package com.example.server.enumerations;

import static com.example.server.enumerations.DisplayType.ALWAYS_SHOW;
import static com.example.server.enumerations.DisplayType.OPTIONAL_SHOW;
import static com.example.server.enumerations.FilterType.NUMBER_FILTER;
import static com.example.server.enumerations.FilterType.TEXT_FILTER;

public enum Column {
    PLAYER_NAME("Name", "scorecard_name", ALWAYS_SHOW, TEXT_FILTER, true, true)
  , RUNS("Runs", "runs", OPTIONAL_SHOW, NUMBER_FILTER, true, false)
  , RUNS_TOTAL("Runs", "SUM(runs)", OPTIONAL_SHOW, NUMBER_FILTER, true, false)
  , DELIVERIES("Deliveries", "deliveries", OPTIONAL_SHOW, NUMBER_FILTER, true, false)
  , DELIVERIES_TOTAL("Deliveries", "SUM(deliveries)", OPTIONAL_SHOW, NUMBER_FILTER, true, false)
  , INNINGS_COUNT("Innings", "COUNT(*)", OPTIONAL_SHOW, NUMBER_FILTER, true, false)
  ;

  public final String title;
  public final String selectString;
  public final DisplayType displayType;
  public final FilterType filterType;
  public final boolean sortColumn;
  public final boolean defaultSortAsc;

  Column(String title, String selectString, DisplayType displayType, FilterType filterType,
         boolean sortColumn, boolean defaultSortAsc) {
    this.title = title;
    this.selectString = selectString;
    this.displayType = displayType;
    this.filterType = filterType;
    this.sortColumn = sortColumn;
    this.defaultSortAsc = defaultSortAsc;
  }
}
package com.example.server.enumerations;

import java.util.List;

import static com.example.server.enumerations.Column.*;

public enum ReportType {
    BATTING(
        "batting",
        "player_batting_innings_details",
        List.of(PLAYER_NAME, RUNS, DELIVERIES),
        List.of(PLAYER_NAME)),
    BATTING_GROUPED(
        "batting",
        "player_batting_innings_details",
        List.of(INNINGS_COUNT, RUNS_TOTAL, DELIVERIES_TOTAL),
        List.of(PLAYER_NAME)),
    BOWLING(
        "bowling",
        "player_bowling_innings_details",
        List.of(PLAYER_NAME, RUNS, DELIVERIES),
        List.of(PLAYER_NAME))
  ;

  public final String key;
  public final String dbName;
  public final List<Column> columns;
  public final List<Column> groupOptions;

  ReportType(String key, String dbName, List<Column> columns, List<Column> groupOptions) {
    this.key = key;
    this.dbName = dbName;
    this.columns = columns;
    this.groupOptions = groupOptions;
  }
}

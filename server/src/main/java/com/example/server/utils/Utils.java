package com.example.server.utils;

import java.util.Arrays;

public class Utils {
  public static WicketType getWicketTypeFromSql(String sqlType) {
    return Arrays.stream(WicketType.values())
               .filter(e -> e.getSqlType().equals(sqlType))
               .findAny()
               .orElseThrow();
  }
  public static Result getResultFromSql(String sqlResult) {
    return Arrays.stream(Result.values())
               .filter(e -> e.getSqlResult().equals(sqlResult))
               .findAny()
               .orElseThrow();
  }

  public static Double calculateStrikeRate(int runs, int deliveries) {
    if (deliveries > 0) {
      double dRuns = (double) runs;
      double dDeliveries = (double) deliveries;
      return (dRuns * 100) / dDeliveries;
    } else {
      return null;
    }
  }

  public static Double calculatePercentOfTotal(int playerRuns, int teamRuns) {
    if (teamRuns > 0) {
      double dPlayerRuns = (double) playerRuns;
      double dTeamRuns = (double) teamRuns;
      return (dPlayerRuns * 100) / dTeamRuns;
    } else {
      return null;
    }
  }
}

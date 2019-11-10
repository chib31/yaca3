package com.example.server.utils;

import com.example.server.enumerations.Result;
import com.example.server.enumerations.WicketType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class Utils {

  private static final int OVER_LENGTH = 6;

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

  public static Double calculateBattingStrikeRate(int runs, int deliveries) {
    if (deliveries > 0) {
      double dRuns = (double) runs;
      double dDeliveries = (double) deliveries;
      BigDecimal bd = BigDecimal.valueOf((dRuns * 100) / dDeliveries);
      bd = bd.setScale(1, RoundingMode.HALF_UP);
      return bd.doubleValue();
    } else {
      return null;
    }
  }

  public static Double calculateBowlingStrikeRate(int deliveries, int wickets) {
    if (wickets > 0) {
      double dDeliveries = (double) deliveries;
      double dWickets = (double) wickets;
      BigDecimal bd = BigDecimal.valueOf(dDeliveries / dWickets);
      bd = bd.setScale(1, RoundingMode.HALF_UP);
      return bd.doubleValue();
    } else {
      return null;
    }
  }

  public static Double calculateBowlingAverage(int runs, int wickets) {
    if (wickets > 0) {
      double dRuns = (double) runs;
      double dWickets = (double) wickets;
      BigDecimal bd = BigDecimal.valueOf(dRuns / dWickets);
      bd = bd.setScale(1, RoundingMode.HALF_UP);
      return bd.doubleValue();
    } else {
      return null;
    }
  }

  public static Double calculatePercentOfTotal(int playerRuns, int teamRuns) {
    if (teamRuns > 0) {
      double dPlayerRuns = (double) playerRuns;
      double dTeamRuns = (double) teamRuns;
      BigDecimal bd = BigDecimal.valueOf((dPlayerRuns * 100) / dTeamRuns);
      bd = bd.setScale(1, RoundingMode.HALF_UP);
      return bd.doubleValue();
    } else {
      return null;
    }
  }

  public static Double calculateEconomy(int runs, int deliveries) {
    if (deliveries > 0) {
      double dRuns = (double) runs;
      double dDeliveries = (double) deliveries;
      BigDecimal bd = BigDecimal.valueOf((dRuns * OVER_LENGTH) / dDeliveries);
      bd = bd.setScale(2, RoundingMode.HALF_UP);
      return bd.doubleValue();
    } else {
      return null;
    }
  }

  public static String calculateOvers(int deliveries, int overLength) {
    int overs = deliveries / overLength;
    int extraDeliveries = deliveries % overLength;
    return overs + "." + extraDeliveries;
  }

}
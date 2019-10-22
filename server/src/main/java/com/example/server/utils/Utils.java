package com.example.server.utils;

import com.example.server.annotations.DisplayName;
import com.example.server.annotations.Formatter;
import com.example.server.annotations.Hidden;
import com.example.server.annotations.Sortable;
import com.example.server.enumerations.Index;
import com.example.server.enumerations.Result;
import com.example.server.enumerations.WicketType;
import org.springframework.lang.Nullable;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
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

  public static String constructColumnsJson(Class statClass, Index index) {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    if (index == Index.NUMBERED) {
      arrayBuilder.add(createStatColumn("index", "#", null, false));
    }
    Field[] fields = statClass.getDeclaredFields();
    for (Field field : fields) {
      if (!field.isAnnotationPresent(Hidden.class)) {
        arrayBuilder.add(createStatColumn(field.getName(),
            field.getAnnotation(DisplayName.class) == null ? null : field.getAnnotation(DisplayName.class).value(),
            field.getAnnotation(Formatter.class) == null ? null : field.getAnnotation(Formatter.class).value(),
            field.isAnnotationPresent(Sortable.class)));
      }
    }
    return arrayBuilder.build().toString();
  }

  private static JsonObjectBuilder createStatColumn(String name, @Nullable String displayName,
                                                    @Nullable String formatter, boolean sortable) {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder().add("key", name);
    if (displayName != null) {
      jsonObjectBuilder.add("label", displayName);
    }
//    if (formatter != null) {
//      jsonObjectBuilder.add("formatter", formatter );
//    }
    if (sortable) {
      jsonObjectBuilder.add("sortable", true);
    }
    return jsonObjectBuilder;
  }
}
package com.example.server.services;

import com.example.server.enumerations.Column;
import com.example.server.enumerations.ReportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

  private final EntityManager em;

  @Autowired
  public ReportService(EntityManager em) {
    this.em = em;
  }

  public String fetchReport(ReportType reportType, @Nullable List<Column> groupBy) {
    List<Column> columns = determineColumns(reportType, groupBy);
    JsonObjectBuilder job = Json.createObjectBuilder();
    job.add("reportInfo", fetchReportInfo(reportType));
    job.add("columns", fetchColumns(columns));
    job.add("tableData", fetchData(reportType, columns, groupBy));
    return job.build().toString();
  }

  private JsonObjectBuilder fetchReportInfo(ReportType reportType) {
    JsonArrayBuilder jab = Json.createArrayBuilder();
    reportType.groupOptions.forEach(e -> jab.add(
        Json.createObjectBuilder().add("key", e.name()).add("label", e.title)));
    return Json.createObjectBuilder().add("groupOptions",  jab);
  }

  private JsonArrayBuilder fetchColumns(List<Column> columns) {
    JsonArrayBuilder jab = Json.createArrayBuilder();
    columns.forEach(e -> jab.add(constructColumnObject(e)));
    return jab;
  }

  private JsonObjectBuilder constructColumnObject(Column col) {
    JsonObjectBuilder job = Json.createObjectBuilder();
    job.add("key", col.name().toLowerCase());
    job.add("label", col.title);
    job.add("displayType", col.displayType.toString());
    job.add("filterType", col.filterType.toString());
    job.add("sortColumn", col.sortColumn);
    job.add("defaultSortDir", col.defaultSortAsc ? "Asc" : "Desc");
    return job;
  }

  private List<Column> determineColumns(ReportType reportType, List<Column> groupBy) {
    List<Column> columns = new ArrayList<>(reportType.columns);
    if (groupBy != null) {
      columns.addAll(groupBy);
    }
    return columns;
  }

  private JsonArrayBuilder fetchData(ReportType reportType, List<Column> columns, @Nullable List<Column> groupBy) {
    String selectString = columns.stream().map(e -> e.selectString + " AS " + e.name())
        .collect(Collectors.joining(", "));
    String fromString = reportType.dbName;
    String groupString = "";
    if(groupBy != null) {
      groupString = "GROUP BY " + groupBy.stream().map(Enum::name).collect(Collectors.joining(", "));
    }
    @SuppressWarnings("unchecked")
    List<Tuple> qryResults = em.createNativeQuery(String.format("SELECT %s FROM %s %s", selectString, fromString,
        groupString), Tuple.class).getResultList();

    JsonArrayBuilder jab = Json.createArrayBuilder();
    for(Tuple t : qryResults) {
      JsonObjectBuilder job = Json.createObjectBuilder();
      for(Column column : columns) {
        String alias = column.name().toLowerCase();
        Class type = t.getElements().stream()
            .filter(e -> e.getAlias().equals(alias))
            .findAny().orElseThrow().getJavaType();
        if(type == Integer.class) {
          job.add(alias, (Integer) t.get(alias));
        }
        else if(type == BigInteger.class) {
          job.add(alias, (BigInteger) t.get(alias));
        }
        else {
          job.add(alias, (String) t.get(alias));
        }
      }
      jab.add(job.build());
    }
    return jab;
  }
}

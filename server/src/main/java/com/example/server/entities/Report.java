package com.example.server.entities;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Report {
  HashMap reportInfo;
  List<Map<String, Object>> columnList;
  List<?> dataList;

  public Report(HashMap reportInfo, List<Map<String, Object>> columnList, List<?> dataList) {
    this.reportInfo = reportInfo;
    this.columnList = columnList;
    this.dataList = dataList;
  }
}

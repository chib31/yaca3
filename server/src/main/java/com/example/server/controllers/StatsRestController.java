package com.example.server.controllers;

import com.example.server.enumerations.Column;
import com.example.server.enumerations.ReportType;
import com.example.server.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RequestMapping("/api")
public class StatsRestController {

  @Autowired
  private ReportService reportService;

  @RequestMapping(path = "/reports")
  public @ResponseBody
  String fetchReport(@RequestParam ReportType reportType, @RequestParam(required = false) List<Column> groupBy) {
    return reportService.fetchReport(reportType, groupBy);
  }
}
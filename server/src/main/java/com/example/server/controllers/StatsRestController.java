package com.example.server.controllers;

import com.example.server.services.PlayerBattingService;
import com.example.server.services.PlayerBowlingService;
import com.example.server.statsEntities.PlayerBattingStat;
import com.example.server.statsEntities.PlayerBowlingStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RequestMapping("/api")
public class StatsRestController {

  @Autowired
  private PlayerBattingService playerBattingService;

  @Autowired
  private PlayerBowlingService playerBowlingService;

  @RequestMapping(path = "/batting")
  public @ResponseBody
  List<PlayerBattingStat> getPlayerBatting() {
    return playerBattingService.fetchAllPlayerBattingDetails();
  }

  @RequestMapping(path = "/battingColumns")
  public @ResponseBody
  String getPlayerBattingColumns() {
    return playerBattingService.constructPlayerBattingColumnsJson();
  }

  @RequestMapping(path = "/bowling")
  public @ResponseBody
  List<PlayerBowlingStat> getPlayerBowling() {
    return playerBowlingService.fetchAllPlayerBowlingDetails();
  }

  @RequestMapping(path = "/bowlingColumns")
  public @ResponseBody
  String getPlayerBowlingColumns() {
    return playerBowlingService.constructPlayerBowlingColumnsJson();
  }
}
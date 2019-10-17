package com.example.server.controllers;

import com.example.server.JsonObjects.PlayerBattingDetailsJson;
import com.example.server.entities.PlayerBattingDetails;
import com.example.server.entities.PlayerBowlingDetails;
import com.example.server.services.StatsService;
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
    private StatsService statsService;

    @RequestMapping(path = "/playerBatting")
    public @ResponseBody
    List<PlayerBattingDetailsJson> getPlayerBatting() {
        return statsService.fetchAllPlayerBattingDetails();
    }

//    @RequestMapping(path = "/playerBattingColumns")
//    public @ResponseBody
//    String getPlayerBattingColumns() {
//        return statsService.fetchPlayerBattingInningsColumns();
//    }

    @RequestMapping(path = "/playerBowling")
    public @ResponseBody
    List<PlayerBowlingDetails> getPlayerBowling() {
        return statsService.fetchAllPlayerBowlingInningsDetails();
    }
}
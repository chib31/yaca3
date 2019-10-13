package com.example.server.controllers;

import com.example.server.entities.PlayerBattingInningsDetails;
import com.example.server.entities.PlayerBowlingInningsDetails;
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
    List<PlayerBattingInningsDetails> getPlayerBatting() {
        return statsService.fetchAllPlayerBattingInningsDetails();
    }

    @RequestMapping(path = "/playerBowling")
    public @ResponseBody
    List<PlayerBowlingInningsDetails> getPlayerBowling() {
        return statsService.fetchAllPlayerBowlingInningsDetails();
    }
}
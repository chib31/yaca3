package com.example.server.controllers;

import com.example.server.entities.PlayerBattingInningsDetails;
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

    @RequestMapping(path = "/hello")
    public @ResponseBody
    String sayHello() {
        return "Hello from Spring Boot Backend!";
    }

    @RequestMapping(path = "/playerBatting")
    public @ResponseBody
    List<PlayerBattingInningsDetails> getPlayerBatting() {
        return statsService.fetchAllPlayerBattingInningsDetails();
    }
}
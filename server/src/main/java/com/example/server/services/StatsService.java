package com.example.server.services;

import com.example.server.JsonObjects.PlayerBattingDetailsJson;
import com.example.server.entities.PlayerBattingDetails;
import com.example.server.entities.PlayerBowlingDetails;
import com.example.server.repositories.PlayerBattingDetailsRepository;
import com.example.server.repositories.PlayerBowlingDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatsService {

    private PlayerBattingDetailsRepository playerBattingDetailsRepository;
    private PlayerBowlingDetailsRepository playerBowlingDetailsRepository;

    @Autowired
    public StatsService(PlayerBattingDetailsRepository playerBattingDetailsRepository,
                        PlayerBowlingDetailsRepository playerBowlingDetailsRepository) {
        this.playerBattingDetailsRepository = playerBattingDetailsRepository;
        this.playerBowlingDetailsRepository = playerBowlingDetailsRepository;
    }

    public List<PlayerBattingDetailsJson> fetchAllPlayerBattingDetails() {
        return playerBattingDetailsRepository.findAllByOrderByRunsDescDeliveriesAsc().stream()
            .map(PlayerBattingDetails::getJson)
            .collect(Collectors.toList());
    }

    public List<PlayerBowlingDetails> fetchAllPlayerBowlingInningsDetails() {
        return playerBowlingDetailsRepository.findAllByOrderByWicketsDescEconomyAsc();
    }
}

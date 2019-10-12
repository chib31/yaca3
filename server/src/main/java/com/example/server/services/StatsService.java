package com.example.server.services;

import com.example.server.entities.PlayerBattingInningsDetails;
import com.example.server.entities.PlayerDetails;
import com.example.server.repositories.PlayerBattingInningsDetailsRepository;
import com.example.server.repositories.PlayerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {

    private PlayerDetailsRepository playerDetailsRepository;

    private PlayerBattingInningsDetailsRepository playerBattingInningsDetailsRepository;

    @Autowired
    public StatsService(PlayerDetailsRepository playerDetailsRepository,
                        PlayerBattingInningsDetailsRepository playerBattingInningsDetailsRepository) {
        this.playerDetailsRepository = playerDetailsRepository;
        this.playerBattingInningsDetailsRepository = playerBattingInningsDetailsRepository;
    }

    public List<PlayerDetails> fetchAllPlayers() {
        return playerDetailsRepository.findAll();
    }

    public List<PlayerBattingInningsDetails> fetchAllPlayerBattingInningsDetails() {
        return playerBattingInningsDetailsRepository.findAllByOrderByRunsDescDeliveriesAsc();
    }
}

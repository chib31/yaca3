package com.example.server.services;

import com.example.server.entities.PlayerBowlingDetail;
import com.example.server.enumerations.IndexColumnType;
import com.example.server.repositories.PlayerBowlingDetailsRepository;
import com.example.server.statsEntities.PlayerBowlingStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.server.utils.Utils.*;

@Service
public class PlayerBowlingService {

  private PlayerBowlingDetailsRepository playerBowlingDetailsRepository;

  @Autowired
  public PlayerBowlingService(PlayerBowlingDetailsRepository playerBowlingDetailsRepository) {
    this.playerBowlingDetailsRepository = playerBowlingDetailsRepository;
  }

  public List<PlayerBowlingStat> fetchAllPlayerBowlingDetails() {
    return playerBowlingDetailsRepository.findAllByOrderByWicketsDescEconomyAsc().stream()
        .map(this::convertToPlayerBowlingStats)
        .collect(Collectors.toList());
  }

  public String constructPlayerBowlingColumnsJson() {
    return ReportCreator.constructColumnsJson(PlayerBowlingStat.class, IndexColumnType.NUMBERED);
  }

  private PlayerBowlingStat convertToPlayerBowlingStats(PlayerBowlingDetail playerBowlingDetail) {
    PlayerBowlingStat playerBowlingStat = new PlayerBowlingStat();
    playerBowlingStat.setId(playerBowlingDetail.getId());
    playerBowlingStat.setPlayerName(playerBowlingDetail.getPlayerName());
    playerBowlingStat.setBowlerNumber(playerBowlingDetail.getBowlerNumber());
    playerBowlingStat.setDeliveries(playerBowlingDetail.getDeliveries());
    playerBowlingStat.setOvers(calculateOvers(playerBowlingDetail.getDeliveries(),
        playerBowlingDetail.getOverLength()));
    playerBowlingStat.setMaidens(playerBowlingDetail.getMaidens());
    playerBowlingStat.setRuns(playerBowlingDetail.getRuns());
    playerBowlingStat.setWickets(playerBowlingDetail.getWickets());
    playerBowlingStat.setWides(playerBowlingDetail.getWides());
    playerBowlingStat.setNoBalls(playerBowlingDetail.getNoBalls());
    playerBowlingStat.setStrikeRate(calculateBowlingStrikeRate(playerBowlingDetail.getDeliveries(),
        playerBowlingDetail.getWickets()));
    playerBowlingStat.setAverage(calculateBowlingAverage(playerBowlingDetail.getRuns(),
        playerBowlingDetail.getWickets()));
    playerBowlingStat.setEconomy(calculateEconomy(playerBowlingDetail.getRuns(), playerBowlingDetail.getDeliveries()));
    playerBowlingStat.setFixture(String.format("%s vs %s (%s)", playerBowlingDetail.getDate().toString(),
        playerBowlingDetail.getOpposition(), getResultFromSql(playerBowlingDetail.getResult()).getValue()));
    return playerBowlingStat;
  }
}

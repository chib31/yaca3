package com.example.server.services;

import com.example.server.entities.PlayerBattingDetail;
import com.example.server.enumerations.Index;
import com.example.server.repositories.PlayerBattingDetailsRepository;
import com.example.server.statsEntities.PlayerBattingStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.server.utils.Utils.*;

@Service
public class PlayerBattingService {

  private PlayerBattingDetailsRepository playerBattingDetailsRepository;

  @Autowired
  public PlayerBattingService(PlayerBattingDetailsRepository playerBattingDetailsRepository) {
    this.playerBattingDetailsRepository = playerBattingDetailsRepository;
  }

  public List<PlayerBattingStat> fetchAllPlayerBattingDetails() {
    return playerBattingDetailsRepository.findAllByOrderByRunsDescDeliveriesAsc().stream()
               .map(this::convertToPlayerBattingStats)
               .collect(Collectors.toList());
  }

  public String constructPlayerBattingColumnsJson() {
    return constructColumnsJson(PlayerBattingStat.class, Index.NUMBERED);
  }

  private PlayerBattingStat convertToPlayerBattingStats(PlayerBattingDetail playerBattingDetail) {
    PlayerBattingStat playerBattingStat = new PlayerBattingStat();
    playerBattingStat.setId(playerBattingDetail.getId());
    playerBattingStat.setPlayerName(playerBattingDetail.getPlayerName());
    playerBattingStat.setOpposition(playerBattingDetail.getOpposition());
    playerBattingStat.setDate(playerBattingDetail.getDate());
    playerBattingStat.setPosition(playerBattingDetail.getPosition());
    playerBattingStat.setDeliveries(playerBattingDetail.getDeliveries());
    playerBattingStat.setRuns(playerBattingDetail.getRuns());
    playerBattingStat.setFours(playerBattingDetail.getFours());
    playerBattingStat.setSixes(playerBattingDetail.getSixes());
    playerBattingStat.setWicketType(getWicketTypeFromSql(playerBattingDetail.getWicketType()).getType());
    playerBattingStat.setStrikeRate(calculateBattingStrikeRate(playerBattingDetail.getRuns(),
        playerBattingDetail.getDeliveries()));
    playerBattingStat.setPercentOfTotal(calculatePercentOfTotal(playerBattingDetail.getRuns(),
        playerBattingDetail.getTeamTotal()));
    playerBattingStat.setFixture(String.format("%s vs %s (%s)", playerBattingDetail.getDate().toString(),
        playerBattingDetail.getOpposition(), getResultFromSql(playerBattingDetail.getResult()).getValue()));
    return playerBattingStat;
  }
}

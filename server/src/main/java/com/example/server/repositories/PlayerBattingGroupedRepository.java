package com.example.server.repositories;

import com.example.server.entities.PlayerBattingDetail;
import com.example.server.entities.PlayerBattingGrouped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerBattingGroupedRepository extends JpaRepository<PlayerBattingDetail, Long> {
  @Query(
      value =
        "SELECT scorecard_name AS playername, " +
        "SUM(deliveries) AS deliveries, " +
        "SUM(runs) AS runs, " +
        "SUM(fours) AS fours, " +
        "SUM(sixes) AS sixes " +
        "FROM player_batting_innings_details " +
        "GROUP BY scorecard_name",
      nativeQuery = true)
  List<PlayerBattingGrouped> findAllByPlayerName();
}
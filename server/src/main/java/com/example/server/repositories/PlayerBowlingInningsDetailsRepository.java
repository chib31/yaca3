package com.example.server.repositories;

import com.example.server.entities.PlayerBowlingInningsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerBowlingInningsDetailsRepository extends JpaRepository<PlayerBowlingInningsDetails, Long> {
    List<PlayerBowlingInningsDetails> findAll();

    List<PlayerBowlingInningsDetails> findAllByOrderByWicketsDescEconomyAsc();

}
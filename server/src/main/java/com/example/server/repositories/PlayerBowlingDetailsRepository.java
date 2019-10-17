package com.example.server.repositories;

import com.example.server.entities.PlayerBowlingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerBowlingDetailsRepository extends JpaRepository<PlayerBowlingDetails, Long> {
    List<PlayerBowlingDetails> findAll();

    List<PlayerBowlingDetails> findAllByOrderByWicketsDescEconomyAsc();

}
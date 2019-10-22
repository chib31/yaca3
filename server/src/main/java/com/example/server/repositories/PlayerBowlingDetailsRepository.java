package com.example.server.repositories;

import com.example.server.entities.PlayerBowlingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerBowlingDetailsRepository extends JpaRepository<PlayerBowlingDetail, Long> {
    List<PlayerBowlingDetail> findAll();

    List<PlayerBowlingDetail> findAllByOrderByWicketsDescEconomyAsc();
}
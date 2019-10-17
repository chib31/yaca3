package com.example.server.repositories;

import com.example.server.entities.PlayerBattingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerBattingDetailsRepository extends JpaRepository<PlayerBattingDetails, Long> {
    List<PlayerBattingDetails> findAll();

    List<PlayerBattingDetails> findAllByOrderByRunsDescDeliveriesAsc();

}
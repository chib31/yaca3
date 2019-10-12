package com.example.server.repositories;

import com.example.server.entities.PlayerBattingInningsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerBattingInningsDetailsRepository extends JpaRepository<PlayerBattingInningsDetails, Long> {
    List<PlayerBattingInningsDetails> findAll();

    List<PlayerBattingInningsDetails> findAllByOrderByRunsDescDeliveriesAsc();

}
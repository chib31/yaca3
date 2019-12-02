package com.example.server.repositories;

import com.example.server.entities.PlayerBattingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerBattingDetailsRepository extends JpaRepository<PlayerBattingDetail, Long> {
    List<PlayerBattingDetail> findAll();

    List<PlayerBattingDetail> findAllByOrderByRunsDescDeliveriesAsc();



}
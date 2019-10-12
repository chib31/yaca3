package com.example.server.repositories;

import com.example.server.entities.PlayerBattingInnings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerBattingInningsRepository extends JpaRepository<PlayerBattingInnings, Long> {
    List<PlayerBattingInnings> findAll();

}
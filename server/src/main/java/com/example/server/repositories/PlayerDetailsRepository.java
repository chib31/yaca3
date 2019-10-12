package com.example.server.repositories;

import com.example.server.entities.PlayerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerDetailsRepository extends JpaRepository<PlayerDetails, Long> {
    List<PlayerDetails> findAll();

}
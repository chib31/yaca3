package com.example.server.repositories;

import com.example.server.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByFirstName(String FirstName);
    List<Player> findAll();

}
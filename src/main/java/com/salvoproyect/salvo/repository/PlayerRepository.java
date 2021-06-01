package com.salvoproyect.salvo.repository;

import com.salvoproyect.salvo.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByEmail(String email);
}

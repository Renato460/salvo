package com.salvoproyect.salvo.repository;

import com.salvoproyect.salvo.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    public List<Game> findById(long id);

}

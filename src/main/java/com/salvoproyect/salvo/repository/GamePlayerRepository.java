package com.salvoproyect.salvo.repository;

import com.salvoproyect.salvo.model.Game;
import com.salvoproyect.salvo.model.GamePlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GamePlayerRepository extends JpaRepository<GamePlayer, Long> {


}

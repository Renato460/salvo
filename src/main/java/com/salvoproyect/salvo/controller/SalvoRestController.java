package com.salvoproyect.salvo.controller;

import com.salvoproyect.salvo.GamePlayer;
import com.salvoproyect.salvo.repository.GamePlayerRepository;
import com.salvoproyect.salvo.repository.GameRepository;
import com.salvoproyect.salvo.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;



@RestController
@RequestMapping("/api")
public class SalvoRestController {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @RequestMapping("/games")
    public List<Object> getGames(){
        return gameRepository.findAll().stream().map(Game::getGames).collect(toList());
    }

    @RequestMapping("/game_view/{gameId}")
    public Map<String, Object> findGame(@PathVariable Long gameId){
        Optional<GamePlayer> op = gamePlayerRepository.findById(gameId);
        if(op.isPresent()){
            Long id = op.get().getId();
            Game game = gameRepository.findById(id).get();
            return game.getGameById(op.get());
        }
        else return null;
    }
}

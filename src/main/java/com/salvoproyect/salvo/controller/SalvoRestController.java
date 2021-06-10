package com.salvoproyect.salvo.controller;

import com.salvoproyect.salvo.model.GamePlayer;
import com.salvoproyect.salvo.model.Player;
import com.salvoproyect.salvo.repository.GamePlayerRepository;
import com.salvoproyect.salvo.repository.GameRepository;
import com.salvoproyect.salvo.model.Game;
import com.salvoproyect.salvo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;



@RestController
@RequestMapping("/api")
public class SalvoRestController {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping("/games")
    public Map<String,Object> getGames(Authentication authentication){
        Map<String,Object> getGamesInfo = new LinkedHashMap<>();
        if(isGuest(authentication)){
            getGamesInfo.put("player","Guest");
            getGamesInfo.put("games",gameRepository.findAll().stream().map(Game::getGames));

        }if(!isGuest(authentication)){
            getGamesInfo.put("player",playerRepository.findByEmail(authentication.getName()).getPlayerData());
            getGamesInfo.put("games", gameRepository.findAll().stream().map(Game::getGames).collect(toList()));
        }

        return getGamesInfo ;
    }

    @RequestMapping(path="/games", method = RequestMethod.POST)
    public  ResponseEntity<Map<String,Object>> newGame (Authentication authentication){
        if(isGuest(authentication)){
            return new ResponseEntity<>(makeMap("error","Sin authorizacion"),HttpStatus.UNAUTHORIZED);
        }else{
            try {
                LocalDateTime time = LocalDateTime.now();
                Game game = gameRepository.save(new Game(time));
                Player player = playerRepository.save(playerRepository.findByEmail(authentication.getName()));
                GamePlayer gamePlayer = new GamePlayer(game.getCreationDate(),game,player);
                gamePlayerRepository.save(gamePlayer);
                return new ResponseEntity<>(makeMap("gpid",gamePlayer.getId()),HttpStatus.CREATED);
            }catch (Exception ex){
                return new ResponseEntity<>(makeMap("error","Error del servidor"),HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping("/game/{gameid}/players")
    public ResponseEntity<Map<String, Object>> joinGamePlayer( @PathVariable Long gameid, Authentication authentication){
        Optional<Game> game =gameRepository.findById(gameid);
        if (!isGuest(authentication)){
            if(game.isPresent()){
                Game gameGame = game.get();
                Long countPlayers = gamePlayerRepository.findAll().stream().filter(gp -> gp.getGame().getId()==gameGame.getId()).count();
                if(countPlayers==1){
                    Player player = playerRepository.findByEmail(authentication.getName());

                    LocalDateTime time = LocalDateTime.now();
                    GamePlayer gamePlayer = new GamePlayer(time,gameGame,player);
                    gamePlayerRepository.save(gamePlayer);
                    return new ResponseEntity<>(makeMap("gpid",gamePlayer.getId()), HttpStatus.CREATED);
                }else {
                    return new ResponseEntity<>(makeMap("error", "Game is Full"), HttpStatus.FORBIDDEN);
                }

            }else if (!game.isPresent()){
                return new ResponseEntity<>(makeMap("error","No such game"), HttpStatus.FORBIDDEN);
            }
        }else {
            return new ResponseEntity<>(makeMap("error", "Sin autorizacion"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(makeMap("error", "Sin autorizacion"), HttpStatus.UNAUTHORIZED);
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(key, value);
        return map;
    }

    @RequestMapping("/game_view/{gamePlayerId}")
    public ResponseEntity<Map<String, Object>> findGame(@PathVariable Long gamePlayerId,Authentication authentication){
        Optional<GamePlayer> op = gamePlayerRepository.findById(gamePlayerId);
        if(!isGuest(authentication)){
            if(op.isPresent()){
                GamePlayer gamePlayerP = op.get();
                Player player = playerRepository.findByEmail(authentication.getName());
                if (gamePlayerP.getPlayer().getId()==player.getId()){
                    return new ResponseEntity<>(gamePlayerP.getGame().getGameById(op.get()), HttpStatus.ACCEPTED);
                }else {
                    return new ResponseEntity<>(makeMap("error", "Error"), HttpStatus.FORBIDDEN);
                }
            }else {
                return new ResponseEntity<>(makeMap("error","No se encuentra"), HttpStatus.FORBIDDEN);
            }
        }else {
            return new ResponseEntity<>(makeMap("error", "Sin autorizacion"), HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestParam String email, @RequestParam String password) {

        if (email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (playerRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        playerRepository.save(new Player(email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

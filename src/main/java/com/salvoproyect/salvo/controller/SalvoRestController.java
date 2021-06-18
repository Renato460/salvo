package com.salvoproyect.salvo.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salvoproyect.salvo.model.*;
import com.salvoproyect.salvo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private SalvoRepository salvoRepository;

    @Autowired
    private ScoreRepository scoreRepository;

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
        Optional<Game> gameOptional =gameRepository.findById(gameid);
        if (!isGuest(authentication)){
            if(gameOptional.isPresent()){
                Game game = gameOptional.get();
                Long countPlayers = gamePlayerRepository.findAll().stream().filter(gp -> gp.getGame().getId()==game.getId()).count();
                if(countPlayers==1){
                    Player player = playerRepository.findByEmail(authentication.getName());
                    Long findPlayer = game.getGamePlayerSet().stream().filter(gp -> gp.getPlayer().getId() == player.getId()).count();
                    if(findPlayer==0){
                        LocalDateTime time = LocalDateTime.now();
                        GamePlayer gamePlayer = new GamePlayer(time,game,player);
                        gamePlayerRepository.save(gamePlayer);
                        return new ResponseEntity<>(makeMap("gpid",gamePlayer.getId()), HttpStatus.CREATED);
                    }else {
                        return new ResponseEntity<>(makeMap("error", "Ya estás en éste juego"), HttpStatus.FORBIDDEN);
                    }
                }else {
                    return new ResponseEntity<>(makeMap("error", "Game is Full"), HttpStatus.FORBIDDEN);
                }

            }else if (!gameOptional.isPresent()){
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

    private GamePlayer opPlayer (GamePlayer gamePlayer, Player player){
        Optional<GamePlayer> opGamePlayer = gamePlayer.getGame().getGamePlayerSet().stream()
                .filter(gp -> gp.getPlayer().getId()!=player.getId())
                .findFirst();
        return opGamePlayer.orElse(new GamePlayer());
    }

    @RequestMapping("/game_view/{gamePlayerId}")
    public ResponseEntity<Map<String, Object>> findGame(@PathVariable Long gamePlayerId,Authentication authentication){
        Optional<GamePlayer> op = gamePlayerRepository.findById(gamePlayerId);
        if(!isGuest(authentication)){
            if(op.isPresent()){
                GamePlayer gamePlayerP = op.get();
                Player player = playerRepository.findByEmail(authentication.getName());
                if (gamePlayerP.getPlayer().getId()==player.getId()){
                    Map<String,Object> mapGamePlayer = gamePlayerP.getGame().getGameById(op.get());
                   /* if(gamePlayerP.getSalvos().size() == 0){
                        mapGamePlayer.put("gameState","PLACESHIPS");
                    }*/

                    mapGamePlayer.put("hits",setHitsDTO(gamePlayerP, opPlayer(gamePlayerP, player)));
                    mapGamePlayer.put("gameState", statusGame(gamePlayerP,opPlayer(gamePlayerP,player)));
                    return new ResponseEntity<>(mapGamePlayer, HttpStatus.ACCEPTED);
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



    private Map<String, Object> setHitsDTO (GamePlayer gamePlayer, GamePlayer opGamePlayer){

        Map<String, Object> hits = new LinkedHashMap<>();
        hits.put("self",self(gamePlayer, opGamePlayer));
        hits.put("opponent",self(opGamePlayer,gamePlayer));
        //System.out.println(hits);
        return hits;
    }

    private Set<Map<String,Object>> self (GamePlayer gamePlayer, GamePlayer opGamePlayer){

        Set<Map<String,Object>> setSelfMap= new LinkedHashSet<>();

        int carrier = 0;
        int battleship = 0;
        int submarine = 0;
        int destroyer = 0;
        int patrolboat = 0;
    if (opGamePlayer!=null){
        for(Salvo salvo : opGamePlayer.getSalvoSet()){
            Map<String,Object> selfMap = new LinkedHashMap<>();
            Map<String,Object> damagesMap = new LinkedHashMap<>();
            Set<String> hitsDone = new LinkedHashSet<>();
            int carrierHits = 0;
            int battleshipHits = 0;
            int submarineHits = 0;
            int destroyerHits = 0;
            int patrolboatHits = 0;

            for(String salvoLocation : salvo.getSalvoLocations()) {

                for (Ship ship : gamePlayer.getShipSet()) {

                    for (String shipLocation : ship.getShipLocations()) {

                        if (salvoLocation.equals(shipLocation)) {
                            hitsDone.add(salvoLocation);

                            if (ship.getType().equals("carrier")) {
                                carrierHits += 1;
                                carrier += 1;
                            }
                            if (ship.getType().equals("battleship")) {
                                battleshipHits += 1;
                                battleship += 1;
                            }
                            if (ship.getType().equals("submarine")) {
                                submarineHits += 1;
                                submarine += 1;
                            }
                            if (ship.getType().equals("destroyer")) {
                                destroyerHits += 1;
                                destroyer += 1;
                            }
                            if (ship.getType().equals("patrolboat")) {
                                patrolboatHits += 1;
                                patrolboat += 1;
                            }
                        }
                    }
                }
            }
            selfMap.put("turn", salvo.getTurn());
            selfMap.put("hitLocations", hitsDone);
            damagesMap.put("carrierHits", carrierHits);
            damagesMap.put("battleshipHits", battleshipHits);
            damagesMap.put("submarineHits", submarineHits);
            damagesMap.put("destroyerHits", destroyerHits);
            damagesMap.put("patrolboatHits", patrolboatHits);
            damagesMap.put("carrier", carrier);
            damagesMap.put("battleship", battleship);
            damagesMap.put("submarine", submarine);
            damagesMap.put("destroyer", destroyer);
            damagesMap.put("patrolboat", patrolboat);
            selfMap.put("damages", damagesMap);
            int miss = salvo.getSalvoLocations().size()-hitsDone.size();
            selfMap.put("missed", miss);
            setSelfMap.add(selfMap);
        }

        return setSelfMap;
    }

        return setSelfMap;
    }

    private String statusGame (GamePlayer gamePlayer, GamePlayer opGamePlayer){
        int salvoSize = gamePlayer.getSalvoSet().size();
        int salvoSizeOponnent = opGamePlayer.getSalvoSet().size();

        if (gamePlayer.getShipSet().isEmpty()){
            return "PLACESHIPS";
        }
        if (opGamePlayer.getShipSet().isEmpty()){
            return "WAIT";
        }
        if((totalHit(gamePlayer,opGamePlayer) == shipsSize(gamePlayer))&&(totalHit(opGamePlayer,gamePlayer)==shipsSize(opGamePlayer))){
            scoreRepository.save(new Score(gamePlayer.getGame(),gamePlayer.getPlayer(),0.5,gamePlayer.getGame().getCreationDate()));
            return "TIE";
        }
        if (totalHit(gamePlayer,opGamePlayer) == shipsSize(gamePlayer)){
            scoreRepository.save(new Score(gamePlayer.getGame(),gamePlayer.getPlayer(),0,gamePlayer.getGame().getCreationDate()));
            return "LOST";
        }
        if (totalHit(opGamePlayer,gamePlayer)==shipsSize(opGamePlayer)){
            scoreRepository.save(new Score(gamePlayer.getGame(),gamePlayer.getPlayer(),1,gamePlayer.getGame().getCreationDate()));
            return "WON";
        }
        if (salvoSize>salvoSizeOponnent){
            return "WAITINGFOROPP";
        }
        return "PLAY";
    }

    private int totalHit(GamePlayer gamePlayer, GamePlayer opGamePlayer){
         int totalHit = self(gamePlayer, opGamePlayer).stream().mapToInt(map -> ((Set<String>) map.get("hitLocations")).size()).sum();
        //System.out.println(totalHit);
        return totalHit;
    }
    private int shipsSize(GamePlayer gamePlayer){
        int shipSize = gamePlayer.getShipSet().stream().mapToInt(ship -> (ship.getShipLocations().size())).sum();
        return shipSize;
    }

    @RequestMapping(value="/games/players/{gamePlayerID}/ships", method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> addShips(@PathVariable long gamePlayerID, @RequestBody List<Ship> ship, Authentication authentication) {
        Optional<GamePlayer>  gamePlayerOptional = gamePlayerRepository.findById(gamePlayerID);

        if (isGuest(authentication)){
            return new ResponseEntity<>(makeMap("error", "No tiene autorización"),HttpStatus.UNAUTHORIZED);
        }

        if(!gamePlayerOptional.isPresent()){
            return new ResponseEntity<>(makeMap("error", "No existe game correspondiente a este player"),HttpStatus.UNAUTHORIZED);
        }

        GamePlayer gamePlayer = gamePlayerOptional.get();

        Player player = playerRepository.findByEmail(authentication.getName());

        if(gamePlayer.getPlayer().getId() != player.getId()){
            return new ResponseEntity<>(makeMap("error", "No corresponde Player"),HttpStatus.UNAUTHORIZED);
        }

        if(gamePlayer.getShips().size()!=0){
            return new ResponseEntity<>(makeMap("error", "Ya tiene Ships creados"),HttpStatus.FORBIDDEN);
        }

        for (Ship s: ship) {
            s.setGamePlayer(gamePlayer);
            shipRepository.save(s);
        }
        return new ResponseEntity<>(makeMap("OK", "Ships Posicionados"),HttpStatus.CREATED);

    }

    @RequestMapping(value="/games/players/{gamePlayerID}/salvoes", method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> addSalvos(@PathVariable long gamePlayerID, @RequestBody Salvo salvos, Authentication authentication) {
        Optional<GamePlayer>  gamePlayerOptional = gamePlayerRepository.findById(gamePlayerID);

        if (isGuest(authentication)){
            return new ResponseEntity<>(makeMap("error", "No tiene autorización"),HttpStatus.UNAUTHORIZED);
        }

        if(!gamePlayerOptional.isPresent()){
            return new ResponseEntity<>(makeMap("error", "No existe game correspondiente a este player"),HttpStatus.UNAUTHORIZED);
        }

        GamePlayer gamePlayer = gamePlayerOptional.get();

        Player player = playerRepository.findByEmail(authentication.getName());

        GamePlayer opGamePlayer = opPlayer(gamePlayer, player);

        if (opGamePlayer==null){
            return new ResponseEntity<>(makeMap("error", "No se encontró Oponente"),HttpStatus.UNAUTHORIZED);
        }

        if(gamePlayer.getShipSet().size() == 0 || opGamePlayer.getShipSet().size() == 0){
            return new ResponseEntity<>(makeMap("error", "No se encontraron Ships"),HttpStatus.UNAUTHORIZED);
        }


        if(gamePlayer.getPlayer().getId() != player.getId()){
            return new ResponseEntity<>(makeMap("error", "No corresponde Player"),HttpStatus.UNAUTHORIZED);
        }

        if(salvos.getSalvoLocations().size()<1 || salvos.getSalvoLocations().size()>5){
            return new ResponseEntity<>(makeMap("error", "Cantidad erronea"),HttpStatus.UNAUTHORIZED);
        }

        int p1Turn = gamePlayer.getSalvoSet().size();

        int p2Turn = opGamePlayer.getSalvoSet().size();

        if(p1Turn>p2Turn){
            return new ResponseEntity<>(makeMap("error", "No es su turno"),HttpStatus.FORBIDDEN);
        }
        //Long id = Long.parseLong("11");


        salvos.setTurn(p1Turn+1);
        salvos.setGamePlayer(gamePlayer);
        salvoRepository.save(salvos);
        //setHits(gamePlayer);


        return new ResponseEntity<>(makeMap("OK", "Salvo ejecutado!!"),HttpStatus.CREATED);

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

package com.salvoproyect.salvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salvoproyect.salvo.model.Game;
import com.salvoproyect.salvo.model.Player;
import com.salvoproyect.salvo.model.Salvo;
import com.salvoproyect.salvo.model.Ship;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy="gamePlayer", fetch= FetchType.EAGER)
    @OrderBy
    private Set<Ship> shipSet = new LinkedHashSet<>();

    @OneToMany(mappedBy="gamePlayerId", fetch= FetchType.EAGER)
    @OrderBy
    private Set<Salvo> salvoSet = new LinkedHashSet<>();

    private LocalDateTime joinDate;

    public Map<String, Object> getGames (){
        Map<String, Object> gamePlayer = new LinkedHashMap<>();
        gamePlayer.put("id", this.id);
        gamePlayer.put("player", this.player.getPlayerData());
        return gamePlayer;
    }
    public Map<String, Object> getGamesScore (Game game){
        Map<String, Object> gamePlayer = new LinkedHashMap<>();
        gamePlayer.put("id", this.id);
        gamePlayer.put("player", this.player.getPlayerData());
        //gamePlayer.put("score", getPlayer().getScorePlayerSet().stream().filter(b -> this.getGame().getId()== b.getGameId().getId()).map(Score::getScore).findFirst());

        return gamePlayer;
    }

    @JsonIgnore
    public List<Map<String,Object>> getShips(){
        List<Map<String,Object>> ships;
            ships = this.shipSet.stream().map(Ship::getShipDTO).collect(toList());
            return ships;
    }

    @JsonIgnore
    public List<Map<String,Object>> getSalvos(){
        List<Map<String,Object>> salvos;
        salvos = this.salvoSet.stream().map(Salvo::getSalvoesDTO).collect(toList());
        return salvos;
    }

    public GamePlayer(LocalDateTime joinDate, Game game, Player player) {

        this.joinDate = joinDate;
        this.game = game;
        this.player = player;
    }

    public GamePlayer() {
    }

    public Set<Ship> getShipSet() {
        return shipSet;
    }

    public void setShipSet(Set<Ship> shipSet) {
        this.shipSet = shipSet;
    }

    public Set<Salvo> getSalvoSet() {
        return salvoSet;
    }

    public void setSalvoSet(Set<Salvo> salvoSet) {
        this.salvoSet = salvoSet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonIgnore
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @JsonIgnore
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

}

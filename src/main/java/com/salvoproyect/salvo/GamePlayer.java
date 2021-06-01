package com.salvoproyect.salvo;

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
    private Set<Ship> shipSet;

    @OneToMany(mappedBy="gamePlayer", fetch= FetchType.EAGER)
    private Set<Salvo> salvoSet;

    private LocalDateTime joinDate;

    public Map<String, Object> getGames (){
        Map<String, Object> gamePlayer = new LinkedHashMap<>();
        gamePlayer.put("id", this.id);
        gamePlayer.put("player", this.player.getPlayerData());
        return gamePlayer;
    }

    public List<Map<String,Object>> getShips(){
        List<Map<String,Object>> ships;
            ships = this.shipSet.stream().filter(ship -> ship.getGamePlayer().getId() == this.id).map(Ship::getShipDTO).collect(Collectors.toList());
        System.out.println(ships);
            return ships;
    }

    public GamePlayer(LocalDateTime joinDate, Game game, Player player) {

        this.joinDate = joinDate;
        this.game = game;
        this.player = player;
    }

    public GamePlayer() {
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

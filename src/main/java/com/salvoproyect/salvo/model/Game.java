package com.salvoproyect.salvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salvoproyect.salvo.GamePlayer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private LocalDateTime creationDate;

    public Game(LocalDateTime creationDate) {

        this.creationDate = creationDate;

    }

    public Game() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @JsonIgnore
    public Set<GamePlayer> getGamePlayerSet() {
        return gamePlayerSet;
    }

    public void setGamePlayerSet(Set<GamePlayer> gamePlayerSet) {
        this.gamePlayerSet = gamePlayerSet;
    }

    public void addGamePlayer(GamePlayer gamePlayer){
        gamePlayer.setGame(this);
        gamePlayerSet.add(gamePlayer);
    }

    public List<Player> getPlayers() {
        return gamePlayerSet.stream().map(GamePlayer::getPlayer).collect(toList());
    }

    @OneToMany(mappedBy="game", fetch= FetchType.EAGER)
    private Set<GamePlayer> gamePlayerSet;

    @JsonIgnore
    public Map<String, Object> getGames (){
        Map<String, Object> objeto = new LinkedHashMap<>();
        objeto.put("id", this.id);
        objeto.put("created", this.creationDate);
        objeto.put("gamePlayers", this.getGamePlayerSet().stream().map(GamePlayer::getGames)
        .collect(toList()));
        return objeto;
    }

    @JsonIgnore
    public Map<String, Object> getGameById (GamePlayer gamePlayer){

            Map<String, Object> objeto = new LinkedHashMap<>();
            objeto.put("id", this.id);
            objeto.put("created", this.creationDate);
            objeto.put("gamePlayers", this.getGamePlayerSet().stream().map(GamePlayer::getGames)
                    .collect(toList()));
            objeto.put("ships", this.getGamePlayerSet().stream().map(GamePlayer::getShips));
            return objeto;

    }
}

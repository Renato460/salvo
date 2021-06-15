package com.salvoproyect.salvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Entity
public class Game {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany(mappedBy="game", fetch= FetchType.EAGER)
    private Set<GamePlayer> gamePlayerSet;

    @OneToMany(mappedBy="gameId", fetch= FetchType.EAGER)
    private Set<Score> scoreGameSet;

    private LocalDateTime creationDate;

    public Game(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Game() {
    }


    @JsonIgnore
    public Map<String, Object> getGames (){
        Map<String, Object> objeto = new LinkedHashMap<>();
        objeto.put("id", this.id);
        objeto.put("created", this.creationDate);
        objeto.put("gamePlayers", this.getGamePlayerSet().stream().map(b -> b.getGamesScore(this))
                .collect(toList()));
        objeto.put("scores", this.scoreGameSet.stream().map(Score::getScoreDTO).collect(Collectors.toList()));
        return objeto;
    }

    @JsonIgnore
    public Map<String, Object> getGameById (GamePlayer gamePlayer){

        Map<String, Object> objeto = new LinkedHashMap<>();

        objeto.put("id", this.id);
        objeto.put("created", this.creationDate);
        objeto.put("gamePlayers", this.getGamePlayerSet().stream().map(GamePlayer::getGames)
                .collect(toList()));
        objeto.put("gameState","PLACESHIPS");
        objeto.put("ships", gamePlayer.getShips());
        objeto.put("salvoes", getGamePlayerSet().stream().map(GamePlayer::getSalvos).flatMap(Collection::stream).collect(Collectors.toList()));

        return objeto;

    }



    public Set<Score> getScoreGameSet() {
        return scoreGameSet;
    }

    public void setScoreGameSet(Set<Score> scoreGameSet) {
        this.scoreGameSet = scoreGameSet;
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
}

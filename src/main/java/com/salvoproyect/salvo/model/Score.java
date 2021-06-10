package com.salvoproyect.salvo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game gameId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player playerId;

    private double score;

    private LocalDateTime finishDate;

    public Score() {
    }

    public Score(Game game, Player player, double score, LocalDateTime finishDate) {
        this.score = score;
        this.finishDate = finishDate;
        this.gameId = game;
        this.playerId = player;
    }
    public Score(Game game, Player player,LocalDateTime finishDate) {
        this.finishDate = finishDate;
        this.gameId = game;
        this.playerId = player;
    }

    public void addScore(double score){
        this.score = score;
    }

    public Map<String,Object> getScoreDTO(){
        Map<String,Object> scores = new LinkedHashMap<>();
        scores.put("score", this.score);
        scores.put("player",this.playerId.getId());
        scores.put("finishDate",this.finishDate);
        return scores;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGameId() {
        return gameId;
    }

    public void setGameId(Game gameId) {
        this.gameId = gameId;
    }

    public Player getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Player playerId) {
        this.playerId = playerId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }
}

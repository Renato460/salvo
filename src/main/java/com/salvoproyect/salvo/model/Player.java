package com.salvoproyect.salvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String email;

    private String password;

    @OneToMany(mappedBy="player", fetch= FetchType.EAGER)
    private Set<GamePlayer> gamePlayerSet;

    @OneToMany(mappedBy="playerId", fetch= FetchType.EAGER)
    private Set<Score> scorePlayerSet;


    @JsonIgnore
    public Map<String,Object> getPlayerData (){
        Map<String, Object> dataPlayer = new LinkedHashMap<>();
        dataPlayer.put("id",this.id);
        dataPlayer.put("email", this.email);
        return dataPlayer;
    }

    public Player() { }

    public Player(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public void addGamePlayer(GamePlayer gamePlayer){
        gamePlayer.setPlayer(this);
        gamePlayerSet.add(gamePlayer);
    }

    public Set<Score> getScorePlayerSet() {
        return scorePlayerSet;
    }

    public void setScorePlayerSet(Set<Score> scorePlayerSet) {
        this.scorePlayerSet = scorePlayerSet;
    }

    @JsonIgnore
    public Set<GamePlayer> getGamePlayerSet() {
        return gamePlayerSet;
    }

    public void setGamePlayerSet(Set<GamePlayer> gamePlayerSet) {
        this.gamePlayerSet = gamePlayerSet;
    }

    @JsonIgnore
    public List<Game> getGame() {
        return gamePlayerSet.stream().map(GamePlayer::getGame).collect(toList());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.salvoproyect.salvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private Integer turn;

    @ElementCollection
    @Column(name = "location")
    private List<String> locations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayerId;

    public Salvo() {
    }

    @JsonIgnore
    public Map<String, Object> getSalvoesDTO(){
        Map<String,Object> salvoes = new LinkedHashMap<>();
        salvoes.put("turn", this.turn);
        salvoes.put("player", this.gamePlayerId.getPlayer().getId());
        salvoes.put("locations", this.locations);
        return salvoes;
    }

    public Salvo(Integer turn, GamePlayer gamePlayer) {
        this.turn = turn;
        this.gamePlayerId = gamePlayer;
    }

    public void addLocation(String location){
        this.locations.add(location);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayerId;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayerId = gamePlayer;
    }
}

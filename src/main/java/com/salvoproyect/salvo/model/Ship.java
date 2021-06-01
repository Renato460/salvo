package com.salvoproyect.salvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salvoproyect.salvo.GamePlayer;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String type;

    @ElementCollection
    @Column(name = "locations")
    private List<String> locations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_Id")
    private GamePlayer gamePlayer;

    @JsonIgnore
    public Map<String, Object> getShipDTO(){
        Map<String,Object> ships = new LinkedHashMap<>();
        ships.put("type", this.type);
        ships.put("locations", this.locations);
        return ships;
    }

    public void addGamePlayer(GamePlayer gamePlayer){
        this.gamePlayer = gamePlayer;
    }
    public void addlocation(String location){
        this.locations.add(location);
    }
    public Ship() {
    }

    public Ship(String type) {
        this.type = type;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
}

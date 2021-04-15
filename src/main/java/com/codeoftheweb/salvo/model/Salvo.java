package com.codeoftheweb.salvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
    public class Salvo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ElementCollection
    @Column(name = "locations")
    private List<String> salvoLocations;

    private int turn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    public Salvo() {

    }

    public Salvo(int turn, List<String> locations) {
        setTurn(turn);
        setSalvoLocations(locations);
    }

    public List<String> getSalvoLocations() {
        return salvoLocations;
    }

    public int getTurn() {
        return this.turn;
    }

    public Long getId() {
        return this.id;
    }

    @JsonIgnore
    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }


    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public void setSalvoLocations(List<String> locations) {
        this.salvoLocations = locations;
    }

    public Map<String, Object> salvoDTO() {
                Map<String, Object> dto = new LinkedHashMap<>();
                dto.put("turn", getTurn());
                dto.put("player", getGamePlayer().getPlayer().getId());
                dto.put("locations", getSalvoLocations());
                return dto;
    }
    @Override
    public String toString(){
        return getSalvoLocations() + " " + getTurn();
    }
}

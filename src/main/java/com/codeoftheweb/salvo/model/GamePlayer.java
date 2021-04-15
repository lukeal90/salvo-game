package com.codeoftheweb.salvo.model;

import javax.persistence.FetchType;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;

    @Entity
    public class GamePlayer {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
        @GenericGenerator(name = "native", strategy = "native")
        private Long id;
        private LocalDateTime joinDate;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "player_id")
        private Player player;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "game_id")
        private Game game;

        @OneToMany(mappedBy="gamePlayer", fetch = FetchType.EAGER) // Colocar los cascade cuando cambie el salvo app.
        private Set<Ship> ships = new HashSet<>();

        @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
        private Set<Salvo> salvos = new HashSet<>();

        public GamePlayer(){
            this.joinDate = LocalDateTime.now();
        }

        public GamePlayer(Player player,Game game){
            this();
            setPlayer(player);
            setGame(game);
        }

        public LocalDateTime getJoinDate() {
            return joinDate;
        }

        public void setJoinDate(LocalDateTime joinDate) {
            this.joinDate = joinDate;
        }

        public Long getId(){
            return this.id;
        }

        public void addShip(Ship ship) {
            ship.setGamePlayer(this);
            ships.add(ship);
        }

        public void addSalvos(Salvo salvo) {
            salvo.setGamePlayer(this);
            salvos.add(salvo);
        }

        public List<Ship> getShips(){
            return ships.stream().collect(Collectors.toList());
        }

        public List<Salvo> getSalvos(){ return salvos.stream().collect(Collectors.toList()); }

        @JsonIgnore
        public Player getPlayer() {
            return player;
        }

        @JsonIgnore
        public Game getGame() {
            return game;
        }

        public Optional<Score> getScore() {
            return player.getScore(game);
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public void setGame(Game game) {
            this.game = game;
        }

        public Map<String, Object> gamePlayerDTO() {
            Map<String, Object> dto = new LinkedHashMap<>();
            dto.put("id", getId());
            dto.put("player", player.playerDTO());
            return dto;
        }

    }

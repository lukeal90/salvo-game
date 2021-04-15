package com.codeoftheweb.salvo.model;

import javax.persistence.FetchType;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.*;



    @Entity
    public class Player {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
        @GenericGenerator(name = "native", strategy = "native")
        private Long id;
        private String userName;
        private String password;

        @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
        private Set<GamePlayer> gamePlayers = new HashSet<>();

        @OneToMany(mappedBy = "player", fetch=FetchType.EAGER)
        Set<Score> scores = new HashSet<>();

        public Player() {}

        public Player(String userName,String password){
           setUserName(userName);
           setPassword(password);
        }

        public Long getId() { return id; }

        public String getUserName() {
            return userName;
        }

        public Set<Score> getScores() {
            return scores;
        }

        public String getPassword(){
            return this.password;
        }
        public Optional<Score> getScore(Game game) {
            return scores.stream().filter(p -> p.getGame().getId() == game.getId()).findFirst();
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setPassword(String password){
            this.password = password;
        }

        public void addGamePlayer(GamePlayer gamePlayer){
            gamePlayer.setPlayer(this);
            gamePlayers.add(gamePlayer);
        }

        public void addScores(Score score) {
            score.setPlayer(this);
            scores.add(score);
        }

        public void SetGamePlayers(Set<GamePlayer> gamePlayers){
            this.gamePlayers = gamePlayers;
        }

        public Map<String, Object> playerDTO() {
            Map<String, Object> dto = new LinkedHashMap<>();
            dto.put("id", getId());
            dto.put("email", getUserName());
            //dto.put("score", scorePlayerDTO());
            return dto;
        }
    /*
        private Double totalScore() {
            return getScores().stream()
                    .map(score -> score != null ? score.getScore() : 0.0)
                    .reduce(0.00, (a, b) -> a + b);
        }

        private Long winScore() {
            return getScores().stream()
                    .filter(score -> score != null ? score.getScore() == 1 : false)
                    .count();
        }

        private Long tieScore() {
            return getScores().stream()
                    .filter(score -> score != null ? score.getScore() == 0.5 : false)
                    .count();
        }

        private Long loseScore() {
            return getScores().stream()
                    .filter(score -> score != null ? score.getScore() == 0 : false)
                    .count();
        }

        public Map<String, Object> scorePlayerDTO() {
            Map<String, Object> dto = new LinkedHashMap<>();
            dto.put("total", totalScore());
            dto.put("win", winScore());
            dto.put("tie", tieScore());
            dto.put("lose", loseScore());
            return dto;
        }

     */
}

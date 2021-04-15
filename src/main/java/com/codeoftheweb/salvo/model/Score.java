package com.codeoftheweb.salvo.model;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
    public class Score {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private double score;

        @Column(name="finish_date")
        private LocalDateTime finishDate;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "player_id")
        private Player player;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "game_id")
        private Game game;

        public Score() {
        }

        public Score(double score, Player player, Game game, LocalDateTime date) {
            setScore(score);
            setPlayer(player);
            setGame(game);
            setLocalDateTime(date);
        }

        public Long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public void setLocalDateTime(LocalDateTime date){
            this.finishDate = date;
        }

        public LocalDateTime getFinishDate() {
            return finishDate;
        }

        public void setFinishDate(LocalDateTime finishDate) {
            this.finishDate = finishDate;
        }

        @JsonIgnore
        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        @JsonIgnore
        public Game getGame() {
            return game;
        }

        public void setGame(Game game) {
            this.game = game;
        }

        public Map<String, Object> scoreDTO() {
            Map<String, Object> dto = new LinkedHashMap<>();
            dto.put("finishDate", getFinishDate());
            dto.put("score", getScore());
            dto.put("player", getPlayer().getId());
            return dto;
        }

    }

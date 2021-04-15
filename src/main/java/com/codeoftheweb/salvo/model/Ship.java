package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.*;

    @Entity
    public class Ship {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
        @GenericGenerator(name = "native", strategy = "native")
        private Long id;
        private ShipType type;

        @ElementCollection
        @Column(name="locations")
        private List<String> locations;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "gamePlayer_id")
        private GamePlayer gamePlayer;

        public Ship(){
        }

        public Ship(ShipType shiptype,List<String> locations){
         //   this();
            setType(shiptype);
            setLocations(locations);
        }

        private Long getId() {
            return this.id;
        }

        public List<String> getLocations(){
            return this.locations;
        }

        public void setLocations(List<String> locations){
            this.locations = locations;
        }
        public ShipType getType(){
            return this.type;
        }

        public void setType(ShipType shiptype){
            this.type = shiptype;
        }

        public GamePlayer getGamePlayer(){
            return gamePlayer;
        }
        public void setGamePlayer(GamePlayer gamePlayer) {
            this.gamePlayer = gamePlayer;
        }

        public Map<String, Object> shipDTO() {
            Map<String, Object> dto = new LinkedHashMap<>();
            dto.put("type", getType());
            dto.put("locations", getLocations());
            return dto;
        }


}

package com.codeoftheweb.salvo.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum ShipType {
    @JsonProperty("patrolboat")
    PATROL_BOAT,
    @JsonProperty("destroyer")
    DESTROYER,
    @JsonProperty("submarine")
    SUBMARINE,
    @JsonProperty("battleship")
    BATTLESHIP,
    @JsonProperty("carrier")
    CARRIER;
}

package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.model.Ship;
import com.codeoftheweb.salvo.repository.GamePlayerRepository;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import com.codeoftheweb.salvo.repository.ShipRepository;
import com.codeoftheweb.salvo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ShipController {

    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ShipRepository shipRepository;

    @GetMapping("/ships")
    public List<Object> getShips(){
        return shipRepository.findAll()
                .stream().map(Ship::shipDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/games/players/{gamePlayerId}/ships")
    public ResponseEntity<Map<String, Object>> insertShips(@PathVariable Long gamePlayerId, Authentication authentication, @RequestBody Set<Ship> ships){

        ResponseEntity<Map<String, Object>> response;
        Optional<GamePlayer> gamePlayer = gamePlayerRepository.findById(gamePlayerId);
        Player playerLogged = playerRepository.findByUserName(authentication.getName());

        if(Utils.isGuest(authentication)){
            response = new ResponseEntity<>(Utils.makeMap("error", "player is no unauthorized"), HttpStatus.UNAUTHORIZED);
        }else if(gamePlayer.isEmpty()){
            response = new ResponseEntity<>(Utils.makeMap("error", "game player not found"), HttpStatus.UNAUTHORIZED);
        }else if(!gamePlayer.get().getPlayer().getId().equals(playerLogged.getId())){
            response = new ResponseEntity<>(Utils.makeMap("error", "player id not match with the game player"), HttpStatus.UNAUTHORIZED);
        }else if(gamePlayer.get().getShips().size() > 0){
            response = new ResponseEntity<>(Utils.makeMap("error", "player already has ships located"), HttpStatus.FORBIDDEN);
        }else{
            ships.forEach(ship -> {
                gamePlayer.get().addShip(ship);
                shipRepository.save(ship);
            });
            gamePlayerRepository.save(gamePlayer.get());
            response = new ResponseEntity<>(Utils.makeMap("OK", "success"), HttpStatus.ACCEPTED);
        }

        return response;
    }

}

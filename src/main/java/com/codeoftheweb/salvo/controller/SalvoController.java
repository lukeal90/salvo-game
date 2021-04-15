package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.model.Salvo;
import com.codeoftheweb.salvo.repository.GamePlayerRepository;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import com.codeoftheweb.salvo.repository.SalvoRepository;
import com.codeoftheweb.salvo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    SalvoRepository salvoRepository;

    @PostMapping("/games/players/{gamePlayerId}/salvoes")
    public ResponseEntity<Map<String, Object>> getSalvo(@PathVariable Long gamePlayerId, Authentication authentication, @RequestBody Salvo salvo){

        Optional<GamePlayer> gamePlayer = gamePlayerRepository.findById(gamePlayerId);
        Player playerLogged = playerRepository.findByUserName(authentication.getName());

        if(Utils.isGuest(authentication))
            return new ResponseEntity<>(Utils.makeMap("error", "player is no unauthorized"), HttpStatus.UNAUTHORIZED);
        if(gamePlayer.isEmpty())
            return new ResponseEntity<>(Utils.makeMap("error", "game player not found"), HttpStatus.UNAUTHORIZED);
        if(!gamePlayer.get().getPlayer().getId().equals(playerLogged.getId()))
            return new ResponseEntity<>(Utils.makeMap("error", "player id not match with the game player"), HttpStatus.UNAUTHORIZED);

        Optional<GamePlayer> oponent = gamePlayer.get().getGame().searchOponent(gamePlayer.get());
        if(!oponent.isPresent())
            return new ResponseEntity<>(Utils.makeMap("error", "no oponent in this game"), HttpStatus.FORBIDDEN);

        if(gamePlayer.get().getGame().compareListSalvos(gamePlayer.get(),oponent.get())){
            salvo.setTurn(gamePlayer.get().getSalvos().size() + 1);
            gamePlayer.get().addSalvos(salvo);
            gamePlayerRepository.save(gamePlayer.get());
            salvoRepository.save(salvo);
            return new ResponseEntity<>(Utils.makeMap("OK", "success"), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(Utils.makeMap("error", "user already shot a salvo"), HttpStatus.FORBIDDEN);
        }
    }
}

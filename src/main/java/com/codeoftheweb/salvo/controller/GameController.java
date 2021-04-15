package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.*;
import com.codeoftheweb.salvo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @GetMapping("/games")
    public Map<String, Object> getGames(Authentication authentication){
        Map<String, Object> dto = new LinkedHashMap<>();

        if(Utils.isGuest(authentication)){
            dto.put("player", "Guest");
        }else{
            dto.put("player", playerRepository.findByUserName(authentication.getName())
                    .playerDTO());
        }

        dto.put("games",gameRepository.findAll()
                .stream().map(Game::gameDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    @PostMapping("/games")
    public ResponseEntity<Map<String,Object>> createGame(Authentication authentication){

        ResponseEntity<Map<String, Object>> response;
        Game newGame;
        Player playerLogged;
        GamePlayer newGamePlayer;

        if(!Utils.isGuest(authentication)){
            newGame = gameRepository.save(new Game(LocalDateTime.now()));
            playerLogged = playerRepository.findByUserName(authentication.getName());
            newGamePlayer = gamePlayerRepository.save(new GamePlayer(playerLogged,newGame));

            response = new ResponseEntity<>(Utils.makeMap("gpid", newGamePlayer.getId()), HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(Utils.makeMap("error", "Player not  authorized"), HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

    @PostMapping("/game/{gameId}/players")
    public ResponseEntity<Map<String,Object>> joinGame(@PathVariable Long gameId, Authentication authentication){

        ResponseEntity<Map<String, Object>> response;
        Optional<Game> game = gameRepository.findById(gameId);
        Player player;
        GamePlayer gamePlayer;

        if(Utils.isGuest(authentication)){
            response = new ResponseEntity<>(Utils.makeMap("error", "player is no unauthorized"), HttpStatus.UNAUTHORIZED);
        }else if(game.isEmpty()){
            response = new ResponseEntity<>(Utils.makeMap("error", "no such game"), HttpStatus.FORBIDDEN);
        }else if(game.get().getPlayers().size() == 2){
            response = new ResponseEntity<>(Utils.makeMap("error", "game is full of players"), HttpStatus.FORBIDDEN);
        }else{
            player = playerRepository.findByUserName(authentication.getName());
            gamePlayer = gamePlayerRepository.save(new GamePlayer(player, game.get()));
            response = new ResponseEntity<>(Utils.makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
        }
        return response;
    }






}

package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import com.codeoftheweb.salvo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

    @RestController
    @RequestMapping("/api")
    public class PlayerController {

        @Autowired
        PlayerRepository playerRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @GetMapping("/players")
        public List<Object> getPlayers(){
            return playerRepository.findAll()
                    .stream().map(Player::playerDTO)
                    .collect(Collectors.toList());
        }

        @PostMapping("/players")
        public ResponseEntity<Object> register(@RequestParam String email, @RequestParam String password) {

            if (email.isEmpty() || password.isEmpty()) {
                return new ResponseEntity<>(Utils.makeMap("error", "Missing data"), HttpStatus.FORBIDDEN);
            }

            if (playerRepository.findByUserName(email) !=  null) {
                return new ResponseEntity<>(Utils.makeMap("error", "Name alredy in use"), HttpStatus.FORBIDDEN);
            }

            playerRepository.save(new Player(email, passwordEncoder.encode(password)));
            return new ResponseEntity<>(Utils.makeMap("OK","success, player created"), HttpStatus.CREATED);
        }

    }

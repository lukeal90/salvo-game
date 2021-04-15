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
import java.util.*;
import java.util.stream.Collectors;

    @RestController
    @RequestMapping("/api")
    public class AppController {

        @Autowired
        private GamePlayerRepository gamePlayerRepository;

        @Autowired
        private ScoreRepository scoreRepository;

        @GetMapping("/gamePlayers")
        public List<Object> getGamePlayers(){
            return gamePlayerRepository.findAll()
                    .stream().map(GamePlayer::gamePlayerDTO)
                    .collect(Collectors.toList());
        }

        @RequestMapping("/game_view/{gamePlayerId}")
        public ResponseEntity<Map<String, Object>> getGameView(@PathVariable Long gamePlayerId,Authentication authentication) {

            Optional<GamePlayer> gPFound = gamePlayerRepository.findById(gamePlayerId);

            ResponseEntity<Map<String, Object>> response;
            Map<String, Object> auxMap;

            if (gPFound.isPresent()) {
                if (authentication.getName().equals(gPFound.get().getPlayer().getUserName())) {

                    GamePlayer self = gPFound.get();
                    Optional<GamePlayer> opponent = gPFound.get().getGame().searchOponent(self);
                    auxMap = gPFound.get().getGame().gameDTO();
                    Map<String, Object> hits = new LinkedHashMap<>();

                    if(opponent.isPresent()){
                        hits.put("self", hitsResume(self, opponent.get()));
                        hits.put("opponent", hitsResume(opponent.get(),self));
                    }else{
                        hits.put("self", new ArrayList<>());
                        hits.put("opponent", new ArrayList<>());
                    }

                    auxMap.put("hits", hits);
                    auxMap.put("gameState", gameSateUpdate(self));
                    auxMap.put("ships", gPFound.get().getShips().stream().map(Ship::shipDTO).collect(Collectors.toList()));
                    auxMap.put("salvoes", gPFound.get().getGame().getGamePlayers().stream()
                            .flatMap(gamePlayer1 -> gamePlayer1.getSalvos()
                                    .stream()
                                    .map(Salvo::salvoDTO))
                            .collect(Collectors.toList()));

                    response = new ResponseEntity<>(auxMap, HttpStatus.OK);
                }else{
                    auxMap = Utils.makeMap("error", "gamePlayer not  authorized");
                    response = new ResponseEntity<>(auxMap, HttpStatus.UNAUTHORIZED);
                }
                } else {
                    auxMap = Utils.makeMap("error", "gamePlayer does not exist");
                    response = new ResponseEntity<>(auxMap, HttpStatus.UNAUTHORIZED);
                }
                return response;
            }

        public String gameSateUpdate(GamePlayer self){
            GamePlayer gamePlayer = self;
            Optional<GamePlayer> opponent = gamePlayer.getGame().searchOponent(self);
            if( self.getShips().size() == 0){
                return "PLACESHIPS";
            }
            if(opponent.isEmpty()) {
                return "WAITINGFOROPP";
            }
            if (opponent.get().getShips().isEmpty()) {
                return "WAIT";
            }
            if (self.getSalvos().size() < opponent.get().getSalvos().size()) {
                return "PLAY";
            }

            if (self.getSalvos().size() > opponent.get().getSalvos().size()) {   // Revisar si va o vuela.
                return "WAIT";
            }
            if (self.getSalvos().size() == opponent.get().getSalvos().size()) {
                boolean selfLost = getIfAllSunk(self, opponent.get());
                boolean opponentLost = getIfAllSunk(opponent.get(), self);

                if (selfLost && opponentLost) {
                    if (self.getGame().getScores().size() == 0) {
                        scoreRepository.save(new Score(0.5, self.getPlayer(), self.getGame(), LocalDateTime.now()));
                        scoreRepository.save(new Score(0.5, opponent.get().getPlayer(), opponent.get().getGame(), LocalDateTime.now()));
                    }
                    return "TIE";
                }
                if (selfLost) {
                    if (self.getGame().getScores().size() == 0) {
                        scoreRepository.save(new Score(0.0, self.getPlayer(), self.getGame(), LocalDateTime.now()));
                        scoreRepository.save(new Score(1.0,opponent.get().getPlayer(), opponent.get().getGame(), LocalDateTime.now()));
                    }
                    return "LOST";
                }
                if (opponentLost) {
                    if (self.getGame().getScores().size() == 0) {
                        scoreRepository.save(new Score(1.0, self.getPlayer(), self.getGame(), LocalDateTime.now()));
                        scoreRepository.save(new Score(0.0, opponent.get().getPlayer(), opponent.get().getGame(), LocalDateTime.now()));
                    }
                    return "WON";
                }
                return "PLAY";
            }
            return "WAIT";
        }


        public int calculateSalvoTurn(GamePlayer gamePlayer, GamePlayer opponent) {
            if (opponent!= null && gamePlayer.getSalvos().size() <= opponent.getSalvos().size()) {
                return gamePlayer.getSalvos().size() + 1;
            }
            return 0;
        }


        private List<Map<String,Object>> hitsResume(GamePlayer self, GamePlayer opponent) {
            List<Map<String, Object>> dtoFinal = new LinkedList<>();
            int[] totalDamages = new int[5];

            List<String> patrolBoatLocation = findShipLocations(self, ShipType.PATROL_BOAT);
            List<String> destroyerLocation = findShipLocations(self, ShipType.DESTROYER);
            List<String> submarineLocation = findShipLocations(self, ShipType.SUBMARINE);
            List<String> battleShipLocation = findShipLocations(self, ShipType.BATTLESHIP);
            List<String> carrierLocationLocation = findShipLocations(self, ShipType.CARRIER);

            List<Salvo> opponentSalvos = new ArrayList<>(opponent.getSalvos());
            opponentSalvos.sort(Comparator.comparing(Salvo::getId));
            for (Salvo salvo : opponentSalvos) {

                Map<String, Object> dtoHit = new LinkedHashMap<>();
                Map<String, Object> damage = new LinkedHashMap<>();
                ArrayList<String> hitCellList = new ArrayList<>();
                int[] hits = new int[5];
                int missedShots = salvo.getSalvoLocations().size();

                for (String location : salvo.getSalvoLocations()) {
                    if (carrierLocationLocation.contains(location)) {
                        totalDamages[0]++;
                        hits[0]++;
                        hitCellList.add(location);
                        missedShots--;
                    }
                    if (battleShipLocation.contains(location)) {
                        totalDamages[1]++;
                        hits[1]++;
                        hitCellList.add(location);
                        missedShots--;
                    }
                    if (destroyerLocation.contains(location)) {
                        totalDamages[2]++;
                        hits[2]++;
                        hitCellList.add(location);
                        missedShots--;
                    }
                    if (submarineLocation.contains(location)) {
                        totalDamages[3]++;
                        hits[3]++;
                        hitCellList.add(location);
                        missedShots--;
                    }
                    if (patrolBoatLocation.contains(location)) {
                        totalDamages[4]++;
                        hits[4]++;
                        hitCellList.add(location);
                        missedShots--;
                    }
                }
                damage.put("carrierHits", hits[0]);
                damage.put("battleshipHits", hits[1]);
                damage.put("destroyerHits", hits[2]);
                damage.put("submarineHits", hits[3]);
                damage.put("patrolboatHits", hits[4]);
                damage.put("carrier", totalDamages[0]);
                damage.put("battleship", totalDamages[1]);
                damage.put("destroyer", totalDamages[2]);
                damage.put("submarine", totalDamages[3]);
                damage.put("patrolboat", totalDamages[4]);
                dtoHit.put("turn", salvo.getTurn());
                dtoHit.put("hitLocations", hitCellList);
                dtoHit.put("damages", damage);
                dtoHit.put("missed", missedShots);
                dtoFinal.add(dtoHit);
            }

            return dtoFinal;
        }

        public List<String> findShipLocations(GamePlayer self, ShipType type){
            Optional<Ship> response;
            response = self.getShips().stream().filter(ship -> ship.getType().equals(type)).findFirst();
            if(response.isEmpty()){
                return new ArrayList<>();
            }else{
                return response.get().getLocations();
            }
        }
        private boolean getIfAllSunk(GamePlayer self, GamePlayer opponent) {
            if (!opponent.getShips().isEmpty() && !self.getSalvos().isEmpty()) {
                return opponent.getSalvos().stream().flatMap(salvo -> salvo.getSalvoLocations().stream()).collect(Collectors.toList())
                        .containsAll(self.getShips().stream().flatMap(ship -> ship.getLocations().stream()).collect(Collectors.toList()));
            }
            return false;
        }

    }


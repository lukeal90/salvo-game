package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
	public class SalvoApplication extends SpringBootServletInitializer {

		public static void main(String[] args) {
			SpringApplication.run(SalvoApplication.class, args);

		}

		@Bean
		public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository,
										  SalvoRepository salvoRepository, ScoreRepository scoreRepository) {
			return (args) -> {

				Player player1 = playerRepository.save(new Player("j.bauer@ctu.gov", passwordEncoder().encode("24")));
				Player player2 = playerRepository.save(new Player("c.obrian@ctu.gov",passwordEncoder().encode("42")));
				Player player3 = playerRepository.save(new Player("kim_bauer@gmail.com",passwordEncoder().encode("kb")));
				Player player4 = playerRepository.save(new Player("t.almeida@ctu.gov",passwordEncoder().encode("mole")));

				Game game1 = gameRepository.save(new Game(LocalDateTime.now().plusHours(1)));
				Game game2 = gameRepository.save(new Game(LocalDateTime.now().plusHours(2)));
				Game game3 = gameRepository.save(new Game(LocalDateTime.now().plusHours(2)));
				Game game4 = gameRepository.save(new Game(LocalDateTime.now().plusHours(2)));
				Game game5 = gameRepository.save(new Game(LocalDateTime.now().plusHours(2)));
				Game game6 = gameRepository.save(new Game(LocalDateTime.now().plusHours(2)));
				Game game7 = gameRepository.save(new Game(LocalDateTime.now().plusHours(2)));
				Game game8 = gameRepository.save(new Game(LocalDateTime.now().plusHours(2)));

				List<String> location1 = new ArrayList<>(Arrays.asList("E1","A1","G1"));
				List<String> location2 = new ArrayList<>(Arrays.asList("B7","C7","D7"));
				List<String> location3 = new ArrayList<>(Arrays.asList("A2","A3","A4"));
				List<String> location4 = new ArrayList<>(Arrays.asList("E3","E4","E5"));
				List<String> location5 = new ArrayList<>(Arrays.asList("E6","B2","F3"));
				List<String> location6 = new ArrayList<>(Arrays.asList("G1","D3","A7"));
				List<String> location7 = new ArrayList<>(Arrays.asList("D3","G4","B5"));
				List<String> location8 = new ArrayList<>(Arrays.asList("A3","E4","B5"));

				Ship ship1 = new Ship(ShipType.DESTROYER, location1);
				Ship ship2 = new Ship(ShipType.PATROL_BOAT, location2);
				Ship ship3 = new Ship(ShipType.SUBMARINE, location3);
				Ship ship4 = new Ship(ShipType.BATTLESHIP, location4);
				Ship ship5 = new Ship(ShipType.DESTROYER, location1);
				Ship ship6 = new Ship(ShipType.CARRIER, location2);
				Ship ship7 = new Ship(ShipType.DESTROYER, location3);
				Ship ship8 = new Ship(ShipType.CARRIER, location4);
				Ship ship9 = new Ship(ShipType.DESTROYER, location1);
				Ship ship10 = new Ship(ShipType.CARRIER, location2);
				Ship ship11= new Ship(ShipType.DESTROYER, location3);
				Ship ship12= new Ship(ShipType.CARRIER, location4);
				Ship ship13 = new Ship(ShipType.DESTROYER, location1);
				Ship ship14 = new Ship(ShipType.SUBMARINE, location2);
				Ship ship15 = new Ship(ShipType.SUBMARINE, location3);
				Ship ship16 = new Ship(ShipType.BATTLESHIP, location4);
				Ship ship17 = new Ship(ShipType.DESTROYER, location1);
				Ship ship18 = new Ship(ShipType.CARRIER, location2);
				Ship ship19 = new Ship(ShipType.DESTROYER, location3);
				Ship ship20 = new Ship(ShipType.CARRIER, location4);
				Ship ship21 = new Ship(ShipType.DESTROYER, location1);
				Ship ship22 = new Ship(ShipType.CARRIER, location2);
				Ship ship23= new Ship(ShipType.DESTROYER, location3);
				Ship ship24= new Ship(ShipType.CARRIER, location4);
				Ship ship25 = new Ship(ShipType.CARRIER, location4);
				Ship ship26 = new Ship(ShipType.DESTROYER, location1);
				Ship ship27 = new Ship(ShipType.CARRIER, location2);
				Ship ship28= new Ship(ShipType.DESTROYER, location3);
				Ship ship29= new Ship(ShipType.CARRIER, location4);
				Ship ship30 = new Ship(ShipType.CARRIER, location2);
				//Ship ship31= new Ship(ShipType.DESTROYER, location3);
				//Ship ship32= new Ship(ShipType.CARRIER, location4);

				Salvo salvo1 = new Salvo(1, location1);
				Salvo salvo2 = new Salvo(2, location2);
				Salvo salvo3 = new Salvo(1, location3);
				Salvo salvo4 = new Salvo(2, location4);
				Salvo salvo5 = new Salvo(2, location5);
				Salvo salvo6 = new Salvo(2, location6);
				Salvo salvo7 = new Salvo(1, location7);
				Salvo salvo8 = new Salvo(2, location8);

				GamePlayer gamePlayer1 = new GamePlayer(player1,game1);
				GamePlayer gamePlayer2 = new GamePlayer(player2,game1);

				GamePlayer gamePlayer3 = new GamePlayer(player1,game2);
				GamePlayer gamePlayer4 = new GamePlayer(player2,game2);

				GamePlayer gamePlayer5 = new GamePlayer(player2,game3);
				GamePlayer gamePlayer6 = new GamePlayer(player4,game3);

				GamePlayer gamePlayer7 = new GamePlayer(player2,game4);
				GamePlayer gamePlayer8 = new GamePlayer(player1,game4);

				GamePlayer gamePlayer9 = new GamePlayer(player4,game5);
				GamePlayer gamePlayer10 = new GamePlayer(player1,game5);

				GamePlayer gamePlayer11 = new GamePlayer(player3,game6);

				GamePlayer gamePlayer12 = new GamePlayer(player4,game7);

				GamePlayer gamePlayer13 = new GamePlayer(player3,game8);
				GamePlayer gamePlayer14 = new GamePlayer(player4,game8);

		        gamePlayer1.addShip(ship1);
				gamePlayer1.addShip(ship2);
				gamePlayer1.addShip(ship3);

				gamePlayer2.addShip(ship4);
				gamePlayer2.addShip(ship5);
				gamePlayer2.addShip(ship6);

				gamePlayer3.addShip(ship7);
				gamePlayer3.addShip(ship8);
				gamePlayer3.addShip(ship9);

				gamePlayer4.addShip(ship10);
				gamePlayer4.addShip(ship11);
				gamePlayer4.addShip(ship12);

				gamePlayer5.addShip(ship13);
				gamePlayer5.addShip(ship14);

				//gamePlayer6.addShip(ship15);
				//gamePlayer6.addShip(ship16);

				//gamePlayer7.addShip(ship17);
				//gamePlayer7.addShip(ship18);

				//gamePlayer8.addShip(ship19);
				//gamePlayer8.addShip(ship20);

				//gamePlayer9.addShip(ship21);
				//gamePlayer9.addShip(ship22);

				//gamePlayer10.addShip(ship23);
				//gamePlayer10.addShip(ship24);

				//gamePlayer11.addShip(ship25);
				//gamePlayer11.addShip(ship26);

				//gamePlayer12.addShip(ship27);
				//gamePlayer12.addShip(ship28);

				//gamePlayer13.addShip(ship29);
				//gamePlayer13.addShip(ship30);

				//gamePlayer14.addShip(ship31);
				//gamePlayer14.addShip(ship32);

				//SALVOS


				gamePlayer1.addSalvos(salvo1);
				gamePlayer1.addSalvos(salvo2);

				gamePlayer2.addSalvos(salvo3);
				gamePlayer2.addSalvos(salvo4);

				//gamePlayer3.addSalvos(salvo5);
				//gamePlayer3.addSalvos(salvo6);

				//gamePlayer4.addSalvos(salvo7);
				//gamePlayer4.addSalvos(salvo8);

				//gamePlayer5.addSalvos(salvo3);
				//gamePlayer5.addSalvos(salvo2);

				//gamePlayer6.addSalvos(salvo1);
				//gamePlayer6.addSalvos(salvo4);

				//gamePlayer7.addSalvos(salvo5);
				//gamePlayer7.addSalvos(salvo4);

				//gamePlayer8.addSalvos(salvo2);
				//gamePlayer8.addSalvos(salvo8);

				//gamePlayer9.addSalvos(salvo5);
				//gamePlayer9.addSalvos(salvo1);

				//gamePlayer10.addSalvos(salvo7);
				//gamePlayer10.addSalvos(salvo5);

				//gamePlayer11.addSalvos(salvo2);
				//gamePlayer11.addSalvos(salvo6);

				//gamePlayer12.addSalvos(salvo1);
				//gamePlayer12.addSalvos(salvo8);

				//gamePlayer13.addSalvos(salvo1);
				//gamePlayer13.addSalvos(salvo2);

				//gamePlayer14.addSalvos(salvo4);
				//gamePlayer14.addSalvos(salvo3);

				gamePlayerRepository.save(gamePlayer1);
				gamePlayerRepository.save(gamePlayer2);
				gamePlayerRepository.save(gamePlayer3);
				gamePlayerRepository.save(gamePlayer4);
				gamePlayerRepository.save(gamePlayer5);
				gamePlayerRepository.save(gamePlayer6);
				gamePlayerRepository.save(gamePlayer7);
				gamePlayerRepository.save(gamePlayer8);
				gamePlayerRepository.save(gamePlayer9);
				gamePlayerRepository.save(gamePlayer10);
				gamePlayerRepository.save(gamePlayer11);
				gamePlayerRepository.save(gamePlayer12);
				gamePlayerRepository.save(gamePlayer13);
				gamePlayerRepository.save(gamePlayer14);

				shipRepository.save(ship1);
				shipRepository.save(ship2);
				shipRepository.save(ship3);
				shipRepository.save(ship4);
				shipRepository.save(ship5);
				shipRepository.save(ship6);
				shipRepository.save(ship7);
				shipRepository.save(ship8);
				shipRepository.save(ship9);
				shipRepository.save(ship10);
				shipRepository.save(ship11);
				shipRepository.save(ship12);
				shipRepository.save(ship13);
				shipRepository.save(ship14);
				shipRepository.save(ship15);
				shipRepository.save(ship16);
				shipRepository.save(ship17);
				shipRepository.save(ship18);
				shipRepository.save(ship19);
				shipRepository.save(ship20);
				shipRepository.save(ship21);
				shipRepository.save(ship22);
				shipRepository.save(ship23);
				shipRepository.save(ship24);
				shipRepository.save(ship25);
				shipRepository.save(ship26);
				shipRepository.save(ship27);
				shipRepository.save(ship28);
				shipRepository.save(ship29);
				shipRepository.save(ship30);
				//shipRepository.save(ship31);
				//shipRepository.save(ship32);

				salvoRepository.save(salvo1);
				salvoRepository.save(salvo2);
				salvoRepository.save(salvo3);
				salvoRepository.save(salvo4);
				salvoRepository.save(salvo5);
				salvoRepository.save(salvo6);
				salvoRepository.save(salvo7);
				salvoRepository.save(salvo8);

				Score score1 = scoreRepository.save(new Score(1,player1,game1,LocalDateTime.now()));
				Score score2 = scoreRepository.save(new Score(0,player2,game1,LocalDateTime.now()));

				Score score3 = scoreRepository.save(new Score(0.5,player1,game2,LocalDateTime.now()));
				Score score4 = scoreRepository.save(new Score(0.5,player2,game2,LocalDateTime.now()));

				Score score5 = scoreRepository.save(new Score(1,player2,game3,LocalDateTime.now()));
				Score score6 = scoreRepository.save(new Score(0,player4,game3,LocalDateTime.now()));

				Score score8 = scoreRepository.save(new Score(0.5,player2,game4,LocalDateTime.now()));
				Score score9 = scoreRepository.save(new Score(0.5,player1,game4,LocalDateTime.now()));

			};
		}

		@Bean
		public PasswordEncoder passwordEncoder() {
			return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		}


	}


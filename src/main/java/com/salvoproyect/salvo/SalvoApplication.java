package com.salvoproyect.salvo;

import com.salvoproyect.salvo.model.*;
import com.salvoproyect.salvo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(GameRepository repository, PlayerRepository playerRepository, GamePlayerRepository gamePlayerRepository,
									  ShipRepository shipRepository, SalvoRepository salvoRepository,
									  ScoreRepository scoreRepository, PasswordEncoder passwordEncoder){
		return args -> {

			Player player1 = new Player("j.bauer@ctu.gov", passwordEncoder.encode("1234"));
			Player player2 = new Player("c.obrian@ctu.gov",passwordEncoder.encode("12345"));
			Player player3 = new Player("kim_bauer@gmail.com",passwordEncoder.encode("123456"));
			Player player4 = new Player("t.almeida@ctu.gov",passwordEncoder.encode("1234567"));

			playerRepository.save(player1);
			playerRepository.save(player2);
			playerRepository.save(player3);
			playerRepository.save(player4);


			LocalDateTime date = LocalDateTime.now();
			Game game1 = new Game(date);
			Game game2 = new Game(date.plusHours(1));
			Game game3 = new Game(date.plusHours(2));
			Game game4 = new Game(date.plusHours(3));
			Game game5 = new Game(date.plusHours(4));
			Game game6 = new Game(date.plusHours(5));
			Game game7 = new Game(date.plusHours(6));
			Game game8 = new Game(date.plusHours(7));


			repository.save(game1);
			repository.save(game2);
			repository.save(game3);
			repository.save(game4);
			repository.save(game5);
			repository.save(game6);
			repository.save(game7);
			repository.save(game8);


			GamePlayer gamePlayer0 = gamePlayerRepository.save(new GamePlayer(game1.getCreationDate(), game1, player1));
			GamePlayer gamePlayer1 = gamePlayerRepository.save(new GamePlayer(game1.getCreationDate(), game1, player2));
			GamePlayer gamePlayer2 = gamePlayerRepository.save(new GamePlayer(game2.getCreationDate(), game2, player1));
			GamePlayer gamePlayer3 = gamePlayerRepository.save(new GamePlayer(game2.getCreationDate(), game2, player2));
			GamePlayer gamePlayer4 = gamePlayerRepository.save(new GamePlayer(game3.getCreationDate(), game3, player2));
			GamePlayer gamePlayer5 = gamePlayerRepository.save(new GamePlayer(game3.getCreationDate(), game3, player4));
			GamePlayer gamePlayer6 = gamePlayerRepository.save(new GamePlayer(game4.getCreationDate(), game4, player2));
			GamePlayer gamePlayer7 = gamePlayerRepository.save(new GamePlayer(game4.getCreationDate(), game4, player1));
			GamePlayer gamePlayer8 = gamePlayerRepository.save(new GamePlayer(game5.getCreationDate(), game5, player4));
			GamePlayer gamePlayer9 = gamePlayerRepository.save(new GamePlayer(game5.getCreationDate(), game5, player1));
			GamePlayer gamePlayer10 = gamePlayerRepository.save(new GamePlayer(game6.getCreationDate(), game6, player3));
			GamePlayer gamePlayer11 = gamePlayerRepository.save(new GamePlayer(game7.getCreationDate(), game7, player4));
			GamePlayer gamePlayer12 = gamePlayerRepository.save(new GamePlayer(game8.getCreationDate(), game8, player3));
			GamePlayer gamePlayer13 = gamePlayerRepository.save(new GamePlayer(game8.getCreationDate(), game8, player4));


			//GAME 1


			Ship ship1 = new Ship("submarine");
			Ship ship2 = new Ship("destroyer");


			ship1.addGamePlayer(gamePlayer0);
			ship2.addGamePlayer(gamePlayer0);

			ship1.addlocation("H2");
			ship1.addlocation("H3");
			ship1.addlocation("H4");
			ship2.addlocation("E1");
			ship2.addlocation("F1");
			ship2.addlocation("G1");



			Ship ship3 = new Ship("destroyer");
			Ship ship4 = new Ship("patrolboat");

			ship3.addGamePlayer(gamePlayer1);
			ship4.addGamePlayer(gamePlayer1);

			ship3.addlocation("B5");
			ship3.addlocation("C5");
			ship3.addlocation("D5");
			ship4.addlocation("F1");
			ship4.addlocation("F2");
			// Game 1 End ---

			//Game 2


			Ship ship5 = new Ship("destroyer");
			Ship ship6 = new Ship("patrolboat");

			ship5.addGamePlayer(gamePlayer2);
			ship6.addGamePlayer(gamePlayer2);

			ship5.addlocation("B5");
			ship5.addlocation("C5");
			ship5.addlocation("D5");
			ship6.addlocation("C6");
			ship6.addlocation("C7");



			Ship ship7 = new Ship("submarine");
			Ship ship8 = new Ship("patrolboat");

			ship7.addGamePlayer(gamePlayer3);
			ship8.addGamePlayer(gamePlayer3);

			ship7.addlocation("A2");
			ship7.addlocation("A3");
			ship7.addlocation("A4");
			ship8.addlocation("G6");
			ship8.addlocation("H6");

			//Game 2 End ---


			//Game 3


			Ship ship9 = new Ship("destroyer");
			Ship ship10 = new Ship("patrolboat");

			ship9.addGamePlayer(gamePlayer4);
			ship10.addGamePlayer(gamePlayer4);

			ship9.addlocation("B5");
			ship9.addlocation("C5");
			ship9.addlocation("D5");
			ship10.addlocation("C6");
			ship10.addlocation("C7");



			Ship ship11 = new Ship("submarine");
			Ship ship12 = new Ship("patrolboat");

			ship11.addGamePlayer(gamePlayer5);
			ship12.addGamePlayer(gamePlayer5);

			ship11.addlocation("A2");
			ship11.addlocation("A3");
			ship11.addlocation("A4");
			ship12.addlocation("G6");
			ship12.addlocation("H6");
			//END Game 3 ----


			//Game 4


			Ship ship13 = new Ship("destroyer");
			Ship ship14 = new Ship("patrolboat");

			ship13.addGamePlayer(gamePlayer6);
			ship14.addGamePlayer(gamePlayer6);

			ship13.addlocation("B5");
			ship13.addlocation("C5");
			ship13.addlocation("D5");
			ship14.addlocation("C6");
			ship14.addlocation("C7");



			Ship ship15 = new Ship("submarine");
			Ship ship16 = new Ship("patrolboat");

			ship15.addGamePlayer(gamePlayer7);
			ship16.addGamePlayer(gamePlayer7);

			ship15.addlocation("B5");
			ship15.addlocation("C5");
			ship15.addlocation("D5");
			ship16.addlocation("G6");
			ship16.addlocation("H6");
			//Game 4 End ---


			//Game 5



			Ship ship17 = new Ship("destroyer");
			Ship ship18 = new Ship("patrolboat");

			ship17.addGamePlayer(gamePlayer8);
			ship18.addGamePlayer(gamePlayer8);

			ship17.addlocation("B5");
			ship17.addlocation("C5");
			ship17.addlocation("D5");
			ship18.addlocation("C6");
			ship18.addlocation("C7");


			Ship ship19 = new Ship("submarine");
			Ship ship20 = new Ship("patrolboat");

			ship19.addGamePlayer(gamePlayer9);
			ship20.addGamePlayer(gamePlayer9);

			ship19.addlocation("A2");
			ship19.addlocation("A3");
			ship19.addlocation("A4");
			ship20.addlocation("G6");
			ship20.addlocation("H6");
			//End Game 5 -----

			//Game 6


			Ship ship21 = new Ship("destroyer");
			Ship ship22 = new Ship("patrolboat");

			ship21.addGamePlayer(gamePlayer10);
			ship22.addGamePlayer(gamePlayer10);

			ship21.addlocation("B5");
			ship21.addlocation("C5");
			ship21.addlocation("D5");
			ship22.addlocation("C6");
			ship22.addlocation("C7");
			//End 6 ----

			//Game 8


			Ship ship23 = new Ship("destroyer");
			Ship ship24 = new Ship("patrolboat ");

			ship23.addGamePlayer(gamePlayer12);
			ship24.addGamePlayer(gamePlayer12);

			ship23.addlocation("B5");
			ship23.addlocation("C5");
			ship23.addlocation("D5");
			ship24.addlocation("C6");
			ship24.addlocation("C7");



			Ship ship25 = new Ship("submarine");
			Ship ship26 = new Ship("patrolboat");

			ship25.addGamePlayer(gamePlayer13);
			ship26.addGamePlayer(gamePlayer13);

			ship25.addlocation("A2");
			ship25.addlocation("A3");
			ship25.addlocation("A4");
			ship26.addlocation("G6");
			ship26.addlocation("H6");



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

//Game1 --
			Salvo salvo1 = new Salvo(1,gamePlayer0);
			salvo1.addLocation("B5");
			salvo1.addLocation("C5");
			salvo1.addLocation("F1");

			Salvo salvo2 = new Salvo(1,gamePlayer1);
			salvo2.addLocation("B4");
			salvo2.addLocation("B5");
			salvo2.addLocation("B6");

			Salvo salvo3 = new Salvo(2,gamePlayer0);
			salvo3.addLocation("F2");
			salvo3.addLocation("D5");

			Salvo salvo4 = new Salvo(2,gamePlayer1);
			salvo4.addLocation("E1");
			salvo4.addLocation("H3");
			salvo4.addLocation("A2");
//Game 1---

			//Game 2--
			Salvo salvo5 = new Salvo(1,gamePlayer2);
			salvo5.addLocation("A2");
			salvo5.addLocation("A4");
			salvo5.addLocation("G6");

			Salvo salvo6 = new Salvo(1,gamePlayer3);
			salvo6.addLocation("B5");
			salvo6.addLocation("D5");
			salvo6.addLocation("C7");

			Salvo salvo7 = new Salvo(2,gamePlayer2);
			salvo7.addLocation("A3");
			salvo7.addLocation("H6");

			Salvo salvo8 = new Salvo(2,gamePlayer3);
			salvo8.addLocation("C5");
			salvo8.addLocation("C6");
			//Game 2  --------

			//Game 3 -----
			Salvo salvo9= new Salvo(1,gamePlayer4);
			salvo9.addLocation("G6");
			salvo9.addLocation("H6");
			salvo9.addLocation("A4");

			Salvo salvo10= new Salvo(1,gamePlayer5);
			salvo10.addLocation("H1");
			salvo10.addLocation("H2");
			salvo10.addLocation("H3");

			Salvo salvo11= new Salvo(2,gamePlayer4);
			salvo11.addLocation("A2");
			salvo11.addLocation("A3");
			salvo11.addLocation("D8");

			Salvo salvo12= new Salvo(2,gamePlayer5);
			salvo12.addLocation("E1");
			salvo12.addLocation("F2");
			salvo12.addLocation("G3");
			//Game 3 ------

			//Game 4 -----
			Salvo salvo13= new Salvo(1,gamePlayer6);
			salvo13.addLocation("A3");
			salvo13.addLocation("A4");
			salvo13.addLocation("F7");

			Salvo salvo14= new Salvo(1,gamePlayer7);
			salvo14.addLocation("B5");
			salvo14.addLocation("C6");
			salvo14.addLocation("H1");

			Salvo salvo15= new Salvo(2,gamePlayer6);
			salvo15.addLocation("A2");
			salvo15.addLocation("G6");
			salvo15.addLocation("H6");

			Salvo salvo16= new Salvo(2,gamePlayer7);
			salvo16.addLocation("C5");
			salvo16.addLocation("C7");
			salvo16.addLocation("D5");
			//Game 4 -------

			//GAME 5 --------
			Salvo salvo17= new Salvo(1,gamePlayer8);
			salvo17.addLocation("A1");
			salvo17.addLocation("A2");
			salvo17.addLocation("A3");

			Salvo salvo18= new Salvo(1,gamePlayer9);
			salvo18.addLocation("B5");
			salvo18.addLocation("B6");
			salvo18.addLocation("C7");

			Salvo salvo19= new Salvo(2,gamePlayer8);
			salvo19.addLocation("G6");
			salvo19.addLocation("G7");
			salvo19.addLocation("G8");

			Salvo salvo20= new Salvo(2,gamePlayer9);
			salvo20.addLocation("C6");
			salvo20.addLocation("D6");
			salvo20.addLocation("E6");

			Salvo salvo21= new Salvo(3,gamePlayer9);
			salvo21.addLocation("H1");
			salvo21.addLocation("H8");

			salvoRepository.save(salvo1);
			salvoRepository.save(salvo2);
			salvoRepository.save(salvo3);
			salvoRepository.save(salvo4);
			salvoRepository.save(salvo5);
			salvoRepository.save(salvo6);
			salvoRepository.save(salvo7);
			salvoRepository.save(salvo8);
			salvoRepository.save(salvo9);
			salvoRepository.save(salvo10);
			salvoRepository.save(salvo11);
			salvoRepository.save(salvo12);
			salvoRepository.save(salvo13);
			salvoRepository.save(salvo14);
			salvoRepository.save(salvo15);
			salvoRepository.save(salvo16);
			salvoRepository.save(salvo17);
			salvoRepository.save(salvo18);
			salvoRepository.save(salvo19);
			salvoRepository.save(salvo20);
			salvoRepository.save(salvo21);

			//Scores ----

			Score score1 = new Score(game1, player1, 1,game1.getCreationDate().plusMinutes(30));
			Score score2 = new Score(game1, player2, 0,game1.getCreationDate().plusMinutes(30));
			Score score3 = new Score(game2, player1, 0.5,game2.getCreationDate().plusMinutes(30));
			Score score4 = new Score(game2, player2, 0.5,game2.getCreationDate().plusMinutes(30));
			Score score5 = new Score(game3, player2, 1,game3.getCreationDate().plusMinutes(30));
			Score score6 = new Score(game3, player4, 0,game4.getCreationDate().plusMinutes(30));
			Score score7 = new Score(game4, player2, 0.5,game4.getCreationDate().plusMinutes(30));
			Score score8 = new Score(game4, player1, 0.5,game4.getCreationDate().plusMinutes(30));


			scoreRepository.save(score1);
			scoreRepository.save(score2);
			scoreRepository.save(score3);
			scoreRepository.save(score4);
			scoreRepository.save(score5);
			scoreRepository.save(score6);
			scoreRepository.save(score7);
			scoreRepository.save(score8);

		};
	}

}

package com.salvoproyect.salvo;

import com.salvoproyect.salvo.model.Game;
import com.salvoproyect.salvo.model.Player;
import com.salvoproyect.salvo.model.Ship;
import com.salvoproyect.salvo.repository.GamePlayerRepository;
import com.salvoproyect.salvo.repository.GameRepository;
import com.salvoproyect.salvo.repository.PlayerRepository;
import com.salvoproyect.salvo.repository.ShipRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(GameRepository repository, PlayerRepository playerRepository, GamePlayerRepository gamePlayerRepository,
									  ShipRepository shipRepository){
		return args -> {
			Player player1 = new Player("j.bauer@ctu.gov");
			Player player2 = new Player("c.obrian@ctu.gov");
			Player player3 = new Player("kim_bauer@gmail.com");
			Player player4 = new Player("t.almeida@ctu.gov");

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

			Ship ship1 = new Ship("Destructor");
			Ship ship2 = new Ship("Submarine");
			Ship ship3 = new Ship("Patrol Boat");
			Ship ship4 = new Ship("Destructor");

			ship1.addGamePlayer(gamePlayer0);
			ship2.addGamePlayer(gamePlayer0);

			ship1.addlocation("H2");
			ship1.addlocation("G2");
			ship1.addlocation("F2");
			ship2.addlocation("A9");
			ship2.addlocation("B9");
			ship2.addlocation("C9");


			shipRepository.save(ship1);
			shipRepository.save(ship2);
			/*shipRepository.save(ship3);
			shipRepository.save(ship4);*/

		};
	}

}

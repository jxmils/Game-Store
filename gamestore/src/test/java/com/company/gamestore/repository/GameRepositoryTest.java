package com.company.gamestore.repository;

import com.company.gamestore.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameRepositoryTest {

    @Autowired
    GameRepository gameRepo;

    @BeforeEach
    public void setUp() throws Exception{
        gameRepo.deleteAll();
    }

    @Test
    public void shouldAddGame(){
        // Arrange
        Game game = new Game();

        game.setTitle("The Elder Scrolls VI");
        game.setDescription("A single-player RPG set in The Elder Scrolls World");
        game.setEsrbRating("M");
        game.setStudio("Bethesda Game Studios");
        game.setQuantity(5);
        game.setPrice(new BigDecimal("59.99"));

        // Act
        game = gameRepo.save(game);

        // Assert
        Optional<Game> videoGame = gameRepo.findById(game.getGameId());

        assertEquals(videoGame.get(), game);
    }

    @Test
    public void shouldGetAllGames(){
        // Add a Game - in case it's empty
        Game game = new Game();
        game.setTitle("The Elder Scrolls VI");
        game.setDescription("A single-player RPG set in The Elder Scrolls World");
        game.setEsrbRating("M");
        game.setStudio("Bethesda Game Studios");
        game.setQuantity(5);
        game.setPrice(new BigDecimal("59.99"));

        game = gameRepo.save(game); // save to database

        // Add a second Game
        Game game2 = new Game();
        game2.setTitle("GTA VI");
        game2.setDescription("The newest addition to the GTA series brings you to the streets of Miami");
        game2.setEsrbRating("M");
        game2.setStudio("Rockstar North");
        game2.setQuantity(5);
        game2.setPrice(new BigDecimal("59.99"));

        game2 = gameRepo.save(game2); // save to database


        List<Game> gameList = gameRepo.findAll();

        assertEquals(gameList.size(),2);
    }

    @Test
    public void shouldGetGameById(){
        // Arrange
        Game game = new Game();
        game.setTitle("The Elder Scrolls VI");
        game.setDescription("A single-player RPG set in The Elder Scrolls World");
        game.setEsrbRating("M");
        game.setStudio("Bethesda Game Studios");
        game.setQuantity(5);
        game.setPrice(new BigDecimal("59.99"));

        game = gameRepo.save(game); // save the game

        // Act
        Optional<Game> gm = gameRepo.findById(game.getGameId());

        // Assert
        assertEquals(gm.get(), game);
    }

    @Test
    public void shouldUpdateGameRecord(){
        // Arrange - set up Game record
        Game game = new Game();
        game.setTitle("The Elder Scrolls VI");
        game.setDescription("A single-player RPG set in The Elder Scrolls World");
        game.setEsrbRating("M");
        game.setStudio("Bethesda Game Studios");
        game.setQuantity(5);
        game.setPrice(new BigDecimal("59.99"));

        game = gameRepo.save(game);

        // Act - update
        game.setPrice(new BigDecimal("39.99"));
        game.setQuantity(3);
        gameRepo.save(game);

        // Assert - comparison
        Optional<Game> gm = gameRepo.findById(game.getGameId());

        assertEquals(gm.get(), game);

    }

    @Test
    public void shouldDeleteGameRecord(){
        // Arrange
        Game game = new Game();
        game.setTitle("The Elder Scrolls VI");
        game.setDescription("A single-player RPG set in The Elder Scrolls World");
        game.setEsrbRating("M");
        game.setStudio("Bethesda Game Studios");
        game.setQuantity(5);
        game.setPrice(new BigDecimal("59.99"));

        game = gameRepo.save(game);

        // Act
        Optional<Game> gm = gameRepo.findById(game.getGameId());

        assertEquals(gm.get(), game); // check if actual game exists

        gameRepo.deleteById(game.getGameId()); // delete the game

        gm = gameRepo.findById(game.getGameId());

        assertFalse(gm.isPresent()); // must be false if item was successfully removed
    }

    @Test
    public void shouldReturnAllGamesBySameStudio() {
        // Arrange
        Game game = new Game();
        game.setTitle("The Elder Scrolls VI");
        game.setDescription("A single-player RPG set in The Elder Scrolls World");
        game.setEsrbRating("M");
        game.setStudio("Bethesda Game Studios");
        game.setQuantity(5);
        game.setPrice(new BigDecimal("59.99"));
        game = gameRepo.save(game);

        // Add a second game
        Game game2 = new Game();
        game2.setTitle("The Elder Scrolls V: Skyrim");
        game2.setDescription("A single-player RPG set in Tamriel's Skyrim");
        game2.setEsrbRating("M");
        game2.setStudio("Bethesda Game Studios");
        game2.setQuantity(5);
        game2.setPrice(new BigDecimal("29.99"));
        game = gameRepo.save(game2);

        // Add a third
        Game game3 = new Game();
        game3.setTitle("Minecraft");
        game3.setDescription("A puzzle-adventure game in which players mine pixelated landscapes to harvest stylized cube-like materials");
        game3.setEsrbRating("E");
        game3.setStudio("Mojang Studios");
        game3.setQuantity(5);
        game3.setPrice(new BigDecimal("19.99"));
        game3 = gameRepo.save(game3);

        // Act
        List<Game> games = gameRepo.findAllByStudio("Bethesda Game Studios");

        // Assert
        assertEquals(games.size(),2);

    }

    @Test
    public void shouldReturnAllGamesWithSameRating(){
        // Arrange - add a game
        Game game = new Game();
        game.setTitle("The Elder Scrolls VI");
        game.setDescription("A single-player RPG set in The Elder Scrolls World");
        game.setEsrbRating("M");
        game.setStudio("Bethesda Game Studios");
        game.setQuantity(5);
        game.setPrice(new BigDecimal("59.99"));
        game = gameRepo.save(game);

        // add another
        Game game2 = new Game();
        game2.setTitle("The Elder Scrolls V: Skyrim");
        game2.setDescription("A single-player RPG set in Tamriel's Skyrim");
        game2.setEsrbRating("M");
        game2.setStudio("Bethesda Game Studios");
        game2.setQuantity(5);
        game2.setPrice(new BigDecimal("29.99"));
        game2 = gameRepo.save(game2);

        // Add a third (with different rating)
        Game game3 = new Game();
        game3.setTitle("Minecraft");
        game3.setDescription("A puzzle-adventure game in which players mine pixelated landscapes to harvest stylized cube-like materials");
        game3.setEsrbRating("E");
        game3.setStudio("Mojang Studios");
        game3.setQuantity(5);
        game3.setPrice(new BigDecimal("19.99"));
        game3 = gameRepo.save(game3);

        // Act
        List<Game> games = gameRepo.findAllByEsrbRating("M"); // filter for all games with M rating

        // Assert
        assertEquals(games.size(), 2); // Size of the list must be 2
    }

    @Test
    public void shouldReturnAGameByTitle(){
        // Arrange
        Game game3 = new Game();
        game3.setTitle("Minecraft");
        game3.setDescription("A puzzle-adventure game in which players mine pixelated landscapes to harvest stylized cube-like materials");
        game3.setEsrbRating("E");
        game3.setStudio("Mojang Studios");
        game3.setQuantity(5);
        game3.setPrice(new BigDecimal("19.99"));
        game3 = gameRepo.save(game3);

        // Act
        Optional<Game> gm = gameRepo.findGameByTitle("Minecraft");

        // Assert
        assertEquals(gm.get(), game3);
    }
}
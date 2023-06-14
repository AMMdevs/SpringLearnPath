package ammanriq.spring_learn_path.application.services;

import ammanriq.spring_learn_path.application.dtos.CreateGameCommand;
import ammanriq.spring_learn_path.application.exceptions.GameNotFoundException;
import ammanriq.spring_learn_path.application.exceptions.TeamNotFoundException;
import ammanriq.spring_learn_path.data.entities.Game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameService {

    void createGame(CreateGameCommand gameDto) throws TeamNotFoundException;

    List<Game> getGames();

    Optional<Game> getGameById(UUID id);

    List<Game> getGamesByTeamId(UUID id) throws TeamNotFoundException;

    void deleteGame(UUID id) throws GameNotFoundException;

    void startGame(UUID id) throws Exception;

    void scoreHomeTeam(UUID id) throws Exception;

    void scoreAwayTeam(UUID id) throws Exception;

    String getScoreGame(UUID id) throws Exception;

    void endGame(UUID id) throws Exception;

    String resultsHistory(UUID id) throws Exception;

}

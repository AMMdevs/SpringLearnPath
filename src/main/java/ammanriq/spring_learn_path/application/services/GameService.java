package ammanriq.spring_learn_path.application.services;

import ammanriq.spring_learn_path.application.dtos.CreateGameCommand;
import ammanriq.spring_learn_path.application.exceptions.*;
import ammanriq.spring_learn_path.data.ApplicationDatabase;
import ammanriq.spring_learn_path.data.entities.Game;
import ammanriq.spring_learn_path.data.entities.Team;
import ammanriq.spring_learn_path.data.entities.TeamGame;
import ammanriq.spring_learn_path.data.enums.GameStatus;
import ammanriq.spring_learn_path.data.enums.Result;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class GameService {
    private final ApplicationDatabase database;

    public GameService(ApplicationDatabase database) {
        this.database = database;
    }

    public void createGame(CreateGameCommand gameDto) throws TeamNotFoundException {

        Team homeTeam = database.teamsTable.get(gameDto.homeTeamId());
        if (homeTeam == null) {
            throw new TeamNotFoundException(gameDto.homeTeamId());
        }

        Team awayTeam = database.teamsTable.get(gameDto.awayTeamId());
        if (awayTeam == null) {
            throw new TeamNotFoundException(gameDto.awayTeamId());
        }

        Game game = new Game(homeTeam, awayTeam);

        database.gamesTable.put(game.getId(), game);
    }

    public List<Game> getGames() {
        return database.gamesTable.values().stream().toList();
    }

    public Game getGameById(UUID id) {
        return database.gamesTable.get(id);
    }

    public List<Game> getGamesByTeamId(UUID id) throws TeamNotFoundException {

        if (database.gamesTable.get(id) == null) {
            throw new TeamNotFoundException(id);
        }

        List<TeamGame> teamGames = database.teamGamesTable.values()
                .stream()
                .filter(teamGame -> teamGame.getTeamId() == id)
                .toList();

        if (teamGames.isEmpty()) {
            return Collections.emptyList();
        }

        return teamGames.stream()
                .map(teamGame -> database.gamesTable.get(id))
                .toList();
    }

    public void deleteGame(UUID id) throws GameNotFoundException {
        if (id == null) {
            throw new GameNotFoundException(id);
        }

        database.gamesTable.remove(id);
    }


    public void startGame(UUID id) throws Exception {
        Game game = database.gamesTable.get(id);

        if (game == null) {
            throw new GameNotFoundException(id);
        }

        if (game.getStatus() != GameStatus.SCHEDULED) {
            throw new GameNotScheduledException(id);
        } else if (game.getStatus() != GameStatus.FINISHED) {
            throw new GameFinishedException(id);
        }

        game.setStatus(GameStatus.STARTED);
        database.gamesTable.put(game.getId(), game);
    }

    public void scoreHomeTeam(UUID id) throws Exception {
        Game game = database.gamesTable.get(id);

        if (game == null) {
            throw new GameNotFoundException(id);
        }

        if (game.getStatus() != GameStatus.STARTED) {
            throw new GameNotStartedException(game.getId());
        }

        game.setHomeScore(game.getHomeScore() + 1);

        Team home = game.getHome();

        int totalTeamGoals = home.getTotalTeamGoals();
        home.setTotalTeamGoals(totalTeamGoals + 1);

        database.gamesTable.put(game.getId(), game);
        database.teamsTable.put(home.getId(), home);
    }

    public void scoreAwayTeam(UUID id) throws Exception {
        Game game = database.gamesTable.get(id);

        if (game == null) {
            throw new GameNotFoundException(id);
        }

        if (game.getStatus() != GameStatus.STARTED) {
            throw new GameNotStartedException(id);
        }

        game.setAwayScore(game.getHomeScore() + 1);

        Team away = game.getAway();

        int totalTeamGoals = away.getTotalTeamGoals();
        away.setTotalTeamGoals(totalTeamGoals + 1);

        database.gamesTable.put(game.getId(), game);
        database.teamsTable.put(away.getId(), away);
    }

    public String getScoreGame(UUID id) throws Exception {
        Game game = database.gamesTable.get(id);

        if (game == null) {
            throw new GameNotFoundException(id);
        }

        if (game.getStatus() != GameStatus.STARTED) {
            throw new GameNotStartedException(id);
        }

        return game.getHome().getName() + ": " + game.getHomeScore() + " - " + game.getAway().getName() + ": " + game.getAwayScore();
    }

    public void endGame(UUID id) throws Exception {
        Game game = database.gamesTable.get(id);

        if (game == null) {
            throw new GameNotFoundException(id);
        }

        if (game.getStatus() != GameStatus.STARTED) {
            throw new GameNotStartedException(id);
        }

        game.setStatus(GameStatus.FINISHED);

        Team home = game.getHome();
        int homePoints = home.getPoints();
        Team away = game.getAway();
        int awayPoints = away.getPoints();

        if (isHomeWinner(id)) {
            home.setResultsTable(game.getId(), Result.WIN);
            home.setPoints(homePoints + Result.WIN.getPoint());
            away.setResultsTable(game.getId(), Result.LOSS);

        } else if (isAwayWinner(id)) {

            home.setResultsTable(game.getId(), Result.LOSS);
            away.setResultsTable(game.getId(), Result.WIN);
            away.setPoints(awayPoints + Result.WIN.getPoint());

        } else {

            home.setResultsTable(game.getId(), Result.DRAW);
            home.setPoints(homePoints + Result.DRAW.getPoint());
            away.setResultsTable(game.getId(), Result.DRAW);
            away.setPoints(awayPoints + Result.DRAW.getPoint());
        }

        database.gamesTable.put(game.getId(), game);
        database.teamsTable.put(home.getId(), home);
        database.teamsTable.put(away.getId(), home);
    }

    private boolean isHomeWinner(UUID id) {
        Game game = database.gamesTable.get(id);

        return game.getHomeScore() > game.getAwayScore();
    }

    private boolean isAwayWinner(UUID id) {
        Game game = database.gamesTable.get(id);

        return game.getHomeScore() < game.getAwayScore();
    }

    public String resultsHistory(UUID id) throws Exception {
        Game game = database.gamesTable.get(id);

        if (game == null) {
            throw new GameNotFoundException(id);
        }

        if (game.getStatus() != GameStatus.FINISHED) {
            throw new GameNotFinishedException(game.getId());
        }

        long winHomeCount = game.getHome().getResultsTable().entrySet().stream()
                .filter((entry) -> entry.getValue() == Result.WIN)
                .count();
        long lossHomeCount = game.getHome().getResultsTable().entrySet().stream()
                .filter((entry) -> entry.getValue() == Result.LOSS)
                .count();
        long drawHomeCount = game.getHome().getResultsTable().entrySet().stream()
                .filter((entry) -> entry.getValue() == Result.DRAW)
                .count();


        long winAwayCount = game.getAway().getResultsTable().entrySet().stream()
                .filter((entry) -> entry.getValue() == Result.WIN)
                .count();
        long lossAwayCount = game.getAway().getResultsTable().entrySet().stream()
                .filter((entry) -> entry.getValue() == Result.LOSS)
                .count();
        long drawAwayCount = game.getAway().getResultsTable().entrySet().stream()
                .filter((entry) -> entry.getValue() == Result.DRAW)
                .count();

        return game.getHome().getName() + "\n\t Won:" + winHomeCount + "\n\t Lost:" + lossHomeCount + "\n\t Tied:" + drawHomeCount + "\n\t Total points: " + game.getHome().getPoints() + "\n" +
                game.getAway().getName() + "\n\t Won:" + winAwayCount + "\n\t Lost:" + lossAwayCount + "\n\t Tied:" + drawAwayCount + "\n\t Total points: " + game.getAway().getPoints();
    }

}

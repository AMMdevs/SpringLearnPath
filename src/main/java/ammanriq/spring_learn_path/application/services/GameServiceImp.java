package ammanriq.spring_learn_path.application.services;

import ammanriq.spring_learn_path.application.dtos.CreateGameCommand;
import ammanriq.spring_learn_path.application.exceptions.*;
import ammanriq.spring_learn_path.data.entities.Game;
import ammanriq.spring_learn_path.data.entities.Team;
import ammanriq.spring_learn_path.data.enums.GameStatus;
import ammanriq.spring_learn_path.data.enums.Result;
import ammanriq.spring_learn_path.data.repositories.GameRepository;
import ammanriq.spring_learn_path.data.repositories.TeamRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GameServiceImp implements GameService {

    private final GameRepository gameRepository;
    private final TeamService teamService;
    private final TeamRepository teamRepository;

    public GameServiceImp(GameRepository gameRepository, TeamService teamService, TeamRepository teamRepository) {
        this.gameRepository = gameRepository;
        this.teamService = teamService;
        this.teamRepository = teamRepository;
    }

    @Override
    public void createGame(CreateGameCommand gameDto) throws TeamNotFoundException {
        Team homeTeam = teamService.getTeamById(gameDto.homeTeamId());
        Team awayTeam = teamService.getTeamById(gameDto.awayTeamId());

        Game game = new Game(homeTeam, awayTeam);

        gameRepository.save(game);
    }

    @Override
    public List<Game> getGames() {
        return gameRepository.getAll();
    }

    @Override
    public Optional<Game> getGameById(UUID id) {
        return gameRepository.getById(id);
    }

    @Override
    public List<Game> getGamesByTeamId(UUID id) throws TeamNotFoundException {
        return gameRepository.getByTeamId(id);
    }

    @Override
    public void deleteGame(UUID id) throws GameNotFoundException {
        if (id == null) {
            throw new GameNotFoundException(id);
        }

        gameRepository.deleteById(id);
    }


    @Override
    public void startGame(UUID id) throws Exception {
        Optional<Game> optionalGame = gameRepository.getById(id);

        if (optionalGame.isEmpty()) {
            throw new GameNotFoundException(id);
        }

        Game game = optionalGame.get();

        if (game.getStatus() != GameStatus.SCHEDULED) {
            throw new GameNotScheduledException(id);
        } else if (game.getStatus() != GameStatus.FINISHED) {
            throw new GameFinishedException(id);
        }

        game.setStatus(GameStatus.STARTED);
        gameRepository.save(game);
    }

    @Override
    public void scoreHomeTeam(UUID id) throws Exception {
        Optional<Game> optionalGame = gameRepository.getById(id);

        if (optionalGame.isEmpty()) {
            throw new GameNotFoundException(id);
        }

        Game game = optionalGame.get();

        if (game.getStatus() != GameStatus.STARTED) {
            throw new GameNotStartedException(game.getId());
        }

        game.setHomeScore(game.getHomeScore() + 1);

        Team home = game.getHome();

        int totalTeamGoals = home.getTotalTeamGoals();
        home.setTotalTeamGoals(totalTeamGoals + 1);

        gameRepository.save(game);
        teamRepository.save(home);
    }

    @Override
    public void scoreAwayTeam(UUID id) throws Exception {
        Optional<Game> optionalGame = gameRepository.getById(id);

        if (optionalGame.isEmpty()) {
            throw new GameNotFoundException(id);
        }

        Game game = optionalGame.get();

        if (game.getStatus() != GameStatus.STARTED) {
            throw new GameNotStartedException(id);
        }

        game.setAwayScore(game.getHomeScore() + 1);

        Team away = game.getAway();

        int totalTeamGoals = away.getTotalTeamGoals();
        away.setTotalTeamGoals(totalTeamGoals + 1);

        gameRepository.save(game);
        teamRepository.save(away);
    }

    @Override
    public String getScoreGame(UUID id) throws Exception {
        Optional<Game> optionalGame = gameRepository.getById(id);

        if (optionalGame.isEmpty()) {
            throw new GameNotFoundException(id);
        }

        Game game = optionalGame.get();

        if (game.getStatus() != GameStatus.STARTED) {
            throw new GameNotStartedException(id);
        }

        return game.getHome().getName() + ": " + game.getHomeScore() + " - " + game.getAway().getName() + ": " + game.getAwayScore();
    }

    @Override
    public void endGame(UUID id) throws Exception {
        Optional<Game> optionalGame = gameRepository.getById(id);

        if (optionalGame.isEmpty()) {
            throw new GameNotFoundException(id);
        }

        Game game = optionalGame.get();

        if (game.getStatus() != GameStatus.STARTED) {
            throw new GameNotStartedException(id);
        }

        game.setStatus(GameStatus.FINISHED);

        /*teamService.addNewResult(home, Result.WIN);
        teamService.addNewResult(away, Result.LOSS);*/

        Team home = game.getHome();
        int homePoints = home.getPoints();
        Team away = game.getAway();
        int awayPoints = away.getPoints();

        if (isHomeWinner(game)) {
            home.setResultsTable(game.getId(), Result.WIN);
            home.setPoints(homePoints + Result.WIN.getPoint());
            away.setResultsTable(game.getId(), Result.LOSS);

        } else if (isAwayWinner(game)) {

            home.setResultsTable(game.getId(), Result.LOSS);
            away.setResultsTable(game.getId(), Result.WIN);
            away.setPoints(awayPoints + Result.WIN.getPoint());

        } else {

            home.setResultsTable(game.getId(), Result.DRAW);
            home.setPoints(homePoints + Result.DRAW.getPoint());
            away.setResultsTable(game.getId(), Result.DRAW);
            away.setPoints(awayPoints + Result.DRAW.getPoint());
        }

        gameRepository.save(game);
        teamRepository.save(home);
        teamRepository.save(away);
    }


    private boolean isHomeWinner(Game game) {
        return game.getHomeScore() > game.getAwayScore();
    }


    private boolean isAwayWinner(Game game) {
        return game.getHomeScore() < game.getAwayScore();
    }

    @Override
    public String resultsHistory(UUID id) throws Exception {
        Optional<Game> optionalGame = gameRepository.getById(id);

        if (optionalGame.isEmpty()) {
            throw new GameNotFoundException(id);
        }

        Game game = optionalGame.get();

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

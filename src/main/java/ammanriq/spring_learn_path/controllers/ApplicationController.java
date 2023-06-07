package ammanriq.spring_learn_path.controllers;

import ammanriq.spring_learn_path.entities.Game;
import ammanriq.spring_learn_path.entities.Player;
import ammanriq.spring_learn_path.entities.Team;
import ammanriq.spring_learn_path.entities.TeamGame;
import ammanriq.spring_learn_path.enums.GameStatus;
import ammanriq.spring_learn_path.enums.Position;
import ammanriq.spring_learn_path.enums.Result;
import ammanriq.spring_learn_path.exceptions.GameNotFinishedException;
import ammanriq.spring_learn_path.exceptions.GameNotStartedException;
import ammanriq.spring_learn_path.exceptions.TeamNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
public class ApplicationController {
    private final HashMap<UUID, Game> gamesTable = new HashMap<>();
    private final HashMap<UUID, Team> teamsTable = new HashMap<>();
    private final HashMap<UUID, Player> playersTable = new HashMap<>();
    private final HashMap<UUID, TeamGame> teamGamesTable = new HashMap<>();


    @PostConstruct
    public void initializeTables() {
        Team valenciaCf = new Team("Valencia CF", "Valencia");
        teamsTable.put(valenciaCf.getId(), valenciaCf);
        Team madridFc = new Team("Real Madrid FC", "Madrid");
        teamsTable.put(madridFc.getId(), madridFc);

        Player joseG = new Player("José Gayà", 14, Position.CENTRE_BACK, valenciaCf);
        playersTable.put(joseG.getId(), joseG);

        Player karimB = new Player("Karim Benzema", 9, Position.CENTRE_FORWARD, madridFc);
        playersTable.put(joseG.getId(), joseG);

        Game game1 = new Game(valenciaCf, madridFc);
        gamesTable.put(game1.getId(), game1);

        Game game2 = new Game(madridFc, valenciaCf);
        gamesTable.put(game2.getId(), game2);
    }

    /* GAMES */

    @GetMapping("games")
    public List<Game> getGames() {
        return gamesTable.values().stream().toList();
    }

    @GetMapping("games/{id}")
    public Game getGameById(@PathVariable UUID id) {
        return gamesTable.get(id);
    }

    @PostMapping("games")
    public void createGame(UUID homeId, UUID awayId) throws TeamNotFoundException {

        Team homeTeam = teamsTable.get(homeId);
        if (homeTeam == null) {
            throw new TeamNotFoundException(homeId);
        }

        Team awayTeam = teamsTable.get(awayId);
        if (awayTeam == null) {
            throw new TeamNotFoundException(awayId);
        }

        Game game = new Game(homeTeam, awayTeam);
        gamesTable.put(game.getId(), game);

        TeamGame homeTeamGame = new TeamGame(homeId, game.getId());
        TeamGame awayTeamGame = new TeamGame(awayId, game.getId());

        teamGamesTable.put(homeTeamGame.getId(), homeTeamGame);
        teamGamesTable.put(awayTeamGame.getId(), awayTeamGame);
    }

    @GetMapping
    public List<Game> getGamesByTeamId(@PathVariable UUID teamId) throws TeamNotFoundException {
        if (teamId == null) {
            throw new IllegalArgumentException("Team id cannot be null");
        }
        if (gamesTable.get(teamId) == null) {
            throw new TeamNotFoundException(teamId);
        }

        List<TeamGame> teamGames = teamGamesTable.values()
                .stream()
                .filter(teamGame -> teamGame.getTeamId() == teamId)
                .toList();

        if (teamGames.isEmpty()) {
            return Collections.emptyList();
        }

        return teamGames.stream()
                .map(teamGame -> gamesTable.get(teamGame.getGameId()))
                .toList();
    }

    @DeleteMapping("games/{id}")
    public void deleteGame(@PathVariable UUID id) {
        gamesTable.remove(id);
    }

    /* TEAMS */

    @GetMapping("teams")
    public List<Team> getTeams() {
        return teamsTable.values().stream().toList();
    }

    @GetMapping("teams/{id}")
    public Team getTeamById(@PathVariable UUID id) {
        return teamsTable.get(id);
    }

    @PostMapping("teams")
    public void createTeam(@RequestBody Team team) {
        teamsTable.put(team.getId(), team);
    }

    @DeleteMapping("teams/{id}")
    public void deleteTeam(@PathVariable UUID id) {
        teamsTable.remove(id);
    }

    /* PLAYERS */

    @GetMapping("players")
    public List<Player> getPlayers() {
        return playersTable.values().stream().toList();
    }

    @GetMapping("players/{id}")
    public Player getPlayerById(@PathVariable UUID id) {
        return playersTable.get(id);
    }

    @PostMapping("players")
    public void createPlayer(@RequestBody Player player) {
        playersTable.put(player.getId(), player);
    }

    @DeleteMapping("players/{id}")
    public void deletePlayer(@PathVariable UUID id) {
        playersTable.remove(id);
    }

    /* GAME FUNCTIONS */

    @PostMapping("games/{id}/start")
    public void startGame(@PathVariable UUID id) {
        Game game = getGameById(id);
        game.setStatus(GameStatus.STARTED);
    }

    @PutMapping("games/{id}/homegoal")
    public void goalHomeTeam(@PathVariable UUID id) throws GameNotStartedException {
        Game game = getGameById(id);

        if (game.getStatus() != GameStatus.STARTED) {
            throw new GameNotStartedException(game.getId());
        }
        game.setHomeScore(game.getHomeScore() + 1);

        Team home = game.getHome();

        int totalTeamGoals = home.getTotalTeamGoals();
        home.setTotalTeamGoals(totalTeamGoals + 1);

        //game.setHomeScore();

        gamesTable.put(game.getId(), game);
        teamsTable.put(home.getId(), home);
    }

    @PutMapping("games/{id}/awaygoal")
    public void goalAwayTeam(@PathVariable UUID id) throws GameNotStartedException {
        Game game = getGameById(id);

        if (game.getStatus() != GameStatus.STARTED) {
            throw new GameNotStartedException(game.getId());
        }
        game.setAwayScore(game.getAwayScore() + 1);

        Team away = game.getAway();

        int totalTeamGoals = away.getTotalTeamGoals();
        away.setTotalTeamGoals(totalTeamGoals + 1);

        gamesTable.put(game.getId(), game);
        teamsTable.put(away.getId(), away);
    }

    @GetMapping("games/{id}/score")
    public String getGameScore(@PathVariable UUID id) {
        Game game = getGameById(id);
        return game.getHome().getName() + ": " + game.getHomeScore() + " - " + game.getAway().getName() + ": " + +game.getAwayScore();
    }

    @PutMapping("games/{id}/end")
    public void endGame(@PathVariable UUID id) throws GameNotStartedException {
        Game game = getGameById(id);
        if (game.getStatus() != GameStatus.STARTED) {
            throw new GameNotStartedException(game.getId());
        }

        game.setStatus(GameStatus.FINISHED);

        Team home = game.getHome();
        int homePoints = home.getPoints();
        Team away = game.getAway();
        int awayPoints = away.getPoints();

        if (game.getHomeScore() > game.getAwayScore()) {
            home.setResultsTable(game.getId(), Result.WIN);
            home.setPoints(homePoints + Result.WIN.getPoint());
            away.setResultsTable(game.getId(), Result.LOSS);

        } else if (game.getHomeScore() < game.getAwayScore()) {

            home.setResultsTable(game.getId(), Result.LOSS);
            away.setResultsTable(game.getId(), Result.WIN);
            away.setPoints(awayPoints + Result.WIN.getPoint());

        } else if (game.getHomeScore() == game.getAwayScore()) {

            home.setResultsTable(game.getId(), Result.DRAW);
            home.setPoints(homePoints + Result.DRAW.getPoint());
            away.setResultsTable(game.getId(), Result.DRAW);
            away.setPoints(awayPoints + Result.DRAW.getPoint());
        }

        gamesTable.put(game.getId(), game);
        teamsTable.put(home.getId(), home);
        teamsTable.put(away.getId(), home);
    }

    @GetMapping("games/{id}/end")
    public String resultsHistory(@PathVariable UUID id) throws GameNotFinishedException {
        Game game = getGameById(id);

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




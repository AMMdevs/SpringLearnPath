package ammanriq.spring_learn_path.data.repositories;

import ammanriq.spring_learn_path.data.ApplicationDatabase;
import ammanriq.spring_learn_path.data.entities.Game;
import ammanriq.spring_learn_path.data.entities.TeamGame;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GameInMemoryRepository implements GameRepository {

    private final ApplicationDatabase database;

    public GameInMemoryRepository(ApplicationDatabase database) {
        this.database = database;
    }

    @Override
    public void save(Game game) {
        database.gamesTable.put(game.getId(), game);
    }

    @Override
    public Optional<Game> getById(UUID gameId) {
        Game game = database.gamesTable.get(gameId);
        return Optional.of(game);
    }

    @Override
    public List<Game> getAll() {
        return database.gamesTable.values().stream().toList();
    }

    @Override
    public List<Game> getByTeamId(UUID id) {
        // 1. conseguir todas las relaciones team-game en la tabla intermedia donde teamId == id
        List<TeamGame> teamGames = database.teamGamesTable.values()
                .stream()
                .filter(teamGame -> teamGame.getTeamId() == id)
                .toList();

        if (teamGames.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. para todas las relaciones obtenidas, obtener las lista de gameIds

        List<UUID> gameIds = teamGames.stream()
                .map(TeamGame::getGameId)
                .toList();


        // 3. extraer todos los games donde game.id está contenido en la lista gameIds

        return database.gamesTable.values()
                .stream()
                .filter(game -> gameIds.contains(game.getId()))
                .toList();
    }

    @Override
    public void deleteById(UUID gameId) {
        database.gamesTable.remove(gameId);
    }
}

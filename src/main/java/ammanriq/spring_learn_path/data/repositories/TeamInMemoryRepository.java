package ammanriq.spring_learn_path.data.repositories;

import ammanriq.spring_learn_path.data.ApplicationDatabase;
import ammanriq.spring_learn_path.data.entities.Team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TeamInMemoryRepository implements TeamRepository {

    private final ApplicationDatabase database;

    public TeamInMemoryRepository(ApplicationDatabase database) {
        this.database = database;
    }

    @Override
    public void save(Team team) {
        database.teamsTable.put(team.getId(), team);
    }

    @Override
    public Optional<Team> getById(UUID teamId) {
        Team team = database.teamsTable.get(teamId);

        return Optional.of(team);
    }

    @Override
    public List<Team> getAll() {
        return database.teamsTable.values().stream().toList();
    }

    @Override
    public void deleteById(UUID teamId) {
        database.teamsTable.remove(teamId);
    }
}

package ammanriq.spring_learn_path.data.repositories;

import ammanriq.spring_learn_path.data.entities.Team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeamRepository {

    void save(Team team);

    Optional<Team> getById(UUID teamId);

    List<Team> getAll();

    void deleteById(UUID teamId);
}

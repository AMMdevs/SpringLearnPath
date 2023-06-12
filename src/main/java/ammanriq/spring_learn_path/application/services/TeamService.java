package ammanriq.spring_learn_path.application.services;

import ammanriq.spring_learn_path.application.dtos.CreateTeamCommand;
import ammanriq.spring_learn_path.application.exceptions.TeamNotFoundException;
import ammanriq.spring_learn_path.data.ApplicationDatabase;
import ammanriq.spring_learn_path.data.entities.Team;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TeamService {
    private final ApplicationDatabase database;

    public TeamService(ApplicationDatabase database) {
        this.database = database;
    }

    public List<Team> getTeams() {
        return database.teamsTable.values().stream().toList();
    }

    public Team getTeamById(UUID id) {
        return database.teamsTable.get(id);
    }

    public void createTeam(CreateTeamCommand teamDto) throws IllegalArgumentException {
        if (teamDto.name() == null || teamDto.name().isBlank() || teamDto.city() == null || teamDto.city().isBlank()) {
            throw new IllegalArgumentException();
        }

        Team team = new Team(teamDto.name(), teamDto.city());

        database.teamsTable.put(team.getId(), team);
    }

    public void deleteTeam(UUID id) throws TeamNotFoundException {
        if (id == null) {
            throw new TeamNotFoundException(id);
        }

        database.teamsTable.remove(id);
    }

}

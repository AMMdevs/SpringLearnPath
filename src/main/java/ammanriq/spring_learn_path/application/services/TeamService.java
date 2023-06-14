package ammanriq.spring_learn_path.application.services;

import ammanriq.spring_learn_path.application.dtos.CreateTeamCommand;
import ammanriq.spring_learn_path.application.exceptions.TeamNotFoundException;
import ammanriq.spring_learn_path.data.entities.Team;

import java.util.List;
import java.util.UUID;

public interface TeamService {

    List<Team> getTeams();

    Team getTeamById(UUID id) throws TeamNotFoundException;

    void createTeam(CreateTeamCommand teamDto);

    void deleteTeam(UUID id) throws TeamNotFoundException;

}

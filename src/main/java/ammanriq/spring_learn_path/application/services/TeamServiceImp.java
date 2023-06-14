package ammanriq.spring_learn_path.application.services;

import ammanriq.spring_learn_path.application.dtos.CreateTeamCommand;
import ammanriq.spring_learn_path.application.exceptions.TeamNotFoundException;
import ammanriq.spring_learn_path.data.entities.Team;
import ammanriq.spring_learn_path.data.repositories.TeamRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class TeamServiceImp implements TeamService {
    private final TeamRepository teamRepository;

    public TeamServiceImp(TeamRepository teamRepository) {

        this.teamRepository = teamRepository;
    }

    @Override
    public List<Team> getTeams() {
        return teamRepository.getAll();
    }

    @Override
    public Team getTeamById(UUID id) throws TeamNotFoundException {
        Optional<Team> optionalTeam = teamRepository.getById(id);
        if (optionalTeam.isEmpty()) {
            throw new TeamNotFoundException(id);
        }

        return optionalTeam.get();
    }

    @Override
    public void createTeam(CreateTeamCommand teamDto) throws IllegalArgumentException {
        if (teamDto.name() == null || teamDto.name().isBlank() || teamDto.city() == null || teamDto.city().isBlank()) {
            throw new IllegalArgumentException();
        }

        Team team = new Team(teamDto.name(), teamDto.city());

        teamRepository.save(team);
    }

    @Override
    public void deleteTeam(UUID id) throws TeamNotFoundException {
        if (id == null) {
            throw new TeamNotFoundException(id);
        }

        teamRepository.deleteById(id);
    }

}

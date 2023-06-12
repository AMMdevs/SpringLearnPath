package ammanriq.spring_learn_path.api.controllers;

import ammanriq.spring_learn_path.application.dtos.CreateTeamCommand;
import ammanriq.spring_learn_path.application.services.TeamService;
import ammanriq.spring_learn_path.data.entities.Game;
import ammanriq.spring_learn_path.data.entities.Team;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "teams")
public class TeamsController {
    private final TeamService teamService;

    public TeamsController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<Team> getTeams() {
        try {
            teamService.getTeams();

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Team> getTeamById(UUID id) {
        try {
            teamService.getTeamById(id);

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody CreateTeamCommand request) {
        try {
            teamService.createTeam(request);

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable UUID id) {
        try {
            teamService.deleteTeam(id);

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

}

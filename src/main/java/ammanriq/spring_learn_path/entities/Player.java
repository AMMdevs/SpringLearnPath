package ammanriq.spring_learn_path.entities;

import ammanriq.spring_learn_path.enums.Position;

import java.util.UUID;

public class Player {
    private final UUID id;
    private String name;
    private int number;
    private Position position;
    private Team team;

    public Player(String name, int number, Position position, Team team) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.number = number;
        this.position = position;
        this.team = team;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}

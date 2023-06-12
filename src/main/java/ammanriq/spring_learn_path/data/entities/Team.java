package ammanriq.spring_learn_path.data.entities;


import ammanriq.spring_learn_path.data.enums.Result;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Team {
    private final UUID id;
    private String name;
    private String city;
    private int totalTeamGoals;

    private int points;
    //private Result result;
    private HashMap<UUID, Result> resultsTable;

    public Team(String name, String city) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.city = city;
        this.totalTeamGoals = 0;
        this.points = 0;
        this.resultsTable = new HashMap<>();
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void addPlayer(Set<Player> players, Player player) {
        players.add(player);
    }

    public int getTotalTeamGoals() {
        return totalTeamGoals;
    }

    public void setTotalTeamGoals(int totalTeamGoals) {
        this.totalTeamGoals = totalTeamGoals;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public HashMap<UUID, Result> getResultsTable() {
        return resultsTable;
    }

    //public void setResultsTable(HashMap<UUID, Enum> resultsTable) {
    //    this.resultsTable = resultsTable;
    //}
    public void setResultsTable(UUID id, Result result) {
        resultsTable.put(id, result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name) && Objects.equals(city, team.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city);
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }


}



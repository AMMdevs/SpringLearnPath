package ammanriq.spring_learn_path.data.enums;

public enum Position {
    GOALKEEPER("Goalkeeeper"),
    CENTRE_BACK("Centre back"),
    SWEEPER("Sweeper"),
    FULL_BACK("Full back"),
    WING_BACK("Wing back"),
    CENTRAL_MIDFIELDER("Centre midfielder"),
    ATTACKING_MIDFIELDER("Attacking midfielder"),
    WIDE_MIDFIELDER("Wider midfielder"),
    SECOND_STRIKER("Second striker"),
    CENTRE_FORWARD("Centre forward"),
    WINGER("Winger");

    private final String position;

    Position(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}


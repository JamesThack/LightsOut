package APIObjects;

public class Driver {

    private String name;
    private int number;
    private Team team;
    private Tyre tyre;

    public Driver(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public Tyre getTyre() {
        return tyre;
    }

    public int getNumber() {
        return number;
    }
}

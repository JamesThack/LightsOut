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
    
    public String getInitials() {
    	return (name.split(" ")[0].toCharArray()[0] + "" + name.split(" ")[1].toCharArray()[0]);
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

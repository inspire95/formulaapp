package Formula1.modele;

/**
 *
 * @author Dawid
 */
public class Driver {

    private String name;
    private int id;
    private String team;

    public Driver() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Driver{" + "name=" + name + ", id=" + id + ", team=" + team + '}';
    }

}

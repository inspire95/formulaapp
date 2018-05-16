package Formula1.modele;

/**
 *
 * @author Dawid
 */
public class Race {

    private int id;
    private String name;

    public Race() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Race{" + "id=" + id + ", name=" + name + '}';
    }



}

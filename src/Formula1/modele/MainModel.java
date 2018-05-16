package Formula1.modele;

import java.util.ArrayList;
import java.util.List;

public class MainModel {

    public List<Driver> drvierList;
    public List<Team> teamList;
    public List<Race> raceList;
    public List<Formula1> formula1List;

    public MainModel() {
        this.drvierList = new ArrayList<>();
        this.teamList = new ArrayList<>();
        this.raceList = new ArrayList<>();
        this.formula1List = new ArrayList<>();
    }

    public void putDriver(Driver d) {
        this.drvierList.add(d);
    }

    public void putTeam(Team t) {
        this.teamList.add(t);
    }

    public void putRace(Race r) {
        this.raceList.add(r);
    }

    public void putFormaula1(Formula1 f) {
        this.formula1List.add(f);
    }

    public List<Driver> getDrvierList() {
        return drvierList;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public List<Race> getRaceList() {
        return raceList;
    }

    public List<Formula1> getFormula1List() {
        return formula1List;
    }

}

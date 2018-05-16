package Formula1.modele;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dawid
 */
public class Formula1 {

    private String year;
    private String race;
    private String winner;
    private List<String> particitians;

    public Formula1() {
        particitians = new ArrayList<>();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public List<String> getParticitians() {
        return particitians;
    }

    public void setParticitians(String particitians) {
        boolean isFirst = true;
        for (String string : particitians.split(",")) {
            if(!isFirst){
                string = " " + string;
            } else {
              isFirst = false;  
            }
            System.out.println("setpartp : "+string);
            this.particitians.add(string);
        }
    }

    public void addParticipant(String s){
        particitians.add(s);
    }
    
    @Override
    public String toString() {
        return "RaceF1{" + "rok=" + year + ", race=" + race + ", winner=" + winner + ", particitians=" + particitians.toString() + '}';
    }

}

package Formula1;

import Formula1.modele.*;
import javafx.stage.FileChooser;

import java.io.*;

public class outSourceData {

    public static MainModel loadDataFormOutSource() {
        MainModel model = new MainModel();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT(*.txt)", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try (FileReader file = new FileReader(selectedFile)) {
                BufferedReader bufor = new BufferedReader(file);
                String linia;

                while ((linia = bufor.readLine()) != null) {
                    String[] pola = linia.split("#");
                    if (pola[0].equals("Driver")) {
                        Driver driver = new Driver();
                        driver.setName(pola[1]);
                        driver.setId(Integer.parseInt(pola[2]));
                        driver.setTeam(pola[3]);
                        model.putDriver(driver);
                    }
                    if (pola[0].equals("Race")) {
                        Race race = new Race();
                        race.setName(pola[1]);
                        race.setId(Integer.parseInt(pola[2]));
                        model.putRace(race);
                    }
                    if (pola[0].equals("Team")) {
                        Team team = new Team();
                        team.setName(pola[1]);
                        team.setId(Integer.parseInt(pola[2]));
                        model.putTeam(team);
                    }
                    if (pola[0].equals("Formula1")) {
                        Formula1 formula1 = new Formula1();
                        formula1.setYear(pola[1]);
                        formula1.setRace(pola[2]);
                        formula1.setWinner(pola[3]);
                        formula1.setParticitians(pola[4]);
                        System.out.println(formula1.toString());
                        model.putFormaula1(formula1);
                    }
                }
            } catch (FileNotFoundException f) {
            } catch (IOException f2) {
            } catch (NumberFormatException f3) {
            }
        }
        return model;
    }
}

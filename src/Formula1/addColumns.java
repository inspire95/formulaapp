package Formula1;

import javafx.scene.control.TableView;
import Formula1.modele.*;
import java.util.List;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Dawid
 */
public class addColumns {

    public static void f1(TableView<Formula1> F1Table) {
        TableColumn<Formula1, String> yearColl = new TableColumn<>("year");
        yearColl.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Formula1, String> raceColl = new TableColumn<>("race");
        raceColl.setCellValueFactory(new PropertyValueFactory<>("race"));

        TableColumn<Formula1, String> winnerColl = new TableColumn<>("winner");
        winnerColl.setCellValueFactory(new PropertyValueFactory<>("winner"));

        TableColumn<Formula1, List<String>> participColl = new TableColumn<>("particitians");
        participColl.setCellValueFactory(new PropertyValueFactory<>("particitians"));

        F1Table.getColumns().add(yearColl);
        F1Table.getColumns().add(raceColl);
        F1Table.getColumns().add(winnerColl);
        F1Table.getColumns().add(participColl);
    }

    public static void team(TableView<Team> TeamsTable) {
        TableColumn<Team, Integer> idTeamColl = new TableColumn<>("id");

        idTeamColl.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn<Team, String> nameTeamColl = new TableColumn<>("name");

        nameTeamColl.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        TeamsTable.getColumns()
                .add(idTeamColl);
        TeamsTable.getColumns()
                .add(nameTeamColl);
    }

    public static void driver(TableView<Driver> DriverTable) {
        TableColumn<Driver, Integer> idDriverColl = new TableColumn<>("id");

        idDriverColl.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn<Driver, String> nameDriverColl = new TableColumn<>("name");

        nameDriverColl.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        TableColumn<Driver, String> teamDriverColl = new TableColumn<>("team");

        teamDriverColl.setCellValueFactory(
                new PropertyValueFactory<>("team"));

        DriverTable.getColumns()
                .add(idDriverColl);
        DriverTable.getColumns()
                .add(nameDriverColl);
        DriverTable.getColumns()
                .add(teamDriverColl);
    }

    public static void race(TableView<Race> RaceTable) {
        TableColumn<Race, Integer> idRaceColl = new TableColumn<>("id");

        idRaceColl.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn<Race, String> nameRaceColl = new TableColumn<>("name");

        nameRaceColl.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        RaceTable.getColumns()
                .add(idRaceColl);
        RaceTable.getColumns()
                .add(nameRaceColl);
    }

}

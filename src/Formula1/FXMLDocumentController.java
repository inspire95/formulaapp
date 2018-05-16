package Formula1;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import Formula1.modele.*;

/**
 *
 * @author Dawid
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TabPane tabPane;
    @FXML
    private Pane F1SidePane;
    @FXML
    private Button F1add;
    @FXML
    private TextArea F1partip;
    @FXML
    private ChoiceBox<String> F1race;
    @FXML
    private TextField F1year;
    @FXML
    private ChoiceBox<String> F1winner;
    @FXML
    private Pane F2SidePane;
    @FXML
    private Pane TeamsSidePane;
    @FXML
    private Button TeamsAdd;
    @FXML
    private TextField TeamsID;
    @FXML
    private TextField TeamsNazwa;
    @FXML
    private Pane DriversSidePane;
    @FXML
    private Button DriverAdd;
    @FXML
    private TextField DriverID;
    @FXML
    private TextField DriverName;
    @FXML
    private ChoiceBox<String> DriverTeam;
    @FXML
    private Pane RaceSidePane;
    @FXML
    private Button RaceAdd;
    @FXML
    private TextField RaceID;
    @FXML
    private TextField RaceName;
    @FXML
    private MenuItem F1Delete;
    @FXML
    private MenuItem F2Delete;
    @FXML
    private MenuItem TeamsDelete;
    @FXML
    private MenuItem DrivresDelete;
    @FXML
    private MenuItem RacesDelete;

    private OntologyDataBase odb;
    @FXML
    private ImageView image;
    @FXML
    private MenuItem Close;
    @FXML
    private MenuItem About;
    @FXML
    private TableView<Formula1> F1Table;
    @FXML
    private TableView<?> F2Table;
    @FXML
    private TableView<Team> TeamsTable;
    @FXML
    private TableView<Driver> DriverTable;
    @FXML
    private TableView<Race> RaceTable;
    @FXML
    private MenuItem Save;
    @FXML
    private Tab tabFormula2;
    @FXML
    private MenuItem open;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File file = new File("rsz_f1.png");
        image.setImage(new Image(file.toURI().toString(), true));

        odb = new OntologyDataBase();

        tabFormula2.setDisable(true);

        F1SidePane.setVisible(true);
        F2SidePane.setVisible(false);
        TeamsSidePane.setVisible(false);
        DriversSidePane.setVisible(false);
        RaceSidePane.setVisible(false);

        tabPane.getSelectionModel().selectedItemProperty().addListener(e -> {
            switch (tabPane.getSelectionModel().getSelectedIndex()) {
                case 0: {
                    F1SidePane.setVisible(true);
                    F2SidePane.setVisible(false);
                    TeamsSidePane.setVisible(false);
                    DriversSidePane.setVisible(false);
                    RaceSidePane.setVisible(false);
                    break;
                }
                case 1: {
                    F1SidePane.setVisible(false);
                    F2SidePane.setVisible(true);
                    TeamsSidePane.setVisible(false);
                    DriversSidePane.setVisible(false);
                    RaceSidePane.setVisible(false);
                    break;
                }
                case 2: {
                    F1SidePane.setVisible(false);
                    F2SidePane.setVisible(false);
                    TeamsSidePane.setVisible(true);
                    DriversSidePane.setVisible(false);
                    RaceSidePane.setVisible(false);
                    break;
                }
                case 3: {
                    F1SidePane.setVisible(false);
                    F2SidePane.setVisible(false);
                    TeamsSidePane.setVisible(false);
                    DriversSidePane.setVisible(true);
                    RaceSidePane.setVisible(false);
                    break;
                }
                case 4: {
                    F1SidePane.setVisible(false);
                    F2SidePane.setVisible(false);
                    TeamsSidePane.setVisible(false);
                    DriversSidePane.setVisible(false);
                    RaceSidePane.setVisible(true);
                    break;
                }
            }
        });
        open.setOnAction(e -> {
            MainModel mainModel = outSourceData.loadDataFormOutSource();
            for (Team team : mainModel.getTeamList()) {
                odb.addTeam(team);
            }
            for (Driver driver : mainModel.getDrvierList()) {
                odb.addDriver(driver);
            }
            for (Race race : mainModel.getRaceList()) {
                odb.addRace(race);
            }
            for (Formula1 formula1 : mainModel.getFormula1List()) {
                odb.addFormula1(formula1);
            }
            updateFormula1Table();
            updateTeamTable();
            updateDriversTable();
            updateRaceTable();

            updateWinnerCB();
            updateRaceCB();
            updateTeamsCB();
        });
        Save.setOnAction(e -> {
            odb.save(OntologyDataBase.file);
        });
        Close.setOnAction(e -> {
            Optional<File> optional = Optional.ofNullable(odb.fileToReload);
            optional.ifPresent(f -> odb.fileToReload.delete());
            System.exit(0);
        });
        About.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PROJEKT FORMULY 1 ");
            alert.setHeaderText(null);
            alert.setContentText("DAWID URBAN - PROJEKT INZYNIERSKI");
            alert.show();
        });
        F1Table.setOnMouseClicked(e -> {
            Formula1 f = odb.loadFormula1().get(F1Table.getSelectionModel().getFocusedIndex());
            F1year.setText(f.getYear());
            F1partip.setText(f.getParticitians().toString());
        });
        TeamsTable.setOnMouseClicked(e -> {
            Team t = odb.loadTeams().get(TeamsTable.getSelectionModel().getFocusedIndex());
            TeamsID.setText(String.valueOf(t.getId()));
            TeamsNazwa.setText(t.getName());
        });
        DriverTable.setOnMouseClicked(e -> {
            Driver d = odb.loadDrivers().get(DriverTable.getSelectionModel().getFocusedIndex());
            DriverID.setText(String.valueOf(d.getId()));
            DriverName.setText(String.valueOf(d.getName()));
        });
        RaceTable.setOnMouseClicked(e -> {
            Race r = odb.loadRaces().get(RaceTable.getSelectionModel().getFocusedIndex());
            RaceID.setText(String.valueOf(r.getId()));
            RaceName.setText(String.valueOf(r.getName()));
        });

        addColumns.driver(DriverTable);
        addColumns.f1(F1Table);
        addColumns.race(RaceTable);
        addColumns.team(TeamsTable);

        updateFormula1Table();
        updateTeamTable();
        updateDriversTable();
        updateRaceTable();

        updateWinnerCB();
        updateRaceCB();
        updateTeamsCB();

        F1add.setOnAction(e -> {
            Formula1 f = new Formula1();
            try {
                f.setYear(F1year.getText());
                f.setRace(F1race.getValue());
                f.setWinner(F1winner.getValue());
                String[] temp = F1partip.getText().substring(1, F1partip.getText().length() - 1).split(",");
                for (String string : temp) {
                    f.addParticipant(string);
                }
                if (czyUniktwaFormula(f)) {
                    if (!f.getYear().equals("")) {
                        odb.addFormula1(f);
                    } else {
                        showEmptyNameError();
                    }
                }
            } catch (NumberFormatException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setHeaderText(null);
                alert.setContentText("zly typ danych");
                alert.show();
            }
            updateFormula1Table();
        });
        DriverAdd.setOnAction(e -> {
            Driver d = new Driver();
            try {
                d.setName(DriverName.getText());
                d.setId(Integer.parseInt(DriverID.getText()));
                d.setTeam(DriverTeam.getValue());
                if (czyUniktwyKierowca(d)) {
                    if (!d.getName().equals("")) {
                        odb.addDriver(d);
                    } else {
                        showEmptyNameError();
                    }
                }
            } catch (NumberFormatException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setHeaderText(null);
                alert.setContentText("zly typ danych");
                alert.show();
            }
            updateDriversTable();
            updateWinnerCB();
        });
        TeamsAdd.setOnAction(e -> {
            Team t = new Team();
            try {
                t.setName(TeamsNazwa.getText());
                t.setId(Integer.parseInt(TeamsID.getText()));
                if (czyUniktwyZespol(t)) {
                    if (!t.getName().equals("")) {
                        odb.addTeam(t);
                    } else {
                        showEmptyNameError();
                    }
                }
            } catch (NumberFormatException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setHeaderText(null);
                alert.setContentText("zly typ danych");
                alert.show();
            }
            updateTeamTable();
            updateTeamsCB();
        });
        RaceAdd.setOnAction(e -> {
            Race r = new Race();
            try {
                r.setName(RaceName.getText());
                r.setId(Integer.parseInt(RaceID.getText()));
                System.out.println(r.toString());
                if (czyUniktwyTor(r)) {
                    if (!r.getName().equals("")) {
                        odb.addRace(r);
                    } else {
                        showEmptyNameError();
                    }
                }
            } catch (NumberFormatException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setHeaderText(null);
                alert.setContentText("zly typ danych");
                alert.show();
            }
            updateRaceTable();
            updateRaceCB();
        });
        F1Delete.setOnAction(e -> {
            String temp = "F1" + F1Table.getItems().get(F1Table.getSelectionModel().getFocusedIndex()).getYear();
            System.out.println(temp);
            odb.delete(temp);
            updateFormula1Table();
        });
        DrivresDelete.setOnAction(e -> {
            odb.delete(DriverTable.getItems().get(DriverTable.getSelectionModel().getFocusedIndex()).getName());
            updateDriversTable();
            updateWinnerCB();
        });
        RacesDelete.setOnAction(e -> {
            odb.delete(RaceTable.getItems().get(RaceTable.getSelectionModel().getFocusedIndex()).getName());
            updateRaceTable();
            updateRaceCB();
        });
        TeamsDelete.setOnAction(e -> {
            odb.delete(TeamsTable.getItems().get(TeamsTable.getSelectionModel().getFocusedIndex()).getName());
            updateTeamTable();
            updateTeamsCB();
        });
    }

    public void updateWinnerCB() {
        F1winner.getItems().clear();
        odb.loadDrivers().forEach(n -> F1winner.getItems().add(n.getName()));
    }

    public void updateRaceCB() {
        F1race.getItems().clear();
        odb.loadRaces().forEach(n -> F1race.getItems().add(n.getName()));
    }

    public void updateTeamsCB() {
        DriverTeam.getItems().clear();
        odb.loadTeams().forEach(n -> DriverTeam.getItems().add(n.getName()));
    }

    private void updateFormula1Table() {
        F1Table.setItems(odb.loadFormula1());
    }

    private void updateDriversTable() {
        DriverTable.setItems(odb.loadDrivers());
    }

    private void updateTeamTable() {
        TeamsTable.setItems(odb.loadTeams());
    }

    private void updateRaceTable() {
        RaceTable.setItems(odb.loadRaces());
    }

    private boolean czyUniktwaFormula(Formula1 f) {
        for (Formula1 formula : F1Table.getItems()) {
            if (formula.getYear().equals(f.getYear())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Bład roku");
                alert.setContentText("Indywiduum z takim rokiem juz istnieje!");
                alert.show();
                return false;
            }
        }
        return true;
    }

    private boolean czyUniktwyKierowca(Driver d) {
        for (Driver driver : DriverTable.getItems()) {
            if (driver.getName().equals(d.getName())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Bład nazwy");
                alert.setContentText("Indywiduum z taka nazwa juz istnieje!");
                alert.show();
                return false;
            }
        }
        return true;
    }

    private boolean czyUniktwyTor(Race r) {
        for (Race race : RaceTable.getItems()) {
            if (race.getName().equals(r.getName())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Bład nazwy");
                alert.setContentText("Indywiduum z taka nazwa juz istnieje!");
                alert.show();
                return false;
            }
        }
        return true;
    }

    private boolean czyUniktwyZespol(Team t) {
        for (Team team : TeamsTable.getItems()) {
            if (team.getName().equals(t.getName())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Bład nazwy");
                alert.setContentText("Indywiduum z taka nazwa juz istnieje!");
                alert.show();
                return false;
            }
        }
        return true;
    }

    private void showEmptyNameError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setHeaderText("BŁĄD NAZWY");
        alert.setContentText("NAZWA INDYWIDUUM NIE MOZE BYC PUSTA");
        alert.show();
    }
}

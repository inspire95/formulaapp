package Formula1;

import java.io.File;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Dawid
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setOnCloseRequest(e -> {
            Optional<File> optional = Optional.ofNullable(OntologyDataBase.fileToReload);
            optional.ifPresent(f -> OntologyDataBase.fileToReload.delete());
            System.exit(0);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Class that starts the game application.
 *
 *
 * @author Abdulrahman Al Bittar
 */
public class ApplicationMain extends Application {

    /**
     * Primary stage, that controls the GUI view.
     */
    private static  Stage primaryStage;

    /**
     * Sets the primary stage to change GUI windows.
     *
     * @param stage Primary stage
     */
    public static void setPrimaryStage(Stage stage) {
        ApplicationMain.primaryStage = stage;
    }

    /**
     * Gets the primary stage
     *
     * @return Primary stage
     */
    public static Stage getPrimaryStage() {
        return ApplicationMain.primaryStage;
    }

    /**
     * Creating the stage and showing it. This is where the initial size and the
     * title of the window are set.
     *
     * @param stage the stage to be shown
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Parsing the GUI
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("UserInterface.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1200, 900);

        primaryStage = stage;
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(800);
        primaryStage.setTitle("Flood Pipe Game");
        Image icon = new Image("img/icon.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main method
     *
     * @param args unused
     */
    public static void main(String... args) {

        // This method will be called inside the main method of the class JarMain
        launch(args);
    }
}

package com.example.fiftypoints.views;

import com.example.fiftypoints.controllers.ExitController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExitView extends Stage {
    private final ExitController exitController;

    /**
     * Initializes the GameView by loading the FXML layout and setting
     * the window's title, scene, and other properties.
     *
     * @throws IOException if there is an issue loading the FXML file
     */
    public ExitView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiftypoints/playAgain-view.fxml"));
        Parent root = loader.load();
        this.setTitle("Cincuentazo");
        this.exitController = loader.getController();
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setResizable(false);
        this.show();
    }

    public ExitController getExitController() throws IOException {
        return this.exitController;
    }
}

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

    /**
     * Holds the single instance of GameView, following the Singleton pattern.
     */
    private static class ExitViewHolder {
        private static ExitView INSTANCE;
    }

    /**
     * Provides access to the single instance of GameView.
     * If the instance does not exist, it is created; otherwise, the existing
     * instance is returned.
     *
     * @return the singleton instance of GameView
     * @throws IOException if there is an issue loading the FXML file
     */
    public static ExitView getInstance() throws IOException {

        if (ExitView.ExitViewHolder.INSTANCE == null) {
            return ExitView.ExitViewHolder.INSTANCE = new ExitView();
        } else {
            return ExitView.ExitViewHolder.INSTANCE;
        }
    }

    public ExitController getGameController() {
        return exitController;
    }
}

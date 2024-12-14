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
     * Retrieves the ExitController associated with this ExitView.
     * The ExitController handles logic related to the exit or play again
     * functionality in the application.
     *
     * @return the ExitController instance tied to this ExitView
     */
    public ExitController getExitController(){
        return this.exitController;
    }

    /**
     * A holder class used to implement the Singleton pattern for the {@link ExitView} class.
     * This class contains a static reference to the single instance of {@link ExitView}.
     * The instance is lazily initialized and accessed through the {@link ExitView#getInstance()} method.
     */
    private static class ExitViewHolder {
        private static ExitView INSTANCE;
    }

    /**
     * Provides access to the singleton instance of ExitView.
     * If the instance does not exist, it is created; otherwise, the existing
     * instance is returned. This method ensures that only one instance of
     * ExitView is created and managed throughout the application's lifecycle.
     *
     * @return the singleton instance of ExitView
     * @throws IOException if there is an issue during the creation of the instance
     */
    public static ExitView getInstance() throws IOException {

        if (ExitView.ExitViewHolder.INSTANCE == null) {
            return ExitView.ExitViewHolder.INSTANCE = new ExitView();
        } else {
            return ExitView.ExitViewHolder.INSTANCE;
        }
    }
}

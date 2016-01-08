package org.kles;

import activtyreport.view.LoginViewController;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    protected Stage primaryStage;
    public BorderPane rootLayout;
    public static ResourceBundle resourceMessage = ResourceBundle.getBundle("resources/language", Locale.getDefault());
    public static final Image LOGO_IMAGE = new Image(MainApp.class.getResourceAsStream("/resources/images/logo.png"));

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("3Kles Tools");

        initRootLayout();

        showLoginView();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {

        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double height = (int) dimension.getHeight() / 2;
        double width = (int) dimension.getWidth() / 2;
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout, width, height);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showLoginView() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LoginView.fxml"));
            //AnchorPane loginView = (AnchorPane) loader.load();
            GridPane loginView = (GridPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(loginView);
            // Give the controller access to the main app.
            LoginViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

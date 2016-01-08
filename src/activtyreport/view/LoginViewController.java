package activtyreport.view;

import activtyreport.MainApp;
import activtyreport.model.Login;
import activtyreport.view.FXMLDocumentController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginViewController extends Application {

    private Stage secondaryStage;
    @FXML
    private TextField identLabel;
    @FXML
    private TextField passwordLabel;
    @FXML
    private Label loginFailed;
    @FXML
    private Button loginBtn;
    // Reference to the main application.
    MainApp mainApp;
    
    private boolean testConnection;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public LoginViewController() {
        testConnection = true;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    public void handleLogin() throws IOException {

        if (testConnection) {
            Login login = new Login(identLabel.toString(), passwordLabel.toString());
            login.setIdent(identLabel.toString());
            mainApp.initRootLayout();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FXMLDocument.fxml"));
            //AnchorPane loginView = (AnchorPane) loader.load();
            GridPane fxmlDocumentView = (GridPane) loader.load();

            // Set person overview into the center of root layout.
            mainApp.rootLayout.setCenter(fxmlDocumentView);
            FXMLDocumentController controller = loader.getController();
            controller.setMainApp(this.mainApp);
            
            
            // Give the controller access to the main app.

            /*
             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
             Parent root1 = (Parent) fxmlLoader.load();
            
             Stage stage = new Stage();
             stage.initModality(Modality.APPLICATION_MODAL);
             stage.initStyle(StageStyle.UNDECORATED);
             stage.setTitle("ABC");
             stage.setScene(new Scene(root1));  
             stage.show();*/
        } else {
            loginFailed.setText("lol");
        }

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

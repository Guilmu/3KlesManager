/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activtyreport.view;

import activtyreport.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

/**
 *
 * @author Guillaume.GLEVAREC
 */
public class FXMLDocumentController implements Initializable {

    // Reference to the main application.
    MainApp mainApp;
    @FXML
    private Label label;
    @FXML
    private Button closeBtn;
    @FXML
    private Button craBtn;
    @FXML
    private Button congeBtn;
    

    @FXML
    private void handleCongeAction(ActionEvent event) throws IOException {
        mainApp.initRootLayout();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/CongeView.fxml"));
        SplitPane ndfView = (SplitPane) loader.load();
        //GridPane FXMLDocumentView = (GridPane) loader.load();

        // Set person overview into the center of root layout.
        mainApp.rootLayout.setCenter(ndfView);
        CongeViewController controller = loader.getController();
        controller.setMainApp(this.mainApp);
    }

    @FXML
    private void handleCGAction(ActionEvent event) {
        System.out.println("You clicked Conge!");
        label.setText("");
        label.setText("Conge");
    }

    @FXML
    private void handleCRAAction(ActionEvent event) throws IOException {

        mainApp.initRootLayout();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/CRAView.fxml"));
        SplitPane craView = (SplitPane) loader.load();
        //GridPane FXMLDocumentView = (GridPane) loader.load();

        // Set person overview into the center of root layout.
        mainApp.rootLayout.setCenter(craView);
        FXMLDocumentController controller = loader.getController();
        controller.setMainApp(this.mainApp);
    }

    @FXML
    private void handleCloseButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        // do what you have to do
        stage.hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void getDays() {
        Calendar calendar = new GregorianCalendar();

        // Get the number of days in that month
        int nbDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        label.setText("" + nbDays);
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

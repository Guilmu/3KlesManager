/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activtyreport.view;

import org.kles.MainApp;
import activtyreport.model.Conges;
import activtyreport.model.Login;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Guillaume.GLEVAREC
 */
public class CongeViewController {

    Conges conge;
    Login login;
    MainApp mainApp;
    @FXML
    private Label currentDate;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label total;
    @FXML
    private TextField cp;
    @FXML
    private TextField rc;
    @FXML
    private TextField cf;
    @FXML
    private TextField css;
    @FXML
    private Button closeBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private DatePicker calendFrom;
    @FXML
    private DatePicker calendTo;
    @FXML
    private Label errorCalendFrom;
    @FXML
    private Label errorCalendTo;

    private int totalOfDays;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public CongeViewController() {

    }

    @FXML
    private void handleCloseButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        // do what you have to do
        stage.hide();
    }

    @FXML
    private void handleSaveButtonAction() {
        LocalDate dateFrom = calendFrom.getValue();
        LocalDate dateTo = calendTo.getValue();
        System.out.println(dateFrom);
        System.out.println(dateTo);
        int ttl = 0;
        if (conge.controlDate(dateFrom, dateTo)) {
            totalOfDays = conge.calculeDiffDate(dateFrom.toString(), dateTo.toString());
            if (conge.pickDaysCalendar(cp, rc, cf, css)) {
                cp.setText("" + totalOfDays);
            } else {
                setField(cp);
                setField(rc);
                setField(cf);
                setField(css);
                ttl = ttl + Integer.parseInt(cp.getText());
                ttl = ttl + Integer.parseInt(rc.getText());
                ttl = ttl + Integer.parseInt(cf.getText());
                ttl = ttl + Integer.parseInt(css.getText());
            }
        } else {
            errorCalendFrom.setText("Date de début supérieur à date de fin");
        }
        total.setText("" + ttl);
    }

    private TextField setField(TextField field) {
        if (field.getText().isEmpty()) {
            field.setText("0");
        } else {
            if (!isInteger(field)) {
                field.setText("0");
            }
        }
        return field;
    }

    private boolean isInteger(TextField field) {
        boolean test = true;
        try {
            int i = Integer.parseInt(field.getText());
            return !field.getText().startsWith("-");
        } catch (NumberFormatException e) {
            test = false;
        }
        return test;
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

        currentDate.setText(LocalDate.now().toString());
        total.setText("");
        errorCalendFrom.setText("");
        errorCalendTo.setText("");
        totalOfDays = 0;
    }
}

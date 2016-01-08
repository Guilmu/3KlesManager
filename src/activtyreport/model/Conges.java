package activtyreport.model;

import activtyreport.view.CongeViewController;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Guillaume.GLEVAREC
 */
public class Conges {

    private Label total;

    private TextField cp;

    private TextField rc;

    private TextField cf;

    private TextField css;


    
    public int calculeDiffDate(String fr, String to) {
        int diff = 0;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dateFr = format.parse(fr);
            Date dateTo = format.parse(to);
            diff = (int) ((dateTo.getTime() - dateFr.getTime()) / (1000 * 60 * 60 * 24));
            //System.out.println(totalOfDays);
        } catch (ParseException ex) {
            Logger.getLogger(CongeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return diff;
    }

    public boolean controlDate(LocalDate from, LocalDate to) {
        if (from.getYear() > to.getYear()) {
            return false;
        } else if (from.getYear() == to.getYear()) {
            if (from.getMonthValue() > to.getMonthValue()) {
                return false;
            } else if (from.getMonthValue() == to.getMonthValue()) {
                if (from.getDayOfMonth() > to.getDayOfMonth()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean pickDaysCalendar(TextField cp, TextField rc, TextField cf, TextField css) {
        return (cp.getText().isEmpty()
                && rc.getText().isEmpty()
                && cf.getText().isEmpty()
                && css.getText().isEmpty());
    }

}

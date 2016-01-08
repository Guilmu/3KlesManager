package activtyreport.model;

import javafx.scene.control.TextField;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class Login {

    private final TextField ident;
    private final TextField password;

    /**
     * Default constructor.
     */
    public Login() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param ident
     * @param password
     */
    public Login(String ident, String password) {
        this.ident = new TextField(ident);
        this.password = new TextField(password);
    }

    public String getIdent() {
        return ident.getText();
    }

    public void setIdent(String ident) {
        this.ident.setText(ident);
    }

    public TextField identProperty() {
        return ident;
    }

    public String getPassword() {
        return password.getText();
    }

    public void setPassword(String password) {
        this.password.setText(password);
    }

    public TextField passwordProperty() {
        return password;
    }
}

package org.kles.view;

import org.kles.fx.custom.FxUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.kles.MainApp;
import org.kles.model.AbstractDataModel;
import org.kles.view.util.TableViewUtils;

public class ModelManagerTableViewController extends AbstractModelManagerController implements IModelManagerView {

    @FXML
    protected TableView<AbstractDataModel> table;

    public ModelManagerTableViewController(String dataname) {
        super(dataname);
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        table.setPlaceholder(new Text("No Data!!!!"));
//        TableViewUtils.addCustomTableMenu(table);
        TableViewUtils.addHeaderFilter(table);
        loadColumnTable();
    }

    public void loadColumnTable() {

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    @Override
    public void setMainApp(MainApp mainApp) {
        super.setMainApp(mainApp);
        listData = mainApp.getDataMap().get(datamodelname);
        table.setItems(listData);
    }

    @Override
    public <T> void setData(ObservableList<T> listData) {
        this.listData = listData;
        table.setItems(this.listData);
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    @Override
    public void handleDelete() {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            table.getItems().remove(selectedIndex);
        } else {
            FxUtil.showAlert(Alert.AlertType.WARNING, MainApp.resourceMessage.getString("main.delete"), MainApp.resourceMessage.getString("main.noselection"), String.format(MainApp.resourceMessage.getString("message.noselection"), MainApp.resourceMessage.getString(datamodelname.toLowerCase() + ".label").toLowerCase()));
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new server.
     */
    @FXML
    @Override
    public void handleCopy() {
        datamodel = table.getSelectionModel().getSelectedItem();
        super.handleCopy();
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected datamodel.
     */
    @FXML
    @Override
    public void handleEdit() {
        datamodel = table.getSelectionModel().getSelectedItem();
        super.handleEdit();
    }

    class PasswordLabelCell extends TableCell<AbstractDataModel, String> {

        private final Label label;

        public PasswordLabelCell() {
            label = new Label();
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            this.setGraphic(null);
        }

        private String genDotString(int len) {
            String dots = "";

            for (int i = 0; i < len; i++) {
                dots += "\u2022";
            }

            return dots;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                label.setText(genDotString(item.length()));
                setGraphic(label);
            } else {
                setGraphic(null);
            }
        }
    }
}

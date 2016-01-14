package org.kles;

import org.kles.fx.custom.DigitalClock;
import insidefx.undecorator.Undecorator;
import insidefx.undecorator.UndecoratorScene;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.launch;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import org.controlsfx.dialog.Dialogs;
import org.kles.view.RootLayoutController;
import resources.Resource;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Locale locale;
    private Properties configProp;
    private final StringProperty title = new SimpleStringProperty(Resource.TITLE);
    private final StringProperty dirData = new SimpleStringProperty();
    public static final String configFileName = "config";
    public static ResourceBundle resourceMessage;
    public static String SKIN = "skin";
    public static String LANGUAGE = "lang";
    public Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
    private RootLayoutController rootController;
    private HashMap<String, ObservableList> dataMap;
    private final DigitalClock clock = new DigitalClock(DigitalClock.CLOCK);

    private final LinkedHashMap<String, String> listSkin = new LinkedHashMap<>();

    public static final Image LOGO_IMAGE = new Image(RootLayoutController.class.getResourceAsStream("/resources/images/logo.png"));

    /**
     * Constructor
     */
    public MainApp() {
        //testOUTParser();
        clock.start();
        if (prefs.get(LANGUAGE, null) == null) {
            prefs.put(LANGUAGE, Locale.getDefault().toString());
        } else {
            Locale.setDefault(new Locale(prefs.get(LANGUAGE, null).split("_")[0], prefs.get(LANGUAGE, null).split("_")[1]));
        }
        locale = Locale.getDefault();
        resourceMessage = ResourceBundle.getBundle("resources/language", Locale.getDefault());
        createDataMap();

        loadSkins();
        if (prefs.get(SKIN, null) == null) {
            prefs.put(SKIN, STYLESHEET_MODENA);
        }
        Application.setUserAgentStylesheet(prefs.get(SKIN, null));
    }

    @Override
    public void start(Stage primaryStage) {
        title.bind(Bindings.concat(Resource.TITLE).concat(dirData).concat("\t").concat(clock.getTimeText()));
        this.primaryStage = primaryStage;
        this.primaryStage.titleProperty().bind(title);
        this.primaryStage.getIcons().add(new Image("file:/resources/images/logo.png"));
        initRootLayout();
//   test();
    }

    private void loadSkins() {
        listSkin.put("CASPIAN", STYLESHEET_CASPIAN);
        listSkin.put("MODENA", STYLESHEET_MODENA);
        listSkin.put("DarkTheme", "org/kles/css/DarkTheme.css");
        listSkin.put("Windows 7", "org/kles/css/Windows7.css");
        listSkin.put("JMetroDarkTheme", "org/kles/css/JMetroDarkTheme.css");
        listSkin.put("JMetroLightTheme", "org/kles/css/JMetroLightTheme.css");

    }

    public void clearData() {
        
    }

    private void createDataMap() {
        dataMap = new HashMap();
    }

    /**
     * Initializes the root layout and tries to load the last opened person
     * file.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("resources.language", locale));
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            //Scene scene = new Scene(rootLayout);
            UndecoratorScene scene = new UndecoratorScene(primaryStage, rootLayout);
            scene.setFadeInTransition();

            primaryStage.setOnCloseRequest((WindowEvent we) -> {
                we.consume();   // Do not hide yet
                scene.setFadeOutTransition();
            });
            primaryStage.setScene(scene);

            rootController = loader.getController();
            rootController.setMainApp(this);
            scene.widthProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) -> {
                System.out.println("Width: " + newSceneWidth);
            });
            scene.heightProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) -> {
                System.out.println("Height: " + newSceneHeight);
            });
            Undecorator undecorator = scene.getUndecorator();
            primaryStage.setMinWidth(undecorator.getMinWidth());
            primaryStage.setMinHeight(undecorator.getMinHeight());

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = getDataDirectoryPath();
        if (file != null) {
            loadDataDirectory(file);
        }
    }

    public File getDataDirectoryPath() {
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setRegistryFilePath(File file) {
        if (file != null) {
            prefs.put("filePath", file.getPath());
            dirData.set(" - " + file.getAbsolutePath());
            //this.primaryStage.setTitle(title + " - " + file.getAbsolutePath() + clock.getTimeText().get());
        } else {
            prefs.remove("filePath");
            //this.primaryStage.setTitle(title + clock.getTimeText().get());
            dirData.set("");
        }
    }

    public void loadDataDirectory(File directory) {
        JAXBContext context;
        Unmarshaller um;
        try {
            if (directory.exists() && directory.isDirectory()) {
                File[] listFile = directory.listFiles();
                for (File f : listFile) {
                    
                }
            }

            // Save the file path to the registry.
            setRegistryFilePath(directory);
        } catch (Exception e) {
            Dialogs.create()
                    .title("Error")
                    .masthead("Could not load data from file:\n" + directory.getPath())
                    .showException(e);
        }
    }

    public void saveDataToFile(File file) {
        try {
            setRegistryFilePath(file);
        } catch (Exception e) {
            Dialogs.create().title("Error")
                    .masthead("Could not save data to file:\n" + file.getPath())
                    .showException(e);
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

    public ResourceBundle getResourceMessage() {
        return resourceMessage;
    }

    public void setResourceMessage(ResourceBundle resourceMessage) {
        MainApp.resourceMessage = resourceMessage;
    }

    public HashMap<String, ObservableList> getDataMap() {
        return dataMap;
    }

    public void setDataMap(HashMap<String, ObservableList> dataMap) {
        this.dataMap = dataMap;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public RootLayoutController getRootController() {
        return rootController;
    }

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }

    public LinkedHashMap<String, String> getListSkin() {
        return listSkin;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

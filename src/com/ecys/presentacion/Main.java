package com.ecys.presentacion;

import com.ecys.controller.*;
import com.ecys.entidades.Usuario;
import com.ecys.util.Authenticator;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ecys
 */
public class Main extends Application {

    private Usuario loggedUser;

    public Stage stage;

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/assets/Logo-Koreano-32.png")));
            stage.setTitle("El Koreano");
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            showLogin();
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Usuario getLoggedUser() {
        return loggedUser;
    }

    public boolean userLogging(String username, String password) {
        if (Authenticator.validate(username, password)) {
            loggedUser = Usuario.of(username);
            showMain();
            return true;
        } else {
            return false;
        }
    }

    public void userLogout() {
        loggedUser = null;
        showLogin();
    }

    public void hideStage() {
        stage.setIconified(true);
    }

    public void closeStage() {
        stage.close();
        System.exit(0);
    }

    private void showLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent("LoginFXML.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showMain() {
        try {
            MainController main = (MainController) replaceSceneContent("MainFXML.fxml");
            main.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showAdministrarAlquiler() {
        try {
            AdministrarAlquilerController administrarAlquiler = (AdministrarAlquilerController) replaceSceneContent("AdministrarAlquilerFXML.fxml");
            administrarAlquiler.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showAdministrarContratistas() {
        try {
            AdministrarContratistasController administrarContratistas = (AdministrarContratistasController) replaceSceneContent("AdministrarContratistasFXML.fxml");
            administrarContratistas.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showAdministrarMaquinarias() {
        try {
            AdministrarEquiposController administrarMaquinarias = (AdministrarEquiposController) replaceSceneContent("AdministrarEquiposFXML.fxml");
            administrarMaquinarias.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        return (Initializable) loader.getController();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[]) null);
    }

}

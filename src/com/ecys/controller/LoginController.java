package com.ecys.controller;

import com.ecys.presentacion.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author ecys
 */
public class LoginController extends AnchorPane implements Initializable {

    private Main application;

    @FXML
    TextField tfUsuario;
    @FXML
    PasswordField pfClave;
    @FXML
    Label lblDescripcion;
    @FXML
    Button btnIngresar;
    @FXML
    AnchorPane apTitle;

    private double stageX = 0, stageY = 0;

    private final String defaultText = "Bienvenido a \"El Koreano\"";

    public void setApp(Main appication) {
        this.application = appication;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfUsuario.setPromptText("Usuario");
        addTextLimiter(tfUsuario, 16);
        pfClave.setPromptText("Clave");
        addTextLimiter(pfClave, 16);
        lblDescripcion.setText(defaultText);
        setDrag(apTitle);
        setToolTipBottom(btnIngresar, "Click para Ingresar al Sistema");

        btnIngresar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    handleLoginAction();
                }
            }
        });

    }

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }

    public static void addTextLimiter(final PasswordField pf, final int maxLength) {
        pf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (pf.getText().length() > maxLength) {
                    String s = pf.getText().substring(0, maxLength);
                    pf.setText(s);
                }
            }
        });
    }

    private void setToolTipBottom(Node node, String msg) {
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblDescripcion.setText(msg);
            }
        });

        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblDescripcion.setText(defaultText);
            }
        });
    }

    private void setDrag(Node node) {
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblDescripcion.setText("Click y arrastre para mover la ventana");
            }
        });

        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblDescripcion.setText(defaultText);
            }
        });

        node.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                stageX = e.getSceneX();
                stageY = e.getSceneY();
            }
        });

        node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                application.stage.getScene().getWindow().setX(e.getScreenX() - stageX);
                application.stage.getScene().getWindow().setY(e.getScreenY() - stageY);
            }
        });
    }

    @FXML
    public void handleLoginAction() {
        if (application == null) {
            lblDescripcion.setText("Hola " + tfUsuario.getText());
        } else {
            if (!application.userLogging(tfUsuario.getText(), pfClave.getText())) {
                lblDescripcion.setText("EL usuario o la clave son incorrectos.");
            }
        }
    }

    @FXML
    public void handleHideAction() {
        application.hideStage();
    }

    @FXML
    public void handleCloseAction() {
        application.closeStage();
    }
}

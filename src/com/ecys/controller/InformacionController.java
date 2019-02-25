package com.ecys.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ecys
 */
public class InformacionController implements Initializable {

    @FXML
    HBox hbHandleTitle;
    @FXML
    Label lblDescripcion;
    @FXML
    Button btnAceptar;

    private double stageX = 0, stageY = 0;

    private final String defaultText = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lblDescripcion.setText(defaultText);

        setDrag(hbHandleTitle);

        setToolTipBottom(btnAceptar, "Click para acetar");

        btnAceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
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
            public void handle(MouseEvent event) {
                stageX = event.getSceneX();
                stageY = event.getSceneY();
            }
        });

        node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.getScene().getWindow().setX(event.getScreenX() - stageX);
                stage.getScene().getWindow().setY(event.getScreenY() - stageY);
            }
        });
    }

}

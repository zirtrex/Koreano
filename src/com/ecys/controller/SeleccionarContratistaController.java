package com.ecys.controller;

import com.ecys.entidades.Contratista;
import com.ecys.negocio.BolContratista;
import com.ecys.negocio.BolDetalleEmpresa;
import com.ecys.presentacion.ContratistaDialog;
import com.ecys.presentacion.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author ecys
 */
public class SeleccionarContratistaController implements Initializable {
    
    @FXML
    HBox hbHandleTitle;
    @FXML
    Label lblTitulo, lblDescripcion;
    @FXML
    TextField tfBuscar;
    @FXML
    Button btnAceptar, btnVolver;
    @FXML
    ToggleGroup tgBuscarPor;
    @FXML
    RadioButton rbCodigo, rbNombre, rbRuc;
    @FXML
    private TableView<Contratista> tblContratista;
    @FXML
    private TableColumn<Contratista, String> codigoContratista, razonSocial, ruc, telefono, correo;
    
    private double stageX = 0, stageY = 0;
    
    private final String defaultText = "";
    
    BolContratista bolContratista = new BolContratista();
    BolDetalleEmpresa bolDetalleEmpresa = new BolDetalleEmpresa();
    
    Contratista contratistaSeleccionado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        tblContratista.setItems(bolContratista.fetchContratistas("", 0));
        
        setDrag(hbHandleTitle);
        
        rbCodigo.setUserData("codigo");
        rbNombre.setUserData("nombre");
        rbRuc.setUserData("ruc");
        
        btnAceptar.setDisable(true);
        
        setToolTipBottom(btnAceptar, "Aceptar");
        setToolTipBottom(btnVolver, "Volver");
        
        tfBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    if (!tfBuscar.getText().isEmpty()) {
                        
                        String buscarPor = tgBuscarPor.getSelectedToggle().getUserData().toString();
                        
                        switch (buscarPor) {
                            case "codigo":
                                searchByCodEquipo(tfBuscar.getText());
                                break;
                            case "nombre":
                                searchByNombre(tfBuscar.getText());
                                break;
                            case "ruc":
                                searchByRuc(tfBuscar.getText());
                                break;
                            default:
                                break;
                        }
                        
                    } else {
                        tblContratista.getItems().clear();
                        tblContratista.layout();
                        tblContratista.setItems(bolContratista.fetchContratistas("", 0));
                    }
                }
                
            }
        });        
        
        btnAceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ContratistaDialog.setContratista(contratistaSeleccionado);
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        });
        
        btnVolver.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        });
        
        codigoContratista.setCellValueFactory(new PropertyValueFactory<Contratista, String>("codContratista"));
        razonSocial.setCellValueFactory(new PropertyValueFactory<Contratista, String>("razonSocial"));
        ruc.setCellValueFactory(new PropertyValueFactory<Contratista, String>("ruc"));
        telefono.setCellValueFactory(new PropertyValueFactory<Contratista, String>("telefono"));
        correo.setCellValueFactory(new PropertyValueFactory<Contratista, String>("email"));
        
        tblContratista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contratista>() {
            @Override
            public void changed(ObservableValue<? extends Contratista> observable, Contratista oldValue, Contratista newValue) {
                contratistaSeleccionado = tblContratista.getSelectionModel().getSelectedItem();
                btnAceptar.setDisable(false);
            }
        });
        
        tblContratista.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                contratistaSeleccionado = tblContratista.getSelectionModel().getSelectedItem();
                btnAceptar.setDisable(false);
            }
            
        });
    }
    
    private void searchByCodEquipo(String texto) {
        tblContratista.getItems().clear();
        tblContratista.layout();
        tblContratista.setItems(bolContratista.fetchContratistas(texto, 1));
    }
    
    private void searchByNombre(String texto) {
        tblContratista.getItems().clear();
        tblContratista.layout();
        tblContratista.setItems(bolContratista.fetchContratistas(texto, 2));
    }
    
    private void searchByRuc(String texto) {
        tblContratista.getItems().clear();
        tblContratista.layout();
        tblContratista.setItems(bolContratista.fetchContratistas(texto, 3));
    }
    
    public void setToolTipBottom(Node node, String msg) {
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
            public void handle(MouseEvent event) {
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.getScene().getWindow().setX(event.getScreenX() - stageX);
                stage.getScene().getWindow().setY(event.getScreenY() - stageY);
            }
            
        });
    }
}

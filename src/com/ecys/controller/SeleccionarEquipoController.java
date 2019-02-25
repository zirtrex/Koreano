package com.ecys.controller;

import com.ecys.entidades.DetalleAlquiler;
import com.ecys.entidades.Equipo;
import com.ecys.negocio.BolDetalleAlquiler;
import com.ecys.negocio.BolEquipo;
import com.ecys.presentacion.DetalleAlquilerDialog;
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
public class SeleccionarEquipoController implements Initializable {

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
    RadioButton rbCodigo, rbNombre, rbMarca;
    @FXML
    private TableView<Equipo> tblEquipo;
    @FXML
    private TableColumn<Equipo, String> codigoEquipo, nombre, marca, categoria, operativo, observaciones;

    private double stageX = 0, stageY = 0;

    private final String defaultText = "";

    BolEquipo bolEquipo = new BolEquipo();
    BolDetalleAlquiler bolDetalleAlquiler = new BolDetalleAlquiler();

    Equipo equipoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tblEquipo.setItems(bolEquipo.fetchEquipos("", 0));

        setDrag(hbHandleTitle);

        rbCodigo.setUserData("codigo");
        rbNombre.setUserData("nombre");
        rbMarca.setUserData("marca");

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
                            case "marca":
                                searchByMarca(tfBuscar.getText());
                                break;
                            default:
                                break;
                        }

                    } else {
                        tblEquipo.getItems().clear();
                        tblEquipo.layout();
                        tblEquipo.setItems(bolEquipo.fetchEquipos("", 0));
                    }
                }

            }
        });

        btnAceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String codAlquiler = DetalleAlquilerDialog.getCodAlquiler();
                DetalleAlquiler detalleAlquiler = new DetalleAlquiler();                
                detalleAlquiler.setCodDetalleAlquiler(null);
                detalleAlquiler.setCodAlquiler(codAlquiler);
                detalleAlquiler.setCodEquipo(equipoSeleccionado.getCodEquipo());
                detalleAlquiler.setNombreEquipo(equipoSeleccionado.getNombre());
                detalleAlquiler.setEstadoDetAlq("1");
                detalleAlquiler.setHoras(0.0);
                detalleAlquiler.setPrecio(0.0);
                detalleAlquiler.setSubTotal(0.0);
                
                DetalleAlquilerDialog.setDetalleAlquiler(detalleAlquiler);

                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        ;
        });

        btnVolver.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DetalleAlquilerDialog.setDetalleAlquiler(null);
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        });

        codigoEquipo.setCellValueFactory(new PropertyValueFactory<Equipo, String>("codEquipo"));
        nombre.setCellValueFactory(new PropertyValueFactory<Equipo, String>("nombre"));
        marca.setCellValueFactory(new PropertyValueFactory<Equipo, String>("marca"));
        categoria.setCellValueFactory(new PropertyValueFactory<Equipo, String>("categoria"));
        operativo.setCellValueFactory(new PropertyValueFactory<Equipo, String>("operativo"));
        observaciones.setCellValueFactory(new PropertyValueFactory<Equipo, String>("observaciones"));

        tblEquipo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Equipo>() {
            @Override
            public void changed(ObservableValue<? extends Equipo> observable, Equipo oldValue, Equipo newValue) {
                equipoSeleccionado = tblEquipo.getSelectionModel().getSelectedItem();
                btnAceptar.setDisable(false);
            }
        });

        tblEquipo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                equipoSeleccionado = tblEquipo.getSelectionModel().getSelectedItem();
                btnAceptar.setDisable(false);
            }

        });
    }

    private void searchByCodEquipo(String texto) {
        tblEquipo.getItems().clear();
        tblEquipo.layout();
        tblEquipo.setItems(bolEquipo.fetchEquipos(texto, 1));
    }

    private void searchByNombre(String texto) {
        tblEquipo.getItems().clear();
        tblEquipo.layout();
        tblEquipo.setItems(bolEquipo.fetchEquipos(texto, 2));
    }

    private void searchByMarca(String texto) {
        tblEquipo.getItems().clear();
        tblEquipo.layout();
        tblEquipo.setItems(bolEquipo.fetchEquipos(texto, 3));
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

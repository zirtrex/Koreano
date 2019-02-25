package com.ecys.controller;

import com.ecys.entidades.Alquiler;
import com.ecys.entidades.Contratista;
import com.ecys.entidades.DetalleAlquiler;
import com.ecys.entidades.Usuario;
import com.ecys.negocio.BolAlquiler;
import com.ecys.negocio.BolContratista;
import com.ecys.negocio.BolDetalleAlquiler;
import com.ecys.presentacion.ContratistaDialog;
import com.ecys.presentacion.DetalleAlquilerDialog;
import com.ecys.presentacion.Main;
import com.ecys.util.Miscelanea;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author ecys
 */
public class AdministrarAlquilerController extends AnchorPane implements Initializable {

    private Main application;

    Stage stContratista, stDetalleAlquiler;

    @FXML
    HBox hbHandleTitle;
    @FXML
    Label lblFechaActual, lblDescripcion, lblTitulo, lblUsuario, lblNumeroSerie, lblNumeroDocumento;
    @FXML
    TextField tfCodContratista, tfRazonSocial, tfRuc, tfEmail, tfTelefono, tfDireccion, tfSubTotal, tfIgv, tfTotal, tfBuscar;
    @FXML
    Button btnNuevo, btnAceptar, btnEditar, btnEliminar, btnVolver, btnAgregarContratista, btnAgregarEquipo, btnQuitarEquipo;
    @FXML
    ToggleGroup tgTipoDocumento, tgBuscarPor;
    @FXML
    RadioButton rbFactura, rbBoleta, rbCodigo, rbContratista;

    @FXML
    private TableView<Alquiler> tblListarAlquiler;
    @FXML
    private TableColumn<Alquiler, String> codigo, nombreContratista, tipoDocumento;
    @FXML
    private TableColumn<Alquiler, String> numeroSerie, numeroDocumento;
    @FXML
    private TableColumn<Alquiler, Double> subTotal, igv, total;

    @FXML
    private TableView<DetalleAlquiler> tblDetalleAlquiler;
    @FXML
    private TableColumn<DetalleAlquiler, String> codigoDetalleAlquiler;
    @FXML
    private TableColumn<DetalleAlquiler, String> nombreEquipo;
    @FXML
    private TableColumn<DetalleAlquiler, Double> horas, precio, subTotalDA;

    private double stageX = 0, stageY = 0;

    private final String defaultText = "";

    Usuario usuario;

    BolAlquiler bolAlquiler = new BolAlquiler();
    BolContratista bolContratista = new BolContratista();
    BolDetalleAlquiler bolDetalleAlquiler = new BolDetalleAlquiler();

    Contratista contratista;

    String codAlquiler;

    private ObservableList<DetalleAlquiler> olDetalleAlquiler = FXCollections.observableArrayList();

    Alquiler alquilerEdit;

    boolean agregando = false, editando = false;

    public void setApp(Main application) {
        this.application = application;
        usuario = application.getLoggedUser();
        lblDescripcion.setText(defaultText);
        lblFechaActual.setText(Miscelanea.getFechaActual(false));
        lblUsuario.setText("Bienvenido: " + usuario.getNombreUsuario());
        tblListarAlquiler.setItems(bolAlquiler.fetchAlquileres("", 0));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setDrag(hbHandleTitle);

        rbBoleta.setUserData("Boleta");
        rbFactura.setUserData("Factura");

        rbCodigo.setUserData("codigo");
        rbContratista.setUserData("contratista");

        btnAceptar.setDisable(true);
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);

        setToolTipBottom(btnNuevo, "Nuevo Alquiler");
        setToolTipBottom(btnEditar, "Editar Alquiler");
        setToolTipBottom(btnEliminar, "Eliminar Alquiler");
        setToolTipBottom(btnAceptar, "Guardar Cambios");
        setToolTipBottom(btnVolver, "Volver al Menú Principal");
        setToolTipBottom(btnAgregarContratista, "Seleccionar Contratista");
        setToolTipBottom(btnAgregarEquipo, "Agregar Equipo");
        setToolTipBottom(btnQuitarEquipo, "Quitar Equipo");

        tgTipoDocumento.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                lblDescripcion.setText(tgTipoDocumento.getSelectedToggle().getUserData().toString());
            }
        });

        btnAgregarContratista.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showContratistaDialog();
            }
        });

        btnAgregarEquipo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showDetalleAlquilerDialog();
                System.out.println(olDetalleAlquiler.size());
            }
        });

        btnQuitarEquipo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DetalleAlquiler detalleAlquilerSeleccionado = tblDetalleAlquiler.getSelectionModel().getSelectedItem();
                if (detalleAlquilerSeleccionado != null) {
                    tblDetalleAlquiler.getItems().remove(detalleAlquilerSeleccionado);
                    calculate(detalleAlquilerSeleccionado);

                } else {
                    Dialogs.create()
                            .owner(application.stage)
                            .title("Imposible Quitar Equipo")
                            .masthead(null)
                            .message("No ha seleccionado ninguna fila.")
                            .showWarning();
                }
            }
        });

        tfBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    if (!tfBuscar.getText().isEmpty()) {
                        String buscarPor = tgBuscarPor.getSelectedToggle().getUserData().toString();
                        switch (buscarPor) {
                            case "codigo":
                                searchByCodido(tfBuscar.getText());
                                break;
                            case "contratista":
                                searchByContratista(tfBuscar.getText());
                                break;
                            default:
                                break;
                        }
                    } else {
                        tblListarAlquiler.getItems().clear();
                        tblListarAlquiler.layout();
                        tblListarAlquiler.setItems(bolAlquiler.fetchAlquileres("", 1));
                    }
                }
            }
        });

        btnNuevo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mostrarDetalles(null);
                agregando = true;
                editando = false;
                btnAgregarContratista.setDisable(false);
                btnAgregarEquipo.setDisable(false);
                btnAceptar.setDisable(false);
                btnEditar.setDisable(true);
                btnEliminar.setDisable(true);

            }
        });

        btnEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tblDetalleAlquiler.setEditable(true);
                Alquiler alquilerSeleccionado = tblListarAlquiler.getSelectionModel().getSelectedItem();
                if (alquilerSeleccionado != null) {
                    editando = true;
                    agregando = false;
                    alquilerEdit = alquilerSeleccionado;
                    btnAgregarContratista.setDisable(false);
                    btnAgregarEquipo.setDisable(true);
                    btnQuitarEquipo.setDisable(true);
                    btnAceptar.setDisable(false);
                    btnEditar.setDisable(true);
                    btnEliminar.setDisable(true);

                } else {
                    Dialogs.create()
                            .owner(application.stage)
                            .title("Imposible Editar")
                            .masthead(null)
                            .message("No ha seleccionado ninguna fila.")
                            .showWarning();
                }
            }
        });

        btnEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alquiler alquilerSeleccionado = tblListarAlquiler.getSelectionModel().getSelectedItem();
                if (alquilerSeleccionado != null) {
                    Action response = Dialogs.create()
                            .owner(application.stage)
                            .title("Confirmación para eliminar")
                            .masthead(null)
                            .message("Seguro que deseas eliminar el Alquiler: ".concat(alquilerSeleccionado.getCodAlquiler()))
                            .actions(Dialog.Actions.YES, Dialog.Actions.CANCEL)
                            .showConfirm();
                    if (response == Dialog.Actions.YES) {
                        if (bolAlquiler.eliminarAlquiler(alquilerSeleccionado.getCodAlquiler()) > 0) {
                            int tblSize = tblDetalleAlquiler.getItems().size();
                            int totalEliminados = 0;
                            for (int i = 0; i < tblSize; i++) {
                                DetalleAlquiler detalleAlquiler = tblDetalleAlquiler.getItems().get(i);
                                if (bolDetalleAlquiler.eliminarDetalleAlquiler(detalleAlquiler.getCodDetalleAlquiler()) > 0) {
                                    totalEliminados++;
                                }
                            }
                            if (totalEliminados == tblSize) {
                                refrescarFormulario();
                                agregando = false;
                                editando = false;
                                btnAgregarContratista.setDisable(true);
                                btnAgregarEquipo.setDisable(true);
                                btnQuitarEquipo.setDisable(true);
                                btnAceptar.setDisable(true);
                                btnEditar.setDisable(true);
                                btnEliminar.setDisable(true);
                            }
                        }
                    }
                } else {
                    Dialogs.create()
                            .owner(application.stage)
                            .title("Imposible Eliminar")
                            .masthead(null)
                            .message("No ha seleccionado ninguna fila.")
                            .showWarning();
                }
            }
        });

        btnAceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (alquilerEdit == null) {
                    Alquiler nuevoAlquiler = new Alquiler();
                    nuevoAlquiler.setCodAlquiler(codAlquiler);
                    nuevoAlquiler.setCodContratista(tfCodContratista.getText());
                    nuevoAlquiler.setCodUsuario(usuario.getCodUsuario());
                    nuevoAlquiler.setFecha(Date.valueOf(LocalDate.now()));
                    nuevoAlquiler.setTipoDocumento(tgTipoDocumento.getSelectedToggle().getUserData().toString());
                    nuevoAlquiler.setNumeroSerie(Integer.parseInt(lblNumeroSerie.getText()));
                    nuevoAlquiler.setNumeroDocumento(Integer.parseInt(lblNumeroDocumento.getText()));
                    nuevoAlquiler.setSubTotal(Double.parseDouble(tfSubTotal.getText()));
                    nuevoAlquiler.setIgv(Double.parseDouble(tfIgv.getText()));
                    nuevoAlquiler.setTotal(Double.parseDouble(tfTotal.getText()));
                    nuevoAlquiler.setEstadoAlquiler("1");

                    int tblSize = tblDetalleAlquiler.getItems().size();

                    if (!tfCodContratista.getText().equals("")) {
                        if (tblSize > 0) {
                            if (bolAlquiler.insertarAlquiler(nuevoAlquiler) > 0) {
                                DetalleAlquiler detalleAlquiler;

                                for (int i = 0; i < tblSize; i++) {
                                    detalleAlquiler = tblDetalleAlquiler.getItems().get(i);
                                    int totalDetalleAlquiler = bolDetalleAlquiler.getTotalDetalleAlquiler() + 1;
                                    String codigoDetalleAlquiler = "0000000".concat(String.valueOf(totalDetalleAlquiler));
                                    codigoDetalleAlquiler = codigoDetalleAlquiler.substring(codigoDetalleAlquiler.length() - 7, codigoDetalleAlquiler.length());
                                    codigoDetalleAlquiler = "DA".concat(codigoDetalleAlquiler);
                                    detalleAlquiler.setCodDetalleAlquiler(codigoDetalleAlquiler);
                                    bolDetalleAlquiler.insertarDetalleAlquiler(detalleAlquiler);
                                }
                                Dialogs.create()
                                        .owner(application.stage)
                                        .title("")
                                        .masthead(null)
                                        .message("Alquiler Insertado Correctamente")
                                        .showInformation();

                                refrescarFormulario();
                                tblDetalleAlquiler.setEditable(false);
                                agregando = false;
                                btnAgregarContratista.setDisable(true);
                                btnAgregarEquipo.setDisable(true);
                                btnQuitarEquipo.setDisable(true);
                                btnAceptar.setDisable(true);
                                btnEditar.setDisable(true);
                                btnEliminar.setDisable(true);
                            }
                        } else {
                            Dialogs.create()
                                    .owner(application.stage)
                                    .title("")
                                    .masthead(null)
                                    .message("Debe Existir almenos un Detalle de Alquiler")
                                    .showInformation();
                        }
                    } else {
                        Dialogs.create()
                                .owner(application.stage)
                                .title("")
                                .masthead(null)
                                .message("Debe agregar un Contratista")
                                .showInformation();
                    }
                } else {
                    alquilerEdit.setCodContratista(tfCodContratista.getText());
                    alquilerEdit.setCodUsuario(usuario.getCodUsuario());
                    alquilerEdit.setTipoDocumento(tgTipoDocumento.getSelectedToggle().getUserData().toString());
                    alquilerEdit.setSubTotal(Double.parseDouble(tfSubTotal.getText()));
                    alquilerEdit.setIgv(Double.parseDouble(tfIgv.getText()));
                    alquilerEdit.setTotal(Double.parseDouble(tfTotal.getText()));
                    
                    int tblSize = tblDetalleAlquiler.getItems().size();

                    if (tblSize > 0) {
                        if (bolAlquiler.modificarAlquiler(alquilerEdit) > 0) {
                            DetalleAlquiler detalleAlquiler;

                            for (int i = 0; i < tblSize; i++) {
                                detalleAlquiler = tblDetalleAlquiler.getItems().get(i);                                
                                bolDetalleAlquiler.modificarDetalleAlquiler(detalleAlquiler);
                            }
                            Dialogs.create()
                                    .owner(application.stage)
                                    .title("")
                                    .masthead(null)
                                    .message("Alquiler Editado Correctamente")
                                    .showInformation();

                            refrescarFormulario();
                            tblDetalleAlquiler.setEditable(false);
                            editando = false;
                            btnAgregarContratista.setDisable(true);
                            btnAgregarEquipo.setDisable(true);
                            btnQuitarEquipo.setDisable(true);
                            btnAceptar.setDisable(true);
                            btnEditar.setDisable(true);
                            btnEliminar.setDisable(true);
                        }
                    } else {
                        Dialogs.create()
                                .owner(application.stage)
                                .title("")
                                .masthead(null)
                                .message("Debe Existir almenos un Detalle de Alquiler")
                                .showInformation();
                    }
                }
            }
        });

        codigo.setCellValueFactory(new PropertyValueFactory<Alquiler, String>("codAlquiler"));
        nombreContratista.setCellValueFactory(new PropertyValueFactory<Alquiler, String>("contratista"));
        tipoDocumento.setCellValueFactory(new PropertyValueFactory<Alquiler, String>("tipoDocumento"));
        numeroSerie.setCellValueFactory(new PropertyValueFactory<Alquiler, String>("numeroSerie"));
        numeroDocumento.setCellValueFactory(new PropertyValueFactory<Alquiler, String>("numeroDocumento"));
        subTotal.setCellValueFactory(new PropertyValueFactory<Alquiler, Double>("subTotal"));
        igv.setCellValueFactory(new PropertyValueFactory<Alquiler, Double>("igv"));
        total.setCellValueFactory(new PropertyValueFactory<Alquiler, Double>("total"));

        codigoDetalleAlquiler.setCellValueFactory(new PropertyValueFactory<DetalleAlquiler, String>("codDetalleAlquiler"));
        nombreEquipo.setCellValueFactory(new PropertyValueFactory<DetalleAlquiler, String>("nombreEquipo"));
        precio.setCellValueFactory(new PropertyValueFactory<DetalleAlquiler, Double>("precio"));
        precio.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        precio.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<DetalleAlquiler, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<DetalleAlquiler, Double> t) {
                        DetalleAlquiler row = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        if (t.getOldValue() != null) {
                            if (!t.getOldValue().equals(t.getNewValue())) {
                                row.setPrecio(t.getNewValue());
                                calculate(row);
                            }
                        }
                    }
                }
        );

        horas.setCellValueFactory(new PropertyValueFactory<DetalleAlquiler, Double>("horas"));
        horas.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        horas.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<DetalleAlquiler, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<DetalleAlquiler, Double> t) {
                        DetalleAlquiler row = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        if (t.getOldValue() != null) {
                            if (!t.getOldValue().equals(t.getNewValue())) {
                                row.setHoras(t.getNewValue());
                                calculate(row);
                            }
                        }
                    }
                }
        );

        subTotalDA.setCellValueFactory(new PropertyValueFactory<DetalleAlquiler, Double>("subTotal"));

        tblListarAlquiler.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Alquiler>() {
            @Override
            public void changed(ObservableValue<? extends Alquiler> observable, Alquiler oldValue, Alquiler newValue) {
                mostrarDetalles(newValue);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
            }
        });

        tblListarAlquiler.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alquiler alquilerSeleccionado = tblListarAlquiler.getSelectionModel().getSelectedItem();
                mostrarDetalles(alquilerSeleccionado);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
            }
        });

        tblDetalleAlquiler.setEditable(false);

        tblDetalleAlquiler.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DetalleAlquiler>() {
            @Override
            public void changed(ObservableValue<? extends DetalleAlquiler> observable, DetalleAlquiler oldValue, DetalleAlquiler newValue) {
                if (agregando || editando) {
                    btnQuitarEquipo.setDisable(false);
                }
            }
        });

        tblDetalleAlquiler.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (agregando || editando) {
                    btnQuitarEquipo.setDisable(false);
                }
            }
        });
    }

    private void refrescarFormulario() {
        olDetalleAlquiler.clear();
        tblListarAlquiler.getItems().clear();
        tblListarAlquiler.layout();
        tblListarAlquiler.setItems(bolAlquiler.fetchAlquileres("", 0));
        codAlquiler = null;
        DetalleAlquilerDialog.setCodAlquiler(null);
        tfCodContratista.setText("");
        tfRazonSocial.setText("");
        tfRuc.setText("");
        tfEmail.setText("");
        tfTelefono.setText("");
        tfDireccion.setText("");
        rbFactura.setSelected(true);
        lblNumeroSerie.setText("0");
        lblNumeroDocumento.setText("0");
        tfSubTotal.setText("0.0");
        tfIgv.setText("0.0");
        tfTotal.setText("0.0");
    }

    private void calculate(DetalleAlquiler row) {
        double subTotalDA = row.getHoras() * row.getPrecio();
        double subTotalDAR = (double) Math.round(subTotalDA * 100) / 100;
        row.setSubTotal(subTotalDAR);
        System.out.println(row);
        int index = tblDetalleAlquiler.getSelectionModel().getSelectedIndex();
        tblDetalleAlquiler.setItems(null);
        tblDetalleAlquiler.layout();
        tblDetalleAlquiler.setItems(olDetalleAlquiler);
        tblDetalleAlquiler.getSelectionModel().select(index);
        int olDALength = olDetalleAlquiler.size();
        double subTotal = 0.0;
        for (int i = 0; i < olDALength; i++) {
            DetalleAlquiler detalleAlquiler = tblDetalleAlquiler.getItems().get(i);
            subTotal += detalleAlquiler.getSubTotal();
        }
        double subTotalR = (double) Math.round(subTotal * 100) / 100;
        tfSubTotal.setText(String.valueOf(subTotalR));
        double igv = subTotal * 0.18;
        double total = subTotal + igv;
        double igvR = (double) Math.round(igv * 100) / 100;
        double totalR = (double) Math.round(total * 100) / 100;
        tfIgv.setText(String.valueOf(igvR));
        tfTotal.setText(String.valueOf(totalR));
    }

    private void mostrarDetalles(Alquiler alquiler) {
        if (alquiler != null) {
            contratista = bolContratista.fetchContratista(alquiler.getCodContratista());
            mostrarContratista(contratista);
            if (alquiler.getTipoDocumento().equals("Factura")) {
                rbFactura.setSelected(true);
            } else {
                rbBoleta.setSelected(true);
            }
            lblNumeroSerie.setText(alquiler.getNumeroSerie().toString());
            lblNumeroDocumento.setText(alquiler.getNumeroDocumento().toString());
            tfSubTotal.setText(alquiler.getSubTotal().toString());
            tfIgv.setText(alquiler.getIgv().toString());
            tfTotal.setText(alquiler.getTotal().toString());
            mostrarDetalleAlquiler(alquiler);

        } else {
            int totalAlquiler = bolAlquiler.getTotalAlquiler() + 1;
            String codigoAlquiler = "00000000".concat(String.valueOf(totalAlquiler));
            codigoAlquiler = codigoAlquiler.substring(codigoAlquiler.length() - 8, codigoAlquiler.length());
            codigoAlquiler = "A".concat(codigoAlquiler);
            codAlquiler = codigoAlquiler;
            DetalleAlquilerDialog.setCodAlquiler(codAlquiler);
            tfCodContratista.setText("");
            tfRazonSocial.setText("");
            tfRuc.setText("");
            tfEmail.setText("");
            tfTelefono.setText("");
            tfDireccion.setText("");
            rbFactura.setSelected(true);
            lblNumeroSerie.setText("0");
            lblNumeroDocumento.setText("0");
            tfSubTotal.setText("0.0");
            tfIgv.setText("0.0");
            tfTotal.setText("0.0");
            olDetalleAlquiler.clear();
            tblDetalleAlquiler.setItems(null);
        }
    }

    private void mostrarDetalleAlquiler(Alquiler alquiler) {
        if (alquiler != null) {
            tblDetalleAlquiler.setItems(null);
            olDetalleAlquiler.clear();
            olDetalleAlquiler = bolDetalleAlquiler.fetchDetalleAlquilerByCodAlquiler(alquiler.getCodAlquiler());
            tblDetalleAlquiler.setItems(olDetalleAlquiler);
        }
    }

    private void mostrarContratista(Contratista contratista) {
        if (contratista != null) {
            tfCodContratista.setText(contratista.getCodContratista());
            tfRazonSocial.setText(contratista.getRazonSocial());
            tfRuc.setText(contratista.getRuc());
            tfEmail.setText(contratista.getEmail());
            tfTelefono.setText(contratista.getTelefono());
            tfDireccion.setText(contratista.getDireccion());
        }
    }

    private void searchByCodido(String texto) {
        tblListarAlquiler.getItems().clear();
        tblListarAlquiler.layout();
        tblListarAlquiler.setItems(bolAlquiler.fetchAlquileres(texto, 1));
    }

    private void searchByContratista(String texto) {
        tblListarAlquiler.getItems().clear();
        tblListarAlquiler.layout();
        tblListarAlquiler.setItems(bolAlquiler.fetchAlquileres(texto, 2));
    }

    private Stage createContratistaDialog(Stage parent, boolean modal) {
        if (stContratista != null) {
            stContratista.close();
        }
        try {
            return new ContratistaDialog(parent, modal, "Seleccionar Contratista");

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private void showContratistaDialog() {
        try {
            if (stContratista == null) {
                stContratista = createContratistaDialog(this.application.stage, true);
            }
            stContratista.sizeToScene();
            stContratista.showAndWait();
            contratista = ContratistaDialog.getContratista();
            mostrarContratista(contratista);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Stage createDetalleAlquilerDialog(Stage parent, boolean modal) {
        if (stDetalleAlquiler != null) {
            stDetalleAlquiler.close();
        }
        try {
            return new DetalleAlquilerDialog(parent, modal, "Seleccionar Detalle de Alquiler");

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private void showDetalleAlquilerDialog() {
        try {
            if (stDetalleAlquiler == null) {
                stDetalleAlquiler = createDetalleAlquilerDialog(this.application.stage, true);
            }
            stDetalleAlquiler.sizeToScene();
            stDetalleAlquiler.showAndWait();
            if (DetalleAlquilerDialog.getDetalleAlquiler() != null) {
                olDetalleAlquiler.add(DetalleAlquilerDialog.getDetalleAlquiler());
                tblDetalleAlquiler.setItems(olDetalleAlquiler);
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            public void handle(MouseEvent e) {
                application.stage.getScene().getWindow().setX(e.getScreenX() - stageX);
                application.stage.getScene().getWindow().setY(e.getScreenY() - stageY);
            }
        });
    }

    @FXML
    public void volverAction() {
        application.showMain();
    }

    @FXML
    public void hideAction() {
        application.hideStage();
    }

    @FXML
    public void closeAction() {
        application.closeStage();
    }
}

package com.ecys.controller;

import com.ecys.entidades.Equipo;
import com.ecys.entidades.Usuario;
import com.ecys.negocio.BolEquipo;
import com.ecys.presentacion.Main;
import com.ecys.util.Miscelanea;
import com.ecys.util.Validation;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author ecys
 */
public class AdministrarEquiposController extends AnchorPane implements Initializable {

    private Main application;

    @FXML
    HBox hbHandleTitle;
    @FXML
    Label lblTitulo, lblUsuario, lblDescripcion, lblFechaActual;
    @FXML
    TextField tfBuscar, tfCodigoEquipo, tfNombre, tfMarca, tfModelo, tfPlaca, tfHorometro, tfKilometraje, tfAnio;
    @FXML
    CheckBox cbOperativo;
    @FXML
    Button btnNuevo, btnAceptar, btnEditar, btnEliminar, btnVolver;
    @FXML
    ToggleGroup tgBuscarPor, tgTipoEmpresa;
    @FXML
    RadioButton rbCodigo, rbNombre, rbVehiculo, rbMaquinaria;
    @FXML
    TextArea taObservaciones, taDescripcion;

    @FXML
    private TableView<Equipo> tblEquipo;
    @FXML
    private TableColumn<Equipo, String> codigoEquipo, nombre, marca, placa, categoria, operativo, observaciones, descripcion;

    private double stageX = 0, stageY = 0;

    private final String defaultText = "";

    BolEquipo bolEquipo = new BolEquipo();

    Equipo equipoEdit = null;
    Usuario usuario = null;

    public void setApp(Main application) {
        this.application = application;
        usuario = application.getLoggedUser();
        lblDescripcion.setText(defaultText);
        lblFechaActual.setText(Miscelanea.getFechaActual(false));
        lblUsuario.setText("Bienvenido: " + usuario.getNombreUsuario());
        tblEquipo.setItems(bolEquipo.fetchEquipos("", 0));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setDrag(hbHandleTitle);

        rbNombre.setUserData("nombre");
        rbCodigo.setUserData("codigo");

        rbMaquinaria.setUserData("Maquinarias");
        rbVehiculo.setUserData("Vehículos");

        btnAceptar.setDisable(true);
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);

        setToolTipBottom(btnNuevo, "Nuevo Equipo");
        setToolTipBottom(btnEditar, "Editar Equipo");
        setToolTipBottom(btnEliminar, "Eliminar Equipo");
        setToolTipBottom(btnAceptar, "Guardar Cambios");
        setToolTipBottom(btnVolver, "Volver al Menú Principal");

        addTextLimiter(tfNombre, 50);
        addTextLimiter(tfMarca, 20);
        addTextLimiter(tfModelo, 100);
        addTextLimiter(tfPlaca, 7);
        addTextLimiter(tfHorometro, 14);
        addTextLimiter(tfKilometraje, 13);
        addTextLimiter(tfAnio, 4);
        addTextLimiter(taObservaciones, 500);
        addTextLimiter(taDescripcion, 1000);

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

        btnNuevo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mostrarDetalles(null);

                setEditableFields(true);

                btnAceptar.setDisable(false);
                btnEditar.setDisable(true);
                btnEliminar.setDisable(true);

                equipoEdit = null;
            }
        });

        btnEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Equipo equipoSeleccionado = tblEquipo.getSelectionModel().getSelectedItem();
                if (equipoSeleccionado != null) {

                    equipoEdit = equipoSeleccionado;
                    setEditableFields(true);

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

                Equipo equipoSeleccionado = tblEquipo.getSelectionModel().getSelectedItem();
                if (equipoSeleccionado != null) {

                    Action response = Dialogs.create()
                            .owner(application.stage)
                            .title("Confirmación para eliminar")
                            .masthead(null)
                            .message("Seguro que deseas eliminar el equipo: ".concat(equipoSeleccionado.getNombre()))
                            .actions(Dialog.Actions.YES, Dialog.Actions.CANCEL)
                            .showConfirm();
                    if (response == Dialog.Actions.YES) {
                        bolEquipo.eliminarEquipo(equipoSeleccionado.getCodEquipo());
                        refrescarTableView();
                        btnAceptar.setDisable(true);
                        btnEditar.setDisable(true);
                        btnEliminar.setDisable(true);
                        setEditableFields(false);
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
                if (isInputValid()) {
                    if (equipoEdit == null) {
                        Equipo nuevoEquipo = new Equipo();
                        nuevoEquipo.setCodEquipo(tfCodigoEquipo.getText());
                        nuevoEquipo.setNombre(tfNombre.getText());
                        nuevoEquipo.setMarca(tfMarca.getText());
                        nuevoEquipo.setModelo(tfModelo.getText());
                        nuevoEquipo.setPlaca(tfPlaca.getText());
                        nuevoEquipo.setCategoria(tgTipoEmpresa.getSelectedToggle().getUserData().toString());
                        nuevoEquipo.setHorometro(Double.parseDouble(tfHorometro.getText()));
                        nuevoEquipo.setKilometraje(Integer.parseInt(tfKilometraje.getText()));
                        nuevoEquipo.setAnioFabricacion(tfAnio.getText());
                        nuevoEquipo.setOperativo((cbOperativo.isSelected()) ? "1" : "0");
                        nuevoEquipo.setObservaciones(taObservaciones.getText());
                        nuevoEquipo.setDescripcion(taDescripcion.getText());
                        nuevoEquipo.setEstadoEquipo("1");

                        if (bolEquipo.insertarEquipo(nuevoEquipo) > 0) {
                            Dialogs.create()
                                    .owner(application.stage)
                                    .title("")
                                    .masthead(null)
                                    .message("Equipo Insertado Correctamente.")
                                    .showInformation();

                            tblEquipo.getItems().add(nuevoEquipo);
                            setEditableFields(false);
                            btnAceptar.setDisable(true);
                            btnEditar.setDisable(true);
                            btnEliminar.setDisable(true);
                        }
                    } else {
                        equipoEdit.setNombre(tfNombre.getText());
                        equipoEdit.setMarca(tfMarca.getText());
                        equipoEdit.setModelo(tfModelo.getText());
                        equipoEdit.setPlaca(tfPlaca.getText());
                        equipoEdit.setCategoria(tgTipoEmpresa.getSelectedToggle().getUserData().toString());
                        equipoEdit.setHorometro(Double.parseDouble(tfHorometro.getText()));
                        equipoEdit.setKilometraje(Integer.parseInt(tfKilometraje.getText()));
                        equipoEdit.setAnioFabricacion(tfAnio.getText());
                        equipoEdit.setOperativo((cbOperativo.isSelected()) ? "1" : "0");
                        equipoEdit.setObservaciones(taObservaciones.getText());
                        equipoEdit.setDescripcion(taDescripcion.getText());
                        equipoEdit.setEstadoEquipo("1");

                        if (bolEquipo.modificarEquipo(equipoEdit) > 0) {
                            Dialogs.create()
                                    .owner(application.stage)
                                    .title("")
                                    .masthead(null)
                                    .message("Equipo Actualizado Correctamente.")
                                    .showInformation();
                            refrescarTableView();
                            equipoEdit = null;
                            btnAceptar.setDisable(true);
                            btnEditar.setDisable(true);
                            btnEliminar.setDisable(true);
                            setEditableFields(false);
                        }
                    }
                }
            }
        });

        codigoEquipo.setCellValueFactory(new PropertyValueFactory<Equipo, String>("codEquipo"));
        nombre.setCellValueFactory(new PropertyValueFactory<Equipo, String>("nombre"));
        marca.setCellValueFactory(new PropertyValueFactory<Equipo, String>("marca"));
        placa.setCellValueFactory(new PropertyValueFactory<Equipo, String>("placa"));
        categoria.setCellValueFactory(new PropertyValueFactory<Equipo, String>("categoria"));
        operativo.setCellValueFactory(new PropertyValueFactory<Equipo, String>("operativo"));
        observaciones.setCellValueFactory(new PropertyValueFactory<Equipo, String>("observaciones"));
        descripcion.setCellValueFactory(new PropertyValueFactory<Equipo, String>("descripcion"));

        tblEquipo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Equipo>() {
            @Override
            public void changed(ObservableValue<? extends Equipo> observable, Equipo oldValue, Equipo newValue) {
                mostrarDetalles(newValue);
                setEditableFields(false);
                btnAceptar.setDisable(true);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
            }
        });

        tblEquipo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Equipo equipoSeleccionado = tblEquipo.getSelectionModel().getSelectedItem();
                mostrarDetalles(equipoSeleccionado);
                setEditableFields(false);
                btnAceptar.setDisable(true);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
            }

        });
    }

    private void setEditableFields(boolean action) {
        tfNombre.setEditable(action);
        tfMarca.setEditable(action);
        tfModelo.setEditable(action);
        tfPlaca.setEditable(action);
        tfHorometro.setEditable(action);
        tfKilometraje.setEditable(action);
        tfAnio.setEditable(action);
        taObservaciones.setEditable(action);
        taDescripcion.setEditable(action);
    }

    private void mostrarDetalles(Equipo equipo) {
        if (equipo != null) {
            tfCodigoEquipo.setText(equipo.getCodEquipo());
            tfNombre.setText(equipo.getNombre());
            tfMarca.setText(equipo.getMarca());
            tfModelo.setText(equipo.getModelo());
            tfPlaca.setText(equipo.getPlaca());
            if (equipo.getCategoria().equals("Maquinarias")) {
                rbMaquinaria.setSelected(true);
            } else {
                rbVehiculo.setSelected(true);
            }
            tfHorometro.setText(equipo.getHorometro().toString());
            tfKilometraje.setText(equipo.getKilometraje().toString());
            tfAnio.setText(equipo.getAnioFabricacion());
            cbOperativo.setSelected(equipo.getOperativo().equals("1"));
            taObservaciones.setText(equipo.getObservaciones() != null ? equipo.getObservaciones() : "");
            taDescripcion.setText(equipo.getDescripcion() != null ? equipo.getDescripcion() : "");
        } else {
            int totalRegistros = bolEquipo.getTotalEquipos() + 1;
            String codigoEquipo = "00000000".concat(String.valueOf(totalRegistros));
            codigoEquipo = codigoEquipo.substring(codigoEquipo.length() - 8, codigoEquipo.length());
            codigoEquipo = "E".concat(codigoEquipo);
            tfCodigoEquipo.setText(codigoEquipo);
            tfNombre.setText("");
            tfMarca.setText("");
            tfModelo.setText("");
            tfPlaca.setText("");
            rbMaquinaria.setSelected(true);
            tfHorometro.setText("0.0");
            tfKilometraje.setText("0");
            tfAnio.setText(String.valueOf(LocalDate.now().getYear()));
            cbOperativo.setSelected(true);
            taObservaciones.setText("");
            taDescripcion.setText("");
        }
    }

    private void refrescarTableView() {
        int selectedIndex = tblEquipo.getSelectionModel().getSelectedIndex();
        tblEquipo.getItems().clear();
        tblEquipo.layout();
        tblEquipo.setItems(bolEquipo.fetchEquipos("", 0));
        tblEquipo.getSelectionModel().select(selectedIndex);
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

    private boolean isInputValid() {

        String errorMessage = "";

        if (Validation.validateVoidFields(tfNombre.getText())) {
            errorMessage += "\nEl campo \"nombre\" no debe estar vacío";
        }
        if (Validation.validateDecimalFormat(tfHorometro.getText())) {
            errorMessage += "\nEl formato ingresado para el campo Horómetro no es válido (0.0)";
        }
        if (Validation.validateIntegerFormat(tfKilometraje.getText())) {
            errorMessage += "\nEl formato ingresado para el campo Kilometraje no es válido (0.0)";
        }
        if (Validation.validateYear(tfAnio.getText())) {
            errorMessage += "\nEl número ingresado para el campo año no es válido";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Dialogs.create()
                    .owner(application.stage)
                    .title("Porfavor, corrija los campos inválidos.")
                    .masthead(null)
                    .message(errorMessage)
                    .showWarning();
            return false;
        }
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

    public static void addTextLimiter(final TextArea ta, final int maxLength) {
        ta.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (ta.getText().length() > maxLength) {
                    String s = ta.getText().substring(0, maxLength);
                    ta.setText(s);
                }
            }
        });
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

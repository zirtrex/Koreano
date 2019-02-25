package com.ecys.controller;

import com.ecys.entidades.Contratista;
import com.ecys.entidades.Usuario;
import com.ecys.negocio.BolContratista;
import com.ecys.negocio.BolDetalleEmpresa;
import com.ecys.presentacion.Main;
import com.ecys.util.Miscelanea;
import com.ecys.util.Validation;
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
import javafx.scene.control.Toggle;
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
public class AdministrarContratistasController extends AnchorPane implements Initializable {

    private Main application;

    @FXML
    HBox hbHandleTitle;
    @FXML
    Label lblTitulo, lblUsuario, lblDescripcion, lblFechaActual, lblRazonSocial, lblRuc;
    @FXML
    TextField tfBuscar, tfCodigoContratista, tfCodDetalleEmpresa, tfRazonSocial, tfRuc, tfNombreEncargado, tfTelefono, tfCorreo, tfDireccion;
    @FXML
    Button btnNuevo, btnAceptar, btnEditar, btnEliminar, btnVolver;
    @FXML
    ToggleGroup tgTipoEmpresa, tgBuscarPor;
    @FXML
    RadioButton rbCodigo, rbNombre, rbRuc, rbJuridica, rbNatural;
    @FXML
    private TableView<Contratista> tblContratista;
    @FXML
    private TableColumn<Contratista, String> codigoContratista, razonSocial, ruc, telefono, tipoEmpresa, correo;

    private double stageX = 0, stageY = 0;

    private final String defaultText = "";

    BolContratista bolContratista = new BolContratista();
    BolDetalleEmpresa bolDetalleEmpresa = new BolDetalleEmpresa();

    Contratista contratistaEdit;
    Usuario usuario;

    public void setApp(Main application) {
        this.application = application;
        usuario = application.getLoggedUser();
        lblDescripcion.setText(defaultText);
        lblFechaActual.setText(Miscelanea.getFechaActual(false));
        lblUsuario.setText("Bienvenido: " + usuario.getNombreUsuario());
        tblContratista.setItems(bolContratista.fetchContratistas("", 0));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDrag(hbHandleTitle);

        addTextLimiter(tfRazonSocial, 500);
        addTextLimiter(tfRuc, 11);
        addTextLimiter(tfNombreEncargado, 200);
        addTextLimiter(tfTelefono, 9);
        addTextLimiter(tfCorreo, 200);
        addTextLimiter(tfDireccion, 200);

        rbCodigo.setUserData("codigo");
        rbNombre.setUserData("nombre");
        rbRuc.setUserData("ruc");

        rbJuridica.setUserData("Jurídica");
        rbNatural.setUserData("Natural");

        btnAceptar.setDisable(true);
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);

        setToolTipBottom(btnNuevo, "Nuevo Contratista");
        setToolTipBottom(btnEditar, "Editar Contratista");
        setToolTipBottom(btnEliminar, "Eliminar Contratista");
        setToolTipBottom(btnAceptar, "Guardar Cambios");
        setToolTipBottom(btnVolver, "Volver al Menú Principal");

        tgTipoEmpresa.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                String opt = newValue.getUserData().toString();
                if (opt.equals("Natural")) {
                    lblRazonSocial.setText("Nombres:");
                    lblRuc.setText("DNI:");
                } else {
                    lblRazonSocial.setText("Razón Social:");
                    lblRuc.setText("RUC:");
                }
            }
        });

        tfBuscar.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    contratistaEdit = null;
                    btnEditar.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnAceptar.setDisable(true);
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

        btnNuevo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mostrarDetalles(null);
                setEditableFields(true);
                btnAceptar.setDisable(false);
                btnEditar.setDisable(true);
                btnEliminar.setDisable(true);
                contratistaEdit = null;
            }
        });

        btnEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Contratista contratistaSeleccionado = tblContratista.getSelectionModel().getSelectedItem();
                if (contratistaSeleccionado != null) {
                    contratistaEdit = contratistaSeleccionado;
                    setEditableFields(true);
                    btnAceptar.setDisable(false);
                    btnEditar.setDisable(true);
                    btnEliminar.setDisable(true);

                } else {
                    Dialogs.create()
                            .owner(application.stage)
                            .title("Imposible Editar ---")
                            .masthead(null)
                            .message("No ha seleccionado ninguna fila.")
                            .showWarning();
                }
            }
        });

        btnEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Contratista contratistaSeleccionado = tblContratista.getSelectionModel().getSelectedItem();
                if (contratistaSeleccionado != null) {
                    Action response = Dialogs.create()
                            .owner(application.stage)
                            .title("Confirmación para eliminar")
                            .masthead(null)
                            .message("Seguro que deseas eliminar el contratista: ".concat(contratistaSeleccionado.getRazonSocial()))
                            .actions(Dialog.Actions.YES, Dialog.Actions.CANCEL)
                            .showConfirm();

                    if (response == Dialog.Actions.YES) {
                        int rpta = bolDetalleEmpresa.eliminarDetalleEmpresa(contratistaSeleccionado.getCodDetalleEmpresa());
                        if (rpta > 0) {
                            bolContratista.eliminarContratista(contratistaSeleccionado.getCodContratista());

                            refrescarTableView();
                            btnAceptar.setDisable(true);
                            btnEditar.setDisable(true);
                            btnEliminar.setDisable(true);
                            setEditableFields(false);
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
                if (isInputValid()) {
                    if (contratistaEdit == null) {
                        Contratista nuevoContratista = new Contratista();
                        nuevoContratista.setCodContratista(tfCodigoContratista.getText());
                        nuevoContratista.setCodDetalleEmpresa(tfCodDetalleEmpresa.getText());
                        nuevoContratista.setRazonSocial(tfRazonSocial.getText());
                        nuevoContratista.setRuc(tfRuc.getText());
                        nuevoContratista.setTelefono(tfTelefono.getText());
                        nuevoContratista.setEmail(tfCorreo.getText());
                        nuevoContratista.setDireccion(tfDireccion.getText());
                        nuevoContratista.setEstadoDetEmp("1");
                        nuevoContratista.setNombreEncargado(tfNombreEncargado.getText());
                        nuevoContratista.setTipoEmpresa(tgTipoEmpresa.getSelectedToggle().getUserData().toString());
                        nuevoContratista.setEstadoContratista("1");

                        if (bolDetalleEmpresa.insertarDetalleEmpresa(nuevoContratista) > 0) {
                            if (bolContratista.insertarContratista(nuevoContratista) > 0) {
                                Dialogs.create()
                                        .owner(application.stage)
                                        .title("")
                                        .masthead(null)
                                        .message("Contratista Insertado Correctamente.")
                                        .showInformation();

                                tblContratista.getItems().add(nuevoContratista);
                                setEditableFields(false);
                                btnAceptar.setDisable(true);
                                btnEditar.setDisable(true);
                                btnEliminar.setDisable(true);
                            }
                        }
                    } else {
                        contratistaEdit.setRazonSocial(tfRazonSocial.getText());
                        contratistaEdit.setRuc(tfRuc.getText());
                        contratistaEdit.setTelefono(tfTelefono.getText());
                        contratistaEdit.setEmail(tfCorreo.getText());
                        contratistaEdit.setDireccion(tfDireccion.getText());
                        contratistaEdit.setEstadoDetEmp("1");
                        contratistaEdit.setNombreEncargado(tfNombreEncargado.getText());
                        contratistaEdit.setTipoEmpresa(tgTipoEmpresa.getSelectedToggle().getUserData().toString());
                        contratistaEdit.setEstadoContratista("1");

                        if (bolDetalleEmpresa.modificarDetalleEmpresa(contratistaEdit) > 0) {
                            if (bolContratista.modificarContratista(contratistaEdit) > 0) {
                                Dialogs.create()
                                        .owner(application.stage)
                                        .title("")
                                        .masthead(null)
                                        .message("Contratista Actualizado Correctamente.")
                                        .showInformation();

                                refrescarTableView();
                                contratistaEdit = null;
                                btnAceptar.setDisable(true);
                                btnEditar.setDisable(true);
                                btnEliminar.setDisable(true);
                                setEditableFields(false);
                            }
                        }
                    }
                }
            }
        });

        codigoContratista.setCellValueFactory(new PropertyValueFactory<Contratista, String>("codContratista"));
        razonSocial.setCellValueFactory(new PropertyValueFactory<Contratista, String>("razonSocial"));
        ruc.setCellValueFactory(new PropertyValueFactory<Contratista, String>("ruc"));
        telefono.setCellValueFactory(new PropertyValueFactory<Contratista, String>("telefono"));
        correo.setCellValueFactory(new PropertyValueFactory<Contratista, String>("email"));
        tipoEmpresa.setCellValueFactory(new PropertyValueFactory<Contratista, String>("tipoEmpresa"));

        tblContratista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contratista>() {
            @Override
            public void changed(ObservableValue<? extends Contratista> observable, Contratista oldValue, Contratista newValue) {
                mostrarDetalles(newValue);
                btnAceptar.setDisable(true);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
            }
        });

        tblContratista.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Contratista contratistaSeleccionado = tblContratista.getSelectionModel().getSelectedItem();
                mostrarDetalles(contratistaSeleccionado);
                btnAceptar.setDisable(true);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
            }
        });
    }

    private void setEditableFields(boolean action) {
        tfRazonSocial.setEditable(action);
        tfRuc.setEditable(action);
        tfNombreEncargado.setEditable(action);
        tfTelefono.setEditable(action);
        tfCorreo.setEditable(action);
        tfDireccion.setEditable(action);
    }

    private void mostrarDetalles(Contratista contratista) {
        if (contratista != null) {
            tfCodigoContratista.setText(contratista.getCodContratista());
            tfCodDetalleEmpresa.setText(contratista.getCodDetalleEmpresa());
            if (contratista.getTipoEmpresa().equals("Jurídica")) {
                rbJuridica.setSelected(true);
            } else {
                rbNatural.setSelected(true);
            }
            tfRazonSocial.setText(contratista.getRazonSocial());
            tfRuc.setText(contratista.getRuc().trim());
            tfNombreEncargado.setText((contratista.getNombreEncargado() != null) ? contratista.getNombreEncargado() : "");
            tfTelefono.setText((contratista.getTelefono() != null) ? contratista.getTelefono() : "");
            tfCorreo.setText((contratista.getEmail() != null) ? contratista.getEmail() : "");
            tfDireccion.setText((contratista.getDireccion() != null) ? contratista.getDireccion() : "");
        } else {
            int totalContratistas = bolContratista.getTotalContratistas() + 1;
            String codigoContratista = "00000000".concat(String.valueOf(totalContratistas));
            codigoContratista = codigoContratista.substring(codigoContratista.length() - 8, codigoContratista.length());
            codigoContratista = "C".concat(codigoContratista);
            tfCodigoContratista.setText(codigoContratista);
            int totalDetalleEmpresa = bolDetalleEmpresa.getTotalDetalleEmpresa() + 1;
            String codigoDetalleEmpresa = "0000000".concat(String.valueOf(totalDetalleEmpresa));
            codigoDetalleEmpresa = codigoDetalleEmpresa.substring(codigoDetalleEmpresa.length() - 7, codigoDetalleEmpresa.length());
            codigoDetalleEmpresa = "DE".concat(codigoDetalleEmpresa);
            tfCodDetalleEmpresa.setText(codigoDetalleEmpresa);
            rbJuridica.setSelected(true);
            tfRazonSocial.setText("");
            tfRuc.setText("");
            tfNombreEncargado.setText("");
            tfTelefono.setText("");
            tfCorreo.setText("");
            tfDireccion.setText("");
        }
    }

    private void refrescarTableView() {
        int selectedIndex = tblContratista.getSelectionModel().getSelectedIndex();
        tblContratista.getItems().clear();
        tblContratista.layout();
        tblContratista.setItems(bolContratista.fetchContratistas("", 0));
        tblContratista.getSelectionModel().select(selectedIndex);
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

    private boolean isInputValid() {

        String errorMessage = "";

        if (tgTipoEmpresa.getSelectedToggle().getUserData().equals("Jurídica")) {
            if (Validation.validateVoidFields(tfRazonSocial.getText())) {
                errorMessage += "\nEl campo \"Razon Social\" no debe estar vacío";
            }
            if (Validation.validateRUC(tfRuc.getText())) {
                errorMessage += "\nEl formato ingresado para el campo \"Ruc\" no es válido";
            }
            if (Validation.validateVoidFields(tfRuc.getText())) {
                errorMessage += "\nEl campo \"RUC\" no debe estar vacío";
            }
        } else {
            if (Validation.validateVoidFields(tfRazonSocial.getText())) {
                errorMessage += "\nEl campo \"Nombres\" no debe estar vacío";
            }
            if (Validation.validateDNI(tfRuc.getText())) {
                errorMessage += "\nEl formato ingresado para el campo \"DNI\" no es válido";
            }
            if (Validation.validateVoidFields(tfRuc.getText())) {
                errorMessage += "\nEl campo \"DNI\" no debe estar vacío";
            }
        }
        if (Validation.validateStringOnly(tfNombreEncargado.getText())) {
            errorMessage += "\nEl campo \"Encargado\" solo debe contener letras";
        }
        if (Validation.validateNumberPhone(tfTelefono.getText())) {
            errorMessage += "\nEl formato ingresado para el campo \"Telefono\" no es válido";
        }
        if (Validation.validateEmail(tfCorreo.getText())) {
            errorMessage += "\nEl correo no es válido (usuario@dominio.com)";
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

package com.ecys.controller;

import com.ecys.entidades.Usuario;
import com.ecys.presentacion.InformationDialog;
import com.ecys.presentacion.Main;
import com.ecys.util.Miscelanea;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author ecys
 */
public class MainController extends AnchorPane implements Initializable {

    private Main application;

    Stage stInformacion;

    @FXML
    HBox hbHandleTitle;
    @FXML
    Label lblFechaActual, lblDescripcion, lblMainTitle, lblUsuario;
    @FXML
    Button flechaNext, flechaPrev;
    @FXML
    Button btnCerrarSession, btnConfiguracion, btnBackup, btnAyuda, btnInformacion, btnSalir, btnAdministrar, btnReporte, btnEstadistica;
    @FXML
    AnchorPane imageViewPanel;

    private List<String> imageFiles = new ArrayList<>();
    private int currentIndex = 0;

    public enum ButtonMove {

        NEXT, PREV
    };

    private double stageX = 0, stageY = 0;

    private final String defaultText = "Bienvenido a \"El Koreano\"";

    public void setApp(Main application) {
        this.application = application;
        Usuario usuario = application.getLoggedUser();
        lblUsuario.setText("Bienvenido: " + usuario.getNombreUsuario());
        lblFechaActual.setText(Miscelanea.getFechaActual(false));
        lblDescripcion.setText(defaultText);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setDrag(hbHandleTitle);

        setToolTipBottom(btnCerrarSession, "Click para Cerrar Sesión");
        setToolTipBottom(btnAyuda, "Click para ver la Ayuda");
        setToolTipBottom(btnInformacion, "Click para ver la Información");
        setToolTipBottom(btnSalir, "Click para salir del Sistema");
        setToolTipBottom(btnAdministrar, "Click para Administrar Alquileres");

        generateImageView();

        btnAdministrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                application.showAdministrarAlquiler();
            }
        });

        btnAyuda.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*try {
                    File path = new File("jhug.pdf");
                    Desktop.getDesktop().open(path);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }*/

                try {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "jhug.pdf");
                    System.out.println("Final");
                } catch (IOException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        btnInformacion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showInformationDialog();
            }
        });

        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Action response = Dialogs.create()
                        .owner(application.stage)
                        .title("Confirmación para salir")
                        .masthead(null)
                        .message("Seguro que deseas salir del Sistema.")
                        .actions(Dialog.Actions.YES, Dialog.Actions.CANCEL)
                        .showConfirm();
                if (response == Dialog.Actions.YES) {
                    System.exit(0);
                }
            }
        });
    }

    public void generateImageView() {
        Group root = new Group();

        final ImageView currentImageView = new ImageView();
        currentImageView.setPreserveRatio(true);
        currentImageView.setFitWidth(400);

        Reflection reflection = new Reflection();
        reflection.setFraction(0.3);
        reflection.setTopOpacity(0.5);
        reflection.setTopOffset(-20);

        currentImageView.setEffect(reflection);

        final HBox pictureRegion = new HBox();
        pictureRegion.setAlignment(Pos.CENTER);
        pictureRegion.setPrefSize(1080, 350);
        pictureRegion.getChildren().add(currentImageView);
        root.getChildren().add(pictureRegion);

        imageFiles.add(0, "assets/alquiler.png");
        imageFiles.add(1, "assets/equipo.png");
        imageFiles.add(2, "assets/contratista.png");

        Image imageimage = new Image(imageFiles.get(0));
        currentImageView.setImage(imageimage);

        flechaPrev.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                int index = gotoImageIndex(ButtonMove.PREV);
                if (index > -1) {
                    String namePict = imageFiles.get(index);
                    final Image image = new Image(namePict);
                    currentImageView.setImage(image);
                    changeScreenAction(index);
                }
            }
        });

        flechaNext.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                int index = gotoImageIndex(ButtonMove.NEXT);
                if (index > -1) {
                    String namePict = imageFiles.get(index);
                    final Image image = new Image(namePict);
                    currentImageView.setImage(image);
                    changeScreenAction(index);
                }
            }
        });

        imageViewPanel.getChildren().add(root);
    }

    public int gotoImageIndex(ButtonMove direction) {
        int size = imageFiles.size();
        if (size == 0) {
            currentIndex = -1;
        } else if (direction == ButtonMove.NEXT && size > 1 && currentIndex < size - 1) {
            currentIndex += 1;
        } else if (direction == ButtonMove.PREV && size > 1 && currentIndex > 0) {
            currentIndex -= 1;
        }
        return currentIndex;
    }

    public void changeScreenAction(int index) {
        switch (index) {
            case 0:
                lblMainTitle.setText("ALQUILER");
                setToolTipBottom(btnAdministrar, "Click para Administrar Alquileres");
                btnAdministrar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        application.showAdministrarAlquiler();
                    }
                });
                break;
            case 1:
                lblMainTitle.setText("EQUIPOS");
                setToolTipBottom(btnAdministrar, "Click para Administrar Equipos");
                btnAdministrar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        application.showAdministrarMaquinarias();
                    }
                });
                break;
            case 2:
                lblMainTitle.setText("CONTRATISTAS");
                setToolTipBottom(btnAdministrar, "Click para Administrar Contratistas");
                btnAdministrar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        application.showAdministrarContratistas();
                    }
                });
                break;
            default:
                break;
        }
    }

    private Stage createInformationDialog(Stage parent, boolean modal) {
        if (stInformacion != null) {
            stInformacion.close();
        }
        try {
            return new InformationDialog(parent, modal, "Información");

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private void showInformationDialog() {
        try {
            if (stInformacion == null) {
                stInformacion = createInformationDialog(this.application.stage, true);
            }
            stInformacion.sizeToScene();
            stInformacion.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public void logoutAction() {
        if (application == null) {
            return;
        }
        application.userLogout();
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

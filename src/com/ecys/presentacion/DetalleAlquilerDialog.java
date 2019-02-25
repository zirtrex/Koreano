package com.ecys.presentacion;

import com.ecys.entidades.DetalleAlquiler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ecys
 */
public class DetalleAlquilerDialog extends Stage {

    public DetalleAlquilerDialog(Stage owner, boolean modality, String title) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("SeleccionarEquipoFXML.fxml"));
        initOwner(owner);
        Modality m = modality ? Modality.APPLICATION_MODAL : Modality.NONE;
        initModality(m);
        initStyle(StageStyle.UNDECORATED);
        setTitle(title);
        Scene scene = new Scene(root);
        setScene(scene);
    }
    static String codAlquiler;
    
    public static String getCodAlquiler(){
        return codAlquiler;
    }
    
    public static void setCodAlquiler(String _codAlquiler){
        codAlquiler = _codAlquiler;
    }

    static DetalleAlquiler detalleAlquiler;    

    public static void setDetalleAlquiler(DetalleAlquiler _detalleAlquiler) {
        detalleAlquiler = _detalleAlquiler;
    }

    public static DetalleAlquiler getDetalleAlquiler() {
        return detalleAlquiler;
    }
}

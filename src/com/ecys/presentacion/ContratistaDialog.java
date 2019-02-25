package com.ecys.presentacion;

import com.ecys.entidades.Contratista;
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

public class ContratistaDialog extends Stage {

    public ContratistaDialog(Stage owner, boolean modality, String title) throws Exception{
        
        Parent root = FXMLLoader.load(getClass().getResource("SeleccionarContratistaFXML.fxml"));
        initOwner(owner);
        Modality m = modality ? Modality.APPLICATION_MODAL : Modality.NONE;
        initModality(m);
        initStyle(StageStyle.UNDECORATED);
        setTitle(title);        
        Scene scene = new Scene(root);       
        setScene(scene);
    }
    
    static Contratista contratista;
    
    public static void setContratista(Contratista _contratista){
        contratista = _contratista;
    }
    
    public static Contratista getContratista(){
        return contratista;
    }
}

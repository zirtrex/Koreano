package com.ecys.presentacion;

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
public class InformationDialog extends Stage {

    public InformationDialog(Stage owner, boolean modality, String title) throws Exception{
        
        Parent root = FXMLLoader.load(getClass().getResource("InformacionFXML.fxml"));
        initOwner(owner);
        Modality m = modality ? Modality.APPLICATION_MODAL : Modality.NONE;
        initModality(m);
        initStyle(StageStyle.UNDECORATED);
        setTitle(title);        
        Scene scene = new Scene(root);        
        setScene(scene);
    }
}

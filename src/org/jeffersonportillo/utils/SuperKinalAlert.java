package org.jeffersonportillo.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class SuperKinalAlert {
    
    private static SuperKinalAlert instance;
    
    private SuperKinalAlert(){
    }
    
    public static SuperKinalAlert getInstance(){
        if(instance == null){
            instance = new SuperKinalAlert();
        }
        return instance;
    }
    
    
    public void mostrarAlertaInfo(int code){
        if(code == 400){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Pendientes");
            alert.setHeaderText("Campos pendientes");
            alert.setContentText("Algunos campos necesarios para el registro están pendientes");
            alert.showAndWait();
        }else if(code == 401){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmación de registro");
            alert.setHeaderText("Confirmación de registro");
            alert.setContentText("El registro de ha creado con éxito!");
            alert.showAndWait();
        }
    }
    
    public Optional<ButtonType> mostrarAlertaConfirmacion(int code){
        Optional<ButtonType> action = null;
        if(code == 405){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminación de registro");
            alert.setHeaderText("Eliminación de registro");
            alert.setContentText("¿Desea confirmar la eliminación del regitro?");
            action = alert.showAndWait();
        }else if(code == 106){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edición de registros");
            alert.setHeaderText("Edición de registros");
            alert.setContentText("¿Desea confirmar la edición del registro?");
            action = alert.showAndWait();
        }
        return action;
    }
}

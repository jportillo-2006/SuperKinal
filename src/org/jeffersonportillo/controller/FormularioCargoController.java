package org.jeffersonportillo.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.jeffersonportillo.dao.Conexion;
import org.jeffersonportillo.dto.CargoDTO;
import org.jeffersonportillo.model.Cargo;
import org.jeffersonportillo.system.Main;
import org.jeffersonportillo.utils.SuperKinalAlert;

public class FormularioCargoController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    
    @FXML
    TextField tfCargoId, tfNombreCargo;
    
    @FXML
    TextArea taDescripcion;
    
    @FXML
    Button btnGuardar, btnCancelar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(CargoDTO.getCargoDTO().getCargo() != null){
            cargarDatos(CargoDTO.getCargoDTO().getCargo());
        }
    }
    
    public void agregarCargos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCargo(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreCargo.getText());
            statement.setString(2, taDescripcion.getText());
            statement.execute();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void cargarDatos(Cargo cargo){
        tfCargoId.setText(Integer.toString(cargo.getCargoId()));
        tfNombreCargo.setText(cargo.getNombreCargo());
        taDescripcion.setText(cargo.getDescripcionCargo());
    }
    
    public void editarCargos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCargos(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            statement.setString(2, tfNombreCargo.getText());
            statement.setString(3, taDescripcion.getText());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            stage.menuCargoView();
            CargoDTO.getCargoDTO().setCargo(null);
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNombreCargo.getText().equals("") && !taDescripcion.getText().equals("")){
                    
                agregarCargos();
                SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                stage.menuCargoView(); 
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNombreCargo.requestFocus();
                    return;
                }  
            }else if(op == 2){
                if(!tfNombreCargo.getText().equals("") && !taDescripcion.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(406).get() == ButtonType.OK){
                        editarCargos();
                        CargoDTO.getCargoDTO().setCargo(null);
                        stage.menuCargoView();   
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNombreCargo.requestFocus();
                    return;
                }
                
            }

        }
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOp(int op) {
        this.op = op;
    }
    
    
        
}
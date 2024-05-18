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
import javafx.scene.control.TextField;
import org.jeffersonportillo.dao.Conexion;
import org.jeffersonportillo.dto.DistribuidorDTO;
import org.jeffersonportillo.model.Distribuidor;
import org.jeffersonportillo.system.Main;
import org.jeffersonportillo.utils.SuperKinalAlert;


public class FormularioDistribuidorController implements Initializable {

   private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    
    @FXML
    TextField tfDistribuidorId, tfNomDistribuidor, tfDirDistribuidor, tfNitDistribuidor, tfTelDistribuidor, tfWeb;
    @FXML
    Button btnGuardar, btnCancelar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            stage.menuDistribuidorView();
            DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNomDistribuidor.getText().equals("") && !tfDirDistribuidor.getText().equals("") && !tfNitDistribuidor.getText().equals("") && !tfTelDistribuidor.getText().equals("")){
                agregarDistribuidor();
                SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                stage.menuDistribuidorView(); 
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNomDistribuidor.requestFocus();
                    return;
                }  
            }else if(op == 2){
                if(!tfNomDistribuidor.getText().equals("") && !tfDirDistribuidor.getText().equals("") && !tfNitDistribuidor.getText().equals("") && !tfTelDistribuidor.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(406).get() == ButtonType.OK){
                        editarDistribuidor();
                        DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
                        stage.menuDistribuidorView();   
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNomDistribuidor.requestFocus();
                    return;
                }
                
            }

        }
    }
    
    public void cargarDatos(Distribuidor distribuidor){
        tfDistribuidorId.setText(Integer.toString(distribuidor.getDistribuidorId()));
        tfNomDistribuidor.setText(distribuidor.getNombre());
        tfDirDistribuidor.setText(distribuidor.getDireccion());
        tfNitDistribuidor.setText(distribuidor.getNit());
        tfTelDistribuidor.setText(distribuidor.getTelefono());
        tfWeb.setText(distribuidor.getWeb());
        
    }
    
    public void agregarDistribuidor(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDistribuidores(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNomDistribuidor.getText());
            statement.setString(2, tfDirDistribuidor.getText());
            statement.setString(3, tfNitDistribuidor.getText());
            statement.setString(4, tfTelDistribuidor.getText());
            statement.setString(5, tfWeb.getText());
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
    
    public void editarDistribuidor(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarDistribuidores(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDistribuidorId.getText()));
            statement.setString(2, tfNomDistribuidor.getText());
            statement.setString(3, tfDirDistribuidor.getText());
            statement.setString(4, tfNitDistribuidor.getText());
            statement.setString(5, tfTelDistribuidor.getText());
            statement.setString(6, tfWeb.getText());
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        if(DistribuidorDTO.getDistribuidorDTO().getDistribuidor() != null){
            cargarDatos(DistribuidorDTO.getDistribuidorDTO().getDistribuidor());
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

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
import org.jeffersonportillo.dto.ClienteDTO;
import org.jeffersonportillo.model.Cliente;
import org.jeffersonportillo.system.Main;
import org.jeffersonportillo.utils.SuperKinalAlert;

public class FormularioClienteController implements Initializable{
    private Main stage;
    private int op;

   
   private static Connection conexion;
   private static PreparedStatement statement;
   
    @FXML
    Button btnCancelar, btnGuardar;
    @FXML
    TextField tfClienteId,tfNombre,tfApellido,tfTelefono,tfDireccion,tfNit;

 
    
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        if(ClienteDTO.getClienteDTO().getCliente() != null){
            cargarDatos(ClienteDTO.getClienteDTO().getCliente());
        }
    }

    
    
   public void agregarCliente(){
       try{
           conexion = Conexion.getInstance().obtenerConexion();
           String sql = "call sp_agregarCliente(?,?,?,?,?)";
           statement = conexion.prepareStatement(sql);
           statement.setString(1, tfNombre.getText());
           statement.setString(2, tfApellido.getText());
           statement.setString(3, tfTelefono.getText());
           statement.setString(4, tfDireccion.getText());
           statement.setString(5, tfNit.getText());
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
               
           }
       }
   }
   
    public void cargarDatos(Cliente cliente){
        tfClienteId.setText(Integer.toString(cliente.getClienteId()));
        tfNombre.setText(cliente.getNombre());
        tfApellido.setText(cliente.getApellido());
        tfTelefono.setText(cliente.getTelefono());
        tfDireccion.setText(cliente.getDireccion());
        tfNit.setText(cliente.getNit());
    }
    
    public void editarCliente(){
        try{
           conexion = Conexion.getInstance().obtenerConexion();
           String sql = "call sp_editarCliente(?,?,?,?,?,?)";
           statement = conexion.prepareStatement(sql);
           statement.setInt(1,Integer.parseInt(tfClienteId.getText()));
           statement.setString(2, tfNombre.getText());
           statement.setString(3, tfApellido.getText());
           statement.setString(4, tfTelefono.getText());
           statement.setString(5, tfDireccion.getText());
           statement.setString(6, tfNit.getText());
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
               
           }
        }
    }
    
   @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource()== btnCancelar){
        stage.menuClienteView();
        ClienteDTO.getClienteDTO().setCliente(null);
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNombre.getText().equals("") && !tfApellido.getText().equals("")&& !tfDireccion.getText().equals("")){
                    
                agregarCliente();
                SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNombre.requestFocus();
                    return;
                }
                stage.menuClienteView();
            }else if(op == 2){
                
                 if(!tfNombre.getText().equals("") && !tfApellido.getText().equals("")&& !tfDireccion.getText().equals("")){
                     if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(406).get() == ButtonType.OK){
                        editarCliente();
                        ClienteDTO.getClienteDTO().setCliente(null);
                        stage.menuClienteView();
                     }
                    
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNombre.requestFocus();
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

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }
}

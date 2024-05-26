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
import org.jeffersonportillo.dto.CategoriaProductoDTO;
import org.jeffersonportillo.model.CategoriaProducto;
import org.jeffersonportillo.system.Main;
import org.jeffersonportillo.utils.SuperKinalAlert;

public class FormularioCategoriaProductoController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    
    @FXML
    TextField tfCategoriaId, tfNombreCategoria;
    
    @FXML
    TextArea taDescripcionCategoria;
    
    @FXML
    Button btnGuardar, btnCancelar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         if(CategoriaProductoDTO.getCategoriaProductoDTO().getCategoriaProducto() != null){
            cargarDatos(CategoriaProductoDTO.getCategoriaProductoDTO().getCategoriaProducto());
        }
    }
    
    public void agregarCategoria(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCategoriaProducto(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreCategoria.getText());
            statement.setString(2, taDescripcionCategoria.getText());
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
    
    public void cargarDatos(CategoriaProducto categoria){
        tfCategoriaId.setText(Integer.toString(categoria.getCategoriaProductoId()));
        tfNombreCategoria.setText(categoria.getNombreCategoria());
        taDescripcionCategoria.setText(categoria.getDescripcionCategoria());
    }
    
     public void editarCategoria(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCategoriaProductos(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCategoriaId.getText()));
            statement.setString(2, tfNombreCategoria.getText());
            statement.setString(3, taDescripcionCategoria.getText());
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
            stage.menuCategoriaProductoView();
            CategoriaProductoDTO.getCategoriaProductoDTO().setCategoriaProducto(null);
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNombreCategoria.getText().equals("") && !taDescripcionCategoria.getText().equals("")){
                    
                agregarCategoria();
                SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                stage.menuCategoriaProductoView(); 
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNombreCategoria.requestFocus();
                    return;
                }  
            }else if(op == 2){
                if(!tfNombreCategoria.getText().equals("") && !taDescripcionCategoria.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(406).get() == ButtonType.OK){
                        editarCategoria();
                        CategoriaProductoDTO.getCategoriaProductoDTO().setCategoriaProducto(null);
                        stage.menuCategoriaProductoView();   
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNombreCategoria.requestFocus();
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

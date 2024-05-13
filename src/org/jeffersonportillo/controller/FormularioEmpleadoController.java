package org.jeffersonportillo.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.jeffersonportillo.dao.Conexion;
import org.jeffersonportillo.dto.EmpleadoDTO;
import org.jeffersonportillo.model.Cargo;
import org.jeffersonportillo.model.Empleado;
import org.jeffersonportillo.system.Main;
import org.jeffersonportillo.utils.SuperKinalAlert;

public class FormularioEmpleadoController implements Initializable {
    
    private Main stage;    
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet = null;
    
    @FXML
    ComboBox cmbEmpleados, cmbCargos;
    @FXML
    TextField tfEmpleadoId, tfNombre, tfApellido, tfSueldo, tfEntrada, tfSalida;
    @FXML
    Button btnGuardar, btnCancelar;
    @FXML
    TableView tblEmpleados;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(EmpleadoDTO.getEmpleadoDTO().getEmpleado() != null){
            cargarDatos(EmpleadoDTO.getEmpleadoDTO().getEmpleado());
        }
        cmbCargos.setItems(listarCargos());
        cmbEmpleados.setItems(listarEmpleados());
    }    
    public void cargarDatos(Empleado empleado){
        tfEmpleadoId.setText(Integer.toString(empleado.getEmpleadoId()));
        tfNombre.setText(empleado.getNombreEmpleado());
        tfApellido.setText(empleado.getApellidoEmpleado());
        tfSueldo.setText(Double.toString(empleado.getSueldo()));
        tfEntrada.setText(empleado.getHoraEntrada());
        tfSalida.setText(empleado.getHoraSalida());
        cmbCargos.getSelectionModel().select(obtenerIndexCargo());
        cmbEmpleados.getSelectionModel().select(obtenerIndexEmpleado());
 
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
    public void agregarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarEmpleado(?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfApellido.getText());
            statement.setString(3, tfSueldo.getText());
            statement.setString(4, tfEntrada.getText());
            statement.setString(5, tfSalida.getText());
            statement.setInt(6,((Cargo)cmbCargos.getSelectionModel().getSelectedItem()).getCargoId());
            statement.setInt(7,((Empleado)cmbEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId());
 
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(conexion != null){
                    conexion.close();
                }else if(statement != null){
                    statement.close();
                }
            }catch(SQLException e){
            }
        }
    }
    public void editarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EditarEmpleado(?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfEmpleadoId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, tfApellido.getText());
            statement.setString(4, tfSueldo.getText());
            statement.setString(5, tfEntrada.getText());
            statement.setString(6, tfSalida.getText());
            statement.setInt(7,((Cargo)cmbCargos.getSelectionModel().getSelectedItem()).getCargoId());
            statement.setInt(8,((Empleado)cmbEmpleados.getSelectionModel().getSelectedItem()).getEncargadoId());
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
        if(event.getSource() == btnCancelar){
            stage.menuEmpleadoView();
            EmpleadoDTO.getEmpleadoDTO().setEmpleado(null);
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNombre.getText().equals("") && !tfApellido.getText().equals("")&& !tfSueldo.getText().equals("")&& !tfEntrada.getText().equals("")&& !tfSalida.getText().equals("")){
                    agregarEmpleado();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                    stage.menuEmpleadoView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNombre.requestFocus();
                    return;
                }
            }else if(op == 2){
                
                if(!tfNombre.getText().equals("") && !tfApellido.getText().equals("")&& !tfSueldo.getText().equals("")&& !tfEntrada.getText().equals("")&& !tfSalida.getText().equals("")){
                     if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(406).get() == ButtonType.OK){
                        editarEmpleado();
                        EmpleadoDTO.getEmpleadoDTO().setEmpleado(null);
                        stage.menuEmpleadoView();
                     }
                    
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNombre.requestFocus();
                    return;
                }
            }
        }
    }
    
    public void cargarDatosEditar(){
        Empleado e = (Empleado)tblEmpleados.getSelectionModel().getSelectedItem();
        if(e != null){
            tfEmpleadoId.setText(Integer.toString(e.getEmpleadoId()));
            tfNombre.setText(e.getNombreEmpleado());
            tfApellido.setText(e.getApellidoEmpleado());
            tfSueldo.setText(Double.toString(e.getSueldo()));
            tfEntrada.setText(e.getHoraEntrada());
            tfEntrada.setText(e.getHoraSalida());
            cmbCargos.getSelectionModel().select(obtenerIndexCargo());
            cmbEmpleados.getSelectionModel().select(obtenerIndexEmpleado());
        }
    }
    
    public int obtenerIndexEmpleado(){
        int index = 0;
        for(int i = 0 ; i <= cmbEmpleados.getItems().size() ; i++){
            String empleadoCmb = cmbEmpleados.getItems().get(i).toString();
            String empleadoTbl = ((Empleado)tblEmpleados.getSelectionModel().getSelectedItems()).getEncargado();
            if(empleadoCmb.equals(empleadoTbl)){
                index = i;
                break;
            }
            
        }
        return index;
    }
    
    public ObservableList<Cargo> listarCargos(){
        ArrayList<Cargo> cargos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCargos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nomCargo = resultSet.getString("nombreCargo");
                String desCargo = resultSet.getString("descripcionCargo");
                
                cargos.add(new Cargo(cargoId, nomCargo, desCargo));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
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
        return FXCollections.observableList(cargos);
    }
    
    public ObservableList<Empleado> listarEmpleados(){
        ArrayList<Empleado> empleados = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarEmpleados";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                double sueldo = resultSet.getDouble("sueldo");
                String horaEntrada = resultSet.getString("horaEntrada");
                String horaSalida = resultSet.getString("horaSalida");
                String cargo = resultSet.getString("cargo");
                String encargado = resultSet.getString("encargado");

                empleados.add(new Empleado(empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada,horaSalida,cargo,encargado));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
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
        
        return FXCollections.observableList(empleados);
    }
    
     public int obtenerIndexCargo(){
        int index = 0;
        for(int i = 0 ; i <= cmbEmpleados.getItems().size() ; i++){
            String cargoCmb = cmbEmpleados.getItems().get(i).toString();
            String cargoTbl = ((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCargo();
            if(cargoCmb.equals(cargoTbl)){
                index = i;
                break;
            }
        }
        return index;
    }
    
}

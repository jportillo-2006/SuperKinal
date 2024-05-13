package org.jeffersonportillo.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.jeffersonportillo.system.Main;

public class MenuPrincipalController implements Initializable{
    private Main stage;

    @FXML
    MenuItem btnMenuClientes,btnTicketSoporte, btnDistribuidor, btnCargos, btnEmpleados, btnProductos, btnCategoriaProductos,
            btnCompras, btnFacturas,btnPromociones;
    
    @Override
        public void initialize(URL location, ResourceBundle resources){
            
        }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource()== btnMenuClientes){
        stage.menuClientesView();
        }else if(event.getSource() == btnDistribuidor){
            stage.menuDistribuidorView();
        }else if(event.getSource() == btnCargos){
            stage.menuCargoView();
        }else if(event.getSource() == btnEmpleados){
            stage.menuEmpleadoView();
        }else if(event.getSource() == btnProductos){
            stage.menuProductoView();
        }else if(event.getSource() == btnCategoriaProductos){
            stage.menuCategoriaProductoView();
        }else if(event.getSource() == btnCompras){
            stage.menuCompraView();
        }else if(event.getSource() == btnFacturas){
            stage.menuFacturaView();
        }
    }
}

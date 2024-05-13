/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeffersonportillo.dto;

import org.jeffersonportillo.model.Cargo;

public class CargoDTO {
private static CargoDTO instance;
    private Cargo cargo;

    public CargoDTO() {
    }
    
    public static CargoDTO getCargoDTO(){
        if(instance == null){
            instance = new CargoDTO();
        }
        return instance;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}

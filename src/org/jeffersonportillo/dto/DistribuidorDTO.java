/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeffersonportillo.dto;

import org.jeffersonportillo.model.Distribuidor;

public class DistribuidorDTO {
    
    private static DistribuidorDTO instance;
    private Distribuidor distribuidor;
    
    private DistribuidorDTO(){
        
    }
    
    public static DistribuidorDTO getDistribuidorDTO(){
        if(instance == null){
            instance = new DistribuidorDTO();
        }
        return instance;
    }

    public Distribuidor getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(Distribuidor distribuidor) {
        this.distribuidor = distribuidor;
    }
}

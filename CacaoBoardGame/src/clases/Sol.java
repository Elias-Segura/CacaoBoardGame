/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;

/**
 *
 * @author Elias
 */
public final class Sol implements Serializable {

    
   
    public int id; 

    public Sol(  int id) {

        this.id = id; 
    }
    int getID(){
        return id; 
    }
    void setID(int id){
        this.id = id;
    }

    
}
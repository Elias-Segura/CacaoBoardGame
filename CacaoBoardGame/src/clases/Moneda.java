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
public final class Moneda implements Serializable {

    
    public int valor; 
    public int id; 

    public Moneda( int valor, int id) {
        
        this.valor = valor; 
        this.id = id; 
    }
    
    public int getValor(){
        return valor; 
    }
    void setValor(int valor){
        this.valor = valor;
    }
    int getID(){
        return id; 
    }
    void setID(int id){
        this.id = id;
    }
    
}
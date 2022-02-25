/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacaoclient;

import java.io.Serializable;

/**
 *
 * @author Elias
 */
public final class infoClient implements Serializable {
	public String color;
        public String nombre;
        public int edad; 
        
	 infoClient(String color, String nombre, int edad) {
		this.color = color; 
                this.nombre = nombre; 
                this.edad=  edad;  
	}
         
         public String getNombre(){
             return this.nombre;
         }
         
         public String getColor(){
          return this.color; 
         }
         
         public int edad(){
            return this.edad; 
         }
         
}
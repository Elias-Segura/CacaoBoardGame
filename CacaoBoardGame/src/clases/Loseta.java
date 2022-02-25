/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import javafx.scene.image.Image;

/**
 *
 * @author Elias
 */
public final class Loseta implements Serializable {

    public int id;
    public int idPlayer = -1;

    public String nombre;
    public String urlImage="/";
    String color = "transparent";
    String tipo;
    public int valor;
    public int tmpvalor;
    int up = 0;
    int right = 0;
    int down = 0;
   int left = 0;
   
       int upR = 0;
    int rightR = 0;
    int downR = 0;
   int leftR = 0;
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
        
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }
  

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    int rotation = 0;

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
  
        Integer[] r = new Integer[]{upR, rightR, downR, leftR};
        
     
        this.rotation = rotation;

        if (rotation == 0 || rotation == 360) {

            up = r[0];
            right = r[1];
            down = r[2];
            left = r[3];
            
            
        } else if (rotation == 90) {

            up = r[3];
            right = r[0];
            down = r[1];
            left = r[2];
        } else if (rotation == 180) {

            up = r[2];
            right = r[3];
            down = r[0];
            left = r[1];
        } else if (rotation == 270) {

            up = r[1];
            right = r[2];
            down = r[3];
            left = r[0];
        }
        
        

    }

    public Loseta(int id, String tipo, String nombre, String urlImage, int valor) {
        this.id = id;
        this.nombre = nombre;
        this.urlImage = urlImage;
        this.valor = valor;
        this.color = "transparent";

        this.tipo = tipo;

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Loseta(int id, String tipo, String nombre, String urlImage) {
        this.id = id;
        this.nombre = nombre;
        this.urlImage = urlImage;
        this.tipo = tipo;

    }

    public Loseta(int id, String tipo, String nombre, String urlImage, int up, int right, int down, int left) {
        this.id = id;
        this.nombre = nombre;
        this.urlImage = urlImage;
        this.tipo = tipo;
        this.up = this.upR = up;
        this.right = this.rightR= right;
        this.down = this.downR = down;
        this.left = this.leftR = left;

    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    int getID() {
        return id;
    }

    void setID(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}

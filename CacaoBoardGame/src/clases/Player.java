/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import cacaoclient.infoClient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javafx.scene.image.Image;

/**
 *
 * @author Elias
 */
public final class Player implements Serializable {

    infoClient info;
    Boolean Finish = false; 
    int n; 
    int cenote = 1; 
    public int tmpvalue=0;
    public int puntos = 0;
    public int puntosTemplo = 0;
    int [] cenotePuntos = new int[]{-10, -4,-1,0,2,4,7,11,16};

    public int getPuntosTemplo() {
        return puntosTemplo;
    }

    public void setPuntosTemplo(int puntosTemplo) {
        this.puntosTemplo = puntosTemplo;
    }
    
    public Boolean getFinish() {
        return Finish;
    }

    public void setFinish(Boolean Finish) {
        this.Finish = Finish;
    }

    
    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    
    public int getCenote() {
        return cenote;
    }
    public int getPuntosCenotes(){
        return cenotePuntos[cenote - 1];
    }
    public void setCenote(int cenote) {
        if(cenote<=9){
         this.cenote = cenote;
        }else{
         this.cenote = 9;
        }
       
    }
    
    public infoClient getInfo() {
        return info;
    }

    public void setInfo(infoClient info) {
        this.info = info;
    }
    int id;
    ArrayList<Loseta> fichas = new ArrayList<>();
    ArrayList<Loseta> manoInicial = new ArrayList<>();

    ArrayList<Moneda> monedas = new ArrayList<>();
    ArrayList<Sol> soles = new ArrayList<>();
  public void popLoseta( String valor) {
        List<Loseta> filteredList = fichas.stream().filter(s -> s.getNombre().equals(valor)).collect(Collectors.toList());

        fichas.remove(filteredList.get(filteredList.size() - 1));

    
  }

    public ArrayList<Moneda> getMonedas() {
        return monedas;
    }
    public int contarMonedas(){
         n = 0 ; 
        for (int i = 0; i < monedas.size(); i++) {
            n+= monedas.get(i).getValor(); 

        }
        return n ; 
    
    }
    public void setMonedas(ArrayList<Moneda> monedas) {
        this.monedas = monedas;
    }

    public ArrayList<Sol> getSoles() {
        return soles;
    }

    public void setSoles(ArrayList<Sol> soles) {
        this.soles = soles;
    }

    public ArrayList<Cacao> getCacaos() {
        return cacaos;
    }

    public void setCacaos(ArrayList<Cacao> cacaos) {
        this.cacaos = cacaos;
    }
    ArrayList<Cacao> cacaos = new ArrayList<>();

    int rowIndex;
    Random random = new Random();

    public Player(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public ArrayList<Loseta> getFichas() {
        return fichas;
    }

    public void setFichas(ArrayList<Loseta> fichas) {
        this.fichas = fichas;
    }

    public ArrayList<Loseta> getManoInicial() {
        return manoInicial;
    }

    public void setManoInicial(ArrayList<Loseta> manoInicial) {
        this.manoInicial = manoInicial;
    }

    public void losetasSelva(int n, String nombre, String urlImage, String color, int up, int right, int down, int left) {
        for (int i = 0; i < n; i++) {
            Loseta s = new Loseta(i, "J", nombre, urlImage, up, right,down, left );
            s.setIdPlayer(id);
            s.setColor(color);
            fichas.add(s);
        }
    }

    public void generarManoInicial(int n) {

        if (n != 0 && fichas.size() > 0) {
            n -= 1;

            rowIndex =random.nextInt((fichas.size() - 1));
            manoInicial.add(fichas.get(rowIndex));
            fichas.remove(rowIndex);

            generarManoInicial(n);

        }

    }

}

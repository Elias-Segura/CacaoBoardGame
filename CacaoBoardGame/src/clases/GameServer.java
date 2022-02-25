/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author Elias
 */
public final class GameServer implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;
    Random random = new Random();
    ArrayList<Loseta> losetasSelva = new ArrayList<>();
    ArrayList<Moneda> monedas = new ArrayList<>();
    ArrayList<Sol> soles = new ArrayList<>();
    ArrayList<Player> playersDisconnected = new ArrayList<>();
    ArrayList<Cacao> cacaos = new ArrayList<>();
    ArrayList<Loseta> selvaExplorada = new ArrayList<>();
    Boolean started = false;
    public Loseta[][] tablero = new Loseta[50][50];
    int[] turnsID = new int[4];
    int turnActive = -1;
    int idB = -1;
    int actualPositionTurn = 0;
    int sizePlayers = 0;
    int rowIndex;
    int colIndex;
    int ans = 0;
    int pOnline = 0;
    int lastID = 0;

    int i = 0;
    int j = 0;
    int puntos = 0;
    List<Player> filteredListPlayer = null;
    List<Loseta> filteredList = null;
    int[] values = new int[]{0, 0, 0, 0};
    List<Moneda> filteredListMoneda = null;
    ArrayList<Loseta> tmp = new ArrayList<>();

    public ArrayList<Player> getPlayersDisconnected() {
        return playersDisconnected;
    }

    public void setPlayersDisconnected(ArrayList<Player> playersDisconnected) {
        this.playersDisconnected = playersDisconnected;
    }

    public int getpOnline() {
        return pOnline;
    }

    public void setpOnline(int pOnline) {
        this.pOnline = pOnline;
    }

    Boolean b = false;

    public GameServer() {

    }

    public int getLastID() {
        return lastID;
    }

    public void setLastID(int lastID) {
        this.lastID = lastID;
    }

    public int getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public int getIdB() {
        return idB;
    }

    public void setIdB(int idB) {
        this.idB = idB;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public void removeChildresTablero() {

        for (i = 0; i < 50; i++) {
            for (j = 0; j < 50; j++) {
                tablero[i][j] = null;
            }
        }

    }

    public void generateTurns(int idM, ArrayList<Player> players) {
        sizePlayers = players.size();
        turnsID[0] = idM;
        turnActive = idM;
        int n = 1;
        for (i = 0; i < players.size(); i++) {

            if (players.get(i).getId() != idM) {
                turnsID[n] = players.get(i).getId();
                n += 1;
            }

        }

    }

    public void nextTurn() {

        if (actualPositionTurn + 1 < sizePlayers) {
            actualPositionTurn += 1;
            turnActive = turnsID[actualPositionTurn];

        } else {
            actualPositionTurn = 0;
            turnActive = turnsID[actualPositionTurn];
        }

    }

    public void setTurnActive(int turnActive) {
        this.turnActive = turnActive;
    }

    public int getActualTurn() {
        return turnActive;
    }

    public void insertInTablero(int r, int c, Loseta loseta) {
        tablero[r][c] = loseta;

    }

    public void generarSelvaExplorada(int n) {

        if (n != 0 && losetasSelva.size() > 0) {
            n -= 1;

            rowIndex = random.nextInt((losetasSelva.size() - 1));
            selvaExplorada.add(losetasSelva.get(rowIndex));
            losetasSelva.remove(rowIndex);

            generarSelvaExplorada(n);

        }

    }

    public void removeDisconnected(Player p) {
        try {
            filteredListPlayer = playersDisconnected.stream().filter(sub -> sub.getId() == p.getId()).collect(Collectors.toList());

            if (filteredListPlayer.size() > 0) {

                playersDisconnected.remove(filteredListPlayer.get(0));
            }

        } catch (Exception e) {
        }
    }

    public void addDisconnected(Player p) {
        filteredListPlayer = playersDisconnected.stream().filter(sub -> sub.getId() == p.getId()).collect(Collectors.toList());

        if (filteredListPlayer.isEmpty()) {
            playersDisconnected.add(p);
        }
    }

    public void losetasIniciales() {
        filteredList = losetasSelva.stream().filter(sub -> sub.getNombre().contains("plantacionSimple")).collect(Collectors.toList());

        tablero[24][24] = filteredList.get(filteredList.size() - 1);
        losetasSelva.remove(filteredList.get(filteredList.size() - 1));

        filteredList = losetasSelva.stream().filter(sub -> sub.getNombre().contains("mercado") && sub.getValor() == 2).collect(Collectors.toList());
        tablero[25][25] = filteredList.get(filteredList.size() - 1);
        losetasSelva.remove(filteredList.get(filteredList.size() - 1));

    }

    public Node getNodeByRowColumnIndex(int row, int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if (node.idProperty().get() != null) {
                if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                    result = node;

                }
            }

        }

        return result;
    }

    public void SetNodeByRowColumnIndexStyle(int row, int column, GridPane gridPane, String style) {

        try {
            ObservableList<Node> childrens = gridPane.getChildren();

            childrens.stream().filter((node) -> (node.idProperty().get() != null)).filter((node) -> (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column)).forEach((node) -> {
                node.setStyle(style);
            });
        } catch (Exception e) {
        }

    }

    public void popCacao(int n, Player p) {

        if (n != 0) {
            cacaos.add(p.getCacaos().get(0));
            p.getCacaos().remove(0);
            n -= 1;
            popCacao(n, p);
        }

    }

    public void cobrarMoneda(int n, Player p) {

        if (n != 0) {

            if (n >= 5) {
                n -= 5;

                p.getMonedas().add(popMoneda(monedas, 5));
            } else {
                n -= 1;

                p.getMonedas().add(popMoneda(monedas, 1));
            }

            cobrarMoneda(n, p);

        }

    }

    public void cobrarCacao(int n, int c, Player p) {

        if (n != 0) {
            n -= 1;

            if (p.getCacaos().size() == 5) {
                n = 0;
            } else {
                if (c == 1) {
                    p.getCacaos().add(cacaos.remove(0));
                } else if (c == 2) {
                    p.getCacaos().add(cacaos.remove(0));
                    if (p.getCacaos().size() == 5) {
                        n = 0;
                    } else {
                        p.getCacaos().add(cacaos.remove(0));
                    }
                }

            }

            cobrarCacao(n, c, p);

        }

    }

    public void cobrarSol(int n, Player p) {
        if (n != 0) {
            if (p.getSoles().size() < 3) {
                p.getSoles().add(soles.remove(0));
            }

            n -= 1;
            cobrarSol(n, p);
        }

    }

    public int getCantidadCobro(int c, int r) {
        if (c > r) {
            return r;
        } else {
            return c;
        }
    }

    public void ajusteMonedas(ArrayList<Player> players, Player player) {

        for (i = 0; i < players.size(); i++) {

            try {
                while (contarMonedas(players.get(i).getMonedas(), 5) >= 2 && contarMonedas(monedas, 10) >= 1) {

                    monedas.add(popMoneda(players.get(i).getMonedas(), 5));
                    monedas.add(popMoneda(players.get(i).getMonedas(), 5));
                    players.get(i).getMonedas().add(popMoneda(monedas, 10));

                }
                while (contarMonedas(players.get(i).getMonedas(), 5) >= 1 && contarMonedas(players.get(i).getMonedas(), 1) >= 5 && contarMonedas(monedas, 10) >= 1) {

                    monedas.add(popMoneda(players.get(i).getMonedas(), 5));
                    monedas.add(popMoneda(players.get(i).getMonedas(), 1));
                    monedas.add(popMoneda(players.get(i).getMonedas(), 1));
                    monedas.add(popMoneda(players.get(i).getMonedas(), 1));
                    monedas.add(popMoneda(players.get(i).getMonedas(), 1));
                    monedas.add(popMoneda(players.get(i).getMonedas(), 1));
                    players.get(i).getMonedas().add(popMoneda(monedas, 10));

                }
                if (contarMonedas(players.get(i).getMonedas(), 1) >= 5 && contarMonedas(monedas, 5) >= 1) {

                    monedas.add(popMoneda(players.get(i).getMonedas(), 1));
                    monedas.add(popMoneda(players.get(i).getMonedas(), 1));
                    monedas.add(popMoneda(players.get(i).getMonedas(), 1));
                    monedas.add(popMoneda(players.get(i).getMonedas(), 1));
                    monedas.add(popMoneda(players.get(i).getMonedas(), 1));
                    players.get(i).getMonedas().add(popMoneda(monedas, 5));

                }
                if (players.get(i).getId() == player.getId()) {
                    player = players.get(i);
                }
            } catch (Exception e) {
            }

        }
    }

    public void payMent(int row, int col, GridPane grid, Player player, ArrayList<Player> players) {

        ajusteMonedas(players, player);

        for (rowIndex = 0; rowIndex < this.tablero.length; rowIndex++) {
            for (colIndex = 0; colIndex < this.tablero.length; colIndex++) {
                if (tablero[rowIndex][colIndex] != null) {

                    if (tablero[rowIndex][colIndex].idPlayer == player.getId() && rowIndex == row && colIndex + 1 == col) {
                        if (tablero[rowIndex][colIndex].getRight() != 0) {
                            if (this.tablero[row][col].getNombre().contains("plantacion")) {
                                cobrarCacao(tablero[rowIndex][colIndex].getRight(), tablero[row][col].getValor(), player);
                                tablero[rowIndex][colIndex].setRight(0);
                            }
                            if (this.tablero[row][col].getNombre().contains("mercado")) {

                                cobrarMoneda(getCantidadCobro(player.getCacaos().size(), tablero[rowIndex][colIndex].getRight()) * tablero[row][col].getValor(), player);
                                popCacao(getCantidadCobro(player.getCacaos().size(), tablero[rowIndex][colIndex].getRight()), player);
                                tablero[rowIndex][colIndex].setRight(0);

                            }
                            if (this.tablero[row][col].getNombre().contains("minaOro")) {
                                cobrarMoneda(tablero[rowIndex][colIndex].getRight() * tablero[row][col].getValor(), player);

                                tablero[rowIndex][colIndex].setRight(0);

                            }

                            if (this.tablero[row][col].getNombre().contains("centroCultoSolar")) {

                                cobrarSol(tablero[rowIndex][colIndex].getRight(), player);
                                tablero[rowIndex][colIndex].setRight(0);

                            }

                            if (this.tablero[row][col].getNombre().contains("centroCultoSolar")) {

                                cobrarSol(tablero[rowIndex][colIndex].getRight(), player);
                                tablero[rowIndex][colIndex].setRight(0);

                            }

                            if (this.tablero[row][col].getNombre().contains("cenote")) {

                                player.setCenote(player.getCenote() + tablero[rowIndex][colIndex].getRight());

                                tablero[rowIndex][colIndex].setRight(0);

                            }
                        }
                    }

                    if (tablero[rowIndex][colIndex].idPlayer == player.getId() && rowIndex == row && colIndex - 1 == col) {
                        if (tablero[rowIndex][colIndex].getLeft() != 0) {
                            if (this.tablero[row][col].getNombre().contains("plantacion")) {
                                cobrarCacao(tablero[rowIndex][colIndex].getLeft(), tablero[row][col].getValor(), player);
                                tablero[rowIndex][colIndex].setLeft(0);
                            }
                            if (this.tablero[row][col].getNombre().contains("mercado")) {

                                cobrarMoneda(getCantidadCobro(player.getCacaos().size(), tablero[rowIndex][colIndex].getLeft()) * tablero[row][col].getValor(), player);
                                popCacao(getCantidadCobro(player.getCacaos().size(), tablero[rowIndex][colIndex].getLeft()), player);
                                tablero[rowIndex][colIndex].setLeft(0);

                            }
                            if (this.tablero[row][col].getNombre().contains("minaOro")) {
                                cobrarMoneda(tablero[rowIndex][colIndex].getLeft() * tablero[row][col].getValor(), player);

                                tablero[rowIndex][colIndex].setLeft(0);

                            }
                            if (this.tablero[row][col].getNombre().contains("centroCultoSolar")) {

                                cobrarSol(tablero[rowIndex][colIndex].getLeft(), player);
                                tablero[rowIndex][colIndex].setLeft(0);

                            }

                            if (this.tablero[row][col].getNombre().contains("cenote")) {

                                player.setCenote(player.getCenote() + tablero[rowIndex][colIndex].getLeft());

                                tablero[rowIndex][colIndex].setLeft(0);

                            }
                        }
                    }
                    if (tablero[rowIndex][colIndex].idPlayer == player.getId() && rowIndex + 1 == row && colIndex == col) {
                        if (tablero[rowIndex][colIndex].getDown() != 0) {
                            if (this.tablero[row][col].getNombre().contains("plantacion")) {
                                cobrarCacao(tablero[rowIndex][colIndex].getDown(), tablero[row][col].getValor(), player);
                                tablero[rowIndex][colIndex].setDown(0);
                            }
                            if (this.tablero[row][col].getNombre().contains("mercado")) {

                                cobrarMoneda(getCantidadCobro(player.getCacaos().size(), tablero[rowIndex][colIndex].getDown()) * tablero[row][col].getValor(), player);
                                popCacao(getCantidadCobro(player.getCacaos().size(), tablero[rowIndex][colIndex].getDown()), player);
                                tablero[rowIndex][colIndex].setDown(0);

                            }

                            if (this.tablero[row][col].getNombre().contains("minaOro")) {
                                cobrarMoneda(tablero[rowIndex][colIndex].getDown() * tablero[row][col].getValor(), player);

                                tablero[rowIndex][colIndex].setDown(0);

                            }

                            if (this.tablero[row][col].getNombre().contains("centroCultoSolar")) {

                                cobrarSol(tablero[rowIndex][colIndex].getDown(), player);
                                tablero[rowIndex][colIndex].setDown(0);

                            }

                            if (this.tablero[row][col].getNombre().contains("cenote")) {

                                player.setCenote(player.getCenote() + tablero[rowIndex][colIndex].getDown());

                                tablero[rowIndex][colIndex].setDown(0);

                            }
                        }
                    }
                    if (tablero[rowIndex][colIndex].idPlayer == player.getId() && rowIndex - 1 == row && colIndex == col) {
                        if (tablero[rowIndex][colIndex].getUp() != 0) {
                            if (this.tablero[row][col].getNombre().contains("plantacion")) {
                                cobrarCacao(tablero[rowIndex][colIndex].getUp(), tablero[row][col].getValor(), player);
                                tablero[rowIndex][colIndex].setUp(0);
                            }
                            if (this.tablero[row][col].getNombre().contains("mercado")) {
                                cobrarMoneda(getCantidadCobro(player.getCacaos().size(), tablero[rowIndex][colIndex].getUp()) * tablero[row][col].getValor(), player);
                                popCacao(getCantidadCobro(player.getCacaos().size(), tablero[rowIndex][colIndex].getUp()), player);
                                tablero[rowIndex][colIndex].setUp(0);

                            }

                            if (this.tablero[row][col].getNombre().contains("minaOro")) {
                                cobrarMoneda(tablero[rowIndex][colIndex].getUp() * tablero[row][col].getValor(), player);

                                tablero[rowIndex][colIndex].setUp(0);

                            }

                            if (this.tablero[row][col].getNombre().contains("centroCultoSolar")) {

                                cobrarSol(tablero[rowIndex][colIndex].getUp(), player);
                                tablero[rowIndex][colIndex].setUp(0);

                            }

                            if (this.tablero[row][col].getNombre().contains("cenote")) {

                                player.setCenote(player.getCenote() + tablero[rowIndex][colIndex].getUp());

                                tablero[rowIndex][colIndex].setUp(0);

                            }
                        }
                    }
                }
            }
        }
        ajusteMonedas(players, player);

        if (getNodeByRowColumnIndex(row, col, grid) != null) {

            getNodeByRowColumnIndex(row, col, grid).setStyle(" -fx-background-color: " + this.tablero[row][col].getColor() + "; -fx-background-image: url(" + this.tablero[row][col].getUrlImage() + "); -fx-background-size: 100% 100% ;");

        }
    }

    public Boolean markWherePutLSCheck(int rw1, int cl1, int rw2, int cl2) {

        try {

            return tablero[rw1][cl1] != null && tablero[rw1][cl1].getTipo().equals("J") && tablero[rw2][cl2] != null && tablero[rw2][cl2].getTipo().equals("J");

        } catch (Exception e) {
            return false;
        }

    }
    // devuelve true si hay que poner losetgas de selva y lo marca 

    public Boolean markWherePutLSVerify(GridPane grid, int row, int col) {

        b = false;
        try {
            for (rowIndex = 0; rowIndex < this.tablero.length; rowIndex++) {
                for (colIndex = 0; colIndex < this.tablero.length; colIndex++) {
                    if (tablero[rowIndex][colIndex] != null) {
                        if (tablero[rowIndex][colIndex].getTipo().equals("S")) {
                            if (markWherePutLSCheck(rowIndex, colIndex - 1, rowIndex - 1, colIndex)) {
                                if (tablero[rowIndex - 1][colIndex - 1] == null && row == rowIndex - 1 && col == colIndex - 1) {
                                    return true;
                                }

                            }
                            if (markWherePutLSCheck(rowIndex, colIndex + 1, rowIndex - 1, colIndex)) {

                                if (tablero[rowIndex - 1][colIndex + 1] == null && row == rowIndex - 1 && col == colIndex + 1) {
                                    return true;
                                }
                            }
                            if (markWherePutLSCheck(rowIndex, colIndex + 1, rowIndex + 1, colIndex)) {

                                if (tablero[rowIndex + 1][colIndex + 1] == null && row == rowIndex + 1 && col == colIndex + 1) {
                                    return true;

                                }
                            }

                            if (markWherePutLSCheck(rowIndex + 1, colIndex, rowIndex, colIndex - 1)) {

                                if (tablero[rowIndex + 1][colIndex - 1] == null && row == rowIndex + 1 && col == colIndex - 1) {
                                    return true;

                                }
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public Boolean markWherePutLS(GridPane grid, String style) {

        b = false;
        try {
            for (rowIndex = 0; rowIndex < this.tablero.length; rowIndex++) {
                for (colIndex = 0; colIndex < this.tablero.length; colIndex++) {
                    if (tablero[rowIndex][colIndex] != null) {
                        if (tablero[rowIndex][colIndex].getTipo().equals("S")) {
                            if (markWherePutLSCheck(rowIndex, colIndex - 1, rowIndex - 1, colIndex)) {
                                if (tablero[rowIndex - 1][colIndex - 1] == null) {
                                    getNodeByRowColumnIndex(rowIndex - 1, colIndex - 1, grid).setStyle(style);
                                    b = true;
                                }

                            }
                            if (markWherePutLSCheck(rowIndex, colIndex + 1, rowIndex - 1, colIndex)) {

                                if (tablero[rowIndex - 1][colIndex + 1] == null) {
                                    getNodeByRowColumnIndex(rowIndex - 1, colIndex + 1, grid).setStyle(style);
                                    b = true;
                                }
                            }
                            if (markWherePutLSCheck(rowIndex, colIndex + 1, rowIndex + 1, colIndex)) {

                                if (tablero[rowIndex + 1][colIndex + 1] == null) {
                                    getNodeByRowColumnIndex(rowIndex + 1, colIndex + 1, grid).setStyle(style);
                                    b = true;

                                }
                            }

                            if (markWherePutLSCheck(rowIndex + 1, colIndex, rowIndex, colIndex - 1)) {

                                if (tablero[rowIndex + 1][colIndex - 1] == null) {
                                    getNodeByRowColumnIndex(rowIndex + 1, colIndex - 1, grid).setStyle(style);
                                    b = true;

                                }
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            return b;
        }
        return b;
    }
    // chequea si hay mas acciones que hacer por ejemplo poner mas losetas de selva o cobrar 

    public Boolean checkIfIsThereMoreToPut(GridPane gridPane, String style) {
        b = false;
        ObservableList<Node> childrens = gridPane.getChildren();

        childrens.stream().forEach((node) -> {
            try {
                if (node != null) {
                    if (node.getStyle().contains(style)) {
                        b = true;

                    }
                }
            } catch (Exception e) {

            }
        });
        return b;
    }

    // borra fondo de los campos donde se podian colocar losetas de jugador. 
    public void cleanAvailableSpaces(GridPane gridPane, String Style) {

        ObservableList<Node> childrens = gridPane.getChildren();

        childrens.stream().forEach((node) -> {
            try {
                if (node != null) {
                    if (node.getStyle().contains(Style)) {
                        node.setStyle("-fx-background-color :transparent; ");
                    }
                }
            } catch (Exception e) {
            }
        });
    }

    //marca dond se puede jugar 
    public void markAvailableSpacesIF(GridPane grid, String style, int row, int col) {
        if (tablero[row][col] == null) {
            SetNodeByRowColumnIndexStyle(row, col, grid, style);
        }
    }

    public Boolean markAvailableSpacesIF(int row, int col) {
        if (tablero[row][col] == null) {
            return true;
        }
        return false;
    }

    public void markAvailableSpaces(GridPane grid, String style, Player player) {

        for (rowIndex = 0; rowIndex < this.tablero.length; rowIndex++) {
            for (colIndex = 0; colIndex < this.tablero.length; colIndex++) {
                if (tablero[rowIndex][colIndex] != null) {
                    switch (tablero[rowIndex][colIndex].getTipo()) {
                        case "S":
                            markAvailableSpacesIF(grid, style, rowIndex, colIndex + 1);
                            markAvailableSpacesIF(grid, style, rowIndex, colIndex - 1);
                            markAvailableSpacesIF(grid, style, rowIndex + 1, colIndex);
                            markAvailableSpacesIF(grid, style, rowIndex - 1, colIndex);
                            break;
                        case "J":
                            if (losetasSelva.isEmpty() && selvaExplorada.isEmpty() && player.getSoles().size() >= 1 && player.getId() == tablero[rowIndex][colIndex].getIdPlayer()) {

                                SetNodeByRowColumnIndexStyle(rowIndex, colIndex, grid, style);

                            }
                            break;
                    }
                }

            }
        }

    }



//marcar con borde rojo los campos que se pueden cobrar
    public void findPaymentIf(int value, int row, int col) {

    }

    public Boolean findPayment(int id, GridPane grid, Player player) {
        b = false;
        for (rowIndex = 0; rowIndex < this.tablero.length; rowIndex++) {
            for (colIndex = 0; colIndex < this.tablero.length; colIndex++) {
                if (tablero[rowIndex][colIndex] != null) {

                    if (tablero[rowIndex][colIndex].idPlayer == id) {
                        try {
                            if (tablero[rowIndex][colIndex].getRight() != 0) {

                                if (colIndex + 1 < this.tablero.length && this.tablero[rowIndex][colIndex + 1] != null) {

                                    if (!this.tablero[rowIndex][colIndex + 1].getNombre().contains("templo")) {

                                        if (getNodeByRowColumnIndex(rowIndex, colIndex + 1, grid) != null) {
                                            try {

                                                getNodeByRowColumnIndex(rowIndex, colIndex + 1, grid).setStyle("  -fx-border-color :#00ead3; -fx-background-radius : 6.0em; -fx-background-color: " + this.tablero[rowIndex][colIndex + 1].getColor() + "; -fx-background-image: url(" + this.tablero[rowIndex][colIndex + 1].getUrlImage() + "); -fx-background-size: 100% 100% ;");
                                                b = true;
                                            } catch (Exception e) {

                                            }

                                        }

                                    }

                                }
                            }

                        } catch (Exception e) {
                            System.err.println(e);
                        }

                        try {
                            if (tablero[rowIndex][colIndex].getLeft() != 0) {

                                if (colIndex - 1 >= 0 && this.tablero[rowIndex][colIndex - 1] != null) {
                                    if (!this.tablero[rowIndex][colIndex - 1].getNombre().contains("templo")) {

                                        try {

                                            getNodeByRowColumnIndex(rowIndex, colIndex - 1, grid).setStyle("  -fx-border-color :#00ead3; -fx-background-radius : 6.0em; -fx-background-color: " + this.tablero[rowIndex][colIndex - 1].getColor() + "; -fx-background-image: url(" + this.tablero[rowIndex][colIndex - 1].getUrlImage() + "); -fx-background-size: 100% 100% ;");
                                            b = true;
                                        } catch (Exception e) {
                                        }

                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.err.println(e);
                        }

                        try {
                            if (tablero[rowIndex][colIndex].getUp() != 0) {

                                if (rowIndex - 1 >= 0 && this.tablero[rowIndex - 1][colIndex] != null) {
                                    if (!this.tablero[rowIndex - 1][colIndex].getNombre().contains("templo")) {

                                        try {

                                            getNodeByRowColumnIndex(rowIndex - 1, colIndex, grid).setStyle("  -fx-border-color :#00ead3; -fx-background-radius : 6.0em; -fx-background-color: " + this.tablero[rowIndex - 1][colIndex].getColor() + "; -fx-background-image: url(" + this.tablero[rowIndex - 1][colIndex].getUrlImage() + "); -fx-background-size: 100% 100% ;");
                                            b = true;
                                        } catch (Exception e) {
                                        }

                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.err.println(e);
                        }

                        try {
                            if (tablero[rowIndex][colIndex].getDown() != 0) {

                                if (rowIndex + 1 < this.tablero.length && this.tablero[rowIndex + 1][colIndex] != null) {

                                    if (!this.tablero[rowIndex + 1][colIndex].getNombre().contains("templo")) {

                                        try {

                                            getNodeByRowColumnIndex(rowIndex + 1, colIndex, grid).setStyle("  -fx-border-color :#00ead3; -fx-background-radius : 6.0em; -fx-background-color: " + this.tablero[rowIndex + 1][colIndex].getColor() + "; -fx-background-image: url(" + this.tablero[rowIndex + 1][colIndex].getUrlImage() + "); -fx-background-size: 100% 100% ;");

                                            b = true;
                                        } catch (Exception e) {
                                        }

                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.err.println(e);
                        }

                    }
                }

            }
        }
        return b;

    }

    public void removeChildrenS(GridPane gridPane) {
        ObservableList<Node> childrens = gridPane.getChildren();

        childrens.stream().forEach((node) -> {
            try {
                if (node.idProperty().get() != null) {
                    node.setStyle("-fx-background-color: transparent ;");
                }

            } catch (Exception e) {
            }
        });

    }

    public void printNoNullTable() {
        for (rowIndex = 0; rowIndex < this.tablero.length; rowIndex++) {
            for (colIndex = 0; colIndex < this.tablero.length; colIndex++) {

                if (tablero[rowIndex][colIndex] != null) {

                }

            }
        }

    }

    public void loadTable(GridPane gridPane, ScrollPane srcTablero) {

        while (gridPane.getRowConstraints().size() > 0) {
            gridPane.getRowConstraints().remove(0);
        }

        while (gridPane.getColumnConstraints().size() > 0) {
            gridPane.getColumnConstraints().remove(0);
        }

        for (rowIndex = 0; rowIndex < this.tablero.length; rowIndex++) {
            for (colIndex = 0; colIndex < this.tablero.length; colIndex++) {

                if (getNodeByRowColumnIndex(rowIndex, colIndex, gridPane) != null) {

                    try {

                        Node pD = getNodeByRowColumnIndex(rowIndex, colIndex, gridPane);
                        if (this.tablero[rowIndex][colIndex] != null) {

                            pD.setId(rowIndex + "" + colIndex);

                        } else {
                            pD.setId(rowIndex + "" + colIndex);

                        }
                        pD.setStyle(" -fx-background-color: " + this.tablero[rowIndex][colIndex].getColor() + "; -fx-background-image: url(" + this.tablero[rowIndex][colIndex].getUrlImage() + "); -fx-background-size: 100% 100% ;");
                        pD.setRotate(this.tablero[rowIndex][colIndex].getRotation());
                    } catch (Exception e) {
                    }

                } else {
                    Pane p = new Pane();
                    p.setId(rowIndex + "" + colIndex);
                    gridPane.add(p, colIndex, rowIndex);
                }

            }
        }
        gridPane.setGridLinesVisible(true);
        for (rowIndex = 0; rowIndex < this.tablero.length; rowIndex++) {
            RowConstraints c = new RowConstraints();
            c.setFillHeight(true);
            c.setVgrow(Priority.NEVER);
            c.setMinHeight(140);
            c.setPrefHeight(140);
            gridPane.getRowConstraints().add(c);
        }
        for (colIndex = 0; colIndex < this.tablero.length; colIndex++) {
            ColumnConstraints c = new ColumnConstraints();
            c.setFillWidth(true);
            c.setHgrow(Priority.ALWAYS);
            c.setHalignment(HPos.CENTER);
            c.setMinWidth(140);
            c.setPrefWidth(140);
            gridPane.getColumnConstraints().add(c);
        }
        gridPane.setId("tablero");

        gridPane.setHgap(1);
        gridPane.setVgap(1);
        srcTablero.setHvalue(0.5);
        srcTablero.setVvalue(0.5);
    }

    public void generarGanador(ArrayList<Player> players) {
        puntos = 0;
        ans = 0;

        for (i = 0; i < players.size(); i++) {
            players.get(i).setPuntos(0);
               players.get(i).setPuntosTemplo(0);
            puntos += players.get(i).contarMonedas();
            puntos += players.get(i).getPuntosCenotes();
            puntos += players.get(i).getSoles().size();
          
            players.get(i).setPuntos(puntos);
            if (players.get(i).getPuntos() > ans) {
                idB = i;
                ans = players.get(i).getPuntos();

            }
            puntos = 0;
        }
        evaluarTemplos(players);

    }

    public void evaluarTemplos(ArrayList<Player> players) {
        ////evaluar templos 

        tmp.clear();

        for (rowIndex = 0; rowIndex < this.tablero.length; rowIndex++) {
            for (colIndex = 0; colIndex < this.tablero.length; colIndex++) {
                if (tablero[rowIndex][colIndex] != null) {
                
                    if (tablero[rowIndex][colIndex].getNombre().equals(" templo")) {

                        if (colIndex + 1 < this.tablero.length && this.tablero[rowIndex][colIndex + 1] != null) {

                            if (this.tablero[rowIndex][colIndex + 1].getTipo().contains("J")) {
                                this.tablero[rowIndex][colIndex + 1].tmpvalor = this.tablero[rowIndex][colIndex + 1].getLeft();
                              
                                tmp.add(this.tablero[rowIndex][colIndex + 1]);
                            }
                        }

                        if (colIndex - 1 >= 0 && this.tablero[rowIndex][colIndex - 1] != null) {
                            if (this.tablero[rowIndex][colIndex - 1].getTipo().contains("J")) {

                                this.tablero[rowIndex][colIndex - 1].tmpvalor = this.tablero[rowIndex][colIndex - 1].getRight();
                         
                                tmp.add(this.tablero[rowIndex][colIndex - 1]);

                            }
                        }

                        if (rowIndex - 1 >= 0 && this.tablero[rowIndex - 1][colIndex] != null) {
                            if (this.tablero[rowIndex - 1][colIndex].getTipo().contains("J")) {

                                this.tablero[rowIndex - 1][colIndex].tmpvalor = this.tablero[rowIndex - 1][colIndex].getDown();
                              
                                tmp.add(this.tablero[rowIndex - 1][colIndex]);

                            }
                        }

                        if (rowIndex + 1 < this.tablero.length && this.tablero[rowIndex + 1][colIndex] != null) {
                            if (this.tablero[rowIndex + 1][colIndex].getTipo().contains("J")) {

                                this.tablero[rowIndex + 1][colIndex].tmpvalor = this.tablero[rowIndex + 1][colIndex].getUp();
                       
                                tmp.add(this.tablero[rowIndex + 1][colIndex]);
                            }
                        }
                        values[0] = 0;
                        values[1] = 0;
                        values[2] = 0;
                        values[3] = 0;
                        leerLosetasTemplo(players, tmp, 0, values);
                        Arrays.sort(values);
                      

                        puntosTemplo(players, values[3], 6, countPairs(values, values[3]), values);
                        Arrays.sort(values);
                        puntosTemplo(players, values[3], 3, countPairs(values, values[3]), values);
                        tmp.clear();
                        values[0] = values[1] = values[2] = values[3] = 0;

                    }

                }

            }
        }

    }

    public void puntosTemplo(ArrayList<Player> players, int m, int n, int result, int[] values) {
        ans = 0;
   

        if (m != 0) {
            for (j = 0; j < players.size(); j++) {
                if (players.get(j).tmpvalue == m && result != 0) {
                   
                    players.get(j).setPuntosTemplo(players.get(j).getPuntosTemplo() + +(int) (n / result));

                    players.get(j).setPuntos(players.get(j).getPuntos() + (int) (n / result));

                    if (players.get(j).getPuntos() > ans) {
                        idB = j;
                        ans = players.get(j).getPuntos();
                      
                    }

                }
            }
            for (i = 0; i < 4; i++) {
                if (values[i] == m) {
                    values[i] = 0;
                }
            }
        }

    }

    public void leerLosetasTemplo(ArrayList<Player> players, ArrayList<Loseta> losetas, int n, int[] values) {
        for (i = 0; i < players.size(); i++) {
            players.get(i).tmpvalue = 0;

            for (j = 0; j < losetas.size(); j++) {
                if (players.get(i).getId() == losetas.get(j).getIdPlayer()) {
                    players.get(i).tmpvalue += losetas.get(j).tmpvalor;
                }
            }
 
            values[n] = players.get(i).tmpvalue;
            n += 1;
        }
    }

    public int countPairs(int arr[], int n) {
        ans = 0;

        for (i = 0; i < 4; i++) {

            if (arr[i] == n) {
                ans += 1;
            }

        }
        return ans;
    }

    public void cargarMonedas(int n, int valor) {
        for (i = 0; i < n; i++) {
            Moneda m = new Moneda(valor, i);
            monedas.add(m);
        }

    }

    public Boolean findPaymentPlayer(int id, GridPane grid, Player player) {
        b = false;
        for (rowIndex = 0; rowIndex < this.tablero.length; rowIndex++) {
            for (colIndex = 0; colIndex < this.tablero.length; colIndex++) {
                if (tablero[rowIndex][colIndex] != null) {

                    if (tablero[rowIndex][colIndex].idPlayer == id) {

                        try {
                            if (tablero[rowIndex][colIndex].getRight() != 0) {

                                if (colIndex + 1 < this.tablero.length && this.tablero[rowIndex][colIndex + 1] != null) {

                                    if (!this.tablero[rowIndex][colIndex + 1].getNombre().contains("templo")) {

                                        getNodeByRowColumnIndex(rowIndex, colIndex + 1, grid).setStyle("  -fx-border-color :#00ead3; -fx-background-radius : 6.0em; -fx-background-color: " + this.tablero[rowIndex][colIndex + 1].getColor() + "; -fx-background-image: url(" + this.tablero[rowIndex][colIndex + 1].getUrlImage() + "); -fx-background-size: 100% 100% ;");

                                    }

                                }
                            }
                            if (tablero[rowIndex][colIndex].getLeft() != 0) {

                                if (colIndex - 1 >= 0 && this.tablero[rowIndex][colIndex - 1] != null) {
                                    if (!this.tablero[rowIndex][colIndex - 1].getNombre().contains("templo")) {
                                        getNodeByRowColumnIndex(rowIndex, colIndex - 1, grid).setStyle("  -fx-border-color :#00ead3; -fx-background-radius : 6.0em; -fx-background-color: " + this.tablero[rowIndex][colIndex - 1].getColor() + "; -fx-background-image: url(" + this.tablero[rowIndex][colIndex - 1].getUrlImage() + "); -fx-background-size: 100% 100% ;");

                                    }

                                }
                            }
                            if (tablero[rowIndex][colIndex].getUp() != 0) {

                                if (rowIndex - 1 >= 0 && this.tablero[rowIndex - 1][colIndex] != null) {
                                    if (!this.tablero[rowIndex - 1][colIndex].getNombre().contains("templo")) {

                                        getNodeByRowColumnIndex(rowIndex - 1, colIndex, grid).setStyle("  -fx-border-color :#00ead3; -fx-background-radius : 6.0em; -fx-background-color: " + this.tablero[rowIndex - 1][colIndex].getColor() + "; -fx-background-image: url(" + this.tablero[rowIndex - 1][colIndex].getUrlImage() + "); -fx-background-size: 100% 100% ;");

                                    }

                                }
                            }
                            if (tablero[rowIndex][colIndex].getDown() != 0) {

                                if (rowIndex + 1 < this.tablero.length && this.tablero[rowIndex + 1][colIndex] != null) {

                                    if (!this.tablero[rowIndex + 1][colIndex].getNombre().contains("templo")) {

                                        getNodeByRowColumnIndex(rowIndex + 1, colIndex, grid).setStyle("  -fx-border-color :#00ead3; -fx-background-radius : 6.0em; -fx-background-color: " + this.tablero[rowIndex + 1][colIndex].getColor() + "; -fx-background-image: url(" + this.tablero[rowIndex + 1][colIndex].getUrlImage() + "); -fx-background-size: 100% 100% ;");

                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                    }
                }

            }
        }
        return b;

    }

    public Boolean findPaymentServer(int id, Player player) {
        b = false;
        for (rowIndex = 0; rowIndex < this.tablero.length; rowIndex++) {
            for (colIndex = 0; colIndex < this.tablero.length; colIndex++) {
                try {
                    if (tablero[rowIndex][colIndex] != null) {

                        if (tablero[rowIndex][colIndex].idPlayer == id) {

                            try {
                                if (tablero[rowIndex][colIndex].getRight() != 0) {

                                    if (colIndex + 1 < this.tablero.length && this.tablero[rowIndex][colIndex + 1] != null) {

                                        if (!this.tablero[rowIndex][colIndex + 1].getNombre().contains("templo")) {
                                            b = true;
                                        }

                                    }
                                }
                                if (tablero[rowIndex][colIndex].getLeft() != 0) {

                                    if (colIndex - 1 >= 0 && this.tablero[rowIndex][colIndex - 1] != null) {
                                        if (!this.tablero[rowIndex][colIndex - 1].getNombre().contains("templo")) {

                                            b = true;
                                        }

                                    }
                                }
                                if (tablero[rowIndex][colIndex].getUp() != 0) {

                                    if (rowIndex - 1 >= 0 && this.tablero[rowIndex - 1][colIndex] != null) {
                                        if (!this.tablero[rowIndex - 1][colIndex].getNombre().contains("templo")) {

                                            b = true;
                                        }

                                    }
                                }
                                if (tablero[rowIndex][colIndex].getDown() != 0) {

                                    if (rowIndex + 1 < this.tablero.length && this.tablero[rowIndex + 1][colIndex] != null) {

                                        if (!this.tablero[rowIndex + 1][colIndex].getNombre().contains("templo")) {

                                            b = true;
                                        }

                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }
        return b;

    }

    public Moneda popMoneda(ArrayList<Moneda> monedas, int valor) {
        filteredListMoneda = monedas.stream().filter(s -> s.getValor() == valor).collect(Collectors.toList());
        Moneda m = filteredListMoneda.get(filteredListMoneda.size() - 1);
        monedas.remove(filteredListMoneda.get(filteredListMoneda.size() - 1));
        return m;
    }

    public long contarMonedas(ArrayList<Moneda> monedas, int valor) {

        return monedas.stream().filter(s -> s.getValor() == valor).count();
    }

    public void cargarSoles(int n) {
        for (i = 0; i < n; i++) {
            Sol m = new Sol(i);
            soles.add(m);
        }
    }

    public void losetasSelva(int n, String nombre, String urlImage, int valor) {
        for (i = 0; i < n; i++) {
            Loseta s = new Loseta(i, "S", nombre, urlImage, valor);

            losetasSelva.add(s);
        }
    }

    public void losetasSelva(int n, String nombre, String urlImage) {
        for (i = 0; i < n; i++) {
            Loseta s = new Loseta(i, "S", nombre, urlImage);

            losetasSelva.add(s);
        }
    }

    public void cargarCacaos(int n) {
        for (i = 0; i < n; i++) {
            Cacao c = new Cacao(i);
            cacaos.add(c);
        }
    }

    public ArrayList<Loseta> getLosetasSelva() {
        return losetasSelva;
    }

    public void setLosetasSelva(ArrayList<Loseta> losetasSelva) {
        this.losetasSelva = losetasSelva;
    }

    public ArrayList<Moneda> getMonedas() {
        return monedas;
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

    public ArrayList<Loseta> getSelvaExplorada() {
        return selvaExplorada;
    }

    public void setSelvaExplorada(ArrayList<Loseta> selvaExplorada) {
        this.selvaExplorada = selvaExplorada;
    }

    public void init(GameServer game, int size) {
        game.removeChildresTablero();
        game.cargarCacaos(20);
        game.cargarSoles(12);
        game.cargarMonedas(24, 1);
        game.cargarMonedas(12, 5);
        game.cargarMonedas(12, 10);

        game.losetasSelva(   size ==2  ? 4 : 6, " plantacionSimple", "/images/losetaSelva1.png", 1);
        game.losetasSelva(2, " plantacionDoble", "/images/losetaSelva2.png", 2);

        game.losetasSelva(2, " mercado", "/images/mercado2.png", 2);
        game.losetasSelva( size ==2  ? 3 : 4, " mercado", "/images/mercado3.png", 3);
        game.losetasSelva(1, " mercado", "/images/mercado4.png", 4);

        game.losetasSelva(  size ==2  ? 1 : 2, " minaOro", "/images/mina1.png", 1);

        game.losetasSelva(1, " minaOro", "/images/mina2.png", 2);

        game.losetasSelva(  size ==2  ? 2 : 3, " cenote", "/images/cenote.png");
        game.losetasSelva(size ==2  ? 1 : 2, " centroCultoSolar", "/images/culto.png");
        game.losetasSelva(size ==2  ? 4 : 5, " templo", "/images/templo.png");

        game.losetasIniciales();
        game.generarSelvaExplorada(2);

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacaoclient;

import clases.GameServer;
import clases.Player;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Elias
 */
public class PuntajePlayersController implements Initializable {

    @FXML
    private AnchorPane puntaje;
    @FXML
    private VBox p1;
    @FXML
    private VBox p2;
    @FXML
    private VBox p3;
    @FXML
    private VBox p4;
    List<Node> nodes;
    ArrayList<Player> players = new ArrayList<>();
    VBox[] playersBox;
    int c = 0;
    GameServer game;

    public void setFuncionalidad(ArrayList<Player> players, GameServer game) {
        this.players = players;
        this.game = game;

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        playersBox = new VBox[]{p1, p2, p3, p4};
    }

    public void getWinner() {

        ArrayList<Integer> nWin = new ArrayList<>();
        int i = 0;
        game.setAns(0);
        for (i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).getInfo().getNombre() + " " + players.get(i).getPuntos());
            if (players.get(i).getPuntos() > game.getAns()) {

                game.setAns(players.get(i).getPuntos());
                game.setIdB(i);
            }
        }

        for (i = 0; i < players.size(); i++) {

            if (players.get(i).getPuntos() == players.get(game.getIdB()).getPuntos()) {

                playersBox[i].setStyle("  -fx-border-color :#00ead3; -fx-background-radius : 6.0em;");

            } else {
                playersBox[i].setStyle("  -fx-border-color :transparent ; -fx-background-radius : 6.0em;");
            }
        }

    }

    public void setData() {
        for (int i = 0; i < players.size(); i++) {
            nodes = new ArrayList<>();
            nodes.addAll(playersBox[i].getChildren());
            playersBox[i].setVisible(true);
            c = 0;
            for (Node node : nodes) {
                Label lbl = (Label) node;
                if (c == 0) {
                    lbl.setText(players.get(i).getInfo().getNombre());
                }
                if (c == 1) {
                    lbl.setText(Integer.toString(players.get(i).contarMonedas()));
                }
                if (c == 2) {
                    lbl.setText(Integer.toString(players.get(i).getSoles().size()));
                }
                if (c == 3) {
                    lbl.setText(Integer.toString(players.get(i).getPuntosCenotes()));
                }
                if (c == 4) {
                    lbl.setText(Integer.toString(players.get(i).getPuntosTemplo()));
                }
                if (c == 5) {
                    lbl.setText(Integer.toString(players.get(i).getPuntos()));
                }
                c += 1;
            }
           
        }
         getWinner();
    }
}

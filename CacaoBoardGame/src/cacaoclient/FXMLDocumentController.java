/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacaoclient;

import clases.Chat;

import clases.GameServer;
import clases.Player;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.sun.nio.sctp.Notification;
import java.awt.JobAttributes.DialogType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Elias
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private JFXTextField ip;
    @FXML
    private JFXTextField pt;
    @FXML
    private JFXTextField user;
    @FXML
    private JFXTextField edad;
    @FXML
    private StackPane Connect;
    @FXML
    private ScrollPane mainGame;
    ObjectOutputStream toServer = null;
    ObjectInputStream fromServer = null;
    infoClient info;

    GameServer game;
    Player player;
    @FXML
    private JFXColorPicker color;
    @FXML
    private Label lblLosetaSelva;
    @FXML
    private Label lblCacao;
    @FXML
    private Label lblFichaSol;
    @FXML
    private Label lblMoneda1;
    @FXML
    private Label lblMoneda5;
    @FXML
    private Label lblMoneda10;
    Socket s;
    DataInputStream dis;
    DataOutputStream dos;
    Scanner scn = new Scanner(System.in);
    String Action = "";
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ArrayList<Player> players = new ArrayList<>();
    static ArrayList<Chat> chat = new ArrayList<>();
    @FXML
    private GridPane tablero;
    @FXML
    private ScrollPane scrTablero;
    @FXML
    private ImageView selvaExploradaImg1;
    @FXML
    private ImageView selvaExploradaImg2;
    @FXML
    private Pane p1Img1;
    @FXML
    private Pane p1Img2;
    @FXML
    private Pane p1Img3;
    @FXML
    private VBox player1;
    @FXML
    private HBox losetasJugador1;
    @FXML
    private Pane p2Img1;
    @FXML
    private Pane p2Img2;
    @FXML
    private Pane p2Img3;
    @FXML
    private HBox losetasJugador2;
    @FXML
    private Pane p3Img1;
    @FXML
    private Pane p3Img2;
    @FXML
    private Pane p3Img3;
    @FXML
    private Pane p4Img1;
    @FXML
    private Pane p4Img2;
    @FXML
    private Pane p4Img3;
    @FXML
    private VBox playersCard;
    @FXML
    private HBox losetasPlayer1;
    @FXML
    private HBox losetasPlayer2;
    @FXML
    private HBox losetasPlayer3;
    @FXML
    private HBox losetasJugador3;
    @FXML
    private HBox losetasPlayer4;
    @FXML
    private HBox infoP1;
    @FXML
    private Label nombreP1;
    @FXML
    private VBox oroP1;
    @FXML
    private Label lblOroP1;
    @FXML
    private Label lblLosetaP1;
    @FXML
    private HBox CacaoSol1;
    @FXML
    private ImageView p1Cacao1;
    @FXML
    private ImageView p1Cacao2;
    @FXML
    private ImageView p1Cacao3;
    @FXML
    private ImageView p1Cacao4;
    @FXML
    private ImageView p1Cacao5;
    @FXML
    private ImageView p1Sol1;
    @FXML
    private ImageView p1Sol2;
    @FXML
    private ImageView p1Sol3;
    @FXML
    private HBox Cenote1;
    @FXML
    private VBox p1C1;
    @FXML
    private VBox p1C2;
    @FXML
    private VBox p1C3;
    @FXML
    private VBox p1C4;
    @FXML
    private VBox p1C5;
    @FXML
    private VBox p1C6;
    @FXML
    private VBox p1C7;
    @FXML
    private VBox p1C8;
    @FXML
    private VBox p1C9;
    @FXML
    private HBox infoP11;
    @FXML
    private Label nombreP2;
    @FXML
    private VBox oroP11;
    @FXML
    private Label lblOroP2;
    @FXML
    private Label lblLosetaP2;
    @FXML
    private HBox CacaoSol2;
    @FXML
    private ImageView p2Cacao1;
    @FXML
    private ImageView p2Cacao2;
    @FXML
    private ImageView p2Cacao3;
    @FXML
    private ImageView p2Cacao4;
    @FXML
    private ImageView p2Cacao5;
    @FXML
    private ImageView p2Sol1;
    @FXML
    private ImageView p2Sol2;
    @FXML
    private ImageView p2Sol3;
    @FXML
    private HBox Cenote2;
    @FXML
    private VBox p2C1;
    @FXML
    private VBox p2C2;
    @FXML
    private VBox p2C3;
    @FXML
    private VBox p2C4;
    @FXML
    private VBox p2C5;
    @FXML
    private VBox p2C6;
    @FXML
    private VBox p2C7;
    @FXML
    private VBox p2C8;
    @FXML
    private VBox p2C9;
    @FXML
    private HBox infoP111;
    @FXML
    private Label nombreP3;
    @FXML
    private VBox oroP111;
    @FXML
    private Label lblOroP3;
    @FXML
    private Label lblLosetaP3;
    @FXML
    private HBox CacaoSol3;
    @FXML
    private ImageView p3Cacao1;
    @FXML
    private ImageView p3Cacao2;
    @FXML
    private ImageView p3Cacao3;
    @FXML
    private ImageView p3Cacao4;
    @FXML
    private ImageView p3Cacao5;
    @FXML
    private ImageView p3Sol1;
    @FXML
    private ImageView p3Sol2;
    @FXML
    private ImageView p3Sol3;
    @FXML
    private HBox Cenote3;
    @FXML
    private VBox p3C1;
    @FXML
    private VBox p3C2;
    @FXML
    private VBox p3C3;
    @FXML
    private VBox p3C4;
    @FXML
    private VBox p3C5;
    @FXML
    private VBox p3C6;
    @FXML
    private VBox p3C7;
    @FXML
    private VBox p3C8;
    @FXML
    private VBox p3C9;
    @FXML
    private HBox infoP1111;
    @FXML
    private Label nombreP4;
    @FXML
    private VBox oroP1111;
    @FXML
    private Label lblOroP4;
    @FXML
    private Label lblLosetaP4;
    @FXML
    private HBox CacaoSol4;
    @FXML
    private ImageView p4Cacao1;
    @FXML
    private ImageView p4Cacao2;
    @FXML
    private ImageView p4Cacao3;
    @FXML
    private ImageView p4Cacao4;
    @FXML
    private ImageView p4Cacao5;
    @FXML
    private ImageView p4Sol1;
    @FXML
    private ImageView p4Sol2;
    @FXML
    private ImageView p4Sol3;
    @FXML
    private HBox Cenote4;
    @FXML
    private VBox p4C1;
    @FXML
    private VBox p4C2;
    @FXML
    private VBox p4C3;
    @FXML
    private VBox p4C4;
    @FXML
    private VBox p4C5;
    @FXML
    private VBox p4C6;
    @FXML
    private VBox p4C7;
    @FXML
    private VBox p4C8;
    @FXML
    private VBox p4C9;
    Label[] nombres;
    Label[] oro;

    Label[] pts;
    Label[] losetas;
    HBox[] losetasJugador;
    HBox[] cacaoSol;
    HBox[] cenotes;
    VBox[] playersCards;
    List<Node> nodes;
    Boolean isOpenChat = false;
    ChatController chatController;
    Node target;
    Boolean activeLS = false;
    int iL;

    String coord = "";
    int rotation = 0;
    String type = "";
    @FXML
    private HBox losetasJugador4;
    @FXML
    private VBox player2;
    @FXML
    private VBox player3;
    @FXML
    private VBox player4;
    @FXML
    private HBox ct;
    @FXML
    private HBox ct2;
    @FXML
    private AnchorPane archorPane;
    JFXDialog dialog;
    @FXML
    private VBox vboxStack;
    @FXML
    private StackPane stackGame;
    @FXML
    private AnchorPane gameAnchorP;
    @FXML
    private JFXButton btnStart;
    int tOnline = 0;
    Boolean OffLine = false;
    @FXML
    private HBox offLine;
    @FXML
    private JFXButton btnStart1;

    read tRead = new read();

    Thread t1 = new Thread(tRead, "T1");

    URL resource = getClass().getResource("/sounds/message.mp3");
    Media media = new Media(resource.toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);

    URL resourceMove = getClass().getResource("/sounds/move.mp3");
    Media mediaMove = new Media(resourceMove.toString());
    MediaPlayer mediaPlayerMove = new MediaPlayer(mediaMove);
    Label lbl = new Label("Desea colocar de esta forma: ");
    VBox vb = new VBox();
    Scene scene;
    Stage myStage;
    @FXML
    private StackPane StartLoading;
    @FXML
    private Circle CircleStart_1;
    @FXML
    private Circle CircleStart_2;
    @FXML
    private ImageView avatar1;
    Boolean listeningServer = false;
    @FXML
    private HBox offLine1;
    @FXML
    private Label whoIsPlaying;
    Message msg;
    int i = 0, j = 0, p = 0;
    Boolean c = false;
    String sDisconnected = "";
    ImageView icon = new ImageView(new Image("/images/checked.png"));
    ImageView iconMessage = new ImageView(new Image("/images/message.png"));
    @FXML
    private Label pts1;
    @FXML
    private Label pts2;
    @FXML
    private Label pts3;
    @FXML
    private Label pts4;
    GridPane gridPane = new GridPane();

    public void setConfig(Scene sc) {
        scene = sc;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nombres = new Label[]{nombreP1, nombreP2, nombreP3, nombreP4};
        pts = new Label[]{pts1, pts2, pts3, pts4};
        oro = new Label[]{lblOroP1, lblOroP2, lblOroP3, lblOroP4};
        losetas = new Label[]{lblLosetaP1, lblLosetaP2, lblLosetaP3, lblLosetaP4};
        losetasJugador = new HBox[]{losetasJugador1, losetasJugador2, losetasJugador3, losetasJugador4};
        cacaoSol = new HBox[]{CacaoSol1, CacaoSol2, CacaoSol3, CacaoSol4};
        cenotes = new HBox[]{Cenote1, Cenote2, Cenote3, Cenote4};
        playersCards = new VBox[]{player1, player2, player3, player4};

        Connect.setVisible(false);

        mainGame.setVisible(false);
        RotateStart(CircleStart_2, true, 360, 10);
        RotateStart(CircleStart_1, true, 180, 18);
        pt.setText("3001");

        pt.textProperty().addListener((ObservableValue< ? extends String> observable, String viejo, String nuevo) -> {
            try {
                Integer.parseInt(nuevo);
            } catch (Exception e) {
                pt.deleteNextChar();
            }
        });
        edad.textProperty().addListener((ObservableValue< ? extends String> observable, String viejo, String nuevo) -> {
            try {
                Integer.parseInt(nuevo);
            } catch (Exception e) {
                edad.deleteNextChar();
            }
        });
        while (gridPane.getRowConstraints().size() > 0) {
            gridPane.getRowConstraints().remove(0);
        }

        while (gridPane.getColumnConstraints().size() > 0) {
            gridPane.getColumnConstraints().remove(0);
        }
        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
            for (int colIndex = 0; colIndex < 3; colIndex++) {

                Pane p = new Pane();
                p.setId(rowIndex + "" + colIndex);
                gridPane.add(p, colIndex, rowIndex);

            }
        }

        gridPane.setGridLinesVisible(true);
        for (i = 0; i < 3; i++) {
            RowConstraints c = new RowConstraints();
            c.setFillHeight(true);
            c.setVgrow(Priority.NEVER);
            c.setMinHeight(140);
            c.setPrefHeight(140);
            gridPane.getRowConstraints().add(c);
        }
        for (i = 0; i < 3; i++) {
            ColumnConstraints c = new ColumnConstraints();
            c.setFillWidth(true);
            c.setHgrow(Priority.ALWAYS);
            c.setHalignment(HPos.CENTER);
            c.setMinWidth(140);
            c.setPrefWidth(140);
            gridPane.getColumnConstraints().add(c);
        }
        gridPane.setStyle("-fx-background-image: url(\"/images/fondoT.jpg\"); -fx-background-size: 100% 100% ;");
        gridPane.setHgap(1);
        gridPane.setVgap(1);
    }

    private void Rotate(Circle circle, boolean reverse, int angle, int duration) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration), circle);
        rotateTransition.setAutoReverse(reverse);
        rotateTransition.setByAngle(angle);
        rotateTransition.setDelay(Duration.seconds(0));
        rotateTransition.setRate(6);
        rotateTransition.setCycleCount(40);
        rotateTransition.play();
    }

    private void RotateStart(Circle circle, boolean reverse, int angle, int duration) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration), circle);
        rotateTransition.setAutoReverse(reverse);
        rotateTransition.setByAngle(angle);
        rotateTransition.setDelay(Duration.seconds(0));
        rotateTransition.setRate(3);
        rotateTransition.setCycleCount(2);
        rotateTransition.setOnFinished(e -> ShowMenu());
        rotateTransition.play();

    }

    private void ShowMenu() {
        StartLoading.setVisible(false);
        Connect.setVisible(true);
        mainGame.setVisible(false);
        ct.setVisible(false);

    }

    public void showNotification(String message, ImageView icon) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                icon.setFitWidth(35);
                icon.setFitHeight(35);
                Notifications.create()
                        .title("Server")
                        .text(message)
                        .graphic(icon)
                        .position(Pos.TOP_RIGHT)
                        .hideAfter(Duration.seconds(3))
                        .darkStyle()
                        .show();

            }

        });
    }

    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    private void updateLbls(String s1, String s2) {
        selvaExploradaImg1.setVisible(true);
        selvaExploradaImg2.setVisible(true);
        if (game.getSelvaExplorada().size() > 1) {
            selvaExploradaImg1.setImage(new Image(game.getSelvaExplorada().get(0).getUrlImage()));
            selvaExploradaImg2.setImage(new Image(game.getSelvaExplorada().get(1).getUrlImage()));

            selvaExploradaImg1.setStyle(s1);
            selvaExploradaImg2.setStyle(s2);
        } else if (game.getSelvaExplorada().size() == 1) {
            selvaExploradaImg1.setImage(new Image(game.getSelvaExplorada().get(0).getUrlImage()));
            selvaExploradaImg1.setStyle(s1);
            selvaExploradaImg2.setVisible(false);
        } else {
            selvaExploradaImg1.setVisible(false);
            selvaExploradaImg2.setVisible(false);
        }

        lblCacao.setText(String.valueOf(game.getCacaos().size()));
        lblFichaSol.setText(String.valueOf(game.getSoles().size()));
        lblLosetaSelva.setText(String.valueOf(game.getLosetasSelva().size()));

        lblMoneda1.setText(String.valueOf(game.contarMonedas(game.getMonedas(), 1)));
        lblMoneda5.setText(String.valueOf(game.contarMonedas(game.getMonedas(), 5)));
        lblMoneda10.setText(String.valueOf(game.contarMonedas(game.getMonedas(), 10)));

    }

    private void changeCards(int l, int n, Player p, HBox losetaJugador, HBox cacaoSol, HBox cenote) {

        nodes = new ArrayList<>();
        nodes.addAll(cacaoSol.getChildren());

        for (Node node : nodes) {

            if (node.getId().contains("Cacao") && l < p.getCacaos().size() && p.getCacaos().size() > 0) {

                node.setOpacity(1);
                l += 1;

            } else if (node.getId().contains("Cacao")) {
                node.setOpacity(0.46);
                l += 1;
            }
            if (node.getId().contains("Sol") && n < p.getSoles().size() && p.getSoles().size() > 0) {

                node.setOpacity(1);
                n += 1;

            } else if (node.getId().contains("Sol")) {
                node.setOpacity(0.46);
                n += 1;

            }

        }

        l = 0;
        nodes = new ArrayList<>();
        nodes.addAll(cenote.getChildren());
        for (Node node : nodes) {

            if (p.getCenote() > 0 && l < p.getCenote()) {
                node.setOpacity(1);
                l += 1;
            } else {
                node.setOpacity(0.46);
                l += 1;
            }

        }

    }

    private void updatePlayers() {

        i = 1;

        for (Player player5 : players) {
            if (player5.getId() != player.getId()) {
                playersCards[i].setVisible(true);
                nombres[i].setText(player5.getInfo().getNombre());
                oro[i].setText(String.valueOf(player5.contarMonedas()));
                losetas[i].setText(String.valueOf(player5.getFichas().size()));
                pts[i].setText("    Pts:  " + player5.getPuntos());
                nodes.clear();
                nodes.addAll(losetasJugador[i].getChildren());
                p = 0;
                nodes.stream().map((node) -> {
                    if (player5.getManoInicial().size() > 0 && p < player5.getManoInicial().size()) {
                        node.setVisible(true);
                        node.setStyle("  -fx-background-color : " + player5.getInfo().getColor() + ";  -fx-background-size: 100% 100% ;");
                    } else {
                        node.setVisible(false);
                    }
                    return node;
                }).forEach((_item) -> {
                    p += 1;
                });
                changeCards(0, 0, player5, losetasJugador[i], cacaoSol[i], cenotes[i]);
                i += 1;
            }
        }

    }

    private void whoIsPlaying() {
        for (i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == game.getActualTurn()) {
               
                whoIsPlaying.setText(players.get(i).getInfo().getNombre());

            }
        }

    }

    private void updatePlayerFromPlayers() {

        for (i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == player.getId()) {

                player = players.get(i);
                info = player.getInfo();

            }
        }
    }

    private void updatePlayersFromPlayer() {

        for (i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == player.getId()) {
                players.set(i, player);
            }
        }
    }

    private void updatePlayer(String j1, String j2, String j3) {
//-fx-border-color :#00ead3; -fx-background-radius : 6.0em;
        nombres[0].setText(player.getInfo().getNombre());
        oro[0].setText(String.valueOf(player.contarMonedas()));
        losetas[0].setText(String.valueOf(player.getFichas().size()));
        pts[0].setText("    Pts:  " + player.getPuntos());
        p1Img1.setVisible(false);
        p1Img2.setVisible(false);
        p1Img3.setVisible(false);

        if (player.getManoInicial().size() >= 3) {
            p1Img1.setVisible(true);
            p1Img2.setVisible(true);
            p1Img3.setVisible(true);
            p1Img1.setStyle(j1 + "  -fx-background-color : " + info.getColor() + "; -fx-background-image: url(" + player.getManoInicial().get(0).getUrlImage() + "  ); -fx-background-size: 100% 100% ;");
            p1Img2.setStyle(j2 + "  -fx-background-color : " + info.getColor() + "; -fx-background-image: url(" + player.getManoInicial().get(1).getUrlImage() + "  ); -fx-background-size: 100% 100% ;");
            p1Img3.setStyle(j3 + "  -fx-background-color : " + info.getColor() + "; -fx-background-image: url(" + player.getManoInicial().get(2).getUrlImage() + "  ); -fx-background-size: 100% 100% ;");

        } else if (player.getManoInicial().size() >= 2) {
            p1Img1.setVisible(true);
            p1Img2.setVisible(true);

            p1Img1.setStyle(j1 + "  -fx-background-color : " + info.getColor() + "; -fx-background-image: url(" + player.getManoInicial().get(0).getUrlImage() + "  ); -fx-background-size: 100% 100% ;");
            p1Img2.setStyle(j2 + "  -fx-background-color : " + info.getColor() + "; -fx-background-image: url(" + player.getManoInicial().get(1).getUrlImage() + "  ); -fx-background-size: 100% 100% ;");

            p1Img3.setVisible(false);

        } else if (player.getManoInicial().size() == 1) {
            p1Img1.setStyle(j1 + "  -fx-background-color : " + info.getColor() + "; -fx-background-image: url(" + player.getManoInicial().get(0).getUrlImage() + "  ); -fx-background-size: 100% 100% ;");
            p1Img1.setVisible(true);

            p1Img2.setVisible(false);
            p1Img3.setVisible(false);

        }
        changeCards(0, 0, player, losetasJugador1, CacaoSol1, Cenote1);

    }

    @FXML
    private void Conectarse(ActionEvent event) {

        try {
            s = new Socket(ip.getText(), Integer.parseInt(pt.getText()));
//              s = new Socket("127.0.0.1",3001);
          

            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());

            msg = (Message) ois.readObject();

            switch (msg.msg) {
                case "started":
                    Connect.setVisible(false);
                    mainGame.setVisible(true);
                    ct.setVisible(true);
                    MostrarAviso("Lo sentimos, la partida esta en proceso...!", "main");
                    break;
                case "connectingSocker":
                    oos.writeObject(new Message("components")); //
                    t1.start();
                    break;
                default:
                    if (ip.getText().contains("127.0.0.1")) {
                        btnStart.setVisible(true);
                    } else {
                        btnStart.setVisible(false);
                    }
                    Connect.setVisible(false);
                    mainGame.setVisible(true);
                    ct.setVisible(true);
                    info = new infoClient("#" + color.getValue().toString().substring(2), user.getText(), Integer.parseInt(edad.getText()));
                    oos.writeObject(new Message("#" + color.getValue().toString().substring(2))); // 
                    oos = new ObjectOutputStream(s.getOutputStream());
                    ois = new ObjectInputStream(s.getInputStream());
                    msg = (Message) ois.readObject();
                    oos.writeObject(msg);
                    if (msg.msg.equals("alreadyUsed")) {
                        MostrarAviso("Lo sentimos, elija otro color!", "main");

                    } else {
                        try {
                            oos = new ObjectOutputStream(s.getOutputStream());
                            ois = new ObjectInputStream(s.getInputStream());

                            game = (GameServer) ois.readObject();

                            oos.writeObject(info);

                            oos = new ObjectOutputStream(s.getOutputStream());
                            ois = new ObjectInputStream(s.getInputStream());

                            player = (Player) ois.readObject();
                            oos.writeObject(new Message("hello"));

                            updateLbls("", "");
                            updatePlayer("", "", "");
                            t1.start();
                            game.loadTable(tablero, scrTablero);
                            showNotification("Conectado", icon);
                        } catch (Exception e) {
                            if (game.getStarted()) {

                                MostrarAviso("La partida ha iniciado, lo sentimos", "main");
                            }
                        }
//                        } else {
//

//
//                        }
                    }
                    break;
            }

        } catch (NumberFormatException | IOException | ClassNotFoundException e) {
            Connect.setVisible(false);
            mainGame.setVisible(true);
            ct.setVisible(true);

            if (game.getStarted()) {

                MostrarAviso("La partida ha iniciado, lo sentimos", "main");
            } else {
                MostrarAviso("Compruebe los datos.", "main");
            }

           
        }

    }

    private void quitar() {
        try {
            Action = "update";

            updateLbls("", "");

            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new Message("updateComponents"));
            oos.flush();

            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(game);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void openChat(ActionEvent event) {
        quitar();
        OpenChat();
    }

    public void OpenChat() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/cacaoclient/Chat.fxml"));
            Parent root = loader.load();
            chatController = (ChatController) loader.getController();
            chatController.setFuncionalidad(chat, s, info.getNombre());
            stage.setScene(new Scene(root));
            stage.setTitle("Chat");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((HBox) (Cenote1)).getScene().getWindow());
            stage.setResizable(false);
            stage.show();
            isOpenChat = true;
            stage.setOnHidden((WindowEvent event1) -> {
                isOpenChat = false;
            });
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void OpenPuntaje() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/cacaoclient/PuntajePlayers.fxml"));
            Parent root = loader.load();
            PuntajePlayersController controller = (PuntajePlayersController) loader.getController();
            controller.setFuncionalidad(players, game);
            stage.setScene(new Scene(root));
            stage.setTitle("Puntaje");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((HBox) (Cenote1)).getScene().getWindow());
            stage.setResizable(false);
            stage.show();
            controller.setData();

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Node getNode(MouseEvent event) {

        return (Node) event.getTarget();

    }

    @FXML
    private void mouseClickedSelva1(MouseEvent event) {
        rotation = 0;

        if (game.getActualTurn() != player.getId()) {

            MostrarAviso("Espera, aun no es tu turno.", Action);
        } else if (activeLS) {
            updateLbls("-fx-border-color :red; -fx-background-radius : 6.0em;", "");
            iL = 0;
            type = "E";
            coord = "";
        }

    }

    @FXML
    private void mouseClickedSelva2(MouseEvent event) {
        rotation = 0;
        if (game.getActualTurn() != player.getId()) {

            MostrarAviso("Espera, aun no es tu turno.", Action);
        } else if (activeLS) {
            updateLbls("", "-fx-border-color :red; -fx-background-radius : 6.0em;");
            iL = 1;
            type = "E";
            coord = "";
        }

    }

    public void latido() {

        try {
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new Message("latido"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMove() {

        try {
            iL = -1;
            type = "";
            coord = "";
            target = null;
            game.generarGanador(players);
            UpdateDataPlayers();

            game.nextTurn();
            //
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new Message("updateComponents"));
            oos.flush();

            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(game);
            oos.flush();
            game.loadTable(tablero, scrTablero);
            showNotification("Jugada enviada al servidor", icon);
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            MostrarAviso("Algo salio mal.", "");
        }

    }

    @FXML
    private void mouseClickedTablero(MouseEvent event) {
        try {
            if (!game.getPlayersDisconnected().isEmpty()) {
                mostrarDisconnected();
            } else if (game.getActualTurn() != player.getId()) {

                MostrarAviso("Espera, aun no es tu turno.", "");
            } else if (tOnline != players.size()) {
                MostrarAviso("Espera que todos se conecten.", "");

            } else if (offLine.isVisible()) {
                MostrarAviso("Intenta reconectar", Action);

            } else {
                //flecha para aceptar cuando se coloque la loseta 

                Node newTarget = (Node) event.getTarget();

                Node nd = game.getNodeByRowColumnIndex(tablero.getRowIndex(newTarget), tablero.getColumnIndex(newTarget), tablero);

                newTarget = nd;
                if (activeLS == false && newTarget.getStyle().contains(" -fx-border-color :#00ead3;")) {

                    game.payMent(tablero.getRowIndex(newTarget), tablero.getColumnIndex(newTarget), tablero, player, players);

                    updatePlayer("", "", "");
                    updateLbls("", "");
                    if (!game.findPayment(player.getId(), tablero, player)) {

                        sendMove();
                    }

                } else if (activeLS == true && type.equals("E")) {
                    ct2.setVisible(true);
                    if (target != null) {
                        if (!coord.equals(tablero.getRowIndex(newTarget).toString() + "," + tablero.getColumnIndex(newTarget).toString()) && game.tablero[tablero.getRowIndex(target)][tablero.getColumnIndex(target)] == null) {
                            rotation = 0;
                            target.setRotate(rotation);

                            target.setStyle(" -fx-background-color: #435560;");

                        }
                    }
                    target = newTarget;
                    if (target.getStyle().contains(" -fx-background-color: #435560;")) {
                        rotation = 0;
                        target.setRotate(rotation);
                        target.setStyle(" -fx-background-image: url(" + game.getSelvaExplorada().get(iL).getUrlImage() + "); -fx-background-size: 100% 100% ;");
                        coord = tablero.getRowIndex(target).toString() + "," + tablero.getColumnIndex(target).toString();
                    } else {
                        target = null;
                    }

                } else if (type.equals("J") && !activeLS) {

                    ct2.setVisible(true);
                    if (target != null) {
                        if (!coord.equals(tablero.getRowIndex(newTarget).toString() + "," + tablero.getColumnIndex(newTarget).toString()) && game.tablero[tablero.getRowIndex(target)][tablero.getColumnIndex(target)] == null) {
                            rotation = 0;
                            target.setRotate(rotation);
                            target.setStyle(" -fx-background-color: #435560;");

                        }
                    }

                    target = newTarget;
                    if (target != null) {

                        if (target.getStyle().contains(" -fx-background-color: #435560;")) {

                            if (!coord.equals(tablero.getRowIndex(target).toString() + "," + tablero.getColumnIndex(target).toString())) {

                                coord = tablero.getRowIndex(target).toString() + "," + tablero.getColumnIndex(target).toString();

                                target.setStyle(" -fx-background-color: " + player.getManoInicial().get(iL).getColor() + ";-fx-background-image: url(" + player.getManoInicial().get(iL).getUrlImage() + "); -fx-background-size: 100% 100% ;");

                            }
                        } else {

                            if (iL != -1) {
                                if (target.getStyle().contains("-fx-background-image: url(" + player.getManoInicial().get(iL).getUrlImage() + ");")) {

                                    if (game.tablero[tablero.getRowIndex(target)][tablero.getColumnIndex(target)] != null && game.tablero[tablero.getRowIndex(target)][tablero.getColumnIndex(target)].getTipo().contains("J")) {
                                        rotation += 90;

                                        if (rotation == 360) {
                                            rotation = 0;
                                        }
                                        target.setRotate(rotation);
                                    } else if (game.tablero[tablero.getRowIndex(target)][tablero.getColumnIndex(target)] == null) {
                                        rotation += 90;

                                        if (rotation == 360) {
                                            rotation = 0;
                                        }
                                        target.setRotate(rotation);
                                    }

                                } else {
                                    target = null;
                                }
                            } else {
                                target = null;
                            }

                        }

                    }

                }

            }
        } catch (Exception e) {
        }

    }

    @FXML
    private void mouseClickedImg1(MouseEvent event) {
        rotation = 0;

        if (game.getActualTurn() != player.getId()) {

            MostrarAviso("Espera, aun no es tu turno.", Action);
        } else {

            if (activeLS != true) {
                updatePlayer("-fx-border-color :red; -fx-background-radius : 6.0em;", "", "");
                iL = 0;
                type = "J";
                coord = "";
            }
        }

    }

    @FXML
    private void mouseClickedImg2(MouseEvent event) {
        rotation = 0;
        if (game.getActualTurn() != player.getId()) {

            MostrarAviso("Espera, aun no es tu turno.", Action);
        } else {

            if (activeLS != true) {
                updatePlayer("", "-fx-border-color :red; -fx-background-radius : 6.0em;", "");
                iL = 1;
                type = "J";
                coord = "";
            }
        }

    }

    @FXML
    private void mouseClickedImg3(MouseEvent event) {
        rotation = 0;
        if (game.getActualTurn() != player.getId()) {

            MostrarAviso("Espera, aun no es tu turno.", Action);
        } else {
            if (activeLS != true) {
                updatePlayer("", "", "-fx-border-color :red; -fx-background-radius : 6.0em;");
                iL = 2;
                type = "J";
                coord = "";
            }

        }

    }

    @FXML
    private void approveMove(ActionEvent event) {

        try {
            if (target != null && !offLine.isVisible() && game.getActualTurn() == player.getId()) {

                if (game.getPlayersDisconnected().isEmpty()) {
                    game.cleanAvailableSpaces(tablero, " -fx-background-color: #435560;");

                    if (type.equals("E") && activeLS) {

                      

                        game.insertInTablero(tablero.getRowIndex(target), tablero.getColumnIndex(target), game.getSelvaExplorada().get(iL));
                        game.getSelvaExplorada().remove(iL);
                        if (game.getLosetasSelva().size() > 1 && game.getSelvaExplorada().isEmpty()) {
                            game.generarSelvaExplorada(1);
                        } else if (game.getLosetasSelva().size() == 1 && game.getSelvaExplorada().isEmpty()) {
                            game.getSelvaExplorada().add(game.getLosetasSelva().get(0));
                            game.getLosetasSelva().remove(0);

                        }
                        updateLbls("", "");

                        if (game.getSelvaExplorada().size() > 0) {
                            game.markWherePutLS(tablero, " -fx-background-color: #435560;");
                            if (!game.checkIfIsThereMoreToPut(tablero, " -fx-background-color: #435560;")) {
                                activeLS = false;
                                if (!game.findPayment(player.getId(), tablero, player)) {

                                    sendMove();
                                }
                            }

                        } else {
                            activeLS = false;
                            if (!game.findPayment(player.getId(), tablero, player)) {

                                sendMove();
                            }

                        }
                        target = null;

                        type = "";
                        iL = -1;
                        coord = "";
                        ct2.setVisible(false);

                    } else if (type.equals("J") && !activeLS) {

                        approve();

                    } else {
                        game.markAvailableSpaces(tablero, " -fx-background-color: #435560;", player);
                    }

                } else {
                    mostrarDisconnected();
                }

            } else {
                if (offLine.isVisible()) {
                    MostrarAviso("Intenta reconectar", "");
                } else {
                    game.markWherePutLS(tablero, " -fx-background-color: #435560;");
                    MostrarAviso("Debes colocar una loseta", "");
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void mostrarDisconnected() {

        sDisconnected = "No puedes jugar.\n";

        for (i = 0; i < game.getPlayersDisconnected().size(); i++) {

            sDisconnected += "Se desconectó: " + game.getPlayersDisconnected().get(i).getInfo().getNombre() + "\n";

        }
        MostrarAviso(sDisconnected, "");

    }

    private void UpdateDataPlayers() {

        try {
            updatePlayersFromPlayer();
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new Message("updatePlayers"));
            oos.flush();

            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(players);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getWinner() {

        ArrayList<Integer> nWin = new ArrayList<>();
        i = 0;
        game.setAns(0);
        for (i = 0; i < players.size(); i++) {
         
            if (players.get(i).getPuntos() > game.getAns()) {

                game.setAns(players.get(i).getPuntos());
                game.setIdB(i);
            }
        }

        for (i = 0; i < players.size(); i++) {

            if (players.get(i).getPuntos() == players.get(game.getIdB()).getPuntos()) {
                nWin.add(i);
            }
        }

        if (nWin.size() == 1) {
            MostrarAviso("El ganador es: " + players.get(nWin.get(0)).getInfo().getNombre() + " con " + players.get(nWin.get(0)).getPuntos() + " puntos", "");

        } else {
           
            for (i = 0; i < nWin.size(); i++) {

                System.out.println(players.get(nWin.get(i)).getInfo().getNombre() + players.get(nWin.get(i)).getPuntos());
            }
        }

    }

    public void MostrarAviso(String aviso, String action) {

        BoxBlur blur = new BoxBlur(3, 3, 3);
        JFXDialogLayout dialogLayout = new JFXDialogLayout();

        JFXButton button = new JFXButton();
        button.setText("OK.");

        button.setStyle("-fx-background-color:#3b6978;-fx-text-fill : white ;-fx-border-color:#3b6978;-fx-border-width: 0 3 3 0;");
        dialog = new JFXDialog(stackGame, dialogLayout, JFXDialog.DialogTransition.TOP);
        dialog.setDisable(false);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
            dialog.close();
        });
        Label lbl = new Label(aviso);
        lbl.setStyle("-fx-text-fill : black ;");
        dialogLayout.setHeading(lbl);
        dialogLayout.setActions(button);
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event1) -> {

            gameAnchorP.setEffect(null);
            dialog.setDisable(true);

            if (action.equals("main")) {
                Connect.setVisible(true);

                mainGame.setVisible(false);
                ct.setVisible(false);

            }

        });
        gameAnchorP.setEffect(blur);

    }

    private void approve() {

        BoxBlur blur = new BoxBlur(3, 3, 3);
        JFXDialogLayout dialogLayout = new JFXDialogLayout();

        JFXButton aceptar = new JFXButton();
        aceptar.setText("SI.");
        JFXButton cancelar = new JFXButton();
        cancelar.setText("CANCELAR");
        aceptar.setStyle("-fx-background-color:#3b6978;-fx-text-fill : white ;-fx-border-color:#3b6978;-fx-border-width: 0 3 3 0;");
        cancelar.setStyle("-fx-background-color:#da7272;-fx-text-fill : white ;-fx-border-color:#da7272;-fx-border-width: 0 3 3 0;");

        dialog = new JFXDialog(stackGame, dialogLayout, JFXDialog.DialogTransition.TOP);
        dialog.setDisable(false);
        cancelar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
            player.getManoInicial().get(iL).setRotation(0);
            rotation = 0;
            target.setRotate(rotation);
            target.setStyle(" -fx-background-color: #435560;");
            target = null;
            game.markAvailableSpaces(tablero, " -fx-background-color: #435560;", player);
            updatePlayer("", "", "");
            dialog.close();
        });
        aceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
            player.getManoInicial().get(iL).setRotation(rotation);
            if (game.tablero[tablero.getRowIndex(target)][tablero.getColumnIndex(target)] != null) {
                if (game.tablero[tablero.getRowIndex(target)][tablero.getColumnIndex(target)].getTipo().equals("J")) {

                    game.getSoles().add(player.getSoles().get(0));
                    player.getSoles().remove(0);

                }
            }
            game.insertInTablero(tablero.getRowIndex(target), tablero.getColumnIndex(target), player.getManoInicial().get(iL));
//                        game.printNoNullTable();
            player.getManoInicial().remove(iL);

            if (player.getFichas().size() > 1) {

                player.generarManoInicial(1);

            } else if (player.getFichas().size() == 1) {

                player.getManoInicial().add(player.getFichas().get(0));
                player.getFichas().remove(0);

            }

            updatePlayer("", "", "");
            rotation = 0;
            if (game.getSelvaExplorada().size() > 0) {
                if (game.markWherePutLS(tablero, " -fx-background-color: #435560;")) {
                    activeLS = true;
                } else {
                    if (!game.findPayment(player.getId(), tablero, player)) {

                        sendMove();
                    }

                }

            } else {

                if (!game.findPayment(player.getId(), tablero, player)) {

                    sendMove();
                }

            }
            target = null;

            type = "";
            iL = -1;
            coord = "";
            ct2.setVisible(false);

            dialog.close();
        });

        vb.getChildren().clear();
        vb.setAlignment(Pos.CENTER);

        lbl.setStyle("-fx-text-fill: black ");
        ImageView imageView = new ImageView(new Image(player.getManoInicial().get(iL).getUrlImage()));
        imageView.setRotate(rotation);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        vb.getChildren().addAll(lbl, gridPane);

        Node md = game.getNodeByRowColumnIndex(1, 1, gridPane);

        md.setStyle(target.getStyle());
        md.setRotate(rotation);

        md = game.getNodeByRowColumnIndex(1, 0, gridPane);
        md.setStyle(game.getNodeByRowColumnIndex(tablero.getRowIndex(target), tablero.getColumnIndex(target) - 1, tablero).getStyle());

        md = game.getNodeByRowColumnIndex(1, 2, gridPane);
        md.setStyle(game.getNodeByRowColumnIndex(tablero.getRowIndex(target), tablero.getColumnIndex(target) + 1, tablero).getStyle());

        md = game.getNodeByRowColumnIndex(0, 1, gridPane);
        md.setStyle(game.getNodeByRowColumnIndex(tablero.getRowIndex(target) - 1, tablero.getColumnIndex(target), tablero).getStyle());
        md = game.getNodeByRowColumnIndex(2, 1, gridPane);
        md.setStyle(game.getNodeByRowColumnIndex(tablero.getRowIndex(target) + 1, tablero.getColumnIndex(target), tablero).getStyle());

        dialogLayout.setHeading(vb);

        dialogLayout.setActions(cancelar, aceptar);
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
            gameAnchorP.setEffect(null);
            dialog.setDisable(true);

        });
        gameAnchorP.setEffect(blur);

    }

    @FXML
    private void startGame(ActionEvent event) {

        try {
            if (players.size() >= 2) {

                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(new Message("start"));
                oos.flush();
            }else{
                MostrarAviso("NO hay jugadores suficientes.", "");
            }

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static boolean serverListening(String host, int port) {
        Socket s = null;
        try {
            s = new Socket(host, port);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception e) {
                }
            }
        }
    }

    @FXML
    private void ReconnectGame(ActionEvent event) {

        try {
            s.close();
            s = new Socket(ip.getText(), Integer.valueOf(pt.getText()));

            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
            msg = (Message) ois.readObject();
            oos.writeObject(new Message("reconnect")); // 

            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(player);

            UpdateDataPlayers();
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new Message("updateComponents"));
            oos.flush();
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(game);
            oos.flush();

            offLine.setVisible(false);
            OffLine = false;

        } catch (IOException | NumberFormatException | ClassNotFoundException e) {
           
            offLine.setVisible(true);
            OffLine = true;
            MostrarAviso("Servidor fuera de linea", "");

        }

    }

    @FXML
    private void refreshSpace(MouseEvent event) {

        try {
            if (game.getActualTurn() == player.getId()) {

                rotation = 0;
                target = null;

                if (activeLS) {
                    game.markWherePutLS(tablero, " -fx-background-color: #435560;");
                } else {

                    game.markAvailableSpaces(tablero, " -fx-background-color: #435560;", player);
                    updatePlayer("", "", "");
                }

            } else {
                MostrarAviso("Espera, aun no es tu turno.", Action);
            }
        } catch (Exception e) {
        }

    }

    class read implements Runnable {

        @Override
        public void run() {
            while (!listeningServer) {
           

                try {
                    if (!offLine.isVisible()) {
                        ois = new ObjectInputStream(s.getInputStream());
                        msg = (Message) ois.readObject();
                        switch (msg.msg) {
                            case "updateComponents":
                               
                                ct.setVisible(true);
                                ois = new ObjectInputStream(s.getInputStream());
                                msg = (Message) ois.readObject();
                                ois = new ObjectInputStream(s.getInputStream());
                                game = (GameServer) ois.readObject();
                                Platform.runLater(() -> whoIsPlaying());
                                tOnline = game.getpOnline();
                                Platform.runLater(() -> game.loadTable(tablero, scrTablero));
                              
                                if (game.getStarted() && game.getActualTurn() == player.getId() && msg.msg.equals("NotYet")) {
                                    System.out.println("my turn...");
                                    if (game.getLosetasSelva().size() > 1 && game.getSelvaExplorada().size() == 1) {

                                        Platform.runLater(() -> game.generarSelvaExplorada(1));
                                    }
                                    if (!player.getManoInicial().isEmpty()) {
                                        Platform.runLater(() -> game.markAvailableSpaces(tablero, " -fx-background-color: #435560;", player));

                                    }

                                } else if (msg.msg.equals("finish")) {

 
                                    Boolean c = false;
                                    for (int i = 0; i < players.size(); i++) {
                                        if (game.findPaymentServer(players.get(i).getId(), players.get(i))) {
                                            c = true;

                                        }
                                    }
                                    if (c == false) {

                                        Platform.runLater(() -> game.generarGanador(players));

                                        Platform.runLater(() -> OpenPuntaje());

                                    } else {
                                        c = game.findPayment(player.getId(), tablero, player);
                                        if (c == false && game.getActualTurn() == player.getId()) {
                                            sendMove();
                                        }
                                    }
                                }
                                Platform.runLater(() -> updateLbls("", ""));
                                break;
                            case "players":
                                ois = new ObjectInputStream(s.getInputStream());
                                players = (ArrayList<Player>) ois.readObject();
                                ct.setVisible(true);
                                Platform.runLater(() -> updatePlayerFromPlayers());
                                Platform.runLater(() -> updatePlayers());
                                Platform.runLater(() -> updatePlayer("", "", ""));
                                if (game.getStarted() && game.getActualTurn() == player.getId()) {
                                    System.out.println("my turn...");
                                    if (game.getLosetasSelva().size() > 1 && game.getSelvaExplorada().size() == 1) {

                                        Platform.runLater(() -> game.generarSelvaExplorada(1));
                                    }
                                    if (!player.getManoInicial().isEmpty()) {
                                        Platform.runLater(() -> game.markAvailableSpaces(tablero, " -fx-background-color: #435560;", player));

                                    }
                                }
                                break;
                            case "chat":
                                mediaPlayer.play();
                                mediaPlayer.setVolume(0.5);
                                setTimeout(() -> mediaPlayer.stop(), 2000);
                                ois = new ObjectInputStream(s.getInputStream());
                                chat = (ArrayList<Chat>) ois.readObject();
                                showNotification("Nuevo mensaje", iconMessage);
                                if (isOpenChat) {
                                    Platform.runLater(() -> chatController.setFuncionalidad(chat, s, info.getNombre()));

                                }
                                break;
                            case "updatePlayers":
                                ois = new ObjectInputStream(s.getInputStream());
                                players = (ArrayList<Player>) ois.readObject();
                                Platform.runLater(() -> updatePlayerFromPlayers());
                                Platform.runLater(() -> updatePlayers());
                                Platform.runLater(() -> updatePlayer("", "", ""));
                                break;
                            case "start":
                                ois = new ObjectInputStream(s.getInputStream());
                                game = (GameServer) ois.readObject();
                                Platform.runLater(() -> game.generarGanador(players));

                                Platform.runLater(() -> whoIsPlaying());
                                tOnline = game.getpOnline();
                                Platform.runLater(() -> game.removeChildrenS(tablero));
                                Platform.runLater(() -> game.loadTable(tablero, scrTablero));
                                if (game.getStarted() && game.getActualTurn() == player.getId()) {

                                    Platform.runLater(() -> game.markAvailableSpaces(tablero, " -fx-background-color: #435560;", player));

                                }
                                Platform.runLater(() -> updateLbls("", ""));
                                break;
                            case "resetGame":
                             
                                try {
                                    scene.setRoot(FXMLLoader.load(getClass().getResource("FXMLDocument.fxml")));
                                } catch (Exception exc) {
                                }
                                break;
                            case "latido":
                                System.out.println("Recibiendo latido");

                        }
                    }

                } catch (IOException | ClassNotFoundException e) {
//
//                    try {
//                        s.close();s
//                        OffLine = true;
                    offLine.setVisible(true);

//                    } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, e);
//                    }
//
//                    e.printStackTrace();
                }

            }
            System.out.println("Server is stopped....");
        }

        public void stop() {
            listeningServer = true;
        }
    }

}

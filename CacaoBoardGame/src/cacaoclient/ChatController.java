/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacaoclient;

import clases.Chat;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Elias
 */
public class ChatController implements Initializable {

    @FXML
    private JFXTextField lblSend;
    static ArrayList<Chat> chat = new ArrayList<>();
    @FXML
    private VBox chatPannel;
    Socket s;
    String nombre; 
    ObjectInputStream ois;
    ObjectOutputStream oos;
    @FXML
    private FontAwesomeIcon btnSend;
    
    /**
     * Initializes the controller class.
     * @param chat
     * @param s
     * @param nombre
     */
    public void setFuncionalidad(ArrayList<Chat> chat, Socket s, String nombre) {
        this.s = s;
        this.chat = chat;
        this.nombre = nombre;
        readChatsArray();
    }

    private void readChatsArray() {

        chatPannel.getChildren().clear();

        for (Chat chat1 : chat) {
            Label lbl = new Label();
            lbl.setText(chat1.getNombre() + ": " + chat1.getMensaje());
            lbl.setPadding(new Insets(0, 5, 0, 5));
            chatPannel.getChildren().add(lbl);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void sendMessage(ActionEvent event) {
        try {
            Label lbl = new Label();
            lbl.setText( nombre + ": " + lblSend.getText());
            lbl.setPadding(new Insets(0, 5, 0, 5));
            chatPannel.getChildren().add(lbl);
            
            chat.add( new Chat(nombre, lblSend.getText() ));
            
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new Message("chat"));
            oos.flush();
            
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(chat);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

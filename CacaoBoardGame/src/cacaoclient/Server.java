package cacaoclient;

import clases.Chat;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

import clases.GameServer;
import clases.Player;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Elias
 */
public class Server extends Application {

    static ArrayList<Chat> chat = new ArrayList<>();
    static ArrayList<String> color = new ArrayList<>();
    static ArrayList<String> ips = new ArrayList<>();
    static Vector<ClientHandler> ar = new Vector<>();

    public static Player player;

    public static ArrayList<Player> players = new ArrayList<>();
    public static ArrayList<Player> backupPlayers = new ArrayList<>();
    public static GameServer game = new GameServer();
    public static read tRead = new read();

    public static Thread threadRead = new Thread(tRead, "T1");

    static int i = 0;
    static int id = 0;
    static int m = 0;
    static Boolean reconnect = false;
    static ObjectOutputStream toClient = null;
    static ObjectInputStream fromClient = null;

    public static void SendPlayersUpdate() throws IOException {
        Server.ar.stream().forEach(new Consumer<ClientHandler>() {

            @Override
            public void accept(ClientHandler mc) {
                try {
                    game.removeDisconnected(mc.p);
                    mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                    mc.oos.writeObject(new Message("players"));
                    mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                    mc.oos.writeObject(players);

                    mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                    mc.oos.writeObject(new Message("updateComponents"));

                    mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                    mc.oos.writeObject(new Message("NotYet"));

                    mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                    mc.oos.writeObject(Server.game);

                } catch (Exception e) {
                }
            }
        });

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static class ThreadPlayer extends Thread {

        @Override
        public void run() {
            while (true) {

                if (!backupPlayers.equals(players)) {
                    try {
                        SendPlayersUpdate();
                        backupPlayers = players;
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        }
    }

    public static void main(String[] args) throws IOException {
        // server is listening on port 1234
        ServerSocket ss = new ServerSocket(3001);

        Socket s;
        threadRead.start();

        while (true) {

            try {
                s = ss.accept();

                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

 
                oos.writeObject(new Message("components")); //

                Message msg = (Message) ois.readObject(); //

                if (msg.msg.contains("reconnect")) {
                    reconnect = true;
                    Boolean Found = false;

                    ois = new ObjectInputStream(s.getInputStream());
                    player = (Player) ois.readObject();

                    for (ClientHandler mc : Server.ar) {

                        if (mc.s.getRemoteSocketAddress().toString().substring(0, 10).equals(s.getRemoteSocketAddress().toString().substring(0, 10))) {
                          
                            mc.s = s;
                            Found = true;

                            game.removeDisconnected(mc.p);

                        }

                    }

                    if (!Found) {
                        ClientHandler mtch = new ClientHandler(s, "client " + i, player.getInfo(), oos, ois, player);
                        Thread t = new Thread(mtch);
                        ar.add(mtch);
                        t.start();
                    }

                } else {
                    if (color.contains(msg.msg)) {

                        oos = new ObjectOutputStream(s.getOutputStream());
                        ois = new ObjectInputStream(s.getInputStream());

                        oos.writeObject(new Message("alreadyUsed"));
                        msg = (Message) ois.readObject();

                    } else {

                        oos = new ObjectOutputStream(s.getOutputStream());
                        ois = new ObjectInputStream(s.getInputStream());

                        oos.writeObject(new Message("non"));
                        msg = (Message) ois.readObject();

                        oos = new ObjectOutputStream(s.getOutputStream());
                        ois = new ObjectInputStream(s.getInputStream());

                        oos.writeObject(game);
                        infoClient info = (infoClient) ois.readObject();

                        Boolean Found = false;
                        ClientHandler mt = null;
                        for (ClientHandler mc : Server.ar) {
                            try {
                                if (mc.s.getRemoteSocketAddress().toString().substring(0, 10).equals(s.getRemoteSocketAddress().toString().substring(0, 10))) {
                                    System.err.println("here...");
                                    mc.Break = false;
                                    player = mc.p;
                                    Found = true;
                                    mt = mc;
                                    game.removeDisconnected(mc.p);

                                }

                            } catch (Exception e) {
                            }

                        }
                        try {

                            Server.ar.remove(mt);
                        } catch (Exception e) {
                        }
                        if (!Found && !game.getStarted()) {
                            i++;
                            player = new Player(i);
                            player.setInfo(info);
                            players.add(player);

                        }
                        oos = new ObjectOutputStream(s.getOutputStream());
                        ois = new ObjectInputStream(s.getInputStream());

                        if ((Found && game.getStarted()) || (!Found && !game.getStarted())) {

                            oos.writeObject(player);
                            msg = (Message) ois.readObject();
                            ClientHandler mtch = new ClientHandler(s, "client " + i + info.getNombre(), info, oos, ois, player);

                            Thread t = new Thread(mtch);

                            ar.add(mtch);

                            t.start();

                         

                            if (player.getInfo().edad() > m) {
                                m = player.getInfo().edad();
                                id = player.getId();
                            }

                            SendPlayersUpdate();
                        }else{
                            oos.writeObject(null);
                            msg = (Message) ois.readObject();
                        }

                    }

                }

//                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    static class read implements Runnable {

        private volatile boolean exit = false;

        @Override
        public void run() {

            while (true) {
                try {
                    Server.threadRead.sleep(300000);
                    latido();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

        public void latido() {
            try {
                for (ClientHandler mc : Server.ar) {
                    try {
                        mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                        mc.oos.writeObject(new Message("latido"));
                    } catch (Exception e) {
                        game.addDisconnected(mc.p);
                        setTimeout(() -> mc.resetGame(), 300000);
                    }

                }
            } catch (Exception e) {

            }
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

    }
// ClientHandler class

    static class ClientHandler implements Runnable {

        Scanner scn = new Scanner(System.in);
        private final String name;
        DataInputStream dis;
        DataOutputStream dos;

        infoClient info;
        Player p;
        Socket s;
        boolean isloggedin;
        boolean Break = true;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        // constructor
        public ClientHandler(Socket s, String name,
                infoClient info, ObjectOutputStream oos, ObjectInputStream ois, Player p) {

            this.name = name;
            this.s = s;
            this.isloggedin = true;
            this.info = info;
            this.oos = oos;
            this.ois = ois;
            this.p = p;

        }

        public void resetGame() {

            if (Server.game.getPlayersDisconnected().size() > 0) {
                Server.ar.stream().forEach((ClientHandler mc) -> {
                    try {
                        if (mc.isloggedin == true) {

                            mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                            mc.oos.writeObject(new Message("resetGame"));

                        }
                    } catch (Exception e) {

                    }
                });

                Server.ar.clear();
                Server.game = new GameServer();
                Server.players.clear();
                Server.color.clear();
                Server.chat.clear();
                Server.id = 0;

                Break = false;
            } else {
                Break = true;
            }

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

        public Boolean checkIfFinish() {
            return Server.players.stream().noneMatch((pl) -> (pl.getFichas().size() > 0 || pl.getManoInicial().size() > 0 || !Server.game.getStarted()));
        }

        @Override
        public void run() {

            while (Break) {
                try {

                    if (s.isConnected()) {

                        if (s.getInputStream().available() > 0) {

                            ois = new ObjectInputStream(s.getInputStream());
                            Message msg = (Message) ois.readObject();
       
                            switch (msg.msg) {
                                case "updateComponents":
                                    ois = new ObjectInputStream(s.getInputStream());
                                    Server.game = (GameServer) ois.readObject();
                                    game.setpOnline(Server.ar.size());
                                    if (players.size() == Server.ar.size()) {
                                        reconnect = false;
                                    }
                                    Server.ar.stream().forEach((ClientHandler mc) -> {
                                        try {
                                            if (mc.isloggedin == true) {

                                                mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                                                mc.oos.writeObject(msg);
                                                if (checkIfFinish()) {
                                                    mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                                                    mc.oos.writeObject(new Message("finish"));

                                                } else {
                                                    mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                                                    mc.oos.writeObject(new Message("NotYet"));

                                                }
                                                game.removeDisconnected(mc.p);
                                                mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                                                mc.oos.writeObject(Server.game);

                                              
                                            }
                                        } catch (Exception e) {
                                  
                                            game.addDisconnected(mc.p);
                                            setTimeout(() -> resetGame(), 300000);
                                        }
                                    });
                                    break;
                                case "chat":
                                    ois = new ObjectInputStream(s.getInputStream());
                                    Server.chat = (ArrayList<Chat>) ois.readObject();
                                    Server.ar.stream().forEach(new Consumer<ClientHandler>() {

                                        @Override
                                        public void accept(ClientHandler mc) {
                                            try {
                                                if (mc.isloggedin == true && !ClientHandler.this.name.equals(mc.name)) {
                                                    mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                                                    mc.oos.writeObject(msg);
                                                    mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                                                    mc.oos.writeObject(chat);
                                                }
                                            } catch (Exception e) {
                                           
                                                game.addDisconnected(mc.p);
                                                setTimeout(() -> resetGame(), 300000);
                                            }
                                        }
                                    });
                                    break;
                                case "updatePlayers":
                                    ois = new ObjectInputStream(s.getInputStream());
                                    Server.players = (ArrayList<Player>) ois.readObject();
                                    Server.ar.stream().forEach((ClientHandler mc) -> {
                                        try {
                                            if (mc.isloggedin == true) {
                                                mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                                                mc.oos.writeObject(msg);
                                                mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                                                mc.oos.writeObject(players);

                                            }
                                        } catch (Exception e) {
                                         
                                            game.addDisconnected(mc.p);
                                            setTimeout(() -> resetGame(), 300000);
                                        }
                                    });
                                    break;
                                case "start":
                                    Server.game = new GameServer();
                                    game.init(game, players.size());
                                    game.setStarted(true);
                                    game.generateTurns(id, players);
                                    game.setpOnline(Server.ar.size());
                                  
                                    players.stream().map((player1) -> {
                                        player1.getFichas().clear();
                                        return player1;
                                    }).map((player1) -> {
                                        player1.getManoInicial().clear();
                                        return player1;
                                    }).map((player1) -> {
                                        player1.getCacaos().clear();
                                        return player1;
                                    }).map((player1) -> {
                                        player1.getMonedas().clear();
                                        return player1;
                                    }).map((player1) -> {
                                        player1.getSoles().clear();
                                        return player1;
                                    }).map((player1) -> {
                                        player1.tmpvalue = 0;
                                        return player1;
                                    }).map((player1) -> {
                                        player1.setPuntos(0);
                                        return player1;
                                    }).map((player1) -> {
                                        player1.setPuntosTemplo(0);
                                        return player1;
                                    }).map((player1) -> {
                                        player1.setCenote(1);
                                        return player1;
                                    }).map((player1) -> {
                                        player1.losetasSelva(4, "loseta_1-1-1-1", "/images/loseta_1_1_1_1.png", player1.getInfo().getColor(), 1, 1, 1, 1);
                                        return player1;
                                    }).map((player1) -> {
                                        player1.losetasSelva(5, "loseta_2_1_0_1", "/images/loseta_2_1_0_1.png", player1.getInfo().getColor(), 1, 2, 1, 0);
                                        return player1;
                                    }).map((player1) -> {
                                        player1.losetasSelva(1, "loseta_3_0_0_1", "/images/loseta_3_0_0_1.png", player1.getInfo().getColor(), 1, 3, 0, 0);
                                        return player1;
                                    }).map((player1) -> {
                                        player1.losetasSelva(1, "loseta_3_1_0_0", "/images/loseta_3_1_0_0.png", player1.getInfo().getColor(), 0, 3, 1, 0);
                                        return player1;
                                    }).map((player1) -> {
                                        player1.generarManoInicial(3);
                                        return player1;
                                    }).map((player1) -> {
                                        if (players.size() == 3) {
                                            player1.popLoseta("loseta_1-1-1-1");
                                        }
                                        return player1;
                                    }).filter((player1) -> (players.size() == 4)).map((player1) -> {
                                        player1.popLoseta("loseta_1-1-1-1");
                                        return player1;
                                    }).map((player1) -> {
                                        player1.popLoseta("loseta_1-1-1-1");
                                        return player1;
                                    }).forEach((player1) -> {
                                        player1.popLoseta("loseta_2_1_0_1");
                                    });
                                   
                                    Server.ar.stream().filter((mc) -> (mc.isloggedin == true)).forEach(new Consumer<ClientHandler>() {

                                        @Override
                                        public void accept(ClientHandler mc) {
                                            try {

                                                mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                                                mc.oos.writeObject(new Message("updatePlayers"));
                                                mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                                                mc.oos.writeObject(players);
                                                game.removeDisconnected(mc.p);
                                                mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                                                mc.oos.writeObject(new Message("start"));
                                                mc.oos = new ObjectOutputStream(mc.s.getOutputStream());
                                                mc.oos.writeObject(game);
                                            } catch (Exception e) {

                                               

                                                game.addDisconnected(mc.p);
                                                setTimeout(() -> resetGame(), 300000);
                                            }
                                        }
                                    });
                                    break;
                            }

                        }

                    }
                } catch (IOException | ClassNotFoundException e) {
                }

            }
        }

    }
}

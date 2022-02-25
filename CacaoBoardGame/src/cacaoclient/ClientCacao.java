/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacaoclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Elias
 */
public class ClientCacao extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(true);
        stage.setOnCloseRequest((ae) -> {
            Platform.exit();
            System.exit(0);
        });
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLDocument.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        FXMLDocumentController controller = (FXMLDocumentController) loader.getController();
        controller.setConfig(scene);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    private static void printLines(String name, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(name + " " + line);
        }
    }

    private static int runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        printLines(command + " stdout:", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());
        pro.waitFor();
        
        return pro.exitValue();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//
//        Platform.runLater(() -> {
//            try {
//                int k = runProcess("javac src/cacaoclient/Server.java");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });

        launch(args);

    }

}

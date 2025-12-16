package lp.JavaFxClient_Equipa05;


// Atualizar o App.java para abrir o login-view.fxml, é para ajustar o package e o caminho do fxml

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application 
{

    @Override
    public void start(Stage stage) throws Exception 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login-view.fxml"));
        Scene scene = new Scene(loader.load(), 450, 300);
        stage.setTitle("Equipa05 - Cliente JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
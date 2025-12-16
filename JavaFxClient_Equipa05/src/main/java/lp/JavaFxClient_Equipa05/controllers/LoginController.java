package lp.JavaFxClient_Equipa05.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


// ainda não é login real — é só para validar FXML + controller.

public class LoginController 
{

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMsg;

    @FXML
    private void onLogin() 
    {
        lblMsg.setText("Cliquei! user=" + txtUsername.getText());
    }
}


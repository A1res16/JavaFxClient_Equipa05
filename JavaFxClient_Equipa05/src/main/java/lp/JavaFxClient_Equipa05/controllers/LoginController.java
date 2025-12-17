package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ApiService;
import model.PersonDTO;
import model.GuardarSessaoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginController
{
    @FXML
    private VBox contentPane;
    
    @FXML
    private TextField txtUsername;
    
    @FXML
    private PasswordField txtPassword;
    
    private ApiService apiService = new ApiService();
    private ObjectMapper mapper = new ObjectMapper();
    private PersonDTO currentUser;

    @FXML
    public void initialize()
    {
        // Ao iniciar, cria logo o admin para existir pelo menos 1 conta
        criarAdmin();
    }

    @FXML
    public void onLogin()
    {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Preencha todos os campos!");
            a.showAndWait();
            return;
        }
        
        try {
            String json = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
            String response = apiService.post("/persons/login", json);
            
            if (response.startsWith("ERROR:")) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Erro de conexão à base de dados");
                a.showAndWait();
                return;
            }
            
            // A api devolve um erro se nao existir o utilizador 
            if (response.contains("\"status\"") && response.contains("\"error\"")) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Username ou password inválidos!");
                a.showAndWait();
                return;
            }

            PersonDTO person = mapper.readValue(response, PersonDTO.class);
            currentUser = person;
            
            // Guardar dados do utilizador na sessão
            GuardarSessaoDTO.getInstance().setUser(person.getId(), person.getUsername(), person.getPerfil().toString());
            
            String perfil = person.getPerfil().toString();
            
            if (perfil.equalsIgnoreCase("ADMIN")) {
                showMenuAdmin();
            } else if (perfil.equalsIgnoreCase("PROMOTOR")) {
                showMenuPromotor();
            } else if (perfil.equalsIgnoreCase("GESTOR")) {
                showMenuGestor();
            } else if (perfil.equalsIgnoreCase("PARTICIPANTE")) {
                showMenuParticipante();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Login falhou: " + e.getMessage());
            a.showAndWait();
        }
    }

    @FXML
    public void showMenuAdmin()
    {
        loadView("/menuAdmin.fxml");
    }

    @FXML
    public void showMenuPromotor()
    {
        loadView("/menuPromotor.fxml");
    }

    @FXML
    public void showMenuGestor()
    {
        loadView("/menuGestor.fxml");
    }

    @FXML
    public void showMenuParticipante()
    {
        loadView("/menuParticipante.fxml");
    }

    private void loadView(String fxmlPath)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node view = loader.load();

            contentPane.getChildren().setAll(view);
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);

        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Error loading view: " + e.getMessage());
            a.showAndWait();
        }
    }
    
    private void criarAdmin()
    {
       //falta fazer
    }

    @FXML
    public void onExit()
    {
        Stage stage = (Stage) contentPane.getScene().getWindow();
        stage.close();
    }
    
    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.showAndWait();
    }
    
}
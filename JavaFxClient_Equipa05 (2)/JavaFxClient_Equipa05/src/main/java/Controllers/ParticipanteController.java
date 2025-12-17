package Controllers;
/**@author aires
 * @version 1
 * 
 */
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import services.ApiService;
import model.GuardarSessaoDTO;
import model.EventoDTO;
import model.InscricaoDTO;
import services.ConversorJson;
import java.util.List;
import java.util.Optional;

public class ParticipanteController 
{

    @FXML
    private VBox contentPane;

    private ApiService apiService = new ApiService();

    @FXML
    public void onConsultarEventos() 
    {
        String response = apiService.get("/events");
        List<EventoDTO> list = ConversorJson.parseList(response, EventoDTO.class);
        CriarTabelas.show("Eventos Disponíveis", list, EventoDTO.class);
    }

    @FXML
    public void onInscreverEvento() 
    {
        String response = apiService.get("/events");
        List<EventoDTO> list = ConversorJson.parseList(response, EventoDTO.class);
        
        Optional<EventoDTO> selected = CriarTabelas.showAndSelect("Inscrever em Evento", list, EventoDTO.class);
        
        selected.ifPresent(ev -> 
        {
            Long userId = GuardarSessaoDTO.getInstance().getUserId();
            String url = "/events/" + ev.getId() + "/inscricoes/" + userId;
            String resp = apiService.post(url, null);
            
            if (resp.startsWith("ERROR:")) 
            {
                showError("Erro ao inscrever: " + resp);
            } 
            else 
            {
                showInfo("Sucesso", "Inscrição realizada com sucesso!\n" + resp);
            }
        });
    }

    @FXML
    public void onCancelarInscricao() 
    {
        String response = apiService.get("/inscricoes");
        List<InscricaoDTO> list = ConversorJson.parseList(response, InscricaoDTO.class);
        
        Optional<InscricaoDTO> selected = CriarTabelas.showAndSelect("Cancelar Inscrição", list, InscricaoDTO.class);
        
        selected.ifPresent(ins -> {
            Long userId = GuardarSessaoDTO.getInstance().getUserId();
            // Assuming we cancel by Event ID as per previous logic
            String url = "/events/" + ins.getEventoId() + "/inscricoes/" + userId;
            String resp = apiService.delete(url);
            
            if (resp.startsWith("ERROR:")) 
            {
                showError("Erro ao cancelar: " + resp);
            } 
            else 
            {
                showInfo("Sucesso", "Inscrição cancelada com sucesso!");
            }
        });
    }

    @FXML
    public void onMinhasInscricoes() 
    {
        String response = apiService.get("/inscricoes");
        List<InscricaoDTO> list = ConversorJson.parseList(response, InscricaoDTO.class);
        CriarTabelas.show("Minhas Inscrições", list, InscricaoDTO.class);
    }

    @FXML
    public void onSair() 
    {
        GuardarSessaoDTO.getInstance().clear();
        loadView("/login.fxml");
    }

    private void loadView(String fxmlPath) 
    {
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            
            contentPane.getScene().setRoot(view);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            showError("Erro ao carregar vista: " + e.getMessage());
        }
    }

    private void showInfo(String title, String content) 
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        
        TextArea textArea = new TextArea(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(15);
        textArea.setPrefColumnCount(50);
        
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    private void showError(String msg) 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
    }
}

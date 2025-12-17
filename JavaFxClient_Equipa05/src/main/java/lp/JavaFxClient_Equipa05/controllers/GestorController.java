package lp.JavaFxClient_Equipa05.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import services.ApiService;
import model.GuardarSessaoDTO;
import model.PedidoDTO;
import model.EventoDTO;
import services.ConversorJson;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

public class GestorController {

    @FXML
    private VBox contentPane;

    private ApiService apiService = new ApiService();

    @FXML
    public void onListarPedidos() {
        String response = apiService.get("/pedidos");
        List<PedidoDTO> list = ConversorJson.parseList(response, PedidoDTO.class);
        CriarTabelas.show("Lista de Pedidos", list, PedidoDTO.class);
    }

    @FXML
    public void onAvaliarPedido() {
        String response = apiService.get("/pedidos");
        List<PedidoDTO> list = ConversorJson.parseList(response, PedidoDTO.class);
        
        Optional<PedidoDTO> selected = CriarTabelas.showAndSelect("Selecione um pedido para avaliar", list, PedidoDTO.class);
        
        selected.ifPresent(pedido -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Avaliar Pedido: " + pedido.getId());
            dialog.setHeaderText("Avaliação");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            
            ComboBox<String> cmbAprovado = new ComboBox<>();
            cmbAprovado.getItems().addAll("true", "false");
            cmbAprovado.setPromptText("Aprovado?");
            
            TextField txtJustificacao = new TextField();
            txtJustificacao.setPromptText("Justificação");
            txtJustificacao.setText("Excelente");

            grid.add(new Label("Aprovado:"), 0, 0);
            grid.add(cmbAprovado, 1, 0);
            grid.add(new Label("Justificação:"), 0, 1);
            grid.add(txtJustificacao, 1, 1);

            dialog.getDialogPane().setContent(grid);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            
            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    try {
                        boolean aprovado = Boolean.parseBoolean(cmbAprovado.getValue());
                        String justificacao = txtJustificacao.getText();



                        Map<String, Object> payload = new HashMap<>();
                        payload.put("pedidoId", pedido.getId());
                        payload.put("aprovado", aprovado);
                        payload.put("justificacao", justificacao);

                        String json = ConversorJson.toJson(payload);

                        String resp = apiService.post("/avaliacoes", json);
                        showInfo("Resultado", resp);
                    } catch (Exception e) {
                        showError("Erro: " + e.getMessage());
                    }
                }
            });
        });
    }

    @FXML
    public void onEliminarEvento() {
        String response = apiService.get("/events");
        List<EventoDTO> list = ConversorJson.parseList(response, EventoDTO.class);
        
        Optional<EventoDTO> selected = CriarTabelas.showAndSelect("Selecione evento a eliminar", list, EventoDTO.class);
        
        selected.ifPresent(ev -> {
             String res = apiService.delete("/events/" + ev.getId());
             if (res.startsWith("ERROR:")) {
                 showError("Erro ao eliminar: " + res);
             } else {
                 showInfo("Sucesso", "Evento eliminado com sucesso!");
             }
        });
    }

    @FXML
    public void onSair() {
        GuardarSessaoDTO.getInstance().clear();
        loadView("/login.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            
            contentPane.getScene().setRoot(view);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erro ao carregar vista: " + e.getMessage());
        }
    }

    private void showInfo(String title, String content) {
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

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
    }
}

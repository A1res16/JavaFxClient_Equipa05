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
import Enum.TipoEvento;
import Enum.Departamento;
import model.PedidoDTO;
import services.ConversorJson;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

public class PromotorController {

    @FXML
    private VBox contentPane;

    private ApiService apiService = new ApiService();

    // lista os pedidos do promotor
    @FXML
    public void onMeusPedidos() {
        Long userId = GuardarSessaoDTO.getInstance().getUserId();
        String response = apiService.get("/pedidos/promotor/" + userId);
        List<PedidoDTO> list = ConversorJson.parseList(response, PedidoDTO.class);
        CriarTabelas.show("Meus Pedidos", list, PedidoDTO.class);
    }

    // criar um pedido para um evento (preenche o formulario)
    @FXML
    public void onCriarPedido() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Criar Pedido");
        dialog.setHeaderText("Preencha os dados do pedido");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtTitulo = new TextField();
        txtTitulo.setPromptText("Título do Evento");
        
        TextField txtData = new TextField();
        txtData.setPromptText("AAAA-MM-DD");
        
        TextField txtDuracao = new TextField();
        txtDuracao.setPromptText("Duração (minutos)");
        
        TextField txtLocal = new TextField();
        txtLocal.setPromptText("Local");
        
        TextField txtDescricao = new TextField();
        txtDescricao.setPromptText("Descrição");
        
        ComboBox<TipoEvento> cmbTipoEvento = new ComboBox<>();
        cmbTipoEvento.getItems().addAll(TipoEvento.values());
        cmbTipoEvento.setPromptText("Tipo de Evento");
        
        ComboBox<Departamento> cmbDepartamento = new ComboBox<>();
        cmbDepartamento.getItems().addAll(Departamento.values());
        cmbDepartamento.setPromptText("Departamento");
        
        TextField txtOrcamento = new TextField();
        txtOrcamento.setPromptText("Orçamento (€)");
        
        TextField txtCapacidade = new TextField();
        txtCapacidade.setPromptText("Capacidade");

        int row = 0;
        grid.add(new Label("Título:"), 0, row);
        grid.add(txtTitulo, 1, row++);
        grid.add(new Label("Data (AAAA-MM-DD):"), 0, row);
        grid.add(txtData, 1, row++);
        grid.add(new Label("Duração (min):"), 0, row);
        grid.add(txtDuracao, 1, row++);
        grid.add(new Label("Local:"), 0, row);
        grid.add(txtLocal, 1, row++);
        grid.add(new Label("Descrição:"), 0, row);
        grid.add(txtDescricao, 1, row++);
        grid.add(new Label("Tipo Evento:"), 0, row);
        grid.add(cmbTipoEvento, 1, row++);
        grid.add(new Label("Departamento:"), 0, row);
        grid.add(cmbDepartamento, 1, row++);
        grid.add(new Label("Orçamento (€):"), 0, row);
        grid.add(txtOrcamento, 1, row++);
        grid.add(new Label("Capacidade:"), 0, row);
        grid.add(txtCapacidade, 1, row++);

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(350);
        
        dialog.getDialogPane().setContent(scrollPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                try {
                    Map<String, Object> payload = new HashMap<>();
                    payload.put("tituloEvento", txtTitulo.getText());
                    payload.put("data", txtData.getText());
                    payload.put("duracao", Integer.parseInt(txtDuracao.getText()));
                    payload.put("local", txtLocal.getText());
                    payload.put("descricao", txtDescricao.getText());
                    payload.put("tipoEvento", cmbTipoEvento.getValue());
                    payload.put("departamento", cmbDepartamento.getValue());
                    
                    String orcamentoStr = txtOrcamento.getText().replace(",", ".");
                    payload.put("orcamento", Double.parseDouble(orcamentoStr)); // Using Double to be safe, though server might want int
                    
                    payload.put("capacidade", Integer.parseInt(txtCapacidade.getText()));
                    payload.put("promotorId", GuardarSessaoDTO.getInstance().getUserId());

                    String json = ConversorJson.toJson(payload);

                    String response = apiService.post("/pedidos", json);
                    showInfo("Resultado", response);
                } catch (Exception e) {
                    showError("Erro nos dados: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    public void onFormalizarPedido() {
        Long userId = GuardarSessaoDTO.getInstance().getUserId();
        String response = apiService.get("/pedidos/promotor/" + userId);
        List<PedidoDTO> list = ConversorJson.parseList(response, PedidoDTO.class);
        
        Optional<PedidoDTO> selected = CriarTabelas.showAndSelect("Selecione um pedido para formalizar", list, PedidoDTO.class);
        
        selected.ifPresent(pedido -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Formalizar Pedido: " + pedido.getTitulo());
            dialog.setHeaderText("Preencha os dados de formalização");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            
            TextField txtHora = new TextField();
            txtHora.setPromptText("HH:mm:ss");
            
            TextField txtPreco = new TextField();
            txtPreco.setPromptText("Preço Entrada");
            
            TextField txtNomeOrg = new TextField();
            txtNomeOrg.setPromptText("Nome Organizador");
            
            TextField txtEmailOrg = new TextField();
            txtEmailOrg.setPromptText("Email Organizador");
            
            TextField txtContactoOrg = new TextField();
            txtContactoOrg.setPromptText("Contacto Organizador");
            
            TextField txtPatrocinadores = new TextField();
            txtPatrocinadores.setPromptText("Patrocinadores");
            
            TextField txtLink = new TextField();
            txtLink.setPromptText("Link");
            
            TextField txtIdioma = new TextField();
            txtIdioma.setPromptText("Idioma (para Palestras)");
            txtIdioma.setText("Não tem idioma");
            
            TextField txtOradores = new TextField();
            txtOradores.setPromptText("Oradores (para Palestras)");
            txtOradores.setText("Não existem oradores");

            int row = 0;
            grid.add(new Label("Hora (HH:mm:ss):"), 0, row);
            grid.add(txtHora, 1, row++);
            grid.add(new Label("Preço Entrada:"), 0, row);
            grid.add(txtPreco, 1, row++);
            grid.add(new Label("Nome Organizador:"), 0, row);
            grid.add(txtNomeOrg, 1, row++);
            grid.add(new Label("Email Organizador:"), 0, row);
            grid.add(txtEmailOrg, 1, row++);
            grid.add(new Label("Contacto:"), 0, row);
            grid.add(txtContactoOrg, 1, row++);
            grid.add(new Label("Patrocinadores:"), 0, row);
            grid.add(txtPatrocinadores, 1, row++);
            grid.add(new Label("Link:"), 0, row);
            grid.add(txtLink, 1, row++);
            grid.add(new Label("Idioma:"), 0, row);
            grid.add(txtIdioma, 1, row++);
            grid.add(new Label("Oradores:"), 0, row);
            grid.add(txtOradores, 1, row++);

            ScrollPane scrollPane = new ScrollPane(grid);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(350);
            
            dialog.getDialogPane().setContent(scrollPane);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // este showAndWait serve para processar o envio se clicar no botao de ok
            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    try {
                        Map<String, Object> payload = new HashMap<>();
                        payload.put("horarioInicio", txtHora.getText());
                        
                        String precoStr = txtPreco.getText().replace(",", ".");
                        payload.put("precoEntrada", Double.parseDouble(precoStr));
                        
                        payload.put("nomeOrganizador", txtNomeOrg.getText());
                        payload.put("emailOrganizador", txtEmailOrg.getText());
                        payload.put("contactoOrganizador", Integer.parseInt(txtContactoOrg.getText()));
                        payload.put("oradores", txtOradores.getText());
                        payload.put("patrocinadores", txtPatrocinadores.getText());
                        payload.put("link", txtLink.getText());
                        payload.put("idioma", txtIdioma.getText());
                        payload.put("promotorId", GuardarSessaoDTO.getInstance().getUserId());

                        String json = ConversorJson.toJson(payload);

                        // isto faz o post para o endpoint
                        String resp = apiService.post("/pedidos/" + pedido.getId() + "/formalizar", json);
                        showInfo("Resultado", resp);
                    } catch (Exception e) {
                        showError("Erro nos dados: " + e.getMessage());
                    }
                }
            });
        });
    }

    // apagar um pedido que foi feito
    @FXML
    public void onEliminarPedido() {
        Long userId = GuardarSessaoDTO.getInstance().getUserId();
        String response = apiService.get("/pedidos/promotor/" + userId);
        List<PedidoDTO> list = ConversorJson.parseList(response, PedidoDTO.class);
        
        Optional<PedidoDTO> selected = CriarTabelas.showAndSelect("Selecione o pedido a eliminar", list, PedidoDTO.class);
        
        selected.ifPresent(pedido -> {
            // faz delete
             String res = apiService.delete("/pedidos/" + pedido.getId());
             if (res.startsWith("ERROR:")) {
                 showError("Erro ao eliminar: " + res);
             } else {
                 showInfo("Sucesso", "Pedido eliminado com sucesso!");
             }
        });
    }

    // terminar a sessão e volta á pagina de login inicial
    @FXML
    public void onSair() {
        GuardarSessaoDTO.getInstance().clear();
        loadView("/login.fxml");
    }

    // este método serve para mudar a janela que o utilizador está a ver
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

    // este metodo serve mostrar as mensagens quando é criada alguma coisa com sucesso e mostra com um icone
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

    // este metodo serve para mostrar mensagens de erro basicas
    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
    }
}

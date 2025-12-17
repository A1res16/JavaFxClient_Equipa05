package lp.JavaFxClient_Equipa05.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoDTO {
    private Long id;
    
    @JsonProperty("tituloEvento")
    private String titulo;
    
    private String data;
    private int duracao;
    private String local;
    private String descricao;
    private String tipoEvento;
    private String departamento;
    private Double orcamento;
    private Integer capacidade;
    private Long promotorId;
    
    private Boolean aprovado;
    private String justificacao;

    // Campos de formalização
    private String horarioInicio;
    private Double precoEntrada;
    private String nomeOrganizador;
    private String emailOrganizador;
    private Integer contactoOrganizador;
    
    public PedidoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public int getDuracao() { return duracao; }
    public void setDuracao(int duracao) { this.duracao = duracao; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public Double getOrcamento() { return orcamento; }
    public void setOrcamento(Double orcamento) { this.orcamento = orcamento; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }
    
    public Boolean getAprovado() { return aprovado; }
    public void setAprovado(Boolean aprovado) { this.aprovado = aprovado; }

    public String getJustificacao() { return justificacao; }
    public void setJustificacao(String justificacao) { this.justificacao = justificacao; }
    
    @Override
    public String toString() {
        return (titulo != null ? titulo : "Pedido " + id) + " (" + data + ")";
    }
}

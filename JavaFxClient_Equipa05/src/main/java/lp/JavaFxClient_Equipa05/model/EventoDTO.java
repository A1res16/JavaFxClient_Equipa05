package lp.JavaFxClient_Equipa05.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventoDTO {
    private Long id;
    @com.fasterxml.jackson.annotation.JsonProperty("tituloEvento")
    private String titulo;
    private String data;
    private String local;
    private String tipoEvento;
    private String departamento;
    private String horarioInicio;
    private Double precoEntrada;
    
    private int duracao;
    private String descricao;
    private Double orcamento;
    private Integer capacidade;
    private String nomeOrganizador;
    private String emailOrganizador;
    private Integer contactoOrganizador;
    private String oradores;
    private String patrocinadores;
    private String link;
    private String idioma;

    public EventoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public String getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }
    
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    
    public String getHorarioInicio() { return horarioInicio; }
    public void setHorarioInicio(String horarioInicio) { this.horarioInicio = horarioInicio; }
    
    public Double getPrecoEntrada() { return precoEntrada; }
    public void setPrecoEntrada(Double precoEntrada) { this.precoEntrada = precoEntrada; }

    public int getDuracao() { return duracao; }
    public void setDuracao(int duracao) { this.duracao = duracao; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Double getOrcamento() { return orcamento; }
    public void setOrcamento(Double orcamento) { this.orcamento = orcamento; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }

    public String getNomeOrganizador() { return nomeOrganizador; }
    public void setNomeOrganizador(String nomeOrganizador) { this.nomeOrganizador = nomeOrganizador; }

    public String getEmailOrganizador() { return emailOrganizador; }
    public void setEmailOrganizador(String emailOrganizador) { this.emailOrganizador = emailOrganizador; }

    public Integer getContactoOrganizador() { return contactoOrganizador; }
    public void setContactoOrganizador(Integer contactoOrganizador) { this.contactoOrganizador = contactoOrganizador; }

    public String getOradores() { return oradores; }
    public void setOradores(String oradores) { this.oradores = oradores; }

    public String getPatrocinadores() { return patrocinadores; }
    public void setPatrocinadores(String patrocinadores) { this.patrocinadores = patrocinadores; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
}

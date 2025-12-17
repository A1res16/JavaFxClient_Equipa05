package model;
import Enum.Perfil;
import Enum.Tipo;

public class PersonDTO 
{
    private Long id; 
    private String username; 
    private String password;
    private Perfil perfil;
    private Tipo tipo;
    
    public PersonDTO() {
    }
    
    public PersonDTO(Long id, String username, String password, Perfil perfil, Tipo tipo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.perfil = perfil;
        this.tipo = tipo;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Perfil getPerfil() {
        return perfil;
    }
    
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    public Tipo getTipo() {
        return tipo;
    }
    
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
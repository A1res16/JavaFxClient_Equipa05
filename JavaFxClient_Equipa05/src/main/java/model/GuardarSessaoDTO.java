package model;

public class GuardarSessaoDTO {
    
    private static GuardarSessaoDTO instance;
    
    private Long userId;
    private String username;
    private String perfil;
    
    private GuardarSessaoDTO() {}
    
    public static GuardarSessaoDTO getInstance() {
        if (instance == null) {
            instance = new GuardarSessaoDTO();
        }
        return instance;
    }
    
    public void setUser(Long userId, String username, String perfil) {
        this.userId = userId;
        this.username = username;
        this.perfil = perfil;
    }
    
    public void clear() {
        this.userId = null;
        this.username = null;
        this.perfil = null;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPerfil() {
        return perfil;
    }
}

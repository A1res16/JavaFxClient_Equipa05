package model;
/**@author aires
 * @version 1
 * 
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InscricaoDTO 
{
    private Long id;
    private Long eventoId;
    private Long participanteId;
    
    private String dataInscricao;

    public InscricaoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getEventoId() { return eventoId; }
    public void setEventoId(Long eventoId) { this.eventoId = eventoId; }
    
    public Long getParticipanteId() { return participanteId; }
    public void setParticipanteId(Long participanteId) { this.participanteId = participanteId; }

    public String getDataInscricao() { return dataInscricao; }
    public void setDataInscricao(String dataInscricao) { this.dataInscricao = dataInscricao; }
}

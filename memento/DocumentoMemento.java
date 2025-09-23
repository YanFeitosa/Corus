package memento;

import entidade.Documento;
import java.util.ArrayList;
import java.util.List;

/**
 * Memento que guarda o estado dos documentos em um momento específico.
 * Salva uma cópia dos documentos para poder restaurar depois.
 */
public class DocumentoMemento implements Memento {
    private final List<Documento> documentos;
    private final String description;
    private final long timestamp;
    
    public DocumentoMemento(List<Documento> documentos, String description) {
        // Cria uma cópia dos documentos para não interferir no original
        this.documentos = new ArrayList<>();
        for (Documento d : documentos) {
            this.documentos.add(new Documento(d.getNome(), d.getTamanho(), d.getUsuarioAssociado()));
        }
        this.description = description;
        this.timestamp = System.currentTimeMillis();
    }
    
    /**
     * Retorna uma cópia da lista de documentos salva no memento.
     * @return Lista de documentos do estado salvo
     */
    public List<Documento> getDocumentos() {
        List<Documento> copia = new ArrayList<>();
        for (Documento d : documentos) {
            copia.add(new Documento(d.getNome(), d.getTamanho(), d.getUsuarioAssociado()));
        }
        return copia;
    }
    
    @Override
    public String getDescription() {
        return description + " (" + documentos.size() + " documentos)";
    }
    
    @Override
    public long getTimestamp() {
        return timestamp;
    }
}
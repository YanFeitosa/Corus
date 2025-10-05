package memento;

import entidade.Documento;
import entidade.DocumentoBuilder;

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

            //-------Construção direta-------
            // this.documentos.add(new Documento(d.getNome(), d.getTamanho(), d.getUsuarioAssociado()));
            //-------Construção usando o Builder-------
            this.documentos.add(new DocumentoBuilder()
            .withNome(d.getNome())
            .withTamanho(d.getTamanho())
            .withUsuarioAssociado(d.getUsuarioAssociado())
            .build());

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
            
            //-------Construção direta-------
            // copia.add(new Documento(d.getNome(), d.getTamanho(), d.getUsuarioAssociado()));
            //-------Construção usando o Builder-------
            copia.add(new DocumentoBuilder()
            .withNome(d.getNome())
            .withTamanho(d.getTamanho())
            .withUsuarioAssociado(d.getUsuarioAssociado())
            .build());

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
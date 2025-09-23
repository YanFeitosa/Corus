package memento;

/**
 * Interface base do padrão Memento.
 * Representa um snapshot do estado em um momento específico.
 */
public interface Memento {
    /**
     * Retorna uma descrição do estado salvo.
     * @return String descrevendo o memento
     */
    String getDescription();
    
    /**
     * Retorna quando o memento foi criado.
     * @return timestamp da criação
     */
    long getTimestamp();
}
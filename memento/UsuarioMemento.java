package memento;

import entidade.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 * Memento que guarda o estado dos usuários em um momento específico.
 * Salva uma cópia dos usuários para poder restaurar depois.
 */
public class UsuarioMemento implements Memento {
    private final List<Usuario> usuarios;
    private final String description;
    private final long timestamp;
    
    public UsuarioMemento(List<Usuario> usuarios, String description) {
        // Cria uma cópia dos usuários para não interferir no original
        this.usuarios = new ArrayList<>();
        for (Usuario u : usuarios) {
            this.usuarios.add(new Usuario(u.getLogin(), u.getSenha()));
        }
        this.description = description;
        this.timestamp = System.currentTimeMillis();
    }
    
    /**
     * Retorna uma cópia da lista de usuários salva no memento.
     * @return Lista de usuários do estado salvo
     */
    public List<Usuario> getUsuarios() {
        List<Usuario> copia = new ArrayList<>();
        for (Usuario u : usuarios) {
            copia.add(new Usuario(u.getLogin(), u.getSenha()));
        }
        return copia;
    }
    
    @Override
    public String getDescription() {
        return description + " (" + usuarios.size() + " usuários)";
    }
    
    @Override
    public long getTimestamp() {
        return timestamp;
    }
}
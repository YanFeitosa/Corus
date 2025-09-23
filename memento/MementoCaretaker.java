package memento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Caretaker do padrão Memento.
 * Gerencia os mementos (estados salvos) do sistema.
 */
public class MementoCaretaker {
    private final List<Memento> mementos;
    private final int maxMementos = 10; // Limite para não consumir muita memória
    
    public MementoCaretaker() {
        this.mementos = new ArrayList<>();
    }
    
    /**
     * Salva um memento no histórico.
     * @param memento o estado a ser salvo
     */
    public void salvarMemento(Memento memento) {
        mementos.add(memento);
        
        // Remove mementos antigos se exceder o limite
        if (mementos.size() > maxMementos) {
            mementos.remove(0);
        }
    }
    
    /**
     * Recupera o último memento salvo.
     * @return último memento ou null se não houver
     */
    public Memento getUltimoMemento() {
        if (mementos.isEmpty()) {
            return null;
        }
        return mementos.remove(mementos.size() - 1);
    }
    
    /**
     * Recupera um memento específico pelo índice.
     * @param indice posição do memento (0 = mais antigo)
     * @return memento na posição ou null se inválido
     */
    public Memento getMemento(int indice) {
        if (indice < 0 || indice >= mementos.size()) {
            return null;
        }
        return mementos.get(indice);
    }
    
    /**
     * Retorna o histórico de mementos para exibição.
     * @return lista com descrições dos mementos
     */
    public List<String> getHistorico() {
        List<String> historico = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
        for (int i = mementos.size() - 1; i >= 0; i--) {
            Memento m = mementos.get(i);
            String tempo = sdf.format(new Date(m.getTimestamp()));
            historico.add((mementos.size() - i) + ". [" + tempo + "] " + m.getDescription());
        }
        
        return historico;
    }
    
    /**
     * Verifica se há mementos para restaurar.
     * @return true se há mementos salvos
     */
    public boolean temMementos() {
        return !mementos.isEmpty();
    }
    
    /**
     * Limpa todo o histórico de mementos.
     */
    public void limparHistorico() {
        mementos.clear();
    }
    
    /**
     * Retorna a quantidade de mementos salvos.
     * @return número de mementos
     */
    public int getQuantidadeMementos() {
        return mementos.size();
    }
}
package command;

import utils.ExcecoesRepositorio;
import java.util.Stack;

/**
 * Invoker que gerencia a execução e desfazimento de comandos.
 * Mantém um histórico de comandos para operações de undo.
 */
public class CommandInvoker {
    private final Stack<Command> history = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();
    private final int maxHistorySize = 20;

    /**
     * Executa um comando e adiciona ao histórico
     */
    public void executeCommand(Command command) throws ExcecoesRepositorio {
        command.execute();
        history.push(command);
        redoStack.clear(); // Limpa a pilha de redo quando novo comando é executado
        
        // Limita o tamanho do histórico
        if (history.size() > maxHistorySize) {
            history.remove(0);
        }
    }

    /**
     * Desfaz o último comando executado
     */
    public void undo() throws ExcecoesRepositorio {
        if (!history.isEmpty()) {
            Command command = history.pop();
            command.undo();
            redoStack.push(command);
        }
    }

    /**
     * Refaz o último comando desfeito
     */
    public void redo() throws ExcecoesRepositorio {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            history.push(command);
        }
    }

    /**
     * Retorna o histórico de comandos executados
     */
    public String getHistory() {
        StringBuilder sb = new StringBuilder("Histórico de Comandos:\n");
        for (int i = history.size() - 1; i >= 0; i--) {
            sb.append((history.size() - i)).append(". ").append(history.get(i).getDescription()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Verifica se há comandos para desfazer
     */
    public boolean canUndo() {
        return !history.isEmpty();
    }

    /**
     * Verifica se há comandos para refazer
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    /**
     * Limpa o histórico de comandos
     */
    public void clearHistory() {
        history.clear();
        redoStack.clear();
    }

    /**
     * Retorna a quantidade de comandos no histórico
     */
    public int getHistorySize() {
        return history.size();
    }
}
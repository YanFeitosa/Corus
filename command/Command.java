package command;

import utils.ExcecoesRepositorio;

/**
 * Interface base para todos os comandos do sistema.
 * Especializada para operações da UserUI.
 */
public interface Command {
    void execute() throws ExcecoesRepositorio;
    void undo() throws ExcecoesRepositorio;
    String getDescription();
}
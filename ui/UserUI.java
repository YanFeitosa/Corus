package ui;

import entidade.Documento;
import facade.FacadeCommand;
import java.util.List;
import java.util.Scanner;
import utils.ExcecoesRepositorio;

public class UserUI {
    private FacadeCommand facade;
    private Scanner scanner;
    private String usuarioLogado;

    public UserUI(FacadeCommand facade, Scanner scanner, String usuarioLogado) {
        this.facade = facade;
        this.scanner = scanner;
        this.usuarioLogado = usuarioLogado;
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n===== Menu do Usuário (" + usuarioLogado + ") =====");
            System.out.println("1 - Criar documento");
            System.out.println("2 - Listar meus documentos");
            System.out.println("3 - Editar documento");
            System.out.println("4 - Excluir documento");
            System.out.println("5 - Desfazer (Undo)");
            System.out.println("6 - Refazer (Redo)");
            System.out.println("7 - Histórico de comandos");
            System.out.println("8 - Limpar histórico");
            System.out.println("0 - Logout");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    criarDocumento();
                    break;
                case "2":
                    listarMeusDocumentos();
                    break;
                case "3":
                    editarDocumento();
                    break;
                case "4":
                    excluirDocumento();
                    break;
                case "5":
                    desfazer();
                    break;
                case "6":
                    refazer();
                    break;
                case "7":
                    mostrarHistoricoComandos();
                    break;
                case "8":
                    limparHistorico();
                    break;
                case "0":
                    System.out.println("Logout realizado com sucesso.");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void criarDocumento() {
        System.out.println("\n--- Criar Novo Documento ---");
        System.out.print("Nome do documento: ");
        String nome = scanner.nextLine();
        
        System.out.print("Tamanho: ");
        int tamanho = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        try {
            facade.criarDocumentoCommand(nome, tamanho, usuarioLogado);
            System.out.println("Documento criado com sucesso!");
        } catch (ExcecoesRepositorio e) {
            System.out.println("Erro ao criar documento: " + e.getMessage());
        }
    }

    private void listarMeusDocumentos() {
        try {
            List<Documento> documentos = facade.listarDocumentos();
            List<Documento> meusDocumentos = documentos.stream()
                    .filter(d -> d.getUsuarioAssociado().equals(usuarioLogado))
                    .toList();
            
            System.out.println("\n--- Meus Documentos ---");
            if (meusDocumentos.isEmpty()) {
                System.out.println("Nenhum documento cadastrado.");
            } else {
                System.out.println("+----------------------+-----------+");
                System.out.println("| Nome                 | Tamanho   |");
                System.out.println("+----------------------+-----------+");
                
                for (Documento doc : meusDocumentos) {
                    System.out.printf("| %-20s | %-9d |%n", 
                            doc.getNome(), doc.getTamanho());
                }
                
                System.out.println("+----------------------+-----------+");
                System.out.println("Total de documentos: " + meusDocumentos.size());
            }
        } catch (ExcecoesRepositorio e) {
            System.out.println("Erro ao listar documentos: " + e.getMessage());
        }
    }

    private void editarDocumento() {
        System.out.println("\n--- Editar Documento ---");
        System.out.print("Nome do documento a editar: ");
        String nomeAntigo = scanner.nextLine();
        
        try {
            // Verificar se o documento existe e pertence ao usuário
            Documento documentoParaEditar = facade.buscarDocumentoPorNome(nomeAntigo);
            
            if (documentoParaEditar == null) {
                System.out.println("Documento não encontrado.");
                return;
            }
            
            if (!documentoParaEditar.getUsuarioAssociado().equals(usuarioLogado)) {
                System.out.println("Você não tem permissão para editar este documento.");
                return;
            }
            
            System.out.print("Novo nome (deixe em branco para manter o atual): ");
            String novoNome = scanner.nextLine();
            
            System.out.print("Novo tamanho (digite 0 para manter o atual): ");
            int novoTamanho = Integer.parseInt(scanner.nextLine());
            
            // Usar os valores atuais se não foram informados novos
            String nomeFinal = novoNome.isEmpty() ? documentoParaEditar.getNome() : novoNome;
            int tamanhoFinal = novoTamanho == 0 ? documentoParaEditar.getTamanho() : novoTamanho;
            
            facade.editarDocumentoCommand(nomeAntigo, nomeFinal, tamanhoFinal, usuarioLogado);
            
            System.out.println("Documento editado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao editar documento: " + e.getMessage());
        }
    }

    private void excluirDocumento() {
        System.out.println("\n--- Excluir Documento ---");
        System.out.print("Nome do documento a excluir: ");
        String nome = scanner.nextLine();
        
        try {
            // Verificar se o documento existe e pertence ao usuário
            Documento documento = facade.buscarDocumentoPorNome(nome);
            
            if (documento == null) {
                System.out.println("Documento não encontrado.");
                return;
            }
            
            if (!documento.getUsuarioAssociado().equals(usuarioLogado)) {
                System.out.println("Você não tem permissão para excluir este documento.");
                return;
            }
            
            System.out.print("Tem certeza que deseja excluir o documento? (S/N): ");
            String confirmacao = scanner.nextLine();
            
            if (confirmacao.equalsIgnoreCase("S")) {
                facade.excluirDocumentoCommand(nome);
                System.out.println("Documento excluído com sucesso!");
            } else {
                System.out.println("Operação cancelada.");
            }
        } catch (ExcecoesRepositorio e) {
            System.out.println("Erro ao excluir documento: " + e.getMessage());
        }
    }

    private void desfazer() {
        try {
            if (facade.canUndo()) {
                facade.undo();
                System.out.println("Última operação desfeita com sucesso!");
            } else {
                System.out.println("Nenhuma operação para desfazer.");
            }
        } catch (ExcecoesRepositorio e) {
            System.out.println("Erro ao desfazer operação: " + e.getMessage());
        }
    }

    private void refazer() {
        try {
            if (facade.canRedo()) {
                facade.redo();
                System.out.println("Operação refeita com sucesso!");
            } else {
                System.out.println("Nenhuma operação para refazer.");
            }
        } catch (ExcecoesRepositorio e) {
            System.out.println("Erro ao refazer operação: " + e.getMessage());
        }
    }

    private void mostrarHistoricoComandos() {
        System.out.println("\n--- Histórico de Comandos ---");
        String historico = facade.getCommandHistory();
        if (historico.contains("Histórico de Comandos:\n") && 
            historico.split("\n").length == 1) {
            System.out.println("Nenhum comando executado ainda.");
        } else {
            System.out.println(historico);
            System.out.println("Total de comandos no histórico: " + facade.getCommandHistorySize());
        }
    }

    private void limparHistorico() {
        System.out.print("Tem certeza que deseja limpar o histórico de comandos? (S/N): ");
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.equalsIgnoreCase("S")) {
            facade.clearCommandHistory();
            System.out.println("Histórico de comandos limpo com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}
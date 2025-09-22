package infra;

public class ArquivoRepositorioFactory implements RepositorioFactory {
    @Override
    public UsuarioRepositorio createUsuarioRepositorio() {
        return new PersistenciaArquivoUsu();
    }

    @Override
    public DocumentoRepositorio createDocumentoRepositorio() {
        // Para este projeto, o repositório de documentos é sempre em memória.
        return new PersistenciaMemoriaDoc();
    }
}
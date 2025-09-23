package infra;

public class MemoriaRepositorioFactory implements RepositorioFactory {
    @Override
    public UsuarioRepositorio createUsuarioRepositorio() {
        return new PersistenciaMemoriaUsu();
    }

    @Override
    public DocumentoRepositorio createDocumentoRepositorio() {
        return new PersistenciaMemoriaDoc();
    }
}
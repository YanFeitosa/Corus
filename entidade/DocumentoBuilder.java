package entidade;

public class DocumentoBuilder {
    private String nome;
    private int tamanho;
    private String usuarioAssociado;

    public DocumentoBuilder withNome(String nome) {
        this.nome = nome;
        return this;
    }

    public DocumentoBuilder withTamanho(int tamanho) {
        this.tamanho = tamanho;
        return this;
    }

    public DocumentoBuilder withUsuarioAssociado(String usuarioAssociado) {
        this.usuarioAssociado = usuarioAssociado;
        return this;
    }

    public Documento build() {
        return new Documento(nome, tamanho, usuarioAssociado);
    }
}

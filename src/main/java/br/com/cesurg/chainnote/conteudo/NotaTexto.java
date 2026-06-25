package br.com.cesurg.chainnote.conteudo;

public class NotaTexto implements Conteudo {

    private final String texto;

    public NotaTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String serializarParaHash() {
        return texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}

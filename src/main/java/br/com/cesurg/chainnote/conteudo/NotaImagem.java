package br.com.cesurg.chainnote.conteudo;

public class NotaImagem implements Conteudo {

    private final String caminho;

    public NotaImagem(String caminho) {
        this.caminho = caminho;
    }

    @Override
    public String serializarParaHash() {
        return caminho;
    }

    @Override
    public String toString() {
        return "imagem: " + caminho;
    }
}
